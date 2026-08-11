package com.Equatorial.toukenranbu.entity.custom;

import com.Equatorial.toukenranbu.entity.custom.kebiishi.*;
import com.Equatorial.toukenranbu.entity.touken.ToukenDanshiEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

public class OotachiEntity extends Monster implements GeoEntity, JikkoEntity {
    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public OotachiEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setMaxUpStep(2.0F);
    }

    public static AttributeSupplier setAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 50.0D)
                .add(Attributes.ATTACK_DAMAGE, 6.0f)
                .add(Attributes.ATTACK_SPEED, 0.25f)
                .add(Attributes.MOVEMENT_SPEED, 0.15f)
                .add(Attributes.FOLLOW_RANGE, 20.0D)
                .build();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, false));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 0.5D));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, ToukenDanshiEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, KebiishiTachiEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, KebiishiOotachiEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, KebiishiNaginataEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, KebiishiYariEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, KebiishiLeaderEntity.class, true));
    }

    private <E extends OotachiEntity> PlayState predicate(final AnimationState<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(RawAnimation.begin().then("animation.ootachi.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.ootachi.idle"));
        return PlayState.CONTINUE;
    }

    private <E extends OotachiEntity> PlayState attackPredicate(final AnimationState<E> event) {
        if (this.swinging) {
            event.getController().setAnimation(RawAnimation.begin().then("animation.ootachi.attack", Animation.LoopType.PLAY_ONCE));
            this.swinging = false;
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, this::predicate));
        controllers.add(new AnimationController<>(this, "attackController", event -> PlayState.STOP)
                .triggerableAnim("attack", RawAnimation.begin()
                        .then("animation.ootachi.attack", Animation.LoopType.PLAY_ONCE)));
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean hit = super.doHurtTarget(target);
        if (hit) {
            triggerAnim("attackController", "attack");

            if (target instanceof LivingEntity) {
                AABB sweepRange = target.getBoundingBox().inflate(2.5D);
                List<LivingEntity> nearby = this.level().getEntitiesOfClass(
                        LivingEntity.class, sweepRange,
                        entity -> entity != this && entity != target && entity.isAlive()
                );
                float sweepDamage = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.6f;
                for (LivingEntity entity : nearby) {
                    entity.hurt(this.level().damageSources().mobAttack(this), sweepDamage);
                    double dx = entity.getX() - this.getX();
                    double dz = entity.getZ() - this.getZ();
                    entity.knockback(0.4D, dx, dz);
                }
            }
        }
        return hit;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.WARDEN_STEP, 1.0F, 1.0F);
    }

    @Override
    protected SoundEvent getAmbientSound() { return SoundEvents.WARDEN_AMBIENT; }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) { return SoundEvents.WARDEN_HURT; }

    @Override
    protected SoundEvent getDeathSound() { return SoundEvents.WARDEN_DEATH; }

    @Override
    public boolean startRiding(Entity entity, boolean force) {
        // 大太刀无法被矿车或者船困住。
        if (entity instanceof net.minecraft.world.entity.vehicle.Boat
                || entity instanceof net.minecraft.world.entity.vehicle.AbstractMinecart) {
            return false;
        }
        return super.startRiding(entity, force);
    }

    @Override
    protected float getSoundVolume() { return 0.2F; }

    // 记录白天基准值
    private double baseDayAttack;
    private double baseDaySpeed;
    private boolean wasDay = true;

    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        // 记录生成时的白天基准值
        this.baseDayAttack = this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
        this.baseDaySpeed = this.getAttributeBaseValue(Attributes.MOVEMENT_SPEED);
        this.wasDay = this.level().isDay();

        // 如果生成时是夜晚，立即应用夜晚修正
        if (!this.wasDay) {
            applyNightModifier();
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) return;

        // 每 20 tick（1秒）检查一次昼夜变化
        if (this.tickCount % 20 != 0) return;

        boolean isDay = this.level().isDay();
        if (isDay == wasDay) return;

        if (isDay) {
            restoreDayStats();  // 白天恢复
        } else {
            applyNightModifier(); // 夜晚变化
        }
        wasDay = isDay;
    }

    private void applyNightModifier() {
        // 根据刀种改这两个系数
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(this.baseDayAttack * 0.4); // 攻击
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(this.baseDaySpeed * 0.6);  // 速度
    }

    private void restoreDayStats() {
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(this.baseDayAttack);
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(this.baseDaySpeed);
    }

}