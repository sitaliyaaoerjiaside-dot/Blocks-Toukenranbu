package com.Equatorial.toukenranbu.world.feature;

import com.Equatorial.toukenranbu.block.ModBlocks;
import com.Equatorial.toukenranbu.ToukenRanbuMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;
import java.util.Optional;

public class ModConfigureFeature {

    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_BLACK_WOOTZ_STEEL_ORE_KEY = registerkey("overworld_black_wootz_steel_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> END_BLACK_WOOTZ_STEEL_ORE_KEY = registerkey("end_black_wootz_steel_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NETHER_BLACK_WOOTZ_STEEL_ORE_KEY = registerkey("nether_black_wootz_steel_ore");

    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_WHETSTONE_ORE_KEY = registerkey("overworld_whetstone_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> END_BLACK_WHETSTONE_ORE_KEY = registerkey("end_black_whetstone_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NETHER_BLACK_WHETSTONE_ORE_KEY = registerkey("nether_black_whetstone_ore");

    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_COOLANT_ORE_KEY = registerkey("overworld_black_coolant_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> END_BLACK_COOLANT_ORE_KEY = registerkey("end_black_coolant_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NETHER_BLACK_COOLANT_ORE_KEY = registerkey("nether_black_coolant_ore");

    public static final ResourceKey<ConfiguredFeature<?, ?>> CHARRED_KEY = registerkey("charred");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest stonereplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest netherrackReplaceables = new BlockMatchTest(Blocks.NETHERRACK);
        RuleTest endstoneReplaceables = new BlockMatchTest(Blocks.END_STONE);

        List<OreConfiguration.TargetBlockState> overworldBlackWootzOres = List.of(OreConfiguration.target(stonereplaceables,
                        ModBlocks.WOOTZ_STEEL_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.WOOTZ_STEEL_ORE.get().defaultBlockState()));
        List<OreConfiguration.TargetBlockState> overworldWhetstoneOres = List.of(OreConfiguration.target(stonereplaceables,
                        ModBlocks.WHETSTONE_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.WHETSTONE_ORE.get().defaultBlockState()));
        List<OreConfiguration.TargetBlockState> overworldCoolantOres = List.of(OreConfiguration.target(stonereplaceables,
                        ModBlocks.COOLANT_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.COOLANT_ORE.get().defaultBlockState()));

        register(context, OVERWORLD_BLACK_WOOTZ_STEEL_ORE_KEY, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(overworldBlackWootzOres, 9)));
        register(context, NETHER_BLACK_WOOTZ_STEEL_ORE_KEY, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(netherrackReplaceables,
                ModBlocks.WOOTZ_STEEL_ORE.get().defaultBlockState(), 9)));
        register(context, END_BLACK_WOOTZ_STEEL_ORE_KEY, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(endstoneReplaceables,
                ModBlocks.WOOTZ_STEEL_ORE.get().defaultBlockState(), 9)));

        register(context, OVERWORLD_WHETSTONE_ORE_KEY, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(overworldWhetstoneOres, 9)));
        register(context, NETHER_BLACK_WHETSTONE_ORE_KEY, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(netherrackReplaceables,
                ModBlocks.WHETSTONE_ORE.get().defaultBlockState(), 9)));
        register(context, END_BLACK_WHETSTONE_ORE_KEY, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(endstoneReplaceables,
                ModBlocks.WHETSTONE_ORE.get().defaultBlockState(), 9)));

        register(context, OVERWORLD_COOLANT_ORE_KEY, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(overworldCoolantOres, 9)));
        register(context, NETHER_BLACK_COOLANT_ORE_KEY, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(netherrackReplaceables,
                ModBlocks.COOLANT_ORE.get().defaultBlockState(), 9)));
        register(context, END_BLACK_COOLANT_ORE_KEY, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(endstoneReplaceables,
                ModBlocks.COOLANT_ORE.get().defaultBlockState(),9)));

        register(context, CHARRED_KEY, new ConfiguredFeature<>(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.CHARRED_LOG.get().defaultBlockState()),
                new StraightTrunkPlacer(5, 4, 3),
                BlockStateProvider.simple(ModBlocks.CHARRED_LEAVES.get().defaultBlockState()),
                new BlobFoliagePlacer(ConstantInt.of(3), ConstantInt.of(2), 3),
                Optional.empty(),
                new TwoLayersFeatureSize(1, 0, 2)).build()));

    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerkey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, name));
    }

    private static void register(BootstapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key,
                ConfiguredFeature<?, ?> configuredFeature) {context.register(key, configuredFeature);


    }
}