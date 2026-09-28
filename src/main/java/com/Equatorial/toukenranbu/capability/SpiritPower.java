// src/main/java/com/Equatorial/toukenranbu/capability/SpiritPower.java
package com.Equatorial.toukenranbu.capability;

public class SpiritPower implements ISpiritPower {
    private int spiritPower = 50;
    private static final int MAX_SPIRIT_POWER = 100;
    private boolean friendlyFire = true; // 默认开启友伤
    private boolean danshiXp = false; // 默认关闭

    @Override
    public int getSpiritPower() { return spiritPower; }

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

    // 新增
    @Override
    public boolean isFriendlyFireEnabled() {
        return friendlyFire;
    }

    @Override
    public void setFriendlyFireEnabled(boolean enabled) {
        this.friendlyFire = enabled;
    }

    @Override
    public boolean isDanshiXpEnabled() { return danshiXp; }

    @Override
    public void setDanshiXpEnabled(boolean enabled) { this.danshiXp = enabled; }
}