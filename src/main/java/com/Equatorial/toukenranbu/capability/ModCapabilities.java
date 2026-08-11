// src/main/java/com/Equatorial/toukenranbu/capability/ModCapabilities.java
package com.Equatorial.toukenranbu.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class ModCapabilities {
    public static final Capability<ISpiritPower> SPIRIT_POWER =
            CapabilityManager.get(new CapabilityToken<>() {});
}