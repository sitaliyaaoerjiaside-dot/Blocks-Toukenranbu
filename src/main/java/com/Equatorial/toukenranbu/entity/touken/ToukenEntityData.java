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

    // ===== 等级系统 =====
    public static final int MAX_LEVEL = 99;
    public static final long MAX_EXPERIENCE = 9999999L;

    public int level = 1;
    public long experience = 0L;
    public int lastLevelUpStat = -1;

    public int levelImpact = 0;
    public int levelMobility = 0;
    public int levelKilling = 0;
    public int levelScouting = 0;
    public int levelConcealment = 0;
    public int levelTroops = 0;

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
    public int getEffectiveImpact()      { return (int) Math.round((impact + levelImpact + knifeImpactBonus + mountImpactBonus + bladeImpactBonus) * getFatigueMultiplier()); }
    public int getEffectiveMobility()    { return (int) Math.round((mobility + levelMobility + knifeMobilityBonus + mountMobilityBonus + bladeMobilityBonus) * getFatigueMultiplier()); }
    public int getEffectiveKilling()     { return (int) Math.round((killing + levelKilling + knifeKillingBonus + mountKillingBonus + bladeKillingBonus) * getFatigueMultiplier()); }
    public int getEffectiveScouting()    { return (int) Math.round((scouting + levelScouting + knifeScoutingBonus + mountScoutingBonus + bladeScoutingBonus) * getFatigueMultiplier()); }
    public int getEffectiveConcealment() { return (int) Math.round((concealment + levelConcealment + knifeConcealmentBonus + mountConcealmentBonus + bladeConcealmentBonus) * getFatigueMultiplier()); }
    public int getEffectiveTroops()      { return (int) Math.round((troops + levelTroops + knifeTroopsBonus + mountTroopsBonus + bladeTroopsBonus) * getFatigueMultiplier()); }

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


    // ===== 等级曲线 =====
    public static long totalExpForLevel(int targetLevel) {
        if (targetLevel <= 1) return 0;
        return (long) (100 * (Math.pow(1.075, targetLevel - 1) - 1) / 0.075);
    }

    public long getCurrentLevelExp() {
        return totalExpForLevel(level);
    }

    public long getNextLevelExp() {
        if (level >= MAX_LEVEL) return totalExpForLevel(MAX_LEVEL);
        return totalExpForLevel(level + 1);
    }

    public float getLevelProgress() {
        if (level >= MAX_LEVEL) return 1.0f;
        long cur = getCurrentLevelExp();
        long next = getNextLevelExp();
        if (next <= cur) return 0.0f;
        return Math.max(0f, Math.min(1f, (float)(experience - cur) / (next - cur)));
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

        // 等级系统
        tag.putInt("Level", level);
        tag.putLong("Experience", experience);
        tag.putInt("LastLevelUpStat", lastLevelUpStat);
        tag.putInt("LevelImpact", levelImpact);
        tag.putInt("LevelMobility", levelMobility);
        tag.putInt("LevelKilling", levelKilling);
        tag.putInt("LevelScouting", levelScouting);
        tag.putInt("LevelConcealment", levelConcealment);
        tag.putInt("LevelTroops", levelTroops);

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

        level = tag.contains("Level") ? tag.getInt("Level") : 1;
        experience = tag.contains("Experience") ? tag.getLong("Experience") : 0L;
        lastLevelUpStat = tag.contains("LastLevelUpStat") ? tag.getInt("LastLevelUpStat") : -1;
        levelImpact = tag.getInt("LevelImpact");
        levelMobility = tag.getInt("LevelMobility");
        levelKilling = tag.getInt("LevelKilling");
        levelScouting = tag.getInt("LevelScouting");
        levelConcealment = tag.getInt("LevelConcealment");
        levelTroops = tag.getInt("LevelTroops");

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