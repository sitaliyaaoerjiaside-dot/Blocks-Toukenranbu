package com.Equatorial.toukenranbu.entity.touken.ootachi;

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

public class IshikirimaruEntity extends ToukenDanshiEntity {

    public IshikirimaruEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(2.0F);
        this.toukenType = ToukenType.OOTACHI;
        this.baseAttackDamage = 50.0;
        this.baseMaxHealth = 72.0;
        this.toukenData.impact = 61;
        this.toukenData.mobility = 13;
        this.toukenData.killing = 32;
        this.toukenData.scouting = 15;
        this.toukenData.concealment = 23;
        this.toukenData.troops = 24;
    }

    @Override
    protected String getEntityNameKey() {
        return "ishikirimaru";
    }

    public static AttributeSupplier setAttributes() {
        return TamableAnimal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 73.0D)
                .add(Attributes.ATTACK_DAMAGE, 50.0D)
                .add(Attributes.ATTACK_SPEED, 1.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.1D)
                .add(Attributes.ARMOR, 20.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 2.0D)
                .build();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, event -> {
            if (this.isInSittingPose()) {
                event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.ishikirimaru.sit"));
                return PlayState.CONTINUE;
            }
            if (event.isMoving()) {
                event.getController().setAnimation(RawAnimation.begin().then("animation.ishikirimaru.walk", Animation.LoopType.LOOP));
                return PlayState.CONTINUE;
            }
            event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.ishikirimaru.idle"));
            return PlayState.CONTINUE;
        }));
        controllers.add(new AnimationController<>(this, "attackController", 0, event -> {
            if (this.swinging) {
                event.getController().setAnimation(RawAnimation.begin().then("animation.ishikirimaru.attack", Animation.LoopType.PLAY_ONCE));
                return PlayState.CONTINUE;
            }
            return PlayState.STOP;
        }));
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean hit = super.doHurtTarget(target);
        if (hit && target instanceof LivingEntity) {
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
        return hit;
    }

    @Override
    protected String getDeathMessageKey() {
        return "death.toukenranbu_mod.ishikirimaru";
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
