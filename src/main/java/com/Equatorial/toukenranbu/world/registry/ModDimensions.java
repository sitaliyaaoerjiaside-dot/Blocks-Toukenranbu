package com.Equatorial.toukenranbu.world.registry;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;

public class ModDimensions {
    // 废弃历史维度
    public static final ResourceKey<Level> ABANDONED_HISTORY_LEVEL = ResourceKey.create(
            Registries.DIMENSION,
            ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "abandoned_history")
    );

    // 维度类型
    public static final ResourceKey<DimensionType> ABANDONED_HISTORY_TYPE = ResourceKey.create(
            Registries.DIMENSION_TYPE,
            ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "abandoned_history")
    );
}