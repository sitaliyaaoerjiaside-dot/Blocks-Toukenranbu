// src/main/java/com/Equatorial/toukenranbu/capability/SpiritPower.java
package com.Equatorial.toukenranbu.capability;

public class SpiritPower implements ISpiritPower {
    private int spiritPower = 50;
    private static final int MAX_SPIRIT_POWER = 100;

    @Override
    public int getSpiritPower() {
        return spiritPower;
    }

    @Override
    public void setSpiritPower(int amount) {
        this.spiritPower = Math.max(0, Math.min(amount, MAX_SPIRIT_POWER));
    }

    @Override
    public void addSpiritPower(int amount) {
        setSpiritPower(this.spiritPower + amount);
    }

    @Override
    public boolean consumeSpiritPower(int amount) {
        if (this.spiritPower >= amount) {
            this.spiritPower -= amount;
            return true;
        }
        return false;
    }

    @Override
    public int getMaxSpiritPower() {
        return MAX_SPIRIT_POWER;
    }
}