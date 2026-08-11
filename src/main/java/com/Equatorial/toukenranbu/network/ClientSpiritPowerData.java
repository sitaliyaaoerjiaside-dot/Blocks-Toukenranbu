// src/main/java/com/Equatorial/toukenranbu/network/ClientSpiritPowerData.java
package com.Equatorial.toukenranbu.network;

public class ClientSpiritPowerData {
    private static int spiritPower = 50;
    private static int maxSpiritPower = 100;

    public static void set(int current, int max) {
        spiritPower = current;
        maxSpiritPower = max;
    }

    public static int getSpiritPower() {
        return spiritPower;
    }

    public static int getMaxSpiritPower() {
        return maxSpiritPower;
    }
}