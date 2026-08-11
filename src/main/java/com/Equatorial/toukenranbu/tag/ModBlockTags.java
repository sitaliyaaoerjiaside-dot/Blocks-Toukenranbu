package com.Equatorial.toukenranbu.tag;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModBlockTags {

    public static final TagKey<Block> ORE_TAGS = create("ore_tags");

    private static TagKey<Block> create(String pName) {
        return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, pName));
    }

    private static TagKey<Block> createForgeTag(String pName) {
        return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("forge", pName));
    }

}
