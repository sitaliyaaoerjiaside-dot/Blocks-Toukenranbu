package com.Equatorial.toukenranbu.datagen;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.block.ModBlocks;
import com.Equatorial.toukenranbu.tag.ModBlockTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> LookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, LookupProvider, ToukenRanbuMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.WHETSTONE_BLOCK.get())
                .add(ModBlocks.COOLANT_BLOCK.get())
                .add(ModBlocks.WOOTZ_STEEL_BLOCK.get())
                .add(ModBlocks.COOLANT_ORE.get())
                .add(ModBlocks.WHETSTONE_ORE.get())
                .add(ModBlocks.WOOTZ_STEEL_ORE.get())
                .add(ModBlocks.WHITE_NIGHT_PORTAL_FRAME.get());

        tag(BlockTags.NEEDS_STONE_TOOL)
                .add(ModBlocks.COOLANT_ORE.get())
                .add(ModBlocks.WHETSTONE_ORE.get())
                .add(ModBlocks.WOOTZ_STEEL_ORE.get())
                .add(ModBlocks.WHITE_NIGHT_PORTAL_FRAME.get());

        tag(ModBlockTags.ORE_TAGS)
                .add(ModBlocks.COOLANT_ORE.get())
                .add(ModBlocks.WHETSTONE_ORE.get())
                .add(ModBlocks.WOOTZ_STEEL_ORE.get())
                .addTag(BlockTags.COAL_ORES);

        tag(BlockTags.FENCES)
                .add(ModBlocks.WOOTZ_STEEL_FENCE.get());
        tag(BlockTags.FENCE_GATES)
                .add(ModBlocks.WOOTZ_STEEL_FENCE_GATE.get());
        tag(BlockTags.WALLS)
                .add(ModBlocks.WOOTZ_STEEL_WALL.get());
        tag(BlockTags.FENCES)
                .add(ModBlocks.WHETSTONE_FENCE.get());
        tag(BlockTags.FENCE_GATES)
                .add(ModBlocks.WHETSTONE_FENCE_GATE.get());
        tag(BlockTags.WALLS)
                .add(ModBlocks.WHETSTONE_WALL.get());

        tag(BlockTags.LOGS_THAT_BURN)
                .add(ModBlocks.CHARRED_LOG.get())
                .add(ModBlocks.CHARRED_WOOD.get())
                .add(ModBlocks.STRIPPED_CHARRED_LOG.get())
                .add(ModBlocks.STRIPPED_CHARRED_WOOD.get());
        tag(BlockTags.PLANKS)
                .add(ModBlocks.CHARRED_PLANKS.get());
        tag(BlockTags.WOODEN_FENCES)
                .add(ModBlocks.CHARRED_FENCE.get());
        tag(BlockTags.FENCE_GATES)
                .add(ModBlocks.CHARRED_FENCE_GATE.get());

        tag(BlockTags.MINEABLE_WITH_SHOVEL)
                .add(ModBlocks.CHARRED_DIRT.get())
                .add(ModBlocks.CHARRED_GRASS_BLOCK.get());

        // ========== 蓝色樱花 ==========
        tag(BlockTags.LOGS_THAT_BURN)
                .add(ModBlocks.BLUE_CHERRY_LOG.get())
                .add(ModBlocks.BLUE_CHERRY_WOOD.get())
                .add(ModBlocks.STRIPPED_BLUE_CHERRY_LOG.get())
                .add(ModBlocks.STRIPPED_BLUE_CHERRY_WOOD.get());
        tag(BlockTags.PLANKS)
                .add(ModBlocks.BLUE_CHERRY_PLANKS.get());
        tag(BlockTags.WOODEN_FENCES)
                .add(ModBlocks.BLUE_CHERRY_FENCE.get());
        tag(BlockTags.FENCE_GATES)
                .add(ModBlocks.BLUE_CHERRY_FENCE_GATE.get());
        tag(BlockTags.LEAVES)
                .add(ModBlocks.BLUE_CHERRY_LEAVES.get());
        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(ModBlocks.BLUE_CHERRY_LOG.get())
                .add(ModBlocks.BLUE_CHERRY_WOOD.get())
                .add(ModBlocks.STRIPPED_BLUE_CHERRY_LOG.get())
                .add(ModBlocks.STRIPPED_BLUE_CHERRY_WOOD.get())
                .add(ModBlocks.BLUE_CHERRY_PLANKS.get())
                .add(ModBlocks.BLUE_CHERRY_STAIRS.get())
                .add(ModBlocks.BLUE_CHERRY_SLAB.get())
                .add(ModBlocks.BLUE_CHERRY_FENCE.get())
                .add(ModBlocks.BLUE_CHERRY_FENCE_GATE.get())
                .add(ModBlocks.BLUE_CHERRY_BUTTON.get())
                .add(ModBlocks.BLUE_CHERRY_PRESSURE_PLATE.get())
                .add(ModBlocks.BLUE_CHERRY_DOOR.get())
                .add(ModBlocks.BLUE_CHERRY_TRAPDOOR.get());
        tag(BlockTags.BUTTONS)
                .add(ModBlocks.BLUE_CHERRY_BUTTON.get());
        tag(BlockTags.PRESSURE_PLATES)
                .add(ModBlocks.BLUE_CHERRY_PRESSURE_PLATE.get());
        tag(BlockTags.WOODEN_DOORS)
                .add(ModBlocks.BLUE_CHERRY_DOOR.get());
        tag(BlockTags.WOODEN_TRAPDOORS)
                .add(ModBlocks.BLUE_CHERRY_TRAPDOOR.get());
    }

}
