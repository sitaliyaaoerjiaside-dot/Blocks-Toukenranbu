package com.Equatorial.toukenranbu.entity.boss;

import com.Equatorial.toukenranbu.entity.ModEntityTypes;
import com.Equatorial.toukenranbu.entity.touken.ToukenDanshiEntity;
import com.Equatorial.toukenranbu.entity.util.CannotBeCaptured;
import com.Equatorial.toukenranbu.entity.util.CannotBeMillstoned;
import com.Equatorial.toukenranbu.util.InstantDeathHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.Set;

public class WhiteMikazukiEntity extends PathfinderMob implements CannotBeMillstoned, CannotBeCaptured, GeoEntity {

    // ===== 常量 =====
    private static final float MAX_HP = 100000.0f;
    private static final int BLOCK_DURATION_TICKS = 3 * 20;
    private static final int TELEPORT_CD_TICKS = 20 * 20;
    private static final double TELEPORT_DISTANCE_SQR = 16.0 * 16.0;
    private static final double COMBAT_DISTANCE_SQR = 32.0 * 32.0;
    private static final int OUT_OF_COMBAT_TICKS = 5 * 20;
    private static final int TRANSITION_INVUL_TICKS = 5 * 20;
    private static final int TRANSITION_NO_ATTACK_TICKS = 3 * 20;
    private static final int MINION_COUNT = 8;
    private static final double KNOCKBACK_STRENGTH = 2.5;

    // ===== 状态字段 =====
    private int phase = 1;
    private float phaseStartHp = MAX_HP;

    private static final EntityDataAccessor<Boolean> DATA_BLOCKING =
            SynchedEntityData.defineId(WhiteMikazukiEntity.class, EntityDataSerializers.BOOLEAN);

    private int blockingTicks = 0;
    private boolean hasBlockedThisPhase = false;

    private int transitionInvulTicks = 0;
    private int transitionNoAttackTicks = 0;
    private int phaseCooldown = 0;

    private int teleportCd = 0;
    private int outOfCombatTicks = 0;

    @Nullable
    private BlockPos altarPos;

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private final net.minecraft.server.level.ServerBossEvent bossEvent =
            new net.minecraft.server.level.ServerBossEvent(
                    Component.literal("\u200B").append(Component.translatable(
                            "entity.toukenranbu_mod.white_mikazuki_munechika.bossbar")),
                    net.minecraft.world.BossEvent.BossBarColor.WHITE,
                    net.minecraft.world.BossEvent.BossBarOverlay.PROGRESS
            );

    public WhiteMikazukiEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
        this.setMaxUpStep(1.0F);
        this.xpReward = 500;
    }

    public static AttributeSupplier setAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, MAX_HP)
                .add(Attributes.ATTACK_DAMAGE, 55.0D)
                .add(Attributes.ATTACK_SPEED, 1.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.28D)
                .add(Attributes.ARMOR, 0.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.FOLLOW_RANGE, 64.0D)
                .build();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_BLOCKING, false);
    }

    public boolean isBlocking() {
        return this.entityData.get(DATA_BLOCKING);
    }

    private void setBlocking(boolean v) {
        this.entityData.set(DATA_BLOCKING, v);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, false) {
            @Override
            public boolean canUse() {
                if (WhiteMikazukiEntity.this.isBlocking()) return false;
                if (WhiteMikazukiEntity.this.transitionNoAttackTicks > 0) return false;
                return super.canUse();
            }

            @Override
            public boolean canContinueToUse() {
                if (WhiteMikazukiEntity.this.isBlocking()) return false;
                if (WhiteMikazukiEntity.this.transitionNoAttackTicks > 0) return false;
                return super.canContinueToUse();
            }
        });
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));

// 第一优先：玩家（创造/旁观模式除外）
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, 10, false, false,
                target -> target instanceof Player player && !player.isCreative() && !player.isSpectator()));
// 第二优先：任何有主人的实体（刀男、原版宠物、其他模组召唤物）
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<LivingEntity>(this, LivingEntity.class, 10, false, false,
                target -> {
                    if (target == this) return false;
                    if (target.isSpectator()) return false;
                    if (target instanceof Player) return false;  // 玩家由上面那条负责
                    if (target instanceof OwnableEntity ownable) {
                        return ownable.getOwnerUUID() != null;
                    }
                    return false;
                }) {
            @Override
            public boolean canUse() {
                // 已经锁着玩家就不切目标
                if (WhiteMikazukiEntity.this.getTarget() instanceof Player) return false;
                return super.canUse();
            }

            @Override
            public boolean canContinueToUse() {
                if (WhiteMikazukiEntity.this.getTarget() instanceof Player) return false;
                return super.canContinueToUse();
            }
        });
    }

    // ===== GeckoLib =====
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, event -> {
            if (this.isDeadOrDying()) {
                event.getController().setAnimation(
                        RawAnimation.begin().then("animation.white_mikazuki_munechika.death",
                                Animation.LoopType.HOLD_ON_LAST_FRAME));
                return PlayState.CONTINUE;
            }
            if (this.isBlocking()) {
                event.getController().setAnimation(
                        RawAnimation.begin().then("animation.white_mikazuki_munechika.block",
                                Animation.LoopType.HOLD_ON_LAST_FRAME));
                return PlayState.CONTINUE;
            }
            if (event.isMoving()) {
                event.getController().setAnimation(
                        RawAnimation.begin().thenLoop("animation.white_mikazuki_munechika.walk"));
                return PlayState.CONTINUE;
            }
            event.getController().setAnimation(
                    RawAnimation.begin().thenLoop("animation.white_mikazuki_munechika.idle"));
            return PlayState.CONTINUE;
        }));

        controllers.add(new AnimationController<>(this, "attackController", 0, event -> {
            if (this.swinging) {
                event.getController().setAnimation(
                        RawAnimation.begin().then("animation.white_mikazuki_munechika.attack",
                                Animation.LoopType.PLAY_ONCE));
                return PlayState.CONTINUE;
            }
            return PlayState.STOP;
        }));
    }

    // ===== 主 tick =====
    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) return;

        if (phaseCooldown > 0) phaseCooldown--;

        checkPhaseTransition();
        tickBlocking();

        if (transitionInvulTicks > 0) transitionInvulTicks--;
        if (transitionNoAttackTicks > 0) transitionNoAttackTicks--;

        tickTeleport();
        tickOutOfCombat();

        if (this.isBlocking()) {
            Player nearest = this.level().getNearestPlayer(this, 32.0);
            if (nearest != null) {
                this.getLookControl().setLookAt(
                        nearest.getX(), nearest.getEyeY(), nearest.getZ(),
                        30.0F, 30.0F);
                this.getNavigation().stop();
                this.setDeltaMovement(Vec3.ZERO);
            }
        }

        if (this.tickCount % 20 == 0 && this.isAlive()) {
            this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());

            if (this.level() instanceof ServerLevel serverLevel) {
                for (ServerPlayer player : serverLevel.players()) {
                    boolean inRange = player.distanceToSqr(this) < 64 * 64;
                    boolean showing = this.bossEvent.getPlayers().contains(player);
                    if (inRange && !showing) {
                        this.bossEvent.addPlayer(player);
                    } else if (!inRange && showing) {
                        this.bossEvent.removePlayer(player);
                    }
                }
            }
        }
    }

    // ===== 阶段切换 =====
    private void checkPhaseTransition() {
        if (phaseCooldown > 0) return;

        float hpRatio = this.getHealth() / this.getMaxHealth();
        if (phase == 1 && hpRatio <= 0.5f) {
            enterPhase(2);
        } else if (phase == 2 && hpRatio <= 0.3f) {
            enterPhase(3);
        }
    }

    private void enterPhase(int newPhase) {
        this.phase = newPhase;

        this.transitionInvulTicks = TRANSITION_INVUL_TICKS;
        this.transitionNoAttackTicks = TRANSITION_NO_ATTACK_TICKS;
        this.phaseCooldown = 8 * 20;

        this.setBlocking(false);
        this.blockingTicks = 0;
        this.hasBlockedThisPhase = false;
        this.phaseStartHp = this.getHealth();

        knockbackNearbyPlayers();
        clearMinions();
        spawnMinions(MINION_COUNT);
        applyPhaseStats();

        this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.WITHER_SPAWN, this.getSoundSource(), 1.0f, 0.5f);
    }

    private void applyPhaseStats() {
        var attackAttr = this.getAttribute(Attributes.ATTACK_DAMAGE);
        var speedAttr = this.getAttribute(Attributes.MOVEMENT_SPEED);

        if (phase == 1) {
            if (attackAttr != null) attackAttr.setBaseValue(30.0);
            if (speedAttr != null) speedAttr.setBaseValue(0.28);
        } else if (phase == 2) {
            if (attackAttr != null) attackAttr.setBaseValue(45.0);
            if (speedAttr != null) speedAttr.setBaseValue(0.32);
        } else {
            if (attackAttr != null) attackAttr.setBaseValue(60.0);
            if (speedAttr != null) speedAttr.setBaseValue(0.36);
        }
    }

    private void knockbackNearbyPlayers() {
        if (!(this.level() instanceof ServerLevel serverLevel)) return;
        List<ServerPlayer> players = serverLevel.getPlayers(p -> p.distanceToSqr(this) <= 64.0);
        for (ServerPlayer player : players) {
            Vec3 dir = player.position().subtract(this.position());
            dir = new Vec3(dir.x, 0, dir.z);
            if (dir.lengthSqr() < 0.01) dir = new Vec3(1, 0, 0);
            dir = dir.normalize();
            player.setDeltaMovement(dir.x * KNOCKBACK_STRENGTH, 0.0, dir.z * KNOCKBACK_STRENGTH);
            player.hurtMarked = true;
        }
    }

    // ===== 小怪 =====
    private static Set<net.minecraft.world.entity.EntityType<?>> minionTypes = null;

    private static Set<net.minecraft.world.entity.EntityType<?>> getMinionTypes() {
        if (minionTypes == null) {
            minionTypes = Set.of(
                    ModEntityTypes.UCHIGATANA.get(),
                    ModEntityTypes.TACHI.get(),
                    ModEntityTypes.OOTACHI.get(),
                    ModEntityTypes.NAGINATA.get(),
                    ModEntityTypes.UCHIGATANA_PLUS.get(),
                    ModEntityTypes.TACHI_PLUS.get(),
                    ModEntityTypes.OOTACHI_PLUS.get(),
                    ModEntityTypes.NAGINATA_PLUS.get()
            );
        }
        return minionTypes;
    }

    private void clearMinions() {
        if (!(this.level() instanceof ServerLevel serverLevel)) return;
        List<Mob> minions = serverLevel.getEntitiesOfClass(
                Mob.class,
                this.getBoundingBox().inflate(64.0),
                e -> getMinionTypes().contains(e.getType()));
        for (Mob m : minions) m.discard();
    }

    private void spawnMinions(int count) {
        if (!(this.level() instanceof ServerLevel serverLevel)) return;
        List<net.minecraft.world.entity.EntityType<?>> types = new java.util.ArrayList<>(getMinionTypes());

        for (int i = 0; i < count; i++) {
            double angle = (Math.PI * 2.0 / count) * i;
            double dist = 4.0 + serverLevel.random.nextDouble() * 3.0;
            double x = this.getX() + Math.cos(angle) * dist;
            double z = this.getZ() + Math.sin(angle) * dist;

            var type = types.get(serverLevel.random.nextInt(types.size()));
            Entity entity = type.create(serverLevel);
            if (!(entity instanceof Mob minion)) {
                if (entity != null) entity.discard();
                continue;
            }
            minion.moveTo(x, this.getY(), z, serverLevel.random.nextFloat() * 360F, 0);
            minion.setTarget(null);
            minion.getPersistentData().putBoolean("touken_boss_minion", true);
            serverLevel.addFreshEntity(minion);
            serverLevel.addFreshEntity(minion);
        }
    }

    // ===== 格挡 =====
    private void tickBlocking() {
        if (this.isBlocking()) {
            blockingTicks--;
            if (blockingTicks <= 0) {
                this.setBlocking(false);
            }
            return;
        }

        if (!hasBlockedThisPhase) {
            float dropThreshold = this.phaseStartHp - this.getMaxHealth() * 0.1f;
            if (this.getHealth() < dropThreshold) {
                triggerBlock();
                hasBlockedThisPhase = true;
            }
        }
    }

    private void triggerBlock() {
        this.setBlocking(true);
        this.blockingTicks = BLOCK_DURATION_TICKS;
        this.getNavigation().stop();
        this.setTarget(null);
        this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.SHIELD_BLOCK, this.getSoundSource(), 1.0f, 1.0f);
    }

    // ===== 瞬移 =====
    private void tickTeleport() {
        if (teleportCd > 0) { teleportCd--; return; }
        if (this.isBlocking()) return;

        Player target = this.level().getNearestPlayer(this, 128.0);
        if (target == null) return;

        if (this.distanceToSqr(target) > TELEPORT_DISTANCE_SQR) {
            Vec3 safe = ToukenDanshiEntity.findSafePosNear(
                    (ServerLevel) this.level(),
                    target.getX(), target.getY(), target.getZ());
            this.teleportTo(safe.x, safe.y, safe.z);
            this.getNavigation().stop();
            this.setTarget(target);
            teleportCd = TELEPORT_CD_TICKS;
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                    SoundEvents.ENDERMAN_TELEPORT, this.getSoundSource(), 1.0f, 1.0f);
        }
    }

    // ===== 脱战 =====
    private void tickOutOfCombat() {
        Player nearest = this.level().getNearestPlayer(this, 128.0);
        boolean tooFar = (nearest == null) || (this.distanceToSqr(nearest) > COMBAT_DISTANCE_SQR);

        if (tooFar) {
            outOfCombatTicks++;
            if (outOfCombatTicks >= OUT_OF_COMBAT_TICKS) {
                exitCombat();
                outOfCombatTicks = 0;
            }
        } else {
            outOfCombatTicks = 0;
        }
    }

    private void exitCombat() {
        if (this.level() instanceof ServerLevel serverLevel) {
            List<ServerPlayer> players = serverLevel.getPlayers(p -> p.distanceToSqr(this) <= 64.0 * 64.0);
            for (ServerPlayer player : players) InstantDeathHelper.clearCountdown(player);
        }
        clearMinions();
        if (altarPos != null) {
            this.teleportTo(altarPos.getX() + 0.5, altarPos.getY(), altarPos.getZ() + 0.5);
        }
        this.setTarget(null);
        this.getNavigation().stop();
        this.setBlocking(false);
        this.blockingTicks = 0;
    }

    // ===== 伤害 =====
    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        if (transitionInvulTicks > 0) return true;
        return super.isInvulnerableTo(source);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (transitionInvulTicks > 0) return false;

        if (this.isBlocking() && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            Entity attacker = source.getEntity();
            if (attacker instanceof LivingEntity living && living != this) {
                living.hurt(living.damageSources().thorns(this), amount * 0.2f);
            }
            return false;
        }
        return super.hurt(source, amount);
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean hit = super.doHurtTarget(target);
        if (hit && !this.level().isClientSide && target instanceof ServerPlayer player) {
            InstantDeathHelper.addLayer(player);
        }
        return hit;
    }

    // ===== 限制 =====
    @Override
    public boolean startRiding(Entity vehicle, boolean force) { return false; }

    @Override
    public boolean canBeLeashed(Player player) { return false; }

    @Override
    public boolean isPersistenceRequired() { return true; }

    @Override
    public boolean canChangeDimensions() { return false; }

    @Override
    public boolean isSunBurnTick() { return false; }

    @Override
    public void remove(RemovalReason reason) {
        if (!this.level().isClientSide) {
            this.bossEvent.removeAllPlayers();
        }
        super.remove(reason);
    }

    // ===== 音效 =====
    @Nullable @Override
    protected SoundEvent getAmbientSound() { return SoundEvents.WITHER_AMBIENT; }

    @Nullable @Override
    protected SoundEvent getHurtSound(DamageSource source) { return SoundEvents.WITHER_HURT; }

    @Nullable @Override
    protected SoundEvent getDeathSound() { return SoundEvents.WITHER_DEATH; }

    @Override
    protected float getSoundVolume() { return 0.8F; }

    // ===== NBT =====
    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("Phase", phase);
        tag.putFloat("PhaseStartHp", phaseStartHp);
        tag.putBoolean("HasBlockedThisPhase", hasBlockedThisPhase);
        if (altarPos != null) tag.putLong("AltarPos", altarPos.asLong());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.phase = tag.getInt("Phase");
        this.phaseStartHp = tag.getFloat("PhaseStartHp");
        this.hasBlockedThisPhase = tag.getBoolean("HasBlockedThisPhase");
        if (tag.contains("AltarPos")) {
            this.altarPos = BlockPos.of(tag.getLong("AltarPos"));
        }
    }

    public void setAltarPos(BlockPos pos) { this.altarPos = pos; }

    @Override
    public void die(DamageSource source) {
        super.die(source);
        this.bossEvent.removeAllPlayers();
    }
}