package com.Equatorial.toukenranbu.touken;

public enum FormationType {
    NONE("gui.toukenranbu.formation.none", 0, 0, 0, 0),
    FISH_SCALE("gui.toukenranbu.formation.fish_scale", 0.06, 0, 0, 0),
    CRANE_WING("gui.toukenranbu.formation.crane_wing", 0, 0.05, 0, 0),
    GOOSE_LINE("gui.toukenranbu.formation.goose_line", 0, 0, 0.05, 0.10),
    SQUARE("gui.toukenranbu.formation.square", 0.03, 0.03, 0, 0.05);

    public final String translationKey;
    public final double atkPerLevel;
    public final double defPerLevel;
    public final double spdPerLevel;
    public final double rangePerLevel;

    FormationType(String key, double atk, double def, double spd, double range) {
        this.translationKey = key;
        this.atkPerLevel = atk;
        this.defPerLevel = def;
        this.spdPerLevel = spd;
        this.rangePerLevel = range;
    }

    public double getMult(int level, double perLevel) {
        if (level <= 0 || perLevel <= 0) return 1.0;
        return 1.0 + perLevel * level;
    }
}