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

public class YamatonokamiYasusadaEntity extends ToukenDanshiEntity {

    public YamatonokamiYasusadaEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
        this.toukenType = ToukenType.UCHIGATANA;
        this.baseAttackDamage = 28.0;
        this.baseMaxHealth = 47.0;
        this.toukenData.impact = 49;
        this.toukenData.mobility = 49;
        this.toukenData.killing = 38;
        this.toukenData.scouting = 43;
        this.toukenData.concealment = 39;
        this.toukenData.troops = 12;
    }

    @Override
    protected String getEntityNameKey() {
        return "yamatonokami_yasusada";
    }

    public static AttributeSupplier setAttributes() {
        return TamableAnimal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 47.0D)
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
                event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.yamatonokami_yasusada.sit"));
                return PlayState.CONTINUE;
            }
            if (event.isMoving()) {
                event.getController().setAnimation(RawAnimation.begin().then("animation.yamatonokami_yasusada.walk", Animation.LoopType.LOOP));
                return PlayState.CONTINUE;
            }
            event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.yamatonokami_yasusada.idle"));
            return PlayState.CONTINUE;
        }));
        controllers.add(new AnimationController<>(this, "attackController", 0, event -> {
            if (this.swinging) {
                event.getController().setAnimation(RawAnimation.begin().then("animation.yamatonokami_yasusada.attack", Animation.LoopType.PLAY_ONCE));
                return PlayState.CONTINUE;
            }
            return PlayState.STOP;
        }));
    }

    @Override
    protected String getDeathMessageKey() {
        return "death.toukenranbu_mod.yamatonokami_yasusada";
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