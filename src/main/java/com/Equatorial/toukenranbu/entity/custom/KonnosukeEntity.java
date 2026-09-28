package com.Equatorial.toukenranbu.entity.custom;

import com.Equatorial.toukenranbu.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class KonnosukeEntity extends Animal implements GeoEntity {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public KonnosukeEntity(EntityType<? extends Animal> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.5D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.1D, Ingredient.of(ModItems.OIL_TOFU.get()), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        // 用油豆腐喂食 / 吸引繁殖
        if (isFood(stack)) {
            if (!this.level().isClientSide) {
                if (this.isBaby()) {
                    this.ageUp((int)((float)(-this.getAge() / 20) * 0.1F), true);
                } else if (this.canFallInLove()) {
                    this.setInLove(player);
                } else if (this.getHealth() < this.getMaxHealth()) {
                    this.heal(4.0F);
                } else {
                    return super.mobInteract(player, hand);
                }
                if (!player.isCreative()) stack.shrink(1);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }

        return super.mobInteract(player, hand);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(ModItems.OIL_TOFU.get());
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return com.Equatorial.toukenranbu.entity.ModEntityTypes.KONNOSUKE.get().create(level);
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;   // 不因远离玩家而消失
    }

    // ===== GeckoLib =====
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private PlayState predicate(AnimationState<KonnosukeEntity> state) {
        if (state.isMoving()) {
            state.getController().setAnimation(RawAnimation.begin().then("animation.konnosuke.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
        state.getController().setAnimation(RawAnimation.begin().thenLoop("animation.konnosuke.idle"));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() { return cache; }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.FOX_SNIFF, 1.0F, 1.0F);
    }

    @Override
    protected SoundEvent getAmbientSound() { return SoundEvents.FOX_AMBIENT; }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) { return SoundEvents.FOX_HURT; }

    @Override
    protected SoundEvent getDeathSound() { return SoundEvents.FOX_DEATH; }

    @Override
    protected float getSoundVolume() { return 0.2F; }
}