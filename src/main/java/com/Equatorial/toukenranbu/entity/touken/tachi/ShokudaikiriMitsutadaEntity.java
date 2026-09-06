package com.Equatorial.toukenranbu.entity.touken.tachi;

import com.Equatorial.toukenranbu.entity.touken.ToukenDanshiEntity;
import com.Equatorial.toukenranbu.touken.ToukenType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

public class ShokudaikiriMitsutadaEntity extends ToukenDanshiEntity {

    public ShokudaikiriMitsutadaEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(1.0F);
        this.toukenType = ToukenType.TACHI;
        this.baseAttackDamage = 40.0;
        this.baseMaxHealth = 65.0;
        this.toukenData.impact = 53;
        this.toukenData.mobility = 34;
        this.toukenData.killing = 30;
        this.toukenData.scouting = 37;
        this.toukenData.concealment = 30;
        this.toukenData.troops = 16;
    }

    @Override
    protected String getEntityNameKey() {
        return "shokudaikiri_mitsutada";
    }

    public static AttributeSupplier setAttributes() {
        return TamableAnimal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 75.0D)
                .add(Attributes.ATTACK_DAMAGE, 40.0D)
                .add(Attributes.ATTACK_SPEED, 1.5D)
                .add(Attributes.MOVEMENT_SPEED, 0.24D)
                .add(Attributes.ARMOR, 10.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .build();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, event -> {
            if (this.isInSittingPose()) {
                event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.shokudaikiri_mitsutada.sit"));
                return PlayState.CONTINUE;
            }
            if (event.isMoving()) {
                event.getController().setAnimation(RawAnimation.begin().then("animation.shokudaikiri_mitsutada.walk", Animation.LoopType.LOOP));
                return PlayState.CONTINUE;
            }
            event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.shokudaikiri_mitsutada.idle"));
            return PlayState.CONTINUE;
        }));
        controllers.add(new AnimationController<>(this, "attackController", 0, event -> {
            if (this.swinging) {
                event.getController().setAnimation(RawAnimation.begin().then("animation.shokudaikiri_mitsutada.attack", Animation.LoopType.PLAY_ONCE));
                return PlayState.CONTINUE;
            }
            return PlayState.STOP;
        }));
    }

    @Override
    protected String getDeathMessageKey() {
        return "death.toukenranbu_mod.shokudaikiri_mitsutada";
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