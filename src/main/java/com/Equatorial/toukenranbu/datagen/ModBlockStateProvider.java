package com.Equatorial.toukenranbu.datagen;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ToukenRanbuMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlockWithItem(ModBlocks.WHETSTONE_BLOCK.get(), cubeAll(ModBlocks.WHETSTONE_BLOCK.get()));
        simpleBlockWithItem(ModBlocks.COOLANT_BLOCK.get(), cubeAll(ModBlocks.COOLANT_BLOCK.get()));
        simpleBlockWithItem(ModBlocks.WOOTZ_STEEL_BLOCK.get(), cubeAll(ModBlocks.WOOTZ_STEEL_BLOCK.get()));
        simpleBlockWithItem(ModBlocks.COOLANT_ORE.get(), cubeAll(ModBlocks.COOLANT_ORE.get()));
        simpleBlockWithItem(ModBlocks.WHETSTONE_ORE.get(), cubeAll(ModBlocks.WHETSTONE_ORE.get()));
        simpleBlockWithItem(ModBlocks.WOOTZ_STEEL_ORE.get(), cubeAll(ModBlocks.WOOTZ_STEEL_ORE.get()));

        logBlock(((RotatedPillarBlock) ModBlocks.CHARRED_LOG.get()));
        axisBlock(((RotatedPillarBlock) ModBlocks.CHARRED_WOOD.get()), blockTexture(ModBlocks.CHARRED_LOG.get()), blockTexture(ModBlocks.CHARRED_LOG.get()));
        axisBlock(((RotatedPillarBlock) ModBlocks.STRIPPED_CHARRED_LOG.get()), blockTexture(ModBlocks.STRIPPED_CHARRED_LOG.get()),
                ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "block/stripped_charred_log_top"));
        axisBlock(((RotatedPillarBlock) ModBlocks.STRIPPED_CHARRED_WOOD.get()), blockTexture(ModBlocks.STRIPPED_CHARRED_LOG.get()),
                blockTexture(ModBlocks.STRIPPED_CHARRED_LOG.get()));

        blockItem(ModBlocks.CHARRED_LOG);
        blockItem(ModBlocks.CHARRED_WOOD);
        blockItem(ModBlocks.STRIPPED_CHARRED_LOG);
        blockItem(ModBlocks.STRIPPED_CHARRED_WOOD);

        simpleBlockWithItem(ModBlocks.CHARRED_PLANKS.get(), cubeAll(ModBlocks.CHARRED_PLANKS.get()));
        leavesBlock(ModBlocks.CHARRED_LEAVES);

        stairsBlock(ModBlocks.WOOTZ_STEEL_STAIRS.get(), blockTexture(ModBlocks.WOOTZ_STEEL_BLOCK.get()));
        slabBlock(ModBlocks.WOOTZ_STEEL_SLAB.get(), blockTexture(ModBlocks.WOOTZ_STEEL_BLOCK.get()), blockTexture(ModBlocks.WOOTZ_STEEL_BLOCK.get()));
        wallBlock(ModBlocks.WOOTZ_STEEL_WALL.get(), blockTexture(ModBlocks.WOOTZ_STEEL_BLOCK.get()));
        fenceBlock(ModBlocks.WOOTZ_STEEL_FENCE.get(), blockTexture(ModBlocks.WOOTZ_STEEL_BLOCK.get()));
        fenceGateBlock(ModBlocks.WOOTZ_STEEL_FENCE_GATE.get(), blockTexture(ModBlocks.WOOTZ_STEEL_BLOCK.get()));
        pressurePlateBlock(ModBlocks.WOOTZ_STEEL_PRESSURE_PLATE.get(), blockTexture(ModBlocks.WOOTZ_STEEL_BLOCK.get()));
        buttonBlock(ModBlocks.WOOTZ_STEEL_BUTTON.get(), blockTexture(ModBlocks.WOOTZ_STEEL_BLOCK.get()));

        doorBlockWithRenderType(ModBlocks.WOOTZ_STEEL_DOOR.get(), modLoc("block/wootz_steel_door_bottom"), modLoc("block/wootz_steel_door_top"), "cutout");
        trapdoorBlockWithRenderType(ModBlocks.WOOTZ_STEEL_TRAPDOOR.get(), modLoc("block/wootz_steel_trapdoor"), true, "cutout");

        blockItem(ModBlocks.WOOTZ_STEEL_STAIRS);
        blockItem(ModBlocks.WOOTZ_STEEL_SLAB);
        blockItem(ModBlocks.WOOTZ_STEEL_FENCE_GATE);
        blockItem(ModBlocks.WOOTZ_STEEL_PRESSURE_PLATE);
        blockItem(ModBlocks.WOOTZ_STEEL_TRAPDOOR, "_bottom");

        stairsBlock(ModBlocks.WHETSTONE_STAIRS.get(), blockTexture(ModBlocks.WHETSTONE_BLOCK.get()));
        slabBlock(ModBlocks.WHETSTONE_SLAB.get(), blockTexture(ModBlocks.WHETSTONE_BLOCK.get()), blockTexture(ModBlocks.WHETSTONE_BLOCK.get()));
        wallBlock(ModBlocks.WHETSTONE_WALL.get(), blockTexture(ModBlocks.WHETSTONE_BLOCK.get()));
        fenceBlock(ModBlocks.WHETSTONE_FENCE.get(), blockTexture(ModBlocks.WHETSTONE_BLOCK.get()));
        fenceGateBlock(ModBlocks.WHETSTONE_FENCE_GATE.get(), blockTexture(ModBlocks.WHETSTONE_BLOCK.get()));
        pressurePlateBlock(ModBlocks.WHETSTONE_PRESSURE_PLATE.get(), blockTexture(ModBlocks.WHETSTONE_BLOCK.get()));
        buttonBlock(ModBlocks.WHETSTONE_BUTTON.get(), blockTexture(ModBlocks.WHETSTONE_BLOCK.get()));

        doorBlockWithRenderType(ModBlocks.WHETSTONE_DOOR.get(), modLoc("block/whetstone_door_bottom"), modLoc("block/whetstone_door_top"), "cutout");
        trapdoorBlockWithRenderType(ModBlocks.WHETSTONE_TRAPDOOR.get(), modLoc("block/whetstone_trapdoor"), true, "cutout");

        blockItem(ModBlocks.WHETSTONE_STAIRS);
        blockItem(ModBlocks.WHETSTONE_SLAB);
        blockItem(ModBlocks.WHETSTONE_FENCE_GATE);
        blockItem(ModBlocks.WHETSTONE_PRESSURE_PLATE);
        blockItem(ModBlocks.WHETSTONE_TRAPDOOR, "_bottom");

        stairsBlock(ModBlocks.CHARRED_STAIRS.get(), blockTexture(ModBlocks.CHARRED_PLANKS.get()));
        slabBlock(ModBlocks.CHARRED_SLAB.get(), blockTexture(ModBlocks.CHARRED_PLANKS.get()), blockTexture(ModBlocks.CHARRED_PLANKS.get()));
        fenceBlock(ModBlocks.CHARRED_FENCE.get(), blockTexture(ModBlocks.CHARRED_PLANKS.get()));
        fenceGateBlock(ModBlocks.CHARRED_FENCE_GATE.get(), blockTexture(ModBlocks.CHARRED_PLANKS.get()));
        pressurePlateBlock(ModBlocks.CHARRED_PRESSURE_PLATE.get(), blockTexture(ModBlocks.CHARRED_PLANKS.get()));
        buttonBlock(ModBlocks.CHARRED_BUTTON.get(), blockTexture(ModBlocks.CHARRED_PLANKS.get()));

        doorBlockWithRenderType(ModBlocks.CHARRED_DOOR.get(), modLoc("block/charred_door_bottom"), modLoc("block/charred_door_top"), "cutout");
        trapdoorBlockWithRenderType(ModBlocks.CHARRED_TRAPDOOR.get(), modLoc("block/charred_trapdoor"), true, "cutout");

        blockItem(ModBlocks.CHARRED_STAIRS);
        blockItem(ModBlocks.CHARRED_SLAB);
        blockItem(ModBlocks.CHARRED_FENCE_GATE);
        blockItem(ModBlocks.CHARRED_PRESSURE_PLATE);
        blockItem(ModBlocks.CHARRED_TRAPDOOR, "_bottom");

        SaplingBlock(ModBlocks.CHARRED_SAPLING);

        // 泥土：六面同纹理
        simpleBlockWithItem(ModBlocks.CHARRED_DIRT.get(), cubeAll(ModBlocks.CHARRED_DIRT.get()));
        ModelFile ashenGrassModel = models()
                .withExistingParent("charred_grass_block", mcLoc("block/cube_bottom_top"))
                .texture("particle", modLoc("block/charred_grass_block_side"))
                .texture("bottom", modLoc("block/charred_dirt"))
                .texture("top", modLoc("block/charred_grass_block_top"))
                .texture("side", modLoc("block/charred_grass_block_side"));
        simpleBlockWithItem(ModBlocks.CHARRED_GRASS_BLOCK.get(), ashenGrassModel);
        //simpleBlockWithItem(ModBlocks.SWORD_FORGE.get(),
               //models().cubeAll("sword_forge", modLoc("block/sword_forge")));
        //simpleBlockWithoutBlockModel(ModBlocks.SWORD_FORGE);
    }

    private <T extends Block> void simpleBlockWithoutBlockModel(RegistryObject<T> block) {
        ResourceLocation model = modLoc("block/" + block.getId().getPath());
        simpleBlock(block.get(), models().getExistingFile(model));
        simpleBlockItem(block.get(), models().getExistingFile(model));
    }
    private void SaplingBlock(RegistryObject<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get(),
                models().cross(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }
private void leavesBlock(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(),
                models().singleTexture(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), ResourceLocation.fromNamespaceAndPath("minecraft", "block/leaves"),
                        "all", blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }
private <T extends Block> void blockItem(RegistryObject<T> block) {
        simpleBlockItem(block.get(), new ModelFile.UncheckedModelFile(ToukenRanbuMod.MOD_ID + ":block/" + block.getId().getPath()));
    }
private <T extends Block> void blockItem(RegistryObject<T> block, String append) {
        simpleBlockItem(block.get(), new ModelFile.UncheckedModelFile(ToukenRanbuMod.MOD_ID + ":block/" + block.getId().getPath() + append));
    }
}
