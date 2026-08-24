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

public class KashuuKiyomitsuEntity extends ToukenDanshiEntity {

    public KashuuKiyomitsuEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
        this.toukenType = ToukenType.UCHIGATANA;
        this.baseAttackDamage = 28.0;
        this.baseMaxHealth = 53.0;
        // 六维属性，每个子类自己定
        this.toukenData.impact = 50;      // 冲力
        this.toukenData.mobility = 51;     // 机动
        this.toukenData.killing = 41;     // 必杀
        this.toukenData.scouting = 46;    // 侦察
        this.toukenData.concealment = 42;  // 隐蔽
        this.toukenData.troops = 12;      // 兵力
    }

    @Override
    protected String getEntityNameKey() {
        return "kashuu_kiyomitsu";
    }

    public static AttributeSupplier setAttributes() {
        return TamableAnimal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 53.0D)// 初始生命值（会被 baseMaxHealth + 刀装加成覆盖）
                .add(Attributes.ATTACK_DAMAGE, 28.0D)// 初始攻击力（会被 baseAttackDamage + 昼夜/低血量/刀装加成覆盖）
                .add(Attributes.ATTACK_SPEED, 1.6D)// 攻击速度：1.5 = 每秒攻击1.5次，越高挥得越快
                .add(Attributes.MOVEMENT_SPEED, 0.3D)// 移动速度：0.3 ≈ 玩家步行速度，越高跑得越快
                .add(Attributes.ARMOR, 10.0D)//加入了护甲值，以免高速枪太超模
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D) // 抗击退，防止被枪捅飞
                .build();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, event -> {
            if (this.isInSittingPose()) {
                event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.kashuu_kiyomitsu.sit"));
                return PlayState.CONTINUE;
            }
            if (event.isMoving()) {
                event.getController().setAnimation(RawAnimation.begin().then("animation.kashuu_kiyomitsu.walk", Animation.LoopType.LOOP));
                return PlayState.CONTINUE;
            }
            event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.kashuu_kiyomitsu.idle"));
            return PlayState.CONTINUE;
        }));
        controllers.add(new AnimationController<>(this, "attackController", 0, event -> {
            if (this.swinging) {
                event.getController().setAnimation(RawAnimation.begin().then("animation.kashuu_kiyomitsu.attack", Animation.LoopType.PLAY_ONCE));
                return PlayState.CONTINUE;
            }
            return PlayState.STOP;
        }));
    }

    // ===== 加在这里，registerControllers 的 } 下面，类的 } 上面 =====
    @Override
    protected String getDeathMessageKey() {
        return "death.toukenranbu_mod.kashuu_kiyomitsu";
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