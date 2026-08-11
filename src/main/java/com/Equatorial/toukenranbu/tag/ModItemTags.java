package com.Equatorial.toukenranbu.tag;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTags {

    public static final TagKey<Item> SUGER_TAG = bind("suger_tag");
    public static final TagKey<Item> GOLD_KNIFE = bind("gold_knife");
    public static final TagKey<Item> SILVER_KNIFE = bind("silver_knife");
    public static final TagKey<Item> COPPER_KNIFE = bind("copper_knife");
    public static final TagKey<Item> KNIFE_EQUIPMENT = bind("knife_equipment");
    private static TagKey<Item> bind(String pName) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID,pName));

    }
}
