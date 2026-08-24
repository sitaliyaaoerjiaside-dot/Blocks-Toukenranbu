package com.Equatorial.toukenranbu.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ToukenHorseItem extends Item {
    private final int impact;
    private final int mobility;
    private final int killing;
    private final int scouting;
    private final int concealment;
    private final int troops;
    private final double speedBonus;

    public ToukenHorseItem(Properties properties, int impact, int mobility, int killing,
                           int scouting, int concealment, int troops, double speedBonus) {
        super(properties.stacksTo(1));
        this.impact = impact;
        this.mobility = mobility;
        this.killing = killing;
        this.scouting = scouting;
        this.concealment = concealment;
        this.troops = troops;
        this.speedBonus = speedBonus;
    }

    public int getImpactBonus()   { return impact; }
    public int getMobilityBonus() { return mobility; }
    public int getKillingBonus()  { return killing; }
    public int getScoutingBonus() { return scouting; }
    public int getConcealmentBonus() { return concealment; }
    public int getTroopsBonus()   { return troops; }
    public double getSpeedBonus() { return speedBonus; }
}
