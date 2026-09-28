package com.Equatorial.toukenranbu.entity.touken.naginata;

import com.Equatorial.toukenranbu.entity.touken.ToukenDanshiEntity;
import com.Equatorial.toukenranbu.touken.ToukenType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import java.util.List;

public class TomoegataNaginataEntity extends ToukenDanshiEntity {

    public TomoegataNaginataEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(2.0F);
        this.toukenType = ToukenType.NAGINATA;
        this.baseAttackDamage = 45.0;
        this.baseMaxHealth = 62.0;
        this.toukenData.impact = 60;
        this.toukenData.mobility = 45;
        this.toukenData.killing = 26;
        this.toukenData.scouting = 42;
        this.toukenData.concealment = 40;
        this.toukenData.troops = 20;
    }

    @Override
    protected String getEntityNameKey() {
        return "tomoegata_naginata";
    }

    public static AttributeSupplier setAttributes() {
        return TamableAnimal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 62.0D)
                .add(Attributes.ATTACK_DAMAGE, 45.0D)
                .add(Attributes.ATTACK_SPEED, 1.3D)
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.ARMOR, 15.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .build();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, event -> {
            if (this.isInSittingPose()) {
                event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.tomoegata_naginata.sit"));
                return PlayState.CONTINUE;
            }
            if (event.isMoving()) {
                event.getController().setAnimation(RawAnimation.begin().then("animation.tomoegata_naginata.walk", Animation.LoopType.LOOP));
                return PlayState.CONTINUE;
            }
            event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.tomoegata_naginata.idle"));
            return PlayState.CONTINUE;
        }));
        controllers.add(new AnimationController<>(this, "attackController", 0, event -> {
            if (this.swinging) {
                event.getController().setAnimation(RawAnimation.begin().then("animation.tomoegata_naginata.attack", Animation.LoopType.PLAY_ONCE));
                return PlayState.CONTINUE;
            }
            return PlayState.STOP;
        }));
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean hit = super.doHurtTarget(target);
        if (hit && target instanceof LivingEntity) {
            AABB sweepRange = target.getBoundingBox().inflate(3.5D);
            List<LivingEntity> nearby = this.level().getEntitiesOfClass(
                    LivingEntity.class, sweepRange,
                    entity -> {
                        if (entity == this || entity == target || !entity.isAlive()) return false;

                        // 友伤关闭时，横扫不伤主人
                        if (entity instanceof net.minecraft.world.entity.player.Player player
                                && this.isOwnedBy(player)) {
                            return player.getCapability(com.Equatorial.toukenranbu.capability.ModCapabilities.SPIRIT_POWER)
                                    .map(cap -> cap.isFriendlyFireEnabled()).orElse(true);
                        }

                        return true;
                    }
            );
            float sweepDamage = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
            for (LivingEntity entity : nearby) {
                entity.hurt(this.level().damageSources().mobAttack(this), sweepDamage);
                double dx = entity.getX() - this.getX();
                double dz = entity.getZ() - this.getZ();
                entity.knockback(0.4D, dx, dz);
            }
        }
        return hit;
    }

    @Override
    protected String getDeathMessageKey() {
        return "death.toukenranbu_mod.tomoegata_naginata";
    }

    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        this.addEffect(new MobEffectInstance(
                MobEffects.DAMAGE_RESISTANCE,
                -1,
                0,
                false,
                false,
                true
        ));
    }
}