package com.Equatorial.toukenranbu.entity.touken;

import net.minecraft.nbt.CompoundTag;

public class ToukenEntityData {
    // ===== 基础六维（子类构造函数里设定，每个刀不同）=====
    public int impact = 10;        // 冲力
    public int mobility = 10;      // 机动
    public int killing = 10;       // 必杀
    public int scouting = 10;      // 侦察
    public int concealment = 10;   // 隐蔽
    public int troops = 10;        // 兵力
    public int fatigue = 50;       // 疲劳度
    public int formationLevel = 0;
    public int formationCount = 0;

    // ===== 刀装加成（由 updateKnifeBonuses 计算后写入）=====
    public int knifeImpactBonus = 0;
    public int knifeMobilityBonus = 0;
    public int knifeKillingBonus = 0;
    public int knifeScoutingBonus = 0;
    public int knifeConcealmentBonus = 0;
    public int knifeTroopsBonus = 0;

    // ===== 马匹加成 =====
    public int mountImpactBonus = 0;
    public int mountMobilityBonus = 0;
    public int mountKillingBonus = 0;
    public int mountScoutingBonus = 0;
    public int mountConcealmentBonus = 0;
    public int mountTroopsBonus = 0;

    public int bladeImpactBonus = 0;
    public int bladeMobilityBonus = 0;
    public int bladeKillingBonus = 0;
    public int bladeScoutingBonus = 0;
    public int bladeConcealmentBonus = 0;
    public int bladeTroopsBonus = 0;

    // ===== 疲劳度倍率：原作机制 =====
    // 50~100 樱吹雪(+20%) | 49~40 通常(x1.0) | 39~20 疲劳(-20%) | 19~0 严重疲劳(-40%)
    public double getFatigueMultiplier() {
        if (fatigue >= 50) return 1.2;
        if (fatigue >= 40) return 1.0;
        if (fatigue >= 20) return 0.8;
        return 0.6;
    }

    // ===== 有效属性 = (基础值 + 刀装加成) × 疲劳度倍率 =====
    // UI 和实际战斗都读这些值
    public int getEffectiveImpact()      { return (int) Math.round((impact + knifeImpactBonus + mountImpactBonus + bladeImpactBonus) * getFatigueMultiplier()); }
    public int getEffectiveMobility()    { return (int) Math.round((mobility + knifeMobilityBonus + mountMobilityBonus + bladeMobilityBonus) * getFatigueMultiplier()); }
    public int getEffectiveKilling()     { return (int) Math.round((killing + knifeKillingBonus + mountKillingBonus + bladeKillingBonus) * getFatigueMultiplier()); }
    public int getEffectiveScouting()    { return (int) Math.round((scouting + knifeScoutingBonus + mountScoutingBonus + bladeScoutingBonus) * getFatigueMultiplier()); }
    public int getEffectiveConcealment() { return (int) Math.round((concealment + knifeConcealmentBonus + mountConcealmentBonus + bladeConcealmentBonus) * getFatigueMultiplier()); }
    public int getEffectiveTroops()      { return (int) Math.round((troops + knifeTroopsBonus + mountTroopsBonus + bladeTroopsBonus) * getFatigueMultiplier()); }

    // 状态文本键
    public String getFatigueStatusKey() {
        if (fatigue >= 50) return "gui.toukenranbu.status.sakura";
        if (fatigue >= 40) return "gui.toukenranbu.status.normal";
        if (fatigue >= 20) return "gui.toukenranbu.status.tired";
        return "gui.toukenranbu.status.exhausted";
    }

    // 状态颜色
    public int getFatigueStatusColor() {
        if (fatigue >= 50) return 0xFFFF69B4; // 粉色 樱吹雪
        if (fatigue >= 40) return 0xFFFFFFFF; // 白色 通常
        if (fatigue >= 20) return 0xFFFFFF00; // 黄色 疲劳
        return 0xFFFF0000; // 红色 严重疲劳
    }

    public float getFatiguePercent() {
        return fatigue / 100f;
    }

    public CompoundTag serialize() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("impact", impact);
        tag.putInt("mobility", mobility);
        tag.putInt("killing", killing);
        tag.putInt("scouting", scouting);
        tag.putInt("concealment", concealment);
        tag.putInt("troops", troops);
        tag.putInt("fatigue", fatigue);
        tag.putInt("FormationLevel", formationLevel);
        tag.putInt("FormationCount", formationCount);

        // 刀装加成也要存，否则退出重进显示不对
        tag.putInt("knifeImpactBonus", knifeImpactBonus);
        tag.putInt("knifeMobilityBonus", knifeMobilityBonus);
        tag.putInt("knifeKillingBonus", knifeKillingBonus);
        tag.putInt("knifeScoutingBonus", knifeScoutingBonus);
        tag.putInt("knifeConcealmentBonus", knifeConcealmentBonus);
        tag.putInt("knifeTroopsBonus", knifeTroopsBonus);

        //马匹加成存档
        tag.putInt("mountImpactBonus", mountImpactBonus);
        tag.putInt("mountMobilityBonus", mountMobilityBonus);
        tag.putInt("mountKillingBonus", mountKillingBonus);
        tag.putInt("mountScoutingBonus", mountScoutingBonus);
        tag.putInt("mountConcealmentBonus", mountConcealmentBonus);
        tag.putInt("mountTroopsBonus", mountTroopsBonus);

        tag.putInt("bladeImpactBonus", bladeImpactBonus);
        tag.putInt("bladeMobilityBonus", bladeMobilityBonus);
        tag.putInt("bladeKillingBonus", bladeKillingBonus);
        tag.putInt("bladeScoutingBonus", bladeScoutingBonus);
        tag.putInt("bladeConcealmentBonus", bladeConcealmentBonus);
        tag.putInt("bladeTroopsBonus", bladeTroopsBonus);

        return tag;
    }

    public void deserialize(CompoundTag tag) {
        impact = tag.getInt("impact");
        mobility = tag.getInt("mobility");
        killing = tag.getInt("killing");
        scouting = tag.getInt("scouting");
        concealment = tag.getInt("concealment");
        troops = tag.getInt("troops");
        fatigue = tag.getInt("fatigue");
        formationLevel = tag.getInt("FormationLevel");
        formationCount = tag.getInt("FormationCount");

        knifeImpactBonus = tag.getInt("knifeImpactBonus");
        knifeMobilityBonus = tag.getInt("knifeMobilityBonus");
        knifeKillingBonus = tag.getInt("knifeKillingBonus");
        knifeScoutingBonus = tag.getInt("knifeScoutingBonus");
        knifeConcealmentBonus = tag.getInt("knifeConcealmentBonus");
        knifeTroopsBonus = tag.getInt("knifeTroopsBonus");

        mountImpactBonus = tag.getInt("mountImpactBonus");
        mountMobilityBonus = tag.getInt("mountMobilityBonus");
        mountKillingBonus = tag.getInt("mountKillingBonus");
        mountScoutingBonus = tag.getInt("mountScoutingBonus");
        mountConcealmentBonus = tag.getInt("mountConcealmentBonus");
        mountTroopsBonus = tag.getInt("mountTroopsBonus");

        bladeImpactBonus = tag.getInt("bladeImpactBonus");
        bladeMobilityBonus = tag.getInt("bladeMobilityBonus");
        bladeKillingBonus = tag.getInt("bladeKillingBonus");
        bladeScoutingBonus = tag.getInt("bladeScoutingBonus");
        bladeConcealmentBonus = tag.getInt("bladeConcealmentBonus");
        bladeTroopsBonus = tag.getInt("bladeTroopsBonus");

    }
}