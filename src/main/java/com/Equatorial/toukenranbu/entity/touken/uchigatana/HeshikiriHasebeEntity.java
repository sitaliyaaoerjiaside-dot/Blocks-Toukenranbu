package com.Equatorial.toukenranbu.entity.touken.uchigatana;

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

public class HeshikiriHasebeEntity extends ToukenDanshiEntity {

    public HeshikiriHasebeEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
        this.toukenType = ToukenType.UCHIGATANA;
        this.baseAttackDamage = 28.0;
        this.baseMaxHealth = 51.0;
        this.toukenData.impact = 54;
        this.toukenData.mobility = 57;
        this.toukenData.killing = 33;
        this.toukenData.scouting = 43;
        this.toukenData.concealment = 38;
        this.toukenData.troops = 12;
    }

    @Override
    protected String getEntityNameKey() {
        return "heshikiri_hasebe";
    }

    public static AttributeSupplier setAttributes() {
        return TamableAnimal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 51.0D)
                .add(Attributes.ATTACK_DAMAGE, 28.0D)
                .add(Attributes.ATTACK_SPEED, 1.6D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.ARMOR, 10.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D)
                .build();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, event -> {
            if (this.isInSittingPose()) {
                event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.heshikiri_hasebe.sit"));
                return PlayState.CONTINUE;
            }
            if (event.isMoving()) {
                event.getController().setAnimation(RawAnimation.begin().then("animation.heshikiri_hasebe.walk", Animation.LoopType.LOOP));
                return PlayState.CONTINUE;
            }
            event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.heshikiri_hasebe.idle"));
            return PlayState.CONTINUE;
        }));
        controllers.add(new AnimationController<>(this, "attackController", 0, event -> {
            if (this.swinging) {
                event.getController().setAnimation(RawAnimation.begin().then("animation.heshikiri_hasebe.attack", Animation.LoopType.PLAY_ONCE));
                return PlayState.CONTINUE;
            }
            return PlayState.STOP;
        }));
    }

    @Override
    protected String getDeathMessageKey() {
        return "death.toukenranbu_mod.heshikiri_hasebe";
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