package com.Equatorial.toukenranbu.entity.touken.yari;

import com.Equatorial.toukenranbu.entity.touken.ToukenDanshiEntity;
import com.Equatorial.toukenranbu.touken.ToukenType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

public class TonbokiriEntity extends ToukenDanshiEntity {

    public TonbokiriEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(2.0F);
        this.toukenType = ToukenType.YARI;
        this.baseAttackDamage = 35.0;
        this.baseMaxHealth = 73.0;
        this.toukenData.impact = 63;
        this.toukenData.mobility = 27;
        this.toukenData.killing = 30;
        this.toukenData.scouting = 21;
        this.toukenData.concealment = 33;
        this.toukenData.troops = 20;
    }

    @Override
    protected String getEntityNameKey() {
        return "tonbokiri";
    }

    public static AttributeSupplier setAttributes() {
        return TamableAnimal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 73.0D)
                .add(Attributes.ATTACK_DAMAGE, 35.0D)
                .add(Attributes.ATTACK_SPEED, 1.2D)
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.ARMOR, 15.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .build();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, event -> {
            if (this.isInSittingPose()) {
                event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.tonbokiri.sit"));
                return PlayState.CONTINUE;
            }
            if (event.isMoving()) {
                event.getController().setAnimation(RawAnimation.begin().then("animation.tonbokiri.walk", Animation.LoopType.LOOP));
                return PlayState.CONTINUE;
            }
            event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.tonbokiri.idle"));
            return PlayState.CONTINUE;
        }));
        controllers.add(new AnimationController<>(this, "attackController", 0, event -> {
            if (this.swinging) {
                event.getController().setAnimation(RawAnimation.begin().then("animation.tonbokiri.attack", Animation.LoopType.PLAY_ONCE));
                return PlayState.CONTINUE;
            }
            return PlayState.STOP;
        }));
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        if (!(target instanceof LivingEntity livingTarget)) {
            return false;
        }

        float damage = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);

        MobEffectInstance resistance = livingTarget.getEffect(MobEffects.DAMAGE_RESISTANCE);
        if (resistance != null) {
            int level = resistance.getAmplifier() + 1;
            float reduction = Math.min(0.5f, level * 0.2f);
            damage = damage * (1.0f - reduction);
            livingTarget.removeEffect(MobEffects.DAMAGE_RESISTANCE);
        }

        Registry<DamageType> registry = this.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE);
        ResourceKey<DamageType> key = ResourceKey.create(Registries.DAMAGE_TYPE,
                ResourceLocation.fromNamespaceAndPath("toukenranbu_mod", "yari"));
        DamageSource source = new DamageSource(registry.getHolderOrThrow(key), this, this);

        boolean hurt = livingTarget.hurt(source, damage);

        if (resistance != null) {
            livingTarget.addEffect(new MobEffectInstance(
                    MobEffects.DAMAGE_RESISTANCE,
                    resistance.getDuration(),
                    resistance.getAmplifier(),
                    false, false, true
            ));
        }

        if (hurt) {
            double dx = target.getX() - this.getX();
            double dz = target.getZ() - this.getZ();
            livingTarget.knockback(0.4D, dx, dz);
            this.setLastHurtMob(target);
        }

        return hurt;
    }

    @Override
    protected String getDeathMessageKey() {
        return "death.toukenranbu_mod.tonbokiri";
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