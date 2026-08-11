package com.Equatorial.toukenranbu.entity.touken;

import com.Equatorial.toukenranbu.entity.ai.*;
import com.Equatorial.toukenranbu.item.ModItems;
import com.Equatorial.toukenranbu.screen.ModMenuTypes;
import com.Equatorial.toukenranbu.screen.ToukenDanshiMenu;
import com.Equatorial.toukenranbu.tag.ModItemTags;
import com.Equatorial.toukenranbu.touken.ToukenType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 刀剑男士实体基类
 * 所有具体刀剑男士（如三日月宗近）都继承此类。
 * 功能包括：驯服、跟随、种地、自动回血、御守复活、盔甲/刀装系统、动态属性计算、领地系统、自动索敌攻击......
 * 功能还会持续增加，因为还有很多想做的没有做进去，目前测试还是有点小问题，请大家多担待一下了
 * 有什么想加入的功能可以跟我提，我看看能不能弄进去【努力一下】
 */
public abstract class ToukenDanshiEntity extends TamableAnimal implements GeoEntity {

    // ==================== GeckoLib 动画缓存 ====================
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    // ==================== 三个独立背包系统 ====================
    /**
     * 盔甲栏：4格（头盔、胸甲、护腿、靴子）
     * 内容变化时自动更新实体护甲值和韧性
     */
    protected final ItemStackHandler armorHandler = new ItemStackHandler(4) {
        @Override
        protected void onContentsChanged(int slot) {
            ToukenDanshiEntity.this.updateArmorAttributes();
        }
    };

    /**
     * 刀装栏：3格（金/银/铜刀装）
     * 内容变化时自动更新攻击力和生命值加成
     */
    protected final ItemStackHandler knifeHandler = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            ToukenDanshiEntity.this.updateKnifeBonuses();
        }
    };

    /**
     * 通用背包：25格
     * 存放种子、作物、食物、御守、怪物战利品等
     */
    protected final ItemStackHandler inventoryHandler = new ItemStackHandler(25);

    // ==================== 刀种与基础属性 ====================
    /** 刀种（太刀、大太刀、短刀等），决定昼夜攻击力和索敌范围 */
    protected ToukenType toukenType = ToukenType.TACHI;
    /** 基础攻击力，子类可覆盖 */
    protected double baseAttackDamage = 5.0;
    /** 基础最大生命值，子类可覆盖 */
    protected double baseMaxHealth = 20.0;

    // ==================== 低血量加成 ====================
    /** 低血量阈值（默认40%以下） */
    protected double lowHealthThreshold = 0.4;
    /** 低血量时的伤害加成倍率（默认+40%） */
    protected double lowHealthDamageBonus = 0.4;

    // ==================== 刀装加成 ====================
    /** 刀装提供的攻击力加成倍率（如金刀装+0.7） */
    protected double knifeDamageBonus = 0.0;
    /** 刀装提供的额外生命值 */
    protected double knifeHealthBonus = 0.0;

    // ==================== 种地系统标记 ====================
    /** 标记是否需要补充种子（种地AI使用） */
    private boolean needsSeedRefill = false;

    // ==================== 目标锁定系统（防止多个实体种同一格）====================
    /** 全局静态Map：维度 -> (格子坐标 -> 占用者UUID) */
    private static final Map<ResourceKey<Level>, Map<BlockPos, UUID>> FARM_TARGET_LOCKS = new HashMap<>();
    /** 当前实体锁定的种地目标 */
    private BlockPos lockedFarmTarget = null;
    /** 目标锁定计时器，超时自动释放 */
    private int farmLockTimer = 0;

    // ==================== 栅栏领地系统 ====================
    /** 全局静态Map：维度 -> (实体UUID -> 领地坐标集合) */
    private static final Map<ResourceKey<Level>, Map<UUID, Set<BlockPos>>> FARM_TERRITORIES = new ConcurrentHashMap<>();
    /** 当前实体拥有的耕地领地 */
    private Set<BlockPos> myFarmlandTerritory = ConcurrentHashMap.newKeySet();
    /** 领地重新计算冷却 */
    private int territoryRecalcCooldown = 0;
    /** 上次计算领地时的位置，移动过远才重算 */
    private BlockPos lastTerritoryPos = BlockPos.ZERO;

    // ==================== 种地AI引用 ====================
    /** 用于状态切换时立刻唤醒/重置种地AI */
    private ToukenFarmingGoal farmingGoal;

    // ==================== 跨维度跟随追踪 ====================
    /** 全局静态Map：主人UUID -> 该主人的所有活跃刀剑男士 */
    private static final Map<UUID, Set<ToukenDanshiEntity>> OWNED_DANSHI = new ConcurrentHashMap<>();

    // ==================== 网络同步数据 ====================
    /** 是否跟随主人（客户端需要知道以显示按钮状态） */
    private static final EntityDataAccessor<Boolean> DATA_FOLLOWING =
            SynchedEntityData.defineId(ToukenDanshiEntity.class, EntityDataSerializers.BOOLEAN);
    /** 是否种地模式 */
    private static final EntityDataAccessor<Boolean> DATA_FARMING =
            SynchedEntityData.defineId(ToukenDanshiEntity.class, EntityDataSerializers.BOOLEAN);

    // ==================== 自动回血系统字段 ====================
    /** 自动回血冷却计时器 */
    private int autoHealCooldown = 0;
    /** 自动回血检测间隔：每20tick（1秒）检测一次 */
    private static final int AUTO_HEAL_INTERVAL = 20;
    /** 使用一次回血物品后的冷却：60tick（3秒） */
    private static final int AUTO_HEAL_COOLDOWN = 60;

    // ==================== 构造函数 ====================
    protected ToukenDanshiEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
    }

    /**
     * 注册需要在服务端和客户端之间同步的数据
     */
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_FOLLOWING, false);
        this.entityData.define(DATA_FARMING, false);
    }

    /**
     * 注册AI目标（优先级数字越小越优先）
     */
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));              // 游泳
        this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));   // 坐下
        this.goalSelector.addGoal(3, new PickupItemsGoal(this));           // 拾取掉落物
        this.goalSelector.addGoal(4, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false) {
            @Override
            public boolean canUse() {
                return ToukenDanshiEntity.this.isFollowing() && super.canUse();
            }
            @Override
            public boolean canContinueToUse() {
                return ToukenDanshiEntity.this.isFollowing() && super.canContinueToUse();
            }
        });
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.2D, true)); // 近战攻击
        this.goalSelector.addGoal(6, new ToukenDepositGoal(this));      // 存入箱子
        this.farmingGoal = new ToukenFarmingGoal(this);
        this.goalSelector.addGoal(7, this.farmingGoal);                 // 种地
        this.goalSelector.addGoal(8, new RandomStrollGoal(this, 1.0D)); // 随机闲逛
        this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));   // 随机张望

        // 攻击目标选择器
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));  // 主人被攻击时反击
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));    // 主人攻击谁就打谁
        this.targetSelector.addGoal(3, new ToukenHurtByTargetGoal(this)); // 自己被攻击时反击

        // ===== 自动索敌攻击：非坐下、非种地时，主动寻找索敌范围内的敌对生物 =====
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<Mob>(this, Mob.class, 10, true, false,
                target -> {
                    if (target == this) return false;
                    if (target instanceof Player) return false;                    // 不攻击玩家
                    if (this.isOwnedBy(target)) return false;                      // 不攻击主人
                    if (target instanceof TamableAnimal t && t.isTame()) return false; // 不攻击驯服生物
                    if (target instanceof OwnableEntity o && this.getOwner() != null
                            && this.getOwner().equals(o.getOwner())) return false;   // 不攻击同主人的宠物
                    return target instanceof Monster;                               // 只攻击敌对生物（Monster接口）
                }) {
            @Override
            public boolean canUse() {
                if (ToukenDanshiEntity.this.isOrderedToSit()) return false;
                if (ToukenDanshiEntity.this.isFarming()) return false;
                return super.canUse();
            }
            @Override
            public boolean canContinueToUse() {
                if (ToukenDanshiEntity.this.isOrderedToSit()) return false;
                if (ToukenDanshiEntity.this.isFarming()) return false;
                return super.canContinueToUse();
            }
        });
    }

    // ==================== 跨维度跟随追踪访问器 ====================
    public static Map<UUID, Set<ToukenDanshiEntity>> getOwnedDanshi() {
        return OWNED_DANSHI;
    }

    // ==================== 种子补充标记 ====================
    public boolean needsSeedRefill() {
        return needsSeedRefill;
    }

    public void setNeedsSeedRefill(boolean needs) {
        this.needsSeedRefill = needs;
    }

    // ==================== 目标锁定系统 ====================

    /**
     * 尝试占用一个种地目标格子
     * @return true 占用成功，false 已被其他实体占用
     */
    public boolean claimFarmTarget(BlockPos pos) {
        if (pos == null) return false;
        var levelLocks = FARM_TARGET_LOCKS.computeIfAbsent(
                this.level().dimension(), k -> new HashMap<>()
        );
        UUID existing = levelLocks.get(pos);
        if (existing != null && !existing.equals(this.getUUID())) return false;
        releaseFarmTarget();
        levelLocks.put(pos, this.getUUID());
        lockedFarmTarget = pos;
        farmLockTimer = 60;
        return true;
    }

    /** 释放当前占用的种地目标 */
    public void releaseFarmTarget() {
        if (lockedFarmTarget != null) {
            var levelLocks = FARM_TARGET_LOCKS.get(this.level().dimension());
            if (levelLocks != null) {
                levelLocks.remove(lockedFarmTarget, this.getUUID());
            }
            lockedFarmTarget = null;
        }
    }

    /** 静态方法：判断某格子是否被其他实体锁定 */
    public static boolean isFarmTargetLockedByOther(Level level, BlockPos pos, UUID self) {
        var levelLocks = FARM_TARGET_LOCKS.get(level.dimension());
        if (levelLocks == null) return false;
        UUID owner = levelLocks.get(pos);
        return owner != null && !owner.equals(self);
    }

    // ==================== 栅栏领地系统 ====================

    public Set<BlockPos> getMyFarmlandTerritory() {
        return myFarmlandTerritory;
    }

    /**
     * 重新计算耕地领地（BFS搜索）
     * 从最近的耕地开始，被栅栏/墙/关着的门围起来的区域算一块领地
     */
    public void recalcFarmlandTerritory() {
        if (this.level().isClientSide) return;
        if (territoryRecalcCooldown-- > 0 && !myFarmlandTerritory.isEmpty()) return;
        territoryRecalcCooldown = 100;

        if (this.blockPosition().distSqr(lastTerritoryPos) < 16 && !myFarmlandTerritory.isEmpty()) return;
        lastTerritoryPos = this.blockPosition();

        ServerLevel level = (ServerLevel) this.level();

        // 先找最近的耕地作为起点，而不是盲目从脚下开始
        BlockPos start = findNearestFarmland(level, this.blockPosition(), 8);
        if (start == null) {
            releaseFarmlandTerritory(); // 周围真的没有耕地，清空
            return;
        }

        Set<BlockPos> newTerritory = new HashSet<>();
        Queue<BlockPos> queue = new LinkedList<>();
        Set<BlockPos> visited = new HashSet<>();
        int maxSize = 300;

        queue.add(start);

        while (!queue.isEmpty() && newTerritory.size() < maxSize) {
            BlockPos current = queue.poll();
            if (!visited.add(current)) continue;

            BlockState state = level.getBlockState(current);
            if (!isValidGround(state)) continue;

            if (state.getBlock() instanceof FarmBlock) {
                newTerritory.add(current);
            }

            BlockState aboveState = level.getBlockState(current.above());
            if ((state.is(Blocks.SAND) || state.is(Blocks.DIRT) || state.is(Blocks.GRASS_BLOCK))
                    && aboveState.getBlock() instanceof SugarCaneBlock) {
                newTerritory.add(current);
            }

            for (Direction dir : Direction.Plane.HORIZONTAL) {
                BlockPos neighbor = current.relative(dir);
                if (visited.contains(neighbor)) continue;

                BlockState neighborState = level.getBlockState(neighbor);
                BlockState neighborAbove = level.getBlockState(neighbor.above());

                // 修复：开着的门/栅栏门不算墙，允许BFS穿过
                if (isClosedBarrier(neighborState) || isClosedBarrier(neighborAbove)) continue;

                if (isValidGround(neighborState)) {
                    queue.add(neighbor);
                }
            }
        }

        releaseFarmlandTerritory();
        this.myFarmlandTerritory = ConcurrentHashMap.newKeySet();
        this.myFarmlandTerritory.addAll(newTerritory);

        var dimMap = FARM_TERRITORIES.computeIfAbsent(level.dimension(), k -> new ConcurrentHashMap<>());
        dimMap.put(this.getUUID(), this.myFarmlandTerritory);
    }

    /** 螺旋搜索最近的耕地 */
    private BlockPos findNearestFarmland(ServerLevel level, BlockPos center, int radius) {
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        for (int r = 0; r <= radius; r++) {
            for (int dx = -r; dx <= r; dx++) {
                for (int dz = -r; dz <= r; dz++) {
                    if (Math.abs(dx) != r && Math.abs(dz) != r) continue;
                    for (int dy = -2; dy <= 2; dy++) {
                        mutable.set(center.getX() + dx, center.getY() + dy, center.getZ() + dz);
                        BlockState state = level.getBlockState(mutable);
                        if (state.getBlock() instanceof FarmBlock) {
                            return mutable.immutable();
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * 判断是否是"关闭的"障碍物
     * 开着的门/栅栏门允许BFS穿过
     * 同时检查 BlockTags 和 instanceof，兼容自定义栅栏
     */
    private boolean isClosedBarrier(BlockState state) {
        Block block = state.getBlock();
        if (block instanceof FenceGateBlock) return !state.getValue(FenceGateBlock.OPEN);
        if (block instanceof DoorBlock) return !state.getValue(DoorBlock.OPEN);
        return state.is(BlockTags.FENCES)
                || state.is(BlockTags.WALLS)
                || state.is(BlockTags.FENCE_GATES)
                || block instanceof FenceBlock
                || block instanceof WallBlock
                || block instanceof IronBarsBlock;
    }

    /** 判断是否是BFS可行走的地表 */
    private boolean isValidGround(BlockState state) {
        return state.getBlock() instanceof FarmBlock
                || state.is(Blocks.DIRT)
                || state.is(Blocks.GRASS_BLOCK)
                || state.is(Blocks.SAND)
                || state.is(Blocks.COARSE_DIRT)
                || state.is(Blocks.PODZOL)
                || state.is(Blocks.MYCELIUM);
    }

    /** 释放当前领地 */
    public void releaseFarmlandTerritory() {
        var dimMap = FARM_TERRITORIES.get(this.level().dimension());
        if (dimMap != null) {
            dimMap.remove(this.getUUID());
        }
        myFarmlandTerritory.clear();
    }

    /** 判断某坐标是否在其他实体的领地内 */
    public static boolean isPosInOtherTerritory(Level level, BlockPos pos, UUID self) {
        var dimMap = FARM_TERRITORIES.get(level.dimension());
        if (dimMap == null) return false;
        for (var entry : dimMap.entrySet()) {
            if (!entry.getKey().equals(self) && entry.getValue().contains(pos)) {
                return true;
            }
        }
        return false;
    }

    // ==================== tick()：动态属性 + 自动回血 + 目标锁维护 ====================
    @Override
    public void tick() {
        super.tick();

        // ===== 跨维度/超远距离跟随主人（每1秒检查一次） =====
        if (!this.level().isClientSide
                && this.isFollowing()
                && !this.isOrderedToSit()
                && this.tickCount % 5 == 0) {

            UUID ownerUUID = this.getOwnerUUID();
            if (ownerUUID != null && this.level() instanceof ServerLevel currentLevel) {
                MinecraftServer server = currentLevel.getServer();
                if (server != null) {
                    ServerPlayer owner = server.getPlayerList().getPlayer(ownerUUID);
                    if (owner != null) {
                        // 情况1：主人跨维度了（下界/末地/自定义维度）
                        if (owner.level().dimension() != this.level().dimension()) {
                            Entity newEntity = this.changeDimension((ServerLevel) owner.level());
                            if (newEntity != null) {
                                Vec3 safePos = findSafePosNear((ServerLevel) owner.level(), owner.getX(), owner.getY(), owner.getZ());
                                newEntity.teleportTo(safePos.x, safePos.y, safePos.z);
                            }
                        }
                        // 情况2：同维度但距离超过64格
                        else if (this.distanceToSqr(owner) > 4096.0D) {
                            Vec3 safePos = findSafePosNear((ServerLevel) owner.level(), owner.getX(), owner.getY(), owner.getZ());
                            this.teleportTo(safePos.x, safePos.y, safePos.z);
                        }
                    }
                }
            }
        }

        if (!this.level().isClientSide && this.tickCount % 5 == 0) {
            // === 攻击力动态计算 ===
            double shouldBe;

            if (this.level().isNight()) {
                shouldBe = switch (toukenType) {
                    case TACHI -> baseAttackDamage * 0.6;
                    case TANTOU -> baseAttackDamage * 2;
                    case NAGINATA -> baseAttackDamage * 0.5;
                    case WAKIZASHI -> baseAttackDamage * 1.5;
                    case UCHIGATANA -> baseAttackDamage * 1.2;
                    case OOTACHI -> baseAttackDamage * 0.4;
                    case YARI -> baseAttackDamage * 0.8;
                    default -> baseAttackDamage;
                };
            } else {
                shouldBe = baseAttackDamage;
            }

            double healthRatio = this.getHealth() / this.getMaxHealth();
            if (healthRatio <= lowHealthThreshold) {
                shouldBe = shouldBe * (1.0 + lowHealthDamageBonus);
            }

            shouldBe = shouldBe * (1.0 + knifeDamageBonus);

            var attr = this.getAttribute(Attributes.ATTACK_DAMAGE);
            if (attr != null && attr.getBaseValue() != shouldBe) {
                attr.setBaseValue(shouldBe);
            }

            // === 最大生命值动态计算（刀装加成，不随昼夜变动）===
            double shouldHealth = baseMaxHealth + knifeHealthBonus;
            var healthAttr = this.getAttribute(Attributes.MAX_HEALTH);
            if (healthAttr != null && healthAttr.getBaseValue() != shouldHealth) {
                double oldMax = this.getMaxHealth();
                healthAttr.setBaseValue(shouldHealth);
                double newMax = this.getMaxHealth();
                if (newMax > oldMax) {
                    this.setHealth((float)(this.getHealth() + (newMax - oldMax)));
                }
            }

            // === 索敌范围动态同步（让自动攻击AI使用正确的范围）===
            var followRangeAttr = this.getAttribute(Attributes.FOLLOW_RANGE);
            if (followRangeAttr != null) {
                double shouldRange = getFollowRange(this.level().isNight());
                if (followRangeAttr.getBaseValue() != shouldRange) {
                    followRangeAttr.setBaseValue(shouldRange);
                }
            }
        }

        // 维护种地目标锁定
        if (lockedFarmTarget != null) {
            if (--farmLockTimer <= 0 || !this.isFarming() || this.isOrderedToSit() || this.isDeadOrDying()) {
                releaseFarmTarget();
            }
        }

        // ===== 自动回血检测（每秒执行一次）=====
        if (!this.level().isClientSide && this.tickCount % AUTO_HEAL_INTERVAL == 0) {
            tickAutoHeal();
        }
    }

    /**
     * 实体被移除（死亡/卸载）时清理资源
     */
    @Override
    public void remove(RemovalReason reason) {
        releaseFarmTarget();
        releaseFarmlandTerritory();
        // 从跨维度追踪中移除
        if (!this.level().isClientSide && this.getOwnerUUID() != null) {
            Set<ToukenDanshiEntity> set = OWNED_DANSHI.get(this.getOwnerUUID());
            if (set != null) {
                set.remove(this);
                if (set.isEmpty()) OWNED_DANSHI.remove(this.getOwnerUUID());
            }
        }
        super.remove(reason);
    }

    // ==================== 生命周期：加入/离开世界时维护跨维度追踪 ====================
    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        if (!this.level().isClientSide && this.isTame() && this.getOwnerUUID() != null) {
            OWNED_DANSHI.computeIfAbsent(this.getOwnerUUID(), k -> ConcurrentHashMap.newKeySet()).add(this);
        }
    }

    @Override
    public void onRemovedFromWorld() {
        super.onRemovedFromWorld();
        if (!this.level().isClientSide && this.getOwnerUUID() != null) {
            Set<ToukenDanshiEntity> set = OWNED_DANSHI.get(this.getOwnerUUID());
            if (set != null) {
                set.remove(this);
                if (set.isEmpty()) OWNED_DANSHI.remove(this.getOwnerUUID());
            }
        }
    }

    // ==================== 盔甲属性应用 ====================

    /**
     * 根据盔甲栏内容更新实体护甲值和韧性
     */
    private void updateArmorAttributes() {
        double totalArmor = 0.0;
        double totalToughness = 0.0;

        for (int i = 0; i < armorHandler.getSlots(); i++) {
            ItemStack stack = armorHandler.getStackInSlot(i);
            if (stack.getItem() instanceof ArmorItem armor) {
                totalArmor += armor.getDefense();
                totalToughness += armor.getToughness();
            }
        }

        var armorAttr = this.getAttribute(Attributes.ARMOR);
        if (armorAttr != null) armorAttr.setBaseValue(totalArmor);

        var toughAttr = this.getAttribute(Attributes.ARMOR_TOUGHNESS);
        if (toughAttr != null) toughAttr.setBaseValue(totalToughness);
    }

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        List<ItemStack> list = new ArrayList<>();
        for (int i = 0; i < armorHandler.getSlots(); i++) {
            list.add(armorHandler.getStackInSlot(i));
        }
        return list;
    }

    // ==================== 刀装加成计算 ====================

    /**
     * 根据刀装栏内容更新攻击力和生命值加成
     */
    public void updateKnifeBonuses() {
        double bonus = 0.0;
        double healthBonus = 0.0;
        for (int i = 0; i < knifeHandler.getSlots(); i++) {
            ItemStack stack = knifeHandler.getStackInSlot(i);
            if (stack.isEmpty()) continue;
            if (stack.is(ModItemTags.GOLD_KNIFE)) {
                bonus += 0.7;
                healthBonus += 16.0;
            } else if (stack.is(ModItemTags.SILVER_KNIFE)) {
                bonus += 0.4;
                healthBonus += 8.0;
            } else if (stack.is(ModItemTags.COPPER_KNIFE)) {
                bonus += 0.15;
                healthBonus += 4.0;
            }
        }
        this.knifeDamageBonus = bonus;
        this.knifeHealthBonus = healthBonus;
    }

    // ==================== 状态管理（跟随/种地/坐下 互斥）====================

    public boolean isFollowing() {
        return this.entityData.get(DATA_FOLLOWING);
    }

    public void setFollowing(boolean following) {
        this.entityData.set(DATA_FOLLOWING, following);
        if (following) {
            this.setOrderedToSit(false);
            this.setFarming(false);
        } else {
            if (!this.level().isClientSide) {
                this.navigation.stop();
            }
        }
    }

    public boolean isFarming() {
        return this.entityData.get(DATA_FARMING);
    }

    /**
     * 判断种地AI是否处于逃番状态
     */
    public boolean isFarmingEscaping() {
        return this.farmingGoal != null && this.farmingGoal.isEscaping();
    }

    public void setFarming(boolean farming) {
        this.entityData.set(DATA_FARMING, farming);
        if (farming) {
            this.setOrderedToSit(false);
            this.setFollowing(false);
            this.recalcFarmlandTerritory();
            // 重置逃番计数器，立刻复工
            if (this.farmingGoal != null) {
                this.farmingGoal.resetEscape();
            }
        } else {
            this.releaseFarmlandTerritory();
            if (!this.level().isClientSide) {
                this.navigation.stop();
            }
        }
    }

    @Override
    public void setOrderedToSit(boolean sitting) {
        super.setOrderedToSit(sitting);
        if (sitting) {
            this.setFollowing(false);
            this.setFarming(false);
            if (!this.level().isClientSide) {
                this.navigation.stop();
            }
        }
    }

    // ==================== 刀种 + 攻击力 + 索敌范围 ====================

    public ToukenType getToukenType() {
        return toukenType;
    }

    public void setToukenType(ToukenType type) {
        this.toukenType = type;
    }

    public double getBaseAttackDamage() {
        return baseAttackDamage;
    }

    /**
     * 计算最终攻击力（供外部查询，与tick中逻辑一致）
     */
    public double getAttackDamage() {
        double base = getBaseAttackDamage();

        if (this.level().isNight()) {
            base = switch (toukenType) {
                case TACHI -> base * 0.6;
                case TANTOU -> base * 2;
                case NAGINATA -> base * 0.5;
                case WAKIZASHI -> base * 1.5;
                case UCHIGATANA -> base * 1.2;
                case OOTACHI -> base * 0.4;
                case YARI -> base * 0.8;
                default -> base;
            };
        }

        double healthRatio = this.getHealth() / this.getMaxHealth();
        if (healthRatio <= lowHealthThreshold) {
            base = base * (1.0 + lowHealthDamageBonus);
        }

        base = base * (1.0 + knifeDamageBonus);

        return base;
    }

    /**
     * 获取索敌范围
     */
    public double getFollowRange(boolean isNight) {
        double base = toukenType.baseFollowRange;
        if (isNight) {
            return switch (toukenType) {
                case TACHI -> base * 0.6;
                case TANTOU -> base * 2;
                case NAGINATA -> base * 0.5;
                case WAKIZASHI -> base * 1.5;
                case UCHIGATANA -> base * 1.2;
                case OOTACHI -> base * 0.4;
                case YARI -> base * 0.8;
                default -> base;
            };
        }
        return base;
    }

    // ==================== 动态语言键系统 ====================

    /**
     * 子类必须提供实体名称key（用于语言文件）
     * 例如子类"MikazukiMunechikaEntity"返回"mikazuki_munechika"
     */
    protected abstract String getEntityNameKey();

    @Override
    public Component getName() {
        return Component.translatable(getEntityDisplayNameKey());
    }

    /**
     * 获取实体显示名称的翻译键
     * 格式：entity.toukenranbu.touken_danshi.{entityNameKey}
     */
    public String getEntityDisplayNameKey() {
        return "entity.toukenranbu.touken_danshi." + getEntityNameKey();
    }

    /**
     * 获取GUI标题的翻译键（带实体名字）
     * 格式：gui.toukenranbu.touken_danshi.title.{entityNameKey}
     */
    public String getGuiTitleKey() {
        return "gui.toukenranbu.touken_danshi.title." + getEntityNameKey();
    }

    /**
     * 获取死亡提示的翻译键
     * 子类覆盖返回自己的键，如 "death.toukenranbu_mod.mikazuki_munechika"
     * 基类默认返回 null → 不显示任何死亡提示
     */
    protected String getDeathMessageKey() {
        return null;
    }

    // ==================== 御守系统（免死/复活）====================

    @Override
    public void die(DamageSource source) {
        if (!this.level().isClientSide && tryUseAmulet(source)) {
            return;
        }
        // 御守没触发，真要死了，发送死亡提示
        if (!this.level().isClientSide && this.getOwner() instanceof Player player) {
            String deathKey = this.getDeathMessageKey();
            if (deathKey != null) {
                player.displayClientMessage(Component.translatable(deathKey), false);
            }
        }

        super.die(source);
    }

    /**
     * 尝试使用御守复活
     * @return true 成功使用御守，取消死亡
     */
    private boolean tryUseAmulet(DamageSource source) {
        for (int i = 0; i < this.inventoryHandler.getSlots(); i++) {
            ItemStack stack = this.inventoryHandler.getStackInSlot(i);
            if (isAmulet(stack)) {
                stack.shrink(1);
                applyAmuletEffects(source, stack);
                return true;
            }
        }
        return false;
    }

    @Override
    protected void dropEquipment() {
        super.dropEquipment();

        // 掉盔甲栏
        for (int i = 0; i < armorHandler.getSlots(); i++) {
            ItemStack stack = armorHandler.getStackInSlot(i);
            if (!stack.isEmpty()) {
                this.spawnAtLocation(stack);
                armorHandler.setStackInSlot(i, ItemStack.EMPTY);
            }
        }

        // 掉刀装栏
        for (int i = 0; i < knifeHandler.getSlots(); i++) {
            ItemStack stack = knifeHandler.getStackInSlot(i);
            if (!stack.isEmpty()) {
                this.spawnAtLocation(stack);
                knifeHandler.setStackInSlot(i, ItemStack.EMPTY);
            }
        }

        // 掉背包（25格）
        for (int i = 0; i < inventoryHandler.getSlots(); i++) {
            ItemStack stack = inventoryHandler.getStackInSlot(i);
            if (!stack.isEmpty()) {
                this.spawnAtLocation(stack);
                inventoryHandler.setStackInSlot(i, ItemStack.EMPTY);
            }
        }
        this.spawnAtLocation(new ItemStack(ModItems.DAMAGED_SWORD_FRAGMENTS.get()));
    }

    /**
     * 应用御守效果
     * 普通御守回1滴血；极上御守回满血+抗性+再生
     */
    private void applyAmuletEffects(DamageSource source, ItemStack amuletStack) {
        this.clearFire();
        this.removeAllEffects();

        if (amuletStack.is(ModItems.SUPREME_AMULET.get())) {
            this.setHealth(this.getMaxHealth());
            this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 1));
            this.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 1));
        } else {
            this.setHealth(1.0F);
        }

        this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.TOTEM_USE, this.getSoundSource(), 1.0F, 1.0F);

        if (this.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                    ParticleTypes.TOTEM_OF_UNDYING,
                    this.getX(), this.getY() + this.getBbHeight() / 2.0, this.getZ(),
                    100, 0.5, 0.5, 0.5, 0.3
            );
        }
    }

    public static boolean isAmulet(ItemStack stack) {
        if (stack.isEmpty()) return false;
        return stack.is(ModItems.AMULET.get()) || stack.is(ModItems.SUPREME_AMULET.get());
    }

    // ==================== 作物白名单 ====================

    /**
     * 判断物品是否是种地AI需要收集的作物
     */
    public static boolean isCropProduce(ItemStack stack) {
        if (stack.isEmpty()) return false;
        return stack.is(Items.WHEAT) ||
                stack.is(Items.CARROT) ||
                stack.is(Items.POTATO) ||
                stack.is(Items.BEETROOT) ||
                stack.is(Items.MELON_SLICE) ||
                stack.is(Items.PUMPKIN) ||
                stack.is(Items.CACTUS) ||
                stack.is(Items.SUGAR_CANE) ||
                stack.is(Items.BAMBOO) ||
                stack.is(Items.NETHER_WART) ||
                stack.is(Items.COCOA_BEANS) ||
                stack.is(Items.SWEET_BERRIES) ||
                stack.is(Items.GLOW_BERRIES);
    }

    /**
     * 查询背包里是否有种子
     */
    public boolean hasSeeds() {
        for (int i = 0; i < inventoryHandler.getSlots(); i++) {
            ItemStack stack = inventoryHandler.getStackInSlot(i);
            if (ToukenFarmingGoal.isSeed(stack)) {
                return true;
            }
        }
        return false;
    }

    // ==================== Handler 访问器 ====================

    public net.minecraftforge.items.IItemHandler getArmorHandler() {
        return armorHandler;
    }

    public net.minecraftforge.items.IItemHandler getKnifeHandler() {
        return knifeHandler;
    }

    public net.minecraftforge.items.IItemHandler getInventoryHandler() {
        return inventoryHandler;
    }

    // ==================== 交互（右键）====================

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (this.isOwnedBy(player)) {
            // 手动回血物品（右键喂）
            if (itemStack.is(ModItems.WOOTZ_STEEL.get()) ||
                    itemStack.is(ModItems.WHETSTONE.get()) ||
                    itemStack.is(Items.CHARCOAL) ||
                    itemStack.is(ModItems.COOLANT.get())) {

                if (this.getHealth() < this.getMaxHealth()) {
                    this.heal(4.0F);
                    if (!player.isCreative()) itemStack.shrink(1);
                    this.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
                    return InteractionResult.SUCCESS;
                }
            }

            // 空手打开GUI
            if (itemStack.isEmpty()) {
                if (!this.level().isClientSide && player instanceof ServerPlayer serverPlayer) {
                    NetworkHooks.openScreen(serverPlayer, new SimpleMenuProvider(
                            (id, inv, p) -> new ToukenDanshiMenu(ModMenuTypes.TOUKEN_DANSHI_MENU.get(), id, inv, this),
                            Component.translatable(this.getGuiTitleKey())  // 使用带实体名字的标题
                    ), buf -> buf.writeInt(this.getId()));
                }
                return InteractionResult.SUCCESS;
            }
        }
        return super.mobInteract(player, hand);
    }

    // ==================== 自动回血系统 ====================

    /**
     * 主入口：每秒钟调用一次，寻找最佳回血物品并使用
     */
    protected void tickAutoHeal() {
        if (autoHealCooldown > 0) {
            autoHealCooldown--;
            return;
        }
        if (this.getHealth() >= this.getMaxHealth()) return;
        if (this.isDeadOrDying()) return;

        int bestSlot = -1;
        float bestHeal = 0.0F;
        boolean bestHasEffects = false;

        for (int i = 0; i < inventoryHandler.getSlots(); i++) {
            ItemStack stack = inventoryHandler.getStackInSlot(i);
            if (stack.isEmpty()) continue;

            HealInfo info = evaluateHealItem(stack);
            if (info.heal <= 0) continue;

            // 选择逻辑：优先回复量高的；回复量相同时，优先带有效果（如金苹果）的
            boolean better = false;
            if (info.heal > bestHeal) {
                better = true;
            } else if (info.heal == bestHeal && info.hasEffects && !bestHasEffects) {
                better = true;
            }

            if (better) {
                bestHeal = info.heal;
                bestSlot = i;
                bestHasEffects = info.hasEffects;
            }
        }

        if (bestSlot >= 0) {
            ItemStack stack = inventoryHandler.getStackInSlot(bestSlot);
            consumeHealItem(stack, bestSlot);
            autoHealCooldown = AUTO_HEAL_COOLDOWN;
        }
    }

    /**
     * 评估单个物品的回血价值
     * @return 该物品能回多少血，以及是否带有效额外效果
     */
    protected HealInfo evaluateHealItem(ItemStack stack) {
        // 1. 模组特殊回血物品（与右键交互保持一致）
        if (isModHealItem(stack)) {
            return new HealInfo(4.0F, false);
        }

        // 2. 原版 / 模组食物（只要注册了 FoodProperties 的都能识别）
        FoodProperties food = stack.getFoodProperties(this);
        if (food != null) {
            boolean hasEffects = !food.getEffects().isEmpty();
            // nutrition 值通常等于回复的"心数"（1 nutrition = 0.5 心 = 1 HP）
            float heal = (float) food.getNutrition();
            return new HealInfo(heal, hasEffects);
        }

        // 3. 【扩展点】如果你有特殊的模组物品没有 FoodProperties，在这里加判断
        // else if (stack.is(ModItems.SOME_SPECIAL_FOOD.get())) {
        //     return new HealInfo(6.0F, true);
        // }

        return new HealInfo(0.0F, false);
    }

    /**
     * 判断是否是模组里那4种手动回血物品
     */
    protected boolean isModHealItem(ItemStack stack) {
        return stack.is(ModItems.WOOTZ_STEEL.get()) ||
                stack.is(ModItems.WHETSTONE.get()) ||
                stack.is(Items.CHARCOAL) ||
                stack.is(ModItems.COOLANT.get());
    }

    /**
     * 消耗（吃掉/使用）指定格子的回血物品，并实际回血、应用效果
     */
    protected void consumeHealItem(ItemStack stack, int slot) {
        // ----- 模组特殊物品 -----
        if (isModHealItem(stack)) {
            this.heal(4.0F);
            stack.shrink(1);
            if (stack.isEmpty()) {
                inventoryHandler.setStackInSlot(slot, ItemStack.EMPTY);
            }
            this.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
            return;
        }

        // ----- 食物类（原版 + 模组食物）-----
        FoodProperties food = stack.getFoodProperties(this);
        if (food != null) {
            // 1. 回复生命值（nutrition 值）
            this.heal((float) food.getNutrition());

            // 2. 应用食物自带的所有效果（金苹果的吸收、生命恢复；附魔金苹果的抗性、防火等）
            for (var pair : food.getEffects()) {
                MobEffectInstance effectInstance = pair.getFirst();
                float probability = pair.getSecond();
                // 按概率触发（金苹果概率是 1.0，即必定触发）
                if (this.random.nextFloat() < probability) {
                    // 复制一份新的效果，避免修改原模板
                    this.addEffect(new MobEffectInstance(effectInstance));
                }
            }

            // 3. 消耗物品
            stack.shrink(1);
            if (stack.isEmpty()) {
                inventoryHandler.setStackInSlot(slot, ItemStack.EMPTY);
            }

            // 4. 播放吃东西音效
            this.playSound(
                    stack.getItem().getEatingSound(),
                    0.5F + 0.5F * this.random.nextInt(2),
                    (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F
            );
        }
    }

    /**
     * 内部结构：记录一个物品的回血信息
     */
    protected static class HealInfo {
        public final float heal;
        public final boolean hasEffects;

        public HealInfo(float heal, boolean hasEffects) {
            this.heal = heal;
            this.hasEffects = hasEffects;
        }
    }

    // ==================== NBT 持久化 ====================

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("Following", this.isFollowing());
        tag.putBoolean("Farming", this.isFarming());
        tag.putBoolean("Sitting", this.isOrderedToSit());
        tag.put("ArmorItems", armorHandler.serializeNBT());
        tag.put("KnifeItems", knifeHandler.serializeNBT());
        tag.put("Inventory", inventoryHandler.serializeNBT());
        tag.putString("ToukenType", this.toukenType.name());
        tag.putDouble("BaseAttackDamage", this.baseAttackDamage);
        tag.putDouble("BaseMaxHealth", this.baseMaxHealth);

        // 领地持久化
        ListTag territoryList = new ListTag();
        for (BlockPos pos : myFarmlandTerritory) {
            CompoundTag posTag = new CompoundTag();
            posTag.putInt("x", pos.getX());
            posTag.putInt("y", pos.getY());
            posTag.putInt("z", pos.getZ());
            territoryList.add(posTag);
        }
        tag.put("FarmlandTerritory", territoryList);
        tag.putLong("LastTerritoryPos", lastTerritoryPos.asLong());
        tag.putInt("TerritoryRecalcCooldown", territoryRecalcCooldown);
        tag.putBoolean("NeedsSeedRefill", needsSeedRefill);

        // 自动回血冷却持久化
        tag.putInt("AutoHealCooldown", autoHealCooldown);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setFollowing(tag.getBoolean("Following"));
        this.setFarming(tag.getBoolean("Farming"));
        if (tag.contains("Sitting")) {
            super.setOrderedToSit(tag.getBoolean("Sitting"));
        }
        if (tag.contains("ArmorItems")) {
            armorHandler.deserializeNBT(tag.getCompound("ArmorItems"));
        }
        if (tag.contains("KnifeItems")) {
            knifeHandler.deserializeNBT(tag.getCompound("KnifeItems"));
        }
        if (tag.contains("Inventory")) {
            inventoryHandler.deserializeNBT(tag.getCompound("Inventory"));
        }
        if (tag.contains("ToukenType")) {
            try {
                this.toukenType = ToukenType.valueOf(tag.getString("ToukenType"));
            } catch (IllegalArgumentException e) {
                // 非法刀种值，保持默认
            }
        }
        if (tag.contains("BaseAttackDamage")) {
            this.baseAttackDamage = tag.getDouble("BaseAttackDamage");
        }
        if (tag.contains("BaseMaxHealth")) {
            this.baseMaxHealth = tag.getDouble("BaseMaxHealth");
        }

        // 领地读取
        if (tag.contains("FarmlandTerritory", Tag.TAG_LIST)) {
            ListTag territoryList = tag.getList("FarmlandTerritory", Tag.TAG_COMPOUND);
            myFarmlandTerritory.clear();
            for (int i = 0; i < territoryList.size(); i++) {
                CompoundTag posTag = territoryList.getCompound(i);
                myFarmlandTerritory.add(new BlockPos(
                        posTag.getInt("x"), posTag.getInt("y"), posTag.getInt("z")
                ));
            }
        }
        if (tag.contains("LastTerritoryPos")) {
            lastTerritoryPos = BlockPos.of(tag.getLong("LastTerritoryPos"));
        }
        if (tag.contains("TerritoryRecalcCooldown")) {
            territoryRecalcCooldown = tag.getInt("TerritoryRecalcCooldown");
        }
        if (tag.contains("NeedsSeedRefill")) {
            needsSeedRefill = tag.getBoolean("NeedsSeedRefill");
        }

        // 自动回血冷却读取
        if (tag.contains("AutoHealCooldown")) {
            autoHealCooldown = tag.getInt("AutoHealCooldown");
        }
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return null; // 禁止繁殖
    }

    @Override
    protected PortalInfo findDimensionEntryPoint(ServerLevel destination) {
        // 把 Y 坐标限制在目标维度的有效范围内，防止自定义维度因高度问题拒绝传送
        double safeY = Mth.clamp(
                this.getY(),
                destination.getMinBuildHeight() + 1.0,
                destination.getMaxBuildHeight() - 1.0
        );

        return new PortalInfo(
                new Vec3(this.getX(), safeY, this.getZ()),
                this.getDeltaMovement(),
                this.getYRot(),
                this.getXRot()
        );
    }

    // ==================== 跨维度传送安全位置搜索 ====================
    /**
     * 在指定坐标附近螺旋搜索一个安全的站立位置（2格高空气 + 脚下有地面）
     * 如果找不到，向上搜索防止卡在1格高的下界隧道里窒息
     */
    public static Vec3 findSafePosNear(ServerLevel level, double centerX, double centerY, double centerZ) {
        BlockPos center = new BlockPos((int) Math.floor(centerX), (int) Math.floor(centerY), (int) Math.floor(centerZ));

        // 先检查目标位置本身
        if (isSafeStandingPos(level, center)) {
            return new Vec3(center.getX() + 0.5, center.getY(), center.getZ() + 0.5);
        }

        // 螺旋搜索周围，半径5格
        for (int radius = 1; radius <= 5; radius++) {
            for (int dx = -radius; dx <= radius; dx++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    if (Math.abs(dx) != radius && Math.abs(dz) != radius) continue;
                    for (int dy = -2; dy <= 2; dy++) {
                        BlockPos check = center.offset(dx, dy, dz);
                        if (isSafeStandingPos(level, check)) {
                            return new Vec3(check.getX() + 0.5, check.getY(), check.getZ() + 0.5);
                        }
                    }
                }
            }
        }

        // 兜底：周围5格都找不到2格高空间（下界小隧道常见）
        // 向上搜索直到找到空气，防止实体卡在1格高天花板里窒息死
        BlockPos upPos = center.above();
        int maxUp = 10;
        while (maxUp-- > 0 && upPos.getY() < level.getMaxBuildHeight() - 1) {
            if (level.isEmptyBlock(upPos)) {
                return new Vec3(upPos.getX() + 0.5, upPos.getY(), upPos.getZ() + 0.5);
            }
            upPos = upPos.above();
        }

        // 最终兜底：返回原坐标
        return new Vec3(centerX, centerY, centerZ);
    }

    private static boolean isSafeStandingPos(ServerLevel level, BlockPos pos) {
        if (pos.getY() < level.getMinBuildHeight() || pos.getY() >= level.getMaxBuildHeight() - 1) return false;
        return level.isEmptyBlock(pos) && level.isEmptyBlock(pos.above()) && !level.isEmptyBlock(pos.below());
    }

    // ==================== GeckoLib 动画 ====================

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public abstract void registerControllers(AnimatableManager.ControllerRegistrar controllers);
}
