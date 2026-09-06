package com.Equatorial.toukenranbu.entity.touken.wakizashi;

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

public class HorikawaKunihiroEntity extends ToukenDanshiEntity {

    public HorikawaKunihiroEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
        this.toukenType = ToukenType.WAKIZASHI;
        this.baseAttackDamage = 20.0;
        this.baseMaxHealth = 37.0;
        this.toukenData.impact = 36;
        this.toukenData.mobility = 50;
        this.toukenData.killing = 45;
        this.toukenData.scouting = 53;
        this.toukenData.concealment = 46;
        this.toukenData.troops = 8;
    }

    @Override
    protected String getEntityNameKey() {
        return "horikawa_kunihiro";
    }

    public static AttributeSupplier setAttributes() {
        return TamableAnimal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 37.0D)
                .add(Attributes.ATTACK_DAMAGE, 20.0D)
                .add(Attributes.ATTACK_SPEED, 1.7D)
                .add(Attributes.MOVEMENT_SPEED, 0.4D)
                .add(Attributes.ARMOR, 8.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .build();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, event -> {
            if (this.isInSittingPose()) {
                event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.horikawa_kunihiro.sit"));
                return PlayState.CONTINUE;
            }
            if (event.isMoving()) {
                event.getController().setAnimation(RawAnimation.begin().then("animation.horikawa_kunihiro.walk", Animation.LoopType.LOOP));
                return PlayState.CONTINUE;
            }
            event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.horikawa_kunihiro.idle"));
            return PlayState.CONTINUE;
        }));
        controllers.add(new AnimationController<>(this, "attackController", 0, event -> {
            if (this.swinging) {
                event.getController().setAnimation(RawAnimation.begin().then("animation.horikawa_kunihiro.attack", Animation.LoopType.PLAY_ONCE));
                return PlayState.CONTINUE;
            }
            return PlayState.STOP;
        }));
    }

    @Override
    protected String getDeathMessageKey() {
        return "death.toukenranbu_mod.horikawa_kunihiro";
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
