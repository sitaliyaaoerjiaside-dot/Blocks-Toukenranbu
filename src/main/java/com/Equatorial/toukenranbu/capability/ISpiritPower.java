// src/main/java/com/Equatorial/toukenranbu/capability/ISpiritPower.java
package com.Equatorial.toukenranbu.capability;

public interface ISpiritPower {
    int getSpiritPower();
    void setSpiritPower(int amount);
    void addSpiritPower(int amount);
    boolean consumeSpiritPower(int amount);
    int getMaxSpiritPower();
}