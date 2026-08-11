package com.Equatorial.toukenranbu.entity.custom.kebiishi;

import com.Equatorial.toukenranbu.entity.ai.goal.KebiishiTargetGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class KebiishiTachiEntity extends Monster implements GeoEntity, KebiishiEntity {
    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    // 白天基准值（用于动态成长计算）
    private double baseAttack;
    private double baseHealth;
    private double baseSpeed;

    public KebiishiTachiEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.setMaxUpStep(1.0F);
    }

    public static AttributeSupplier setAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 275.0D)      // 基础血量（会被动态成长放大）
                .add(Attributes.ATTACK_DAMAGE, 55.0D)    // 基础攻击（会被动态成长放大）
                .add(Attributes.ATTACK_SPEED, 1.2D)
                .add(Attributes.MOVEMENT_SPEED, 0.22D)
                .add(Attributes.ARMOR, 12.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.6D)
                .add(Attributes.FOLLOW_RANGE, 64.0D)
                .build();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, false));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));

        // 核心：第三方仇恨（玩家 + 刀剑男士 + 溯行军）
        this.targetSelector.addGoal(1, new KebiishiTargetGoal(this));
    }

    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        // 记录基准值
        this.baseAttack = this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
        this.baseHealth = this.getAttributeBaseValue(Attributes.MAX_HEALTH);
        this.baseSpeed = this.getAttributeBaseValue(Attributes.MOVEMENT_SPEED);

        // 应用动态成长
        applyScaling();

        // 检非违使自带抗性提升 I（历史修正力的加护）
        this.addEffect(new MobEffectInstance(
                MobEffects.DAMAGE_RESISTANCE,
                -1, 0, false, false, true
        ));
    }

    /**
     * 扫描玩家装备，计算战斗力评分
     * 自定义护甲（非原版材质）自动按铁套 +10 分算
     */
    private int calculatePlayerPower(ServerPlayer player) {
        int score = 0;

        // 护甲评分
        for (ItemStack stack : player.getArmorSlots()) {
            if (stack.getItem() instanceof ArmorItem armor) {
                ArmorMaterial mat = armor.getMaterial();

                if (mat == ArmorMaterials.LEATHER) score += 5;
                else if (mat == ArmorMaterials.IRON) score += 10;
                else if (mat == ArmorMaterials.DIAMOND) score += 20;
                else if (mat == ArmorMaterials.NETHERITE) score += 30;
                else score += 10; // ← 你的自定义护甲会落到这里，按铁套算

                // 附魔分（每个附魔 +2，自定义护甲的附魔也算）
                score += stack.getEnchantmentTags().size() * 2;
            }
        }

        // 主武器评分
        ItemStack mainHand = player.getMainHandItem();
        if (mainHand.getItem() instanceof SwordItem || mainHand.getItem() instanceof AxeItem) {
            if (mainHand.is(Items.WOODEN_SWORD) || mainHand.is(Items.WOODEN_AXE)) score += 3;
            else if (mainHand.is(Items.STONE_SWORD) || mainHand.is(Items.STONE_AXE)) score += 5;
            else if (mainHand.is(Items.IRON_SWORD) || mainHand.is(Items.IRON_AXE)) score += 10;
            else if (mainHand.is(Items.DIAMOND_SWORD) || mainHand.is(Items.DIAMOND_AXE)) score += 20;
            else if (mainHand.is(Items.NETHERITE_SWORD) || mainHand.is(Items.NETHERITE_AXE)) score += 30;
            else score += 10; // 你的自定义武器也按铁剑算

            score += mainHand.getEnchantmentTags().size() * 2;
        }

        return score;
    }

    /**
     * 动态成长：根据附近玩家装备评分调整属性
     */
    private void applyScaling() {
        if (this.level().isClientSide()) return;
        if (!(this.level() instanceof ServerLevel serverLevel)) return;

        int maxPower = 0;
        for (ServerPlayer player : serverLevel.getPlayers(p -> p.distanceToSqr(this) < 10000.0)) {
            int power = calculatePlayerPower(player);
            if (power > maxPower) maxPower = power;
        }

        // 每10分1级，封顶50级
        // 0~9分=0级(无成长)，10~19分=1级，20~29分=2级...
        int effectiveLevel = Math.min(50, maxPower / 10);

        float attackMult = 1.0f + (effectiveLevel * 0.03f);
        float healthMult = 1.0f + (effectiveLevel * 0.04f);

        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(this.baseAttack * attackMult);
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(this.baseHealth * healthMult);
        this.setHealth((float) (this.baseHealth * healthMult));
    }

    // ===== 动画部分 =====
    private <E extends KebiishiTachiEntity> PlayState predicate(final AnimationState<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(RawAnimation.begin().then("animation.tachi.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.tachi.idle"));
        return PlayState.CONTINUE;
    }

    private <E extends KebiishiTachiEntity> PlayState attackPredicate(final AnimationState<E> event) {
        if (this.swinging) {
            event.getController().setAnimation(RawAnimation.begin().then("animation.tachi.attack", Animation.LoopType.PLAY_ONCE));
            this.swinging = false;
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, this::predicate));
        controllers.add(new AnimationController<>(this, "attackController", 0, this::attackPredicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    // ===== 声音 =====
    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.IRON_GOLEM_STEP, 1.0F, 0.8F);
    }

    @Override
    protected SoundEvent getAmbientSound() { return SoundEvents.IRON_GOLEM_REPAIR; }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) { return SoundEvents.IRON_GOLEM_HURT; }

    @Override
    protected SoundEvent getDeathSound() { return SoundEvents.IRON_GOLEM_DEATH; }

    @Override
    protected float getSoundVolume() { return 0.3F; }

    @Override
    public boolean startRiding(Entity entity, boolean force) {
        // 检非违使无法被船或矿车困住。
        if (entity instanceof net.minecraft.world.entity.vehicle.Boat
                || entity instanceof net.minecraft.world.entity.vehicle.AbstractMinecart) {
            return false;
        }
        return super.startRiding(entity, force);
    }

    @Override
    public boolean fireImmune() { return true; }  // 免疫火焰

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
        return false;  // 免疫摔落
    }

    @Override
    public boolean canBreatheUnderwater() { return true; }  // 免疫溺水
}
