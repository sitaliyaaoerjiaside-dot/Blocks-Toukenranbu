package com.Equatorial.toukenranbu;

import com.Equatorial.toukenranbu.entity.touken.ToukenDanshiEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ToukenCombatEvents {

    /**
     * 必杀暴击 + 隐蔽闪避
     * 改概率和倍率只改 static final 那几行
     */

    // 必杀：暴击概率上限（0.3 = 60%），防止100%暴击
    private static final double CRIT_CHANCE_CAP = 0.60;
    // 必杀：每点必杀加多少暴击概率（0.01 = 1%）
    private static final double CRIT_CHANCE_PER_KILLING = 0.01;
    // 必杀：基础暴击倍率（1.5 = 150%伤害）
    private static final float CRIT_BASE_MULT = 1.5f;
    // 必杀：每点必杀加多少额外倍率（0.02 = 2%）
    private static final float CRIT_BONUS_PER_KILLING = 0.02f;

    // 隐蔽：闪避概率上限（0.25 = 25%）
    private static final double DODGE_CHANCE_CAP = 0.25;
    // 隐蔽：每点隐蔽加多少闪避概率（0.01 = 1%）
    private static final double DODGE_CHANCE_PER_CONCEALMENT = 0.01;
    // 隐蔽：触发闪避后伤害减免比例（0.5 = 减免50%，只受一半伤）
    private static final float DODGE_DAMAGE_REDUCTION = 0.5f;

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        DamageSource source = event.getSource();
        if (source == null) return;

        // ===== 必杀：攻击者是刀剑男士 → 概率暴击 =====
        if (source.getEntity() instanceof ToukenDanshiEntity attacker) {
            int killing = attacker.toukenData.getEffectiveKilling();
            if (killing > 0) {
                // 计算暴击概率（必杀值×1%，不超过上限）
                double critChance = Math.min(CRIT_CHANCE_CAP, killing * CRIT_CHANCE_PER_KILLING);
                if (attacker.getRandom().nextDouble() < critChance) {
                    // 暴击触发：伤害 × 倍率
                    float mult = CRIT_BASE_MULT + (killing * CRIT_BONUS_PER_KILLING);
                    event.setAmount(event.getAmount() * mult);
                    // 可以在这里加暴击粒子/音效，目前先保持简洁
                }
            }
        }

        // ===== 隐蔽：受害者是刀剑男士 → 概率闪避 =====
        if (event.getEntity() instanceof ToukenDanshiEntity victim) {
            int concealment = victim.toukenData.getEffectiveConcealment();
            if (concealment > 0) {
                // 计算闪避概率（隐蔽值×1%，不超过上限）
                double dodgeChance = Math.min(DODGE_CHANCE_CAP, concealment * DODGE_CHANCE_PER_CONCEALMENT);
                if (victim.getRandom().nextDouble() < dodgeChance) {
                    // 闪避触发：只受一半伤害
                    event.setAmount(event.getAmount() * DODGE_DAMAGE_REDUCTION);
                }
            }
        }
    }
}