// src/main/java/com/Equatorial/toukenranbu/capability/SpiritPowerProvider.java
package com.Equatorial.toukenranbu.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SpiritPowerProvider implements ICapabilitySerializable<Tag> {
    private final SpiritPower instance = new SpiritPower();
    private final LazyOptional<ISpiritPower> optional = LazyOptional.of(() -> instance);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return ModCapabilities.SPIRIT_POWER.orEmpty(cap, optional);
    }

    @Override
    public Tag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("spirit_power", instance.getSpiritPower());
        return tag;
    }

    @Override
    public void deserializeNBT(Tag tag) {
        if (tag instanceof CompoundTag compoundTag) {
            instance.setSpiritPower(compoundTag.getInt("spirit_power"));
        }
    }
}