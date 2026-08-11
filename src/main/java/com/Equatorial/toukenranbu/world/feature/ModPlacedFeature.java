package com.Equatorial.toukenranbu.world.feature;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.block.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ModPlacedFeature {

    public static final ResourceKey<PlacedFeature> BLACK_WOOTZ_STEEL_PLACED_KEY = createKey("black_wootz_steel_placed");
    public static final ResourceKey<PlacedFeature> NETHER_BLACK_WOOTZ_STEEL_PLACED_KEY = createKey("nether_black_wootz_steel_placed");
    public static final ResourceKey<PlacedFeature> END_BLACK_WOOTZ_STEEL_PLACED_KEY = createKey("end_black_wootz_steel_placed");

    public static final ResourceKey<PlacedFeature> BLACK_WHETSTONE_PLACED_KEY = createKey("black_whetstone_placed");
    public static final ResourceKey<PlacedFeature> NETHER_BLACK_WHETSTONE_PLACED_KEY = createKey("nether_black_whetstone_placed");
    public static final ResourceKey<PlacedFeature> END_BLACK_WHETSTONE_PLACED_KEY = createKey("end_black_whetstone_placed");

    public static final ResourceKey<PlacedFeature> BLACK_COOLANT_PLACED_KEY = createKey("black_coolant_placed");
    public static final ResourceKey<PlacedFeature> NETHER_BLACK_COOLANT_PLACED_KEY = createKey("nether_black_coolant_placed");
    public static final ResourceKey<PlacedFeature> END_BLACK_COOLANT_PLACED_KEY = createKey("end_black_coolant_placed");

    public static final ResourceKey<PlacedFeature> CHARRED_TREE_PLACED_KEY = createKey("charred_tree_placed");


    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, BLACK_WOOTZ_STEEL_PLACED_KEY, configuredFeatures.getOrThrow(ModConfigureFeature.OVERWORLD_BLACK_WOOTZ_STEEL_ORE_KEY),
                ModOrePlacement.commonOrePlacement(16,
                HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));
        register(context, NETHER_BLACK_WOOTZ_STEEL_PLACED_KEY, configuredFeatures.getOrThrow(ModConfigureFeature.NETHER_BLACK_WOOTZ_STEEL_ORE_KEY),
                ModOrePlacement.commonOrePlacement(9,
                HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));
        register(context, END_BLACK_WOOTZ_STEEL_PLACED_KEY, configuredFeatures.getOrThrow(ModConfigureFeature.END_BLACK_WOOTZ_STEEL_ORE_KEY),
                ModOrePlacement.commonOrePlacement(9,
                HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));

        register(context, BLACK_WHETSTONE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfigureFeature.OVERWORLD_WHETSTONE_ORE_KEY),
                ModOrePlacement.commonOrePlacement(16,
                HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));
        register(context, NETHER_BLACK_WHETSTONE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfigureFeature.NETHER_BLACK_WHETSTONE_ORE_KEY),
                ModOrePlacement.commonOrePlacement(9,
                HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));
        register(context, END_BLACK_WHETSTONE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfigureFeature.END_BLACK_WHETSTONE_ORE_KEY),
                ModOrePlacement.commonOrePlacement(9,
                HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));

        register(context, BLACK_COOLANT_PLACED_KEY, configuredFeatures.getOrThrow(ModConfigureFeature.OVERWORLD_COOLANT_ORE_KEY),
                ModOrePlacement.commonOrePlacement(16,
                HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));
        register(context, NETHER_BLACK_COOLANT_PLACED_KEY, configuredFeatures.getOrThrow(ModConfigureFeature.NETHER_BLACK_COOLANT_ORE_KEY),
                ModOrePlacement.commonOrePlacement(9,
                HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));
        register(context, END_BLACK_COOLANT_PLACED_KEY, configuredFeatures.getOrThrow(ModConfigureFeature.END_BLACK_COOLANT_ORE_KEY),
                ModOrePlacement.commonOrePlacement(9,
                HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));

        // 灰烬树 - 只在灰烬森林生物群系自然生成
        register(context, CHARRED_TREE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfigureFeature.CHARRED_KEY), List.of(
                PlacementUtils.countExtra(1, 0.05f, 1), RarityFilter.onAverageOnceEvery(2),
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, SurfaceWaterDepthFilter.forMaxDepth(0),
                BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(new Vec3i(0, -1, 0),
                ModBlocks.CHARRED_DIRT.get(), ModBlocks.CHARRED_GRASS_BLOCK.get())),
                BiomeFilter.biome()));

    }

    public static ResourceKey<PlacedFeature> createKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, name));
    }
    public static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                PlacementModifier... modifiers) {
        register(context, key, configuration, List.of(modifiers));
    }
}
