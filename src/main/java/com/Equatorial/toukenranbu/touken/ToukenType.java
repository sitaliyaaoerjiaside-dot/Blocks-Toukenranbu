package com.Equatorial.toukenranbu.touken;

public enum ToukenType {
    // 格式: 名称, 白天索敌范围, 攻击范围
    TACHI("tachi", 24.0, 8.0,12.0),           // 太刀
    OOTACHI("ootachi", 14.0, 4.0, 13.0),       // 大太刀
    UCHIGATANA("uchigatana", 28.0, 6.0, 11.0), // 打刀
    WAKIZASHI("wakizashi", 32.0, 8.0, 9.0),     // 胁差
    TANTOU("tantou", 42.0, 8.0, 10.0),          // 短刀
    NAGINATA("naginata", 16.0, 6.0, 10.0),      // 薙刀
    YARI("yari", 20.0, 6.0, 10.0),          // 枪
    // 以后加新刀直接在这里加一行即可
    ;

    public final String id;
    public final double baseFollowRange;   // 白天索敌
    public final double nightFollowRange;  // 夜晚索敌
    public final double attackRange;

    ToukenType(String id, double base, double night, double attack) {
        this.id = id;
        this.baseFollowRange = base;
        this.nightFollowRange = night;
        this.attackRange = attack;
    }

    public boolean isNightSensitive() {
        return this == TACHI || this == OOTACHI;
    }
}