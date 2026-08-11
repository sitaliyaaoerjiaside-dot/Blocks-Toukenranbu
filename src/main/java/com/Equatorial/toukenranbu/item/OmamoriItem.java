package com.Equatorial.toukenranbu.item;

import net.minecraft.world.item.Item;

public class OmamoriItem extends Item {
    private final double attackBonus;
    private final double healthBonus;
    private final double speedBonus;

    public OmamoriItem(double attackBonus, double healthBonus, double speedBonus, Properties properties) {
        super(properties);
        this.attackBonus = attackBonus;
        this.healthBonus = healthBonus;
        this.speedBonus = speedBonus;
    }

    public double getAttackBonus() { return attackBonus; }
    public double getHealthBonus() { return healthBonus; }
    public double getSpeedBonus() { return speedBonus; }
}