package com.Equatorial.toukenranbu.entity.touken;

import com.Equatorial.toukenranbu.entity.ai.*;
import com.Equatorial.toukenranbu.item.ModItems;
import com.Equatorial.toukenranbu.item.ToukenHorseItem;
import com.Equatorial.toukenranbu.screen.ModMenuTypes;
import com.Equatorial.toukenranbu.screen.ToukenDanshiMenu;
import com.Equatorial.toukenranbu.tag.ModItemTags;
import com.Equatorial.toukenranbu.touken.FormationType;
import com.Equatorial.toukenranbu.touken.ToukenType;
import com.Equatorial.toukenranbu.util.EntityCaptureHelper;
import net.minecraft.ChatFormatting;
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
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public abstract class ToukenDanshiEntity extends TamableAnimal implements GeoEntity {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    protected final ItemStackHandler armorHandler = new ItemStackHandler(4) {
        @Override protected void onContentsChanged(int slot) { ToukenDanshiEntity.this.updateArmorAttributes(); }
        @Override public boolean isItemValid(int slot, net.minecraft.world.item.ItemStack stack) {
            if (stack.isEmpty()) return false;
            if (stack.getItem() instanceof net.minecraft.world.item.ArmorItem armor) {
                return switch (slot) {
                    case 0 -> armor.getEquipmentSlot() == net.minecraft.world.entity.EquipmentSlot.HEAD;
                    case 1 -> armor.getEquipmentSlot() == net.minecraft.world.entity.EquipmentSlot.CHEST;
                    case 2 -> armor.getEquipmentSlot() == net.minecraft.world.entity.EquipmentSlot.LEGS;
                    case 3 -> armor.getEquipmentSlot() == net.minecraft.world.entity.EquipmentSlot.FEET;
                    default -> false;
                };
            }
            return false;
        }
    };

    protected final ItemStackHandler knifeHandler = new ItemStackHandler(3) {
        @Override protected void onContentsChanged(int slot) { ToukenDanshiEntity.this.updateKnifeBonuses(); }
        @Override public boolean isItemValid(int slot, net.minecraft.world.item.ItemStack stack) {
            return com.Equatorial.toukenranbu.screen.ToukenDanshiMenu.isKnifeItem(stack);
        }
    };
    protected final ItemStackHandler inventoryHandler = new ItemStackHandler(25);

    public final ToukenEntityData toukenData = new ToukenEntityData();

    protected final ItemStackHandler mountHandler = new ItemStackHandler(1) {
        @Override protected void onContentsChanged(int slot) {
            ToukenDanshiEntity.this.updateMountSpeed();
            ToukenDanshiEntity.this.updateMountBonuses();
        }
        @Override public boolean isItemValid(int slot, net.minecraft.world.item.ItemStack stack) {
            return ToukenDanshiEntity.isMountItem(stack);
        }
    };
    protected final ItemStackHandler bladeHandler = new ItemStackHandler(1) {
        @Override protected void onContentsChanged(int slot) {
            ToukenDanshiEntity.this.updateBladeBonuses();
        }
        @Override public boolean isItemValid(int slot, net.minecraft.world.item.ItemStack stack) {
            return ModItems.isBlade(stack.getItem());
        }
    };

    private static final java.util.UUID MOUNT_SPEED_UUID = java.util.UUID.fromString("a1b2c3d4-e5f6-7890-abcd-ef1234567890");
    private static final java.util.UUID MOBILITY_SPEED_UUID = java.util.UUID.fromString("b2c3d4e5-f6a7-8901-bcde-f12345678901");

    protected ToukenType toukenType = ToukenType.TACHI;
    protected double baseAttackDamage = 5.0;
    protected double baseMaxHealth = 20.0;
    protected double lowHealthThreshold = 0.4;
    protected double lowHealthDamageBonus = 0.4;
    protected double knifeDamageBonus = 0.0;
    protected double knifeHealthBonus = 0.0;

    private boolean needsSeedRefill = false;

    private static final Map<ResourceKey<Level>, Map<BlockPos, UUID>> FARM_TARGET_LOCKS = new HashMap<>();
    private BlockPos lockedFarmTarget = null;
    private int farmLockTimer = 0;

    private static final Map<ResourceKey<Level>, Map<UUID, Set<BlockPos>>> FARM_TERRITORIES = new ConcurrentHashMap<>();
    private Set<BlockPos> myFarmlandTerritory = ConcurrentHashMap.newKeySet();
    private int territoryRecalcCooldown = 0;
    private BlockPos lastTerritoryPos = BlockPos.ZERO;

    // ===== 工作类 AI 状态字段（以后加新工作 AI 也在这里声明布尔字段）=====
    private ToukenFarmingGoal farmingGoal;
    private boolean mining = false;

    private static final Map<UUID, Set<ToukenDanshiEntity>> OWNED_DANSHI = new ConcurrentHashMap<>();

    private static final EntityDataAccessor<Boolean> DATA_FOLLOWING =
            SynchedEntityData.defineId(ToukenDanshiEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_FARMING =
            SynchedEntityData.defineId(ToukenDanshiEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_MINING =
            SynchedEntityData.defineId(ToukenDanshiEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_PATROLLING =
            SynchedEntityData.defineId(ToukenDanshiEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_CAVE_CLEARING =
            SynchedEntityData.defineId(ToukenDanshiEntity.class, EntityDataSerializers.BOOLEAN);
    private BlockPos patrolCenter = null;
    private static final EntityDataAccessor<Boolean> DATA_SPARRING =
            SynchedEntityData.defineId(ToukenDanshiEntity.class, EntityDataSerializers.BOOLEAN);
    private UUID sparringPartnerUUID = null;
    private int sparringTimer = 0;
    private static final EntityDataAccessor<String> DATA_FORMATION_TYPE =
            SynchedEntityData.defineId(ToukenDanshiEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<Boolean> DATA_PICKUP_WHEN_FOLLOWING =
            SynchedEntityData.defineId(ToukenDanshiEntity.class, EntityDataSerializers.BOOLEAN);

    private int autoHealCooldown = 0;
    private static final int AUTO_HEAL_INTERVAL = 10;
    private static final int AUTO_HEAL_COOLDOWN = 10;
    private boolean autoSealEnabled = true;

    private static final EntityDataAccessor<CompoundTag> DATA_EXTRA_DATA =
            SynchedEntityData.defineId(ToukenDanshiEntity.class, EntityDataSerializers.COMPOUND_TAG);

    protected ToukenDanshiEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_FOLLOWING, false);
        this.entityData.define(DATA_FARMING, false);
        this.entityData.define(DATA_EXTRA_DATA, new CompoundTag());
        this.entityData.define(DATA_MINING, false);
        this.entityData.define(DATA_PATROLLING, false);
        this.entityData.define(DATA_SPARRING, false);
        this.entityData.define(DATA_CAVE_CLEARING, false);
        this.entityData.define(DATA_FORMATION_TYPE, FormationType.NONE.name());
        this.entityData.define(DATA_PICKUP_WHEN_FOLLOWING, true);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(3, new PickupItemsGoal(this));
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
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.2D, true));
        this.goalSelector.addGoal(6, new ToukenDepositGoal(this));
        // ===== 工作类 AI 区域（优先级 7，互斥运行）=====
        // 以后加新工作 AI（远征、演练、采集等）都往这里放，保持优先级 7
        this.farmingGoal = new ToukenFarmingGoal(this);
        this.goalSelector.addGoal(7, this.farmingGoal);
        this.goalSelector.addGoal(7, new MiningGoal(this));
        this.goalSelector.addGoal(7, new CaveClearanceGoal(this));

        // ===== 工作类 AI 区域结束 =====
        this.goalSelector.addGoal(7, new ToukenSparringGoal(this));
        this.goalSelector.addGoal(8, new ToukenPatrolGoal(this));
        this.goalSelector.addGoal(8, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, new ToukenHurtByTargetGoal(this));

        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<Mob>(this, Mob.class, 10, true, false,
                target -> {
                    if (target == this) return false;
                    if (target instanceof Player) return false;
                    if (this.isOwnedBy(target)) return false;
                    if (target instanceof TamableAnimal t && t.isTame()) return false;
                    if (target instanceof OwnableEntity o && this.getOwner() != null
                            && this.getOwner().equals(o.getOwner())) return false;
                    return target instanceof Monster;
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

    public static Map<UUID, Set<ToukenDanshiEntity>> getOwnedDanshi() {
        return OWNED_DANSHI;
    }

    public boolean needsSeedRefill() {
        return needsSeedRefill;
    }

    public void setNeedsSeedRefill(boolean needs) {
        this.needsSeedRefill = needs;
    }

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

    public void releaseFarmTarget() {
        if (lockedFarmTarget != null) {
            var levelLocks = FARM_TARGET_LOCKS.get(this.level().dimension());
            if (levelLocks != null) {
                levelLocks.remove(lockedFarmTarget, this.getUUID());
            }
            lockedFarmTarget = null;
        }
    }

    public static boolean isFarmTargetLockedByOther(Level level, BlockPos pos, UUID self) {
        var levelLocks = FARM_TARGET_LOCKS.get(level.dimension());
        if (levelLocks == null) return false;
        UUID owner = levelLocks.get(pos);
        return owner != null && !owner.equals(self);
    }

    public Set<BlockPos> getMyFarmlandTerritory() {
        return myFarmlandTerritory;
    }

    public void recalcFarmlandTerritory() {
        if (this.level().isClientSide) return;
        if (territoryRecalcCooldown-- > 0 && !myFarmlandTerritory.isEmpty()) return;
        territoryRecalcCooldown = 100;

        if (this.blockPosition().distSqr(lastTerritoryPos) < 16 && !myFarmlandTerritory.isEmpty()) return;
        lastTerritoryPos = this.blockPosition();

        ServerLevel level = (ServerLevel) this.level();

        BlockPos start = findNearestFarmland(level, this.blockPosition(), 8);
        if (start == null) {
            releaseFarmlandTerritory();
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

    private boolean isValidGround(BlockState state) {
        return state.getBlock() instanceof FarmBlock
                || state.is(Blocks.DIRT)
                || state.is(Blocks.GRASS_BLOCK)
                || state.is(Blocks.SAND)
                || state.is(Blocks.COARSE_DIRT)
                || state.is(Blocks.PODZOL)
                || state.is(Blocks.MYCELIUM);
    }

    public void releaseFarmlandTerritory() {
        var dimMap = FARM_TERRITORIES.get(this.level().dimension());
        if (dimMap != null) {
            dimMap.remove(this.getUUID());
        }
        myFarmlandTerritory.clear();
    }

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

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide && this.tickCount % 5 == 0) {
            syncExtraData();
        }

        if (!this.level().isClientSide
                && this.isFollowing()
                && !this.isOrderedToSit()
                && !this.isMining()
                && !this.isPatrolling()
                && this.tickCount % 5 == 0) {

            UUID ownerUUID = this.getOwnerUUID();
            if (ownerUUID != null && this.level() instanceof ServerLevel currentLevel) {
                MinecraftServer server = currentLevel.getServer();
                if (server != null) {
                    ServerPlayer owner = server.getPlayerList().getPlayer(ownerUUID);
                    if (owner != null) {
                        if (owner.level().dimension() != this.level().dimension()) {
                            Entity newEntity = this.changeDimension((ServerLevel) owner.level());
                            if (newEntity != null) {
                                Vec3 safePos = findSafePosNear((ServerLevel) owner.level(), owner.getX(), owner.getY(), owner.getZ());
                                newEntity.teleportTo(safePos.x, safePos.y, safePos.z);
                            }
                        }
                        else if (this.distanceToSqr(owner) > 4096.0D) {
                            Vec3 safePos = findSafePosNear((ServerLevel) owner.level(), owner.getX(), owner.getY(), owner.getZ());
                            this.teleportTo(safePos.x, safePos.y, safePos.z);
                        }
                    }
                }
            }
        }

        if (!this.level().isClientSide && this.tickCount % 5 == 0) {
            if (this.tickCount % 20 == 0) updateFormationStatus();

            // ===== 六维属性 → MC Attribute 映射 =====

            // 1. 冲力 → 攻击力（含夜间刀种倍率 + 低血量加成 + 冲力 + 刀装 + 疲劳度）
            double base = this.baseAttackDamage;
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
            double impactAttack = this.toukenData.getEffectiveImpact() * 0.15;
            double killingAttack = this.toukenData.getEffectiveKilling() * 0.03;

            float bladeEnchantDamage = 0.0f;
            ItemStack bladeStack = bladeHandler.getStackInSlot(0);
            if (!bladeStack.isEmpty()) {
                bladeEnchantDamage = net.minecraft.world.item.enchantment.EnchantmentHelper.getDamageBonus(bladeStack, net.minecraft.world.entity.MobType.UNDEFINED);
            }

            double finalAttack = (base + impactAttack + killingAttack + bladeEnchantDamage)
                    * (1.0 + knifeDamageBonus)
                    * this.toukenData.getFatigueMultiplier()
                    * getFormationAttackMult();
            var attackAttr = this.getAttribute(Attributes.ATTACK_DAMAGE);
            if (attackAttr != null && attackAttr.getBaseValue() != finalAttack) {
                attackAttr.setBaseValue(finalAttack);
            }

            // 2. 机动 → 移动速度（用 Modifier 加，不覆盖基础值）
            double mobilityBonus = this.toukenData.getEffectiveMobility() * 0.0005 * getFormationSpeedMult();
            var speedAttr = this.getAttribute(Attributes.MOVEMENT_SPEED);
            if (speedAttr != null) {
                speedAttr.removeModifier(MOBILITY_SPEED_UUID);
                speedAttr.addTransientModifier(new net.minecraft.world.entity.ai.attributes.AttributeModifier(
                        MOBILITY_SPEED_UUID, "touken_mobility", mobilityBonus,
                        net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.ADDITION
                ));
            }

            // 3. 侦察 → 索敌范围（每点侦察+0.1格）
            var followRangeAttr = this.getAttribute(Attributes.FOLLOW_RANGE);
            double scoutingRange = (getFollowRange(this.level().isNight())
                    + this.toukenData.getEffectiveScouting() * 0.1)
                    * getFormationRangeMult();
            if (followRangeAttr != null && followRangeAttr.getBaseValue() != scoutingRange) {
                followRangeAttr.setBaseValue(scoutingRange);
            }

            // 4. 兵力 → 最大生命值（每点兵力+0.5生命）
            double troopsHealth = this.toukenData.getEffectiveTroops() * 0.5;
            double finalHealth = (this.baseMaxHealth + knifeHealthBonus + troopsHealth)
                    * this.toukenData.getFatigueMultiplier()
                    * getFormationDefenseMult();
            var healthAttr = this.getAttribute(Attributes.MAX_HEALTH);
            if (healthAttr != null && healthAttr.getBaseValue() != finalHealth) {
                double oldMax = this.getMaxHealth();
                healthAttr.setBaseValue(finalHealth);
                double newMax = this.getMaxHealth();
                if (newMax > oldMax) {
                    this.setHealth((float)(this.getHealth() + (newMax - oldMax)));
                }
                if (newMax < oldMax && this.getHealth() > newMax) {
                    this.setHealth((float) newMax);
                }
            }
            // 5. 阵型防御 → 护甲加成
            var armorAttr = this.getAttribute(Attributes.ARMOR);
            if (armorAttr != null) {
                double baseArmor = 0.0;
                for (int i = 0; i < armorHandler.getSlots(); i++) {
                    ItemStack stack = armorHandler.getStackInSlot(i);
                    if (stack.getItem() instanceof ArmorItem armor) {
                        baseArmor += armor.getDefense();
                    }
                }
                double finalArmor = (baseArmor + this.toukenData.getEffectiveConcealment() * 0.1) * getFormationDefenseMult();
                if (armorAttr.getBaseValue() != finalArmor) {
                    armorAttr.setBaseValue(finalArmor);
                }
            }
        }

        if (lockedFarmTarget != null) {
            if (--farmLockTimer <= 0 || !this.isFarming() || this.isOrderedToSit() || this.isDeadOrDying()) {
                releaseFarmTarget();
            }
        }

        // ===== 手合计时 =====
        if (!this.level().isClientSide && this.isSparring()) {
            if (this.sparringTimer > 0) {
                this.sparringTimer--;
            } else {
                this.finishSparringReward();
                ToukenDanshiEntity partner = getSparringPartner();
                if (partner != null && partner.isSparring()) {
                    partner.finishSparringReward();
                    partner.setSparring(false);
                }
                this.setSparring(false);
            }
        }
        // ===== 手合计时结束 =====

        if (!this.level().isClientSide && this.tickCount % AUTO_HEAL_INTERVAL == 0) {
            tickAutoHeal();
        }
        // ===== 疲劳度自然恢复（非工作状态下：坐下/闲逛都恢复）=====
        // 每 10 秒（1200 tick）恢复 1 点疲劳，想改速度就改 1200 这个数字
        if (!this.level().isClientSide && this.tickCount % 1200 == 0) {
            if (!this.isFollowing() && !this.isMining() && !this.isFarming()) {
                if (this.toukenData.fatigue < 100) {
                    this.toukenData.fatigue = Math.min(100, this.toukenData.fatigue + 1);
                    this.syncExtraData();
                }
            }
        }
        // ===== 疲劳度自然恢复结束 =====
    }

    @Override
    public void remove(RemovalReason reason) {
        releaseFarmTarget();
        releaseFarmlandTerritory();
        if (!this.level().isClientSide && this.getOwnerUUID() != null) {
            Set<ToukenDanshiEntity> set = OWNED_DANSHI.get(this.getOwnerUUID());
            if (set != null) {
                set.remove(this);
                if (set.isEmpty()) OWNED_DANSHI.remove(this.getOwnerUUID());
            }
        }
        super.remove(reason);
    }

    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        updateMountSpeed();
        var speedAttr = this.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speedAttr != null && speedAttr.getBaseValue() < 0.2) {
            speedAttr.setBaseValue(0.3);
        }
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

    private static final int GOLD_IMPACT = 3;
    private static final int GOLD_MOBILITY = 2;
    private static final int GOLD_KILLING = 2;
    private static final int GOLD_SCOUTING = 2;
    private static final int GOLD_CONCEALMENT = 2;
    private static final int GOLD_TROOPS = 5;
    private static final double GOLD_ATTACK_MULT = 0.7;
    private static final double GOLD_HEALTH = 16.0;

    private static final int SILVER_IMPACT = 2;
    private static final int SILVER_MOBILITY = 1;
    private static final int SILVER_KILLING = 1;
    private static final int SILVER_SCOUTING = 1;
    private static final int SILVER_CONCEALMENT = 1;
    private static final int SILVER_TROOPS = 3;
    private static final double SILVER_ATTACK_MULT = 0.4;
    private static final double SILVER_HEALTH = 8.0;

    private static final int COPPER_IMPACT = 1;
    private static final int COPPER_MOBILITY = 0;
    private static final int COPPER_KILLING = 0;
    private static final int COPPER_SCOUTING = 0;
    private static final int COPPER_CONCEALMENT = 0;
    private static final int COPPER_TROOPS = 1;
    private static final double COPPER_ATTACK_MULT = 0.15;
    private static final double COPPER_HEALTH = 4.0;

    public void updateKnifeBonuses() {
        int totalImpact = 0, totalMobility = 0, totalKilling = 0;
        int totalScouting = 0, totalConcealment = 0, totalTroops = 0;
        double totalAttackMult = 0.0;
        double totalHealthBonus = 0.0;

        for (int i = 0; i < knifeHandler.getSlots(); i++) {
            ItemStack stack = knifeHandler.getStackInSlot(i);
            if (stack.isEmpty()) continue;

            if (stack.is(ModItemTags.GOLD_KNIFE)) {
                totalImpact += GOLD_IMPACT;
                totalMobility += GOLD_MOBILITY;
                totalKilling += GOLD_KILLING;
                totalScouting += GOLD_SCOUTING;
                totalConcealment += GOLD_CONCEALMENT;
                totalTroops += GOLD_TROOPS;
                totalAttackMult += GOLD_ATTACK_MULT;
                totalHealthBonus += GOLD_HEALTH;
            } else if (stack.is(ModItemTags.SILVER_KNIFE)) {
                totalImpact += SILVER_IMPACT;
                totalMobility += SILVER_MOBILITY;
                totalKilling += SILVER_KILLING;
                totalScouting += SILVER_SCOUTING;
                totalConcealment += SILVER_CONCEALMENT;
                totalTroops += SILVER_TROOPS;
                totalAttackMult += SILVER_ATTACK_MULT;
                totalHealthBonus += SILVER_HEALTH;
            } else if (stack.is(ModItemTags.COPPER_KNIFE)) {
                totalImpact += COPPER_IMPACT;
                totalMobility += COPPER_MOBILITY;
                totalKilling += COPPER_KILLING;
                totalScouting += COPPER_SCOUTING;
                totalConcealment += COPPER_CONCEALMENT;
                totalTroops += COPPER_TROOPS;
                totalAttackMult += COPPER_ATTACK_MULT;
                totalHealthBonus += COPPER_HEALTH;
            }
        }

        this.knifeDamageBonus = totalAttackMult;
        this.knifeHealthBonus = totalHealthBonus;

        this.toukenData.knifeImpactBonus = totalImpact;
        this.toukenData.knifeMobilityBonus = totalMobility;
        this.toukenData.knifeKillingBonus = totalKilling;
        this.toukenData.knifeScoutingBonus = totalScouting;
        this.toukenData.knifeConcealmentBonus = totalConcealment;
        this.toukenData.knifeTroopsBonus = totalTroops;

        syncExtraData();
    }

    public boolean isFollowing() {
        return this.entityData.get(DATA_FOLLOWING);
    }

    public void setFollowing(boolean following) {
        this.entityData.set(DATA_FOLLOWING, following);
        if (following) {
            this.setOrderedToSit(false);
            this.setFarming(false);
            this.setMining(false); // 跟随时停止挖矿
            this.setPatrolling(false);
            this.setSparring(false);
            this.setCaveClearing(false);
        } else {
            if (!this.level().isClientSide) {
                this.navigation.stop();
            }
        }
    }

    public boolean isFarming() {
        return this.entityData.get(DATA_FARMING);
    }

    public boolean isFarmingEscaping() {
        return this.farmingGoal != null && this.farmingGoal.isEscaping();
    }

    public void setFarming(boolean farming) {
        this.entityData.set(DATA_FARMING, farming);
        if (farming) {
            this.setOrderedToSit(false);
            this.setFollowing(false);
            this.setMining(false); // 种田时停止挖矿
            this.setPatrolling(false);
            this.setSparring(false);
            this.setCaveClearing(false);
            this.recalcFarmlandTerritory();
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
            this.setMining(false); // 坐下时停止挖矿
            this.setPatrolling(false);
            this.setSparring(false);
            this.setCaveClearing(false);
            if (!this.level().isClientSide) {
                this.navigation.stop();
            }
        }
    }

    // ===== 挖矿 AI（以后加新工作 AI 的 getter/setter 也按这个模板写）=====
    public boolean isMining() {
        return this.entityData.get(DATA_MINING);
    }

    public void setMining(boolean mining) {
        this.entityData.set(DATA_MINING, mining);
        if (mining) {
            this.setOrderedToSit(false);
            this.setFollowing(false);
            this.setFarming(false);
            this.setPatrolling(false);
            this.setSparring(false);
            this.setCaveClearing(false);
        }
        if (!mining && !this.level().isClientSide) {
            this.navigation.stop();
        }
    }
    // ===== 挖矿 AI 结束 =====

    public ToukenType getToukenType() {
        return toukenType;
    }

    public void setToukenType(ToukenType type) {
        this.toukenType = type;
    }

    public double getBaseAttackDamage() {
        return baseAttackDamage;
    }

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

    protected abstract String getEntityNameKey();

    @Override
    public Component getName() {
        return Component.translatable(getEntityDisplayNameKey());
    }

    public String getEntityDisplayNameKey() {
        return "entity.toukenranbu.touken_danshi." + getEntityNameKey();
    }

    public String getGuiTitleKey() {
        return "gui.toukenranbu.touken_danshi.title." + getEntityNameKey();
    }

    protected String getDeathMessageKey() {
        return null;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) return false;

        float newHealth = this.getHealth() - amount;
        if (newHealth <= 1.0f
                && !this.level().isClientSide
                && this.isTame()
                && this.getOwnerUUID() != null
                && autoSealEnabled
                && !source.is(net.minecraft.tags.DamageTypeTags.BYPASSES_INVULNERABILITY)) {

            this.setHealth(1.0f);
            this.invulnerableTime = 60;

            Player owner = this.getOwner() instanceof Player p ? p : null;
            performAutoSeal(owner);

            return true;
        }

        return super.hurt(source, amount);
    }
    @Override
    public boolean doHurtTarget(Entity target) {
        boolean hit = super.doHurtTarget(target);
        if (hit && !this.level().isClientSide) {
            this.toukenData.fatigue = Math.max(0, this.toukenData.fatigue - 2);
            syncExtraData();
        }
        return hit;
    }

    private void performAutoSeal(@javax.annotation.Nullable Player owner) {
        ItemStack sealStack = new ItemStack(com.Equatorial.toukenranbu.item.ModItems.CAPTURE_BALL.get());
        ItemStack result = EntityCaptureHelper.captureEntity(this, sealStack, owner);

        if (owner != null) {
            if (!owner.getInventory().add(result)) {
                var drop = owner.drop(result, false);
                if (drop != null) drop.setNoPickUpDelay();
            }
            owner.displayClientMessage(
                    Component.translatable("gui.toukenranbu.message.auto_seal", this.getName())
                            .withStyle(ChatFormatting.GOLD), false);
        } else {
            var drop = this.spawnAtLocation(result);
            if (drop != null) drop.setNoPickUpDelay();
        }

        if (this.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.TOTEM_OF_UNDYING,
                    this.getX(), this.getY() + this.getBbHeight() / 2.0, this.getZ(),
                    30, 0.3, 0.3, 0.3, 0.1);
        }
        this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.ENDERMAN_TELEPORT, this.getSoundSource(), 1.0f, 1.0f);
    }

    @Override
    public void die(DamageSource source) {
        if (!this.level().isClientSide && tryUseAmulet(source)) {
            return;
        }
        if (!this.level().isClientSide && this.getOwner() instanceof Player player) {
            String deathKey = this.getDeathMessageKey();
            if (deathKey != null) {
                player.displayClientMessage(Component.translatable(deathKey), false);
            }
        }

        super.die(source);
    }

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

        for (int i = 0; i < armorHandler.getSlots(); i++) {
            ItemStack stack = armorHandler.getStackInSlot(i);
            if (!stack.isEmpty()) {
                this.spawnAtLocation(stack);
                armorHandler.setStackInSlot(i, ItemStack.EMPTY);
            }
        }

        for (int i = 0; i < knifeHandler.getSlots(); i++) {
            ItemStack stack = knifeHandler.getStackInSlot(i);
            if (!stack.isEmpty()) {
                this.spawnAtLocation(stack);
                knifeHandler.setStackInSlot(i, ItemStack.EMPTY);
            }
        }

        for (int i = 0; i < mountHandler.getSlots(); i++) {
            ItemStack stack = mountHandler.getStackInSlot(i);
            if (!stack.isEmpty()) {
                this.spawnAtLocation(stack);
                mountHandler.setStackInSlot(i, ItemStack.EMPTY);
            }
        }


        for (int i = 0; i < bladeHandler.getSlots(); i++) {
            ItemStack stack = bladeHandler.getStackInSlot(i);
            if (!stack.isEmpty()) {
                this.spawnAtLocation(stack);
                bladeHandler.setStackInSlot(i, ItemStack.EMPTY);
            }
        }

        for (int i = 0; i < inventoryHandler.getSlots(); i++) {
            ItemStack stack = inventoryHandler.getStackInSlot(i);
            if (!stack.isEmpty()) {
                this.spawnAtLocation(stack);
                inventoryHandler.setStackInSlot(i, ItemStack.EMPTY);
            }
        }
        this.spawnAtLocation(new ItemStack(ModItems.DAMAGED_SWORD_FRAGMENTS.get()));
    }

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

    public boolean hasSeeds() {
        for (int i = 0; i < inventoryHandler.getSlots(); i++) {
            ItemStack stack = inventoryHandler.getStackInSlot(i);
            if (ToukenFarmingGoal.isSeed(stack)) {
                return true;
            }
        }
        return false;
    }

    public net.minecraftforge.items.IItemHandler getArmorHandler() {
        return armorHandler;
    }

    public net.minecraftforge.items.IItemHandler getMountHandler() {
        return mountHandler;
    }

    public static boolean isMountItem(net.minecraft.world.item.ItemStack stack) {
        if (stack.isEmpty()) return false;
        return stack.getItem() instanceof ToukenHorseItem;
    }

    private void updateMountSpeed() {
        if (this.level().isClientSide) return;
        var attr = this.getAttribute(Attributes.MOVEMENT_SPEED);
        if (attr == null) return;
        attr.removeModifier(MOUNT_SPEED_UUID);

        ItemStack stack = mountHandler.getStackInSlot(0);
        if (!stack.isEmpty() && stack.getItem() instanceof ToukenHorseItem horse) {
            double bonus = 0.03 + horse.getSpeedBonus();
            attr.addTransientModifier(new net.minecraft.world.entity.ai.attributes.AttributeModifier(
                    MOUNT_SPEED_UUID, "touken_mount", bonus,
                    net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.ADDITION));
        }
    }

    private void updateMountBonuses() {
        ItemStack stack = mountHandler.getStackInSlot(0);
        if (stack.getItem() instanceof ToukenHorseItem horse) {
            this.toukenData.mountImpactBonus = horse.getImpactBonus();
            this.toukenData.mountMobilityBonus = horse.getMobilityBonus();
            this.toukenData.mountKillingBonus = horse.getKillingBonus();
            this.toukenData.mountScoutingBonus = horse.getScoutingBonus();
            this.toukenData.mountConcealmentBonus = horse.getConcealmentBonus();
            this.toukenData.mountTroopsBonus = horse.getTroopsBonus();
        } else {
            this.toukenData.mountImpactBonus = 0;
            this.toukenData.mountMobilityBonus = 0;
            this.toukenData.mountKillingBonus = 0;
            this.toukenData.mountScoutingBonus = 0;
            this.toukenData.mountConcealmentBonus = 0;
            this.toukenData.mountTroopsBonus = 0;
        }
        syncExtraData();
    }
    public void updateBladeBonuses() {
        ItemStack stack = bladeHandler.getStackInSlot(0);
        if (!stack.isEmpty() && ModItems.isBlade(stack.getItem())) {
            this.toukenData.bladeImpactBonus = 10;
            this.toukenData.bladeMobilityBonus = 10;
            this.toukenData.bladeKillingBonus = 10;
            this.toukenData.bladeScoutingBonus = 10;
            this.toukenData.bladeConcealmentBonus = 10;
            this.toukenData.bladeTroopsBonus = 10;
        } else {
            this.toukenData.bladeImpactBonus = 0;
            this.toukenData.bladeMobilityBonus = 0;
            this.toukenData.bladeKillingBonus = 0;
            this.toukenData.bladeScoutingBonus = 0;
            this.toukenData.bladeConcealmentBonus = 0;
            this.toukenData.bladeTroopsBonus = 0;
        }
        syncExtraData();
    }

    public net.minecraftforge.items.IItemHandler getBladeHandler() {
        return bladeHandler;
    }

    public void syncExtraData() {
        if (!this.level().isClientSide) {
            this.entityData.set(DATA_EXTRA_DATA, this.toukenData.serialize());
        }
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        if (key == DATA_EXTRA_DATA) {
            CompoundTag tag = this.entityData.get(DATA_EXTRA_DATA);
            if (tag != null) this.toukenData.deserialize(tag);
        }
    }

    public net.minecraftforge.items.IItemHandler getKnifeHandler() {
        return knifeHandler;
    }

    public net.minecraftforge.items.IItemHandler getInventoryHandler() {
        return inventoryHandler;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (this.isOwnedBy(player)) {
            boolean isModOre = itemStack.is(ModItems.WOOTZ_STEEL.get()) ||
                    itemStack.is(ModItems.WHETSTONE.get()) ||
                    itemStack.is(Items.CHARCOAL) ||
                    itemStack.is(ModItems.COOLANT.get());
            FoodProperties food = itemStack.getFoodProperties(this);
            boolean isFood = food != null;

            if (isModOre || isFood) {
                boolean needHeal = this.getHealth() < this.getMaxHealth();
                boolean needFatigue = this.toukenData.fatigue < 100;

                if (!needHeal && !needFatigue) {
                    if (!this.level().isClientSide) {
                        player.displayClientMessage(
                                Component.translatable("gui.toukenranbu.message.feed_full", this.getName())
                                        .withStyle(ChatFormatting.YELLOW), true);
                    }
                    return InteractionResult.SUCCESS;
                }

                if (!this.level().isClientSide) {
                    if (isModOre) {
                        this.heal(4.0F);
                        this.toukenData.fatigue = Math.min(100, this.toukenData.fatigue + 10);
                    } else {
                        this.heal((float) food.getNutrition());
                        int fatigueRec = Math.max(5, food.getNutrition() * 2);
                        this.toukenData.fatigue = Math.min(100, this.toukenData.fatigue + fatigueRec);

                        for (var pair : food.getEffects()) {
                            if (this.random.nextFloat() < pair.getSecond()) {
                                this.addEffect(new MobEffectInstance(pair.getFirst()));
                            }
                        }
                    }

                    if (!player.isCreative()) itemStack.shrink(1);
                    this.syncExtraData();

                    if (this.level() instanceof ServerLevel serverLevel) {
                        serverLevel.sendParticles(ParticleTypes.HEART,
                                this.getX(), this.getY() + this.getBbHeight() * 0.8, this.getZ(),
                                3, 0.3, 0.2, 0.3, 0);
                    }
                    this.playSound(SoundEvents.GENERIC_EAT, 0.8F, 1.0F);
                }
                return InteractionResult.SUCCESS;
            }
            if (itemStack.isEmpty()) {
                if (!this.level().isClientSide && player instanceof ServerPlayer serverPlayer) {
                    NetworkHooks.openScreen(serverPlayer, new SimpleMenuProvider(
                            (id, inv, p) -> new ToukenDanshiMenu(ModMenuTypes.TOUKEN_DANSHI_MENU.get(), id, inv, this),
                            Component.translatable(this.getGuiTitleKey())
                    ), buf -> buf.writeInt(this.getId()));
                }
                return InteractionResult.SUCCESS;
            }
        }
        return super.mobInteract(player, hand);
    }

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

    protected HealInfo evaluateHealItem(ItemStack stack) {
        if (isModHealItem(stack)) {
            return new HealInfo(4.0F, false);
        }

        FoodProperties food = stack.getFoodProperties(this);
        if (food != null) {
            boolean hasEffects = !food.getEffects().isEmpty();
            float heal = (float) food.getNutrition();
            return new HealInfo(heal, hasEffects);
        }

        return new HealInfo(0.0F, false);
    }

    protected boolean isModHealItem(ItemStack stack) {
        return stack.is(ModItems.WOOTZ_STEEL.get()) ||
                stack.is(ModItems.WHETSTONE.get()) ||
                stack.is(Items.CHARCOAL) ||
                stack.is(ModItems.COOLANT.get());
    }

    protected void consumeHealItem(ItemStack stack, int slot) {
        if (isModHealItem(stack)) {
            this.heal(4.0F);
            stack.shrink(1);
            if (stack.isEmpty()) {
                inventoryHandler.setStackInSlot(slot, ItemStack.EMPTY);
            }
            this.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
            this.toukenData.fatigue = Math.min(100, this.toukenData.fatigue + 5);
            syncExtraData();
            return;
        }

        FoodProperties food = stack.getFoodProperties(this);
        if (food != null) {
            this.heal((float) food.getNutrition());

            for (var pair : food.getEffects()) {
                MobEffectInstance effectInstance = pair.getFirst();
                float probability = pair.getSecond();
                if (this.random.nextFloat() < probability) {
                    this.addEffect(new MobEffectInstance(effectInstance));
                }
            }

            stack.shrink(1);
            if (stack.isEmpty()) {
                inventoryHandler.setStackInSlot(slot, ItemStack.EMPTY);
            }
            int fatigueRecovery = Math.max(3, food.getNutrition());
            this.toukenData.fatigue = Math.min(100, this.toukenData.fatigue + fatigueRecovery);
            syncExtraData();

            this.playSound(
                    stack.getItem().getEatingSound(),
                    0.5F + 0.5F * this.random.nextInt(2),
                    (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F
            );
        }
    }

    protected static class HealInfo {
        public final float heal;
        public final boolean hasEffects;

        public HealInfo(float heal, boolean hasEffects) {
            this.heal = heal;
            this.hasEffects = hasEffects;
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("Following", this.isFollowing());
        tag.putBoolean("Farming", this.isFarming());
        tag.putBoolean("Mining", this.isMining());
        tag.putBoolean("Sitting", this.isOrderedToSit());
        tag.put("ArmorItems", armorHandler.serializeNBT());
        tag.put("KnifeItems", knifeHandler.serializeNBT());
        tag.put("Inventory", inventoryHandler.serializeNBT());
        tag.putString("ToukenType", this.toukenType.name());
        tag.putDouble("BaseAttackDamage", this.baseAttackDamage);
        tag.putDouble("BaseMaxHealth", this.baseMaxHealth);
        tag.put("ToukenData", toukenData.serialize());
        tag.put("MountItem", mountHandler.serializeNBT());
        tag.put("BladeItem", bladeHandler.serializeNBT());

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
        tag.putBoolean("Patrolling", this.isPatrolling());
        if (patrolCenter != null) {
            tag.putLong("PatrolCenter", patrolCenter.asLong());
        }
        tag.putString("FormationType", this.getFormationType().name());
        tag.putBoolean("Sparring", this.isSparring());
        if (sparringPartnerUUID != null) {
            tag.putUUID("SparringPartner", sparringPartnerUUID);
        }
        tag.putInt("SparringTimer", sparringTimer);
        tag.putBoolean("CaveClearing", this.isCaveClearing());
        tag.putBoolean("PickupWhenFollowing", this.isPickupWhenFollowing());
        tag.putInt("AutoHealCooldown", autoHealCooldown);
        tag.putBoolean("AutoSealEnabled", autoSealEnabled);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setFollowing(tag.getBoolean("Following"));
        this.setFarming(tag.getBoolean("Farming"));
        this.setMining(tag.getBoolean("Mining"));
        if (tag.contains("Sitting")) {
            super.setOrderedToSit(tag.getBoolean("Sitting"));
        }
        if (tag.contains("ArmorItems")) {
            armorHandler.deserializeNBT(tag.getCompound("ArmorItems"));
            updateArmorAttributes();
        }
        if (tag.contains("KnifeItems")) {
            knifeHandler.deserializeNBT(tag.getCompound("KnifeItems"));
            updateKnifeBonuses();
        }
        if (tag.contains("Inventory")) {
            inventoryHandler.deserializeNBT(tag.getCompound("Inventory"));
        }
        if (tag.contains("ToukenType")) {
            try {
                this.toukenType = ToukenType.valueOf(tag.getString("ToukenType"));
            } catch (IllegalArgumentException e) {
            }
        }
        if (tag.contains("BaseAttackDamage")) {
            this.baseAttackDamage = tag.getDouble("BaseAttackDamage");
        }
        if (tag.contains("BaseMaxHealth")) {
            this.baseMaxHealth = tag.getDouble("BaseMaxHealth");
        }

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
        if (tag.contains("Patrolling")) {
            boolean wasPatrolling = tag.getBoolean("Patrolling");
            if (wasPatrolling && tag.contains("PatrolCenter")) {
                this.patrolCenter = BlockPos.of(tag.getLong("PatrolCenter"));
            }
            this.setPatrolling(wasPatrolling);
        }
        if (tag.contains("Sparring")) {
            boolean wasSparring = tag.getBoolean("Sparring");
            if (wasSparring && tag.contains("SparringPartner")) {
                this.sparringPartnerUUID = tag.getUUID("SparringPartner");
            }
            this.sparringTimer = tag.getInt("SparringTimer");
            this.setSparring(wasSparring);
        }
        if (tag.contains("CaveClearing")) {
            this.setCaveClearing(tag.getBoolean("CaveClearing"));
        }
        if (tag.contains("PickupWhenFollowing")) {
            this.setPickupWhenFollowing(tag.getBoolean("PickupWhenFollowing"));
        }
        if (tag.contains("FormationType")) {
            try {
                this.setFormationType(FormationType.valueOf(tag.getString("FormationType")));
            } catch (IllegalArgumentException e) {
                this.setFormationType(FormationType.NONE);
            }
        }
        if (tag.contains("AutoHealCooldown")) {
            autoHealCooldown = tag.getInt("AutoHealCooldown");
        }
        if (tag.contains("AutoSealEnabled")) {
            autoSealEnabled = tag.getBoolean("AutoSealEnabled");
        }
        if (tag.contains("ToukenData")) toukenData.deserialize(tag.getCompound("ToukenData"));
        if (tag.contains("MountItem")) mountHandler.deserializeNBT(tag.getCompound("MountItem"));
        updateMountSpeed();
        updateMountBonuses();
        if (tag.contains("BladeItem")) {
            bladeHandler.deserializeNBT(tag.getCompound("BladeItem"));
            updateBladeBonuses();
        }
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return null;
    }

    @Override
    protected PortalInfo findDimensionEntryPoint(ServerLevel destination) {
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

    public static Vec3 findSafePosNear(ServerLevel level, double centerX, double centerY, double centerZ) {
        BlockPos center = new BlockPos((int) Math.floor(centerX), (int) Math.floor(centerY), (int) Math.floor(centerZ));

        if (isSafeStandingPos(level, center)) {
            return new Vec3(center.getX() + 0.5, center.getY(), center.getZ() + 0.5);
        }

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

        BlockPos upPos = center.above();
        int maxUp = 10;
        while (maxUp-- > 0 && upPos.getY() < level.getMaxBuildHeight() - 1) {
            if (level.isEmptyBlock(upPos)) {
                return new Vec3(upPos.getX() + 0.5, upPos.getY(), upPos.getZ() + 0.5);
            }
            upPos = upPos.above();
        }

        return new Vec3(centerX, centerY, centerZ);
    }

    private static boolean isSafeStandingPos(ServerLevel level, BlockPos pos) {
        if (pos.getY() < level.getMinBuildHeight() || pos.getY() >= level.getMaxBuildHeight() - 1) return false;
        return level.isEmptyBlock(pos) && level.isEmptyBlock(pos.above()) && !level.isEmptyBlock(pos.below());
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public abstract void registerControllers(AnimatableManager.ControllerRegistrar controllers);

    // ===== 手合系统 =====

    public boolean isSparring() {
        return this.entityData.get(DATA_SPARRING);
    }

    public void setSparring(boolean sparring) {
        this.entityData.set(DATA_SPARRING, sparring);
        if (!sparring) {
            this.sparringPartnerUUID = null;
            this.sparringTimer = 0;
            this.navigation.stop();
        }
    }

    public void startSparring(ToukenDanshiEntity partner) {
        this.sparringPartnerUUID = partner.getUUID();
        this.sparringTimer = 18000;
        this.setSparring(true);
        this.setFollowing(false);
        this.setOrderedToSit(false);
        this.setFarming(false);
        this.setMining(false);
        this.setPatrolling(false);
        this.setCaveClearing(false);
    }

    public ToukenDanshiEntity getSparringPartner() {
        if (sparringPartnerUUID == null) return null;
        if (this.level() instanceof ServerLevel serverLevel) {
            Entity e = serverLevel.getEntity(sparringPartnerUUID);
            if (e instanceof ToukenDanshiEntity t) return t;
        }
        return null;
    }

    public void finishSparringReward() {
        if (this.level().isClientSide) return;
        this.toukenData.fatigue = Math.max(0, this.toukenData.fatigue - 15);

        float roll = this.random.nextFloat();
        if (roll < 0.3f) {
            addRandomStat(1);
        } else if (roll < 0.4f) {
            addRandomStat(2);
        }

        this.syncExtraData();
        if (this.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(net.minecraft.core.particles.ParticleTypes.HAPPY_VILLAGER,
                    this.getX(), this.getY() + this.getBbHeight() * 0.5, this.getZ(),
                    5, 0.3, 0.3, 0.3, 0);
        }
    }

    private void addRandomStat(int count) {
        for (int i = 0; i < count; i++) {
            int stat = this.random.nextInt(6);
            switch (stat) {
                case 0 -> this.toukenData.impact = Math.min(999, this.toukenData.impact + 1);
                case 1 -> this.toukenData.mobility = Math.min(999, this.toukenData.mobility + 1);
                case 2 -> this.toukenData.killing = Math.min(999, this.toukenData.killing + 1);
                case 3 -> this.toukenData.scouting = Math.min(999, this.toukenData.scouting + 1);
                case 4 -> this.toukenData.concealment = Math.min(999, this.toukenData.concealment + 1);
                case 5 -> this.toukenData.troops = Math.min(999, this.toukenData.troops + 1);
            }
        }
    }

    public ToukenDanshiEntity findSparringPartner() {
        if (!(this.level() instanceof ServerLevel level)) return null;
        var list = level.getEntitiesOfClass(ToukenDanshiEntity.class,
                this.getBoundingBox().inflate(16.0), e -> {
                    if (e == this) return false;
                    if (!e.isOwnedBy(this.getOwner())) return false;
                    if (e.isOrderedToSit()) return false;
                    if (e.isFollowing()) return false;
                    if (e.isFarming()) return false;
                    if (e.isMining()) return false;
                    if (e.isPatrolling()) return false;
                    if (e.isSparring()) return false;
                    return true;
                });
        return list.isEmpty() ? null : list.get(0);
    }

    // ===== 巡逻系统 =====

    public boolean isPatrolling() {
        return this.entityData.get(DATA_PATROLLING);
    }

    public void setPatrolling(boolean patrolling) {
        this.entityData.set(DATA_PATROLLING, patrolling);
        if (!patrolling) {
            this.patrolCenter = null;
            this.navigation.stop();
        } else {
            this.setCaveClearing(false);
        }
    }

    public void startPatrol() {
        this.patrolCenter = this.blockPosition();
        this.setPatrolling(true);
        this.setFollowing(false);
        this.setOrderedToSit(false);
        this.setFarming(false);
        this.setMining(false);
    }

    public BlockPos getPatrolCenter() {
        return this.patrolCenter;
    }

    //===== 矿洞清缴系统 =====

    public boolean isCaveClearing() {
        return this.entityData.get(DATA_CAVE_CLEARING);
    }

    public void setCaveClearing(boolean clearing) {
        this.entityData.set(DATA_CAVE_CLEARING, clearing);
        if (clearing) {
            this.setOrderedToSit(false);
            this.setFollowing(false);
            this.setFarming(false);
            this.setMining(false);
            this.setPatrolling(false);
            this.setSparring(false);
        } else {
            if (!this.level().isClientSide) {
                this.navigation.stop();
            }
        }
    }

    public boolean hasTorches() {
        for (int i = 0; i < inventoryHandler.getSlots(); i++) {
            if (inventoryHandler.getStackInSlot(i).is(Items.TORCH)) {
                return true;
            }
        }
        return false;
    }

    // ===== 跟随捡物开关 =====
    public boolean isPickupWhenFollowing() {
        return this.entityData.get(DATA_PICKUP_WHEN_FOLLOWING);
    }

    public void setPickupWhenFollowing(boolean v) {
        this.entityData.set(DATA_PICKUP_WHEN_FOLLOWING, v);
    }
    // ===== 跟随捡物开关结束 =====

    // ===== 阵型系统 =====

    public FormationType getFormationType() {
        try {
            return FormationType.valueOf(this.entityData.get(DATA_FORMATION_TYPE));
        } catch (Exception e) {
            return FormationType.NONE;
        }
    }

    public void setFormationType(FormationType type) {
        this.entityData.set(DATA_FORMATION_TYPE, type.name());
    }

    private void updateFormationStatus() {
        if (this.isOrderedToSit() || this.isFarming() || this.isMining() || this.isDeadOrDying()) {
            if (this.toukenData.formationLevel != 0 || this.toukenData.formationCount != 0) {
                this.toukenData.formationLevel = 0;
                this.toukenData.formationCount = 0;
                syncExtraData();
            }
            return;
        }

        Player owner = this.getOwner() instanceof Player ? (Player) this.getOwner() : null;
        if (owner == null || !this.isTame()) {
            if (this.toukenData.formationLevel != 0 || this.toukenData.formationCount != 0) {
                this.toukenData.formationLevel = 0;
                this.toukenData.formationCount = 0;
                syncExtraData();
            }
            return;
        }

        int count = 1;
        AABB box = this.getBoundingBox().inflate(16.0);
        for (ToukenDanshiEntity other : this.level().getEntitiesOfClass(ToukenDanshiEntity.class, box)) {
            if (other == this) continue;
            if (!other.isOwnedBy(owner)) continue;
            if (other.isOrderedToSit() || other.isFarming() || other.isMining() || other.isCaveClearing()) continue;
            if (other.isDeadOrDying()) continue;
            count++;
        }

        int newLevel = 0;
        if (count >= 6) newLevel = 4;
        else if (count >= 5) newLevel = 3;
        else if (count >= 3) newLevel = 2;
        else if (count >= 2) newLevel = 1;

        if (this.toukenData.formationLevel != newLevel || this.toukenData.formationCount != count) {
            this.toukenData.formationLevel = newLevel;
            this.toukenData.formationCount = count;
            syncExtraData();
        }
    }

    public double getFormationAttackMult() {
        FormationType type = getFormationType();
        return type.getMult(this.toukenData.formationLevel, type.atkPerLevel);
    }

    public double getFormationDefenseMult() {
        FormationType type = getFormationType();
        return type.getMult(this.toukenData.formationLevel, type.defPerLevel);
    }

    public double getFormationSpeedMult() {
        FormationType type = getFormationType();
        return type.getMult(this.toukenData.formationLevel, type.spdPerLevel);
    }

    public double getFormationRangeMult() {
        FormationType type = getFormationType();
        return type.getMult(this.toukenData.formationLevel, type.rangePerLevel);
    }
}
