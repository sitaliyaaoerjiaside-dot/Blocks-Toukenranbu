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

public class MikazukiMunechikaEntity extends ToukenDanshiEntity {

    public MikazukiMunechikaEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(1.0F);
        this.toukenType = ToukenType.TACHI;
        this.baseAttackDamage = 40.0;
        this.baseMaxHealth = 63.0;
        // 六维属性，每个子类自己定
        this.toukenData.impact = 55;      // 冲力
        this.toukenData.mobility = 37;     // 机动
        this.toukenData.killing = 30;     // 必杀
        this.toukenData.scouting = 38;    // 侦察
        this.toukenData.concealment = 35;  // 隐蔽
        this.toukenData.troops = 16;      // 兵力
    }

    @Override
    protected String getEntityNameKey() {
        return "mikazuki_munechika";
    }

    public static AttributeSupplier setAttributes() {
        return TamableAnimal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 73.0D)// 初始生命值（会被 baseMaxHealth + 刀装加成覆盖）
                .add(Attributes.ATTACK_DAMAGE, 40.0D)// 初始攻击力（会被 baseAttackDamage + 昼夜/低血量/刀装加成覆盖）
                .add(Attributes.ATTACK_SPEED, 1.5D)// 攻击速度：1.5 = 每秒攻击1.5次，越高挥得越快
                .add(Attributes.MOVEMENT_SPEED, 0.24D)// 移动速度：0.3 ≈ 玩家步行速度，越高跑得越快
                .add(Attributes.ARMOR, 10.0D)// 加入了护甲值，以免高速枪太超模
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D) // 抗击退，防止被枪捅飞
                .build();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, event -> {
            if (this.isInSittingPose()) {
                event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.mikazuki_munechika.sit"));
                return PlayState.CONTINUE;
            }
            if (event.isMoving()) {
                event.getController().setAnimation(RawAnimation.begin().then("animation.mikazuki_munechika.walk", Animation.LoopType.LOOP));
                return PlayState.CONTINUE;
            }
            event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.mikazuki_munechika.idle"));
            return PlayState.CONTINUE;
        }));
        controllers.add(new AnimationController<>(this, "attackController", 0, event -> {
            if (this.swinging) {
                event.getController().setAnimation(RawAnimation.begin().then("animation.mikazuki_munechika.attack", Animation.LoopType.PLAY_ONCE));
                return PlayState.CONTINUE;
            }
            return PlayState.STOP;
        }));
    }

    // ===== 加在这里，registerControllers 的 } 下面，类的 } 上面 =====
    @Override
    protected String getDeathMessageKey() {
        return "death.toukenranbu_mod.mikazuki_munechika";
    }
    // ============================================================

    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();

        // 永久抗性提升 I（减20%伤害）
        this.addEffect(new MobEffectInstance(
                MobEffects.DAMAGE_RESISTANCE,
                -1,        // 永久
                0,         // I级
                false,     // 无环境粒子
                false,     // 不显示粒子
                true       // 显示图标
        ));
    }
}