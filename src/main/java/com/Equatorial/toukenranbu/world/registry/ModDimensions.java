package com.Equatorial.toukenranbu.world.registry;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;

import net.minecraft.data.worldgen.BootstapContext;

import java.util.OptionalLong;

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

    // 白夜之庭维度
    public static final ResourceKey<Level> WHITE_NIGHT_GARDEN_LEVEL = ResourceKey.create(Registries.DIMENSION,
            ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "white_night_garden"));
    public static final ResourceKey<DimensionType> WHITE_NIGHT_GARDEN_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE,
            ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "white_night_garden"));
    public static final ResourceKey<LevelStem> WHITE_NIGHT_GARDEN_STEM = ResourceKey.create(Registries.LEVEL_STEM,
            ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "white_night_garden"));

    // ========== 注册 DimensionType ==========
    // 在 BootstrapContext<DimensionType> 中注册维度的环境规则
    public static void bootstrapType(BootstapContext<DimensionType> context) {
        context.register(WHITE_NIGHT_GARDEN_TYPE, new DimensionType(
                OptionalLong.empty(),    // fixedTime: 锁定为 18000
                true,                     // hasSkyLight: 无天空光，靠方块光
                false,                     // hasCeiling: 无天花板，可以看见天空
                false,                     // ultrawarm: 不是极热维度
                true,                      // natural: 是自然维度 (允许床等)
                1.0,                       // coordinateScale: 坐标缩放 1:1
                true,                      // bedWorks: 床可以工作
                false,                     // respawnAnchorWorks: 重生锚不可用
                -64,                         // minY: 最小高度
                384,                       // height: 总高度
                384,                       // logicalHeight: 逻辑高度
                BlockTags.INFINIBURN_OVERWORLD, // infiniburn: 火焰不灭的方块标签
                ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "white_night_garden"), // effectsLocation: 天空效果
                0.35f,                      // ambientLight: 环境光照 (0~1)
                new DimensionType.MonsterSettings(false, false, ConstantInt.of(0), 0) // 怪物生成设置
        ));
    }
}