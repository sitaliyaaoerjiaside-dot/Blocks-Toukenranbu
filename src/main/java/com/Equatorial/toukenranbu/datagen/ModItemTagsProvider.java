package com.Equatorial.toukenranbu.datagen;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.block.ModBlocks;
import com.Equatorial.toukenranbu.item.ModItems;
import com.Equatorial.toukenranbu.tag.ModItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, ToukenRanbuMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(ModItemTags.SUGER_TAG)
                .add(Items.BEETROOT)
                .add(ModItems.A_BITE_OF_DANGO.get())
                .add(ModItems.IMMORTAL_DUMPLINGS.get())
                .add(ModItems.A_STRING_OF_DANGO.get());
        tag(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.WOOTZ_STEEL_HELMET.get(),
                         ModItems.WOOTZ_STEEL_CHESTPLATE.get(),
                         ModItems.WOOTZ_STEEL_LEGGINGS.get(),
                         ModItems.WOOTZ_STEEL_BOOTS.get(),
                         ModItems.WHETSTONE_HELMET.get(),
                         ModItems.WHETSTONE_CHESTPLATE.get(),
                         ModItems.WHETSTONE_LEGGINGS.get(),
                         ModItems.WHETSTONE_BOOTS.get());

        tag(ItemTags.LOGS_THAT_BURN)
                .add(ModBlocks.CHARRED_LOG.get().asItem())
                .add(ModBlocks.CHARRED_WOOD.get().asItem())
                .add(ModBlocks.STRIPPED_CHARRED_LOG.get().asItem())
                .add(ModBlocks.STRIPPED_CHARRED_WOOD.get().asItem());

        tag(ItemTags.PLANKS)
                .add(ModBlocks.CHARRED_PLANKS.get().asItem());

        tag(ModItemTags.GOLD_KNIFE).add(ModItems.GOLD_OMAMORI.get());
        tag(ModItemTags.SILVER_KNIFE).add(ModItems.SILVER_OMAMORI.get());
        tag(ModItemTags.COPPER_KNIFE).add(ModItems.BRONZE_OMAMORI.get());

        tag(ModItemTags.KNIFE_EQUIPMENT)
                .addTag(ModItemTags.GOLD_KNIFE)
                .addTag(ModItemTags.SILVER_KNIFE)
                .addTag(ModItemTags.COPPER_KNIFE);
    }
}
