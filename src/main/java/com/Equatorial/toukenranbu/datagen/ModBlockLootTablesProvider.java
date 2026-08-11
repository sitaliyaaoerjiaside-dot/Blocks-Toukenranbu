package com.Equatorial.toukenranbu.datagen;

import com.Equatorial.toukenranbu.block.ModBlocks;
import com.Equatorial.toukenranbu.item.ModItems;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTablesProvider extends BlockLootSubProvider {
    public ModBlockLootTablesProvider() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.WHETSTONE_BLOCK.get());
        dropSelf(ModBlocks.COOLANT_BLOCK.get());
        dropSelf(ModBlocks.WOOTZ_STEEL_BLOCK.get());
        add(ModBlocks.COOLANT_ORE.get(), block -> createCopperOreLikeDrops(ModBlocks.COOLANT_ORE.get(), ModItems.COOLANT.get()));
        add(ModBlocks.WHETSTONE_ORE.get(), block -> createCopperOreLikeDrops(ModBlocks.WHETSTONE_ORE.get(), ModItems.WHETSTONE.get()));
        add(ModBlocks.WOOTZ_STEEL_ORE.get(), block -> createCopperOreLikeDrops(ModBlocks.WOOTZ_STEEL_ORE.get(), ModItems.WOOTZ_STEEL.get()));

        dropSelf(ModBlocks.WOOTZ_STEEL_STAIRS.get());
        add(ModBlocks.WOOTZ_STEEL_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.WOOTZ_STEEL_SLAB.get()));
        dropSelf(ModBlocks.WOOTZ_STEEL_BUTTON.get());
        dropSelf(ModBlocks.WOOTZ_STEEL_PRESSURE_PLATE.get());
        dropSelf(ModBlocks.WOOTZ_STEEL_FENCE_GATE.get());
        dropSelf(ModBlocks.WOOTZ_STEEL_FENCE.get());
        dropSelf(ModBlocks.WOOTZ_STEEL_WALL.get());
        add(ModBlocks.WOOTZ_STEEL_DOOR.get(),
                block -> createDoorTable(ModBlocks.WOOTZ_STEEL_DOOR.get()));
        dropSelf(ModBlocks.WOOTZ_STEEL_TRAPDOOR.get());

        dropSelf(ModBlocks.WHETSTONE_STAIRS.get());
        add(ModBlocks.WHETSTONE_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.WHETSTONE_SLAB.get()));
        dropSelf(ModBlocks.WHETSTONE_BUTTON.get());
        dropSelf(ModBlocks.WHETSTONE_PRESSURE_PLATE.get());
        dropSelf(ModBlocks.WHETSTONE_FENCE_GATE.get());
        dropSelf(ModBlocks.WHETSTONE_FENCE.get());
        dropSelf(ModBlocks.WHETSTONE_WALL.get());
        add(ModBlocks.WHETSTONE_DOOR.get(),
                block -> createDoorTable(ModBlocks.WHETSTONE_DOOR.get()));
        dropSelf(ModBlocks.WHETSTONE_TRAPDOOR.get());

        dropSelf(ModBlocks.CHARRED_LOG.get());
        dropSelf(ModBlocks.CHARRED_WOOD.get());
        dropSelf(ModBlocks.CHARRED_PLANKS.get());
        dropSelf(ModBlocks.STRIPPED_CHARRED_LOG.get());
        dropSelf(ModBlocks.STRIPPED_CHARRED_WOOD.get());
        dropSelf(ModBlocks.CHARRED_SAPLING.get());
        dropSelf(ModBlocks.CHARRED_STAIRS.get());
        add(ModBlocks.CHARRED_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.CHARRED_SLAB.get()));
        dropSelf(ModBlocks.CHARRED_BUTTON.get());
        dropSelf(ModBlocks.CHARRED_PRESSURE_PLATE.get());
        dropSelf(ModBlocks.CHARRED_FENCE.get());
        dropSelf(ModBlocks.CHARRED_FENCE_GATE.get());
        add(ModBlocks.CHARRED_DOOR.get(),
                block -> createDoorTable(ModBlocks.CHARRED_DOOR.get()));
        dropSelf(ModBlocks.CHARRED_TRAPDOOR.get());

        add(ModBlocks.CHARRED_LEAVES.get(), block ->
               createLeavesDrops(block, ModBlocks.CHARRED_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));

        dropSelf(ModBlocks.CHARRED_DIRT.get());
        dropSelf(ModBlocks.CHARRED_GRASS_BLOCK.get());
        //dropSelf(ModBlocks.SWORD_FORGE.get());
    }
        protected LootTable.Builder createCopperOreLikeDrops(Block pBlock, Item item) {
            return createSilkTouchDispatchTable(pBlock,
                    this.applyExplosionDecay(pBlock, LootItem.lootTableItem(item)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F)))
                    .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
