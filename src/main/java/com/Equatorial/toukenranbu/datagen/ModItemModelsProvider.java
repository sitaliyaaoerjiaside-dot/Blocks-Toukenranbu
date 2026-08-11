package com.Equatorial.toukenranbu.datagen;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.block.ModBlocks;
import com.Equatorial.toukenranbu.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelsProvider extends ItemModelProvider {
    public ModItemModelsProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ToukenRanbuMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ModItems ModItems;
        basicItem(com.Equatorial.toukenranbu.item.ModItems.WOOTZ_STEEL.get());
        basicItem(com.Equatorial.toukenranbu.item.ModItems.COOLANT.get());
        basicItem(com.Equatorial.toukenranbu.item.ModItems.WHETSTONE.get());
        basicItem(com.Equatorial.toukenranbu.item.ModItems.AMULET.get());
        basicItem(com.Equatorial.toukenranbu.item.ModItems.SUPREME_AMULET.get());
        basicItem(com.Equatorial.toukenranbu.item.ModItems.A_SET_OF_PAPER_AND_PEN.get());
        basicItem(com.Equatorial.toukenranbu.item.ModItems.DAMAGED_SWORD_FRAGMENTS.get());
        basicItem(com.Equatorial.toukenranbu.item.ModItems.POWER_OF_ATTORNEY.get());
        basicItem(com.Equatorial.toukenranbu.item.ModItems.SPEED_UP_POTION.get());
        basicItem(com.Equatorial.toukenranbu.item.ModItems.SMALL_KOBAN.get());

        withExistingParent("tantou_spawn_egg", ResourceLocation.fromNamespaceAndPath("minecraft", "item/template_spawn_egg"));
        withExistingParent("wakizashi_spawn_egg", ResourceLocation.fromNamespaceAndPath("minecraft", "item/template_spawn_egg"));
        withExistingParent("uchigatana_spawn_egg", ResourceLocation.fromNamespaceAndPath("minecraft", "item/template_spawn_egg"));
        withExistingParent("tachi_spawn_egg", ResourceLocation.fromNamespaceAndPath("minecraft", "item/template_spawn_egg"));
        withExistingParent("ootachi_spawn_egg", ResourceLocation.fromNamespaceAndPath("minecraft", "item/template_spawn_egg"));
        withExistingParent("naginata_spawn_egg", ResourceLocation.fromNamespaceAndPath("minecraft", "item/template_spawn_egg"));
        withExistingParent("yari_spawn_egg", ResourceLocation.fromNamespaceAndPath("minecraft", "item/template_spawn_egg"));

        withExistingParent("tantou_plus_spawn_egg", ResourceLocation.fromNamespaceAndPath("minecraft", "item/template_spawn_egg"));
        withExistingParent("wakizashi_plus_spawn_egg", ResourceLocation.fromNamespaceAndPath("minecraft", "item/template_spawn_egg"));
        withExistingParent("uchigatana_plus_spawn_egg", ResourceLocation.fromNamespaceAndPath("minecraft", "item/template_spawn_egg"));
        withExistingParent("tachi_plus_spawn_egg", ResourceLocation.fromNamespaceAndPath("minecraft", "item/template_spawn_egg"));
        withExistingParent("ootachi_plus_spawn_egg", ResourceLocation.fromNamespaceAndPath("minecraft", "item/template_spawn_egg"));
        withExistingParent("naginata_plus_spawn_egg", ResourceLocation.fromNamespaceAndPath("minecraft", "item/template_spawn_egg"));
        withExistingParent("yari_plus_spawn_egg", ResourceLocation.fromNamespaceAndPath("minecraft", "item/template_spawn_egg"));

        withExistingParent("tantou_max_spawn_egg", ResourceLocation.fromNamespaceAndPath("minecraft", "item/template_spawn_egg"));
        withExistingParent("wakizashi_max_spawn_egg", ResourceLocation.fromNamespaceAndPath("minecraft", "item/template_spawn_egg"));
        withExistingParent("uchigatana_max_spawn_egg", ResourceLocation.fromNamespaceAndPath("minecraft", "item/template_spawn_egg"));
        withExistingParent("tachi_max_spawn_egg", ResourceLocation.fromNamespaceAndPath("minecraft", "item/template_spawn_egg"));
        withExistingParent("ootachi_max_spawn_egg", ResourceLocation.fromNamespaceAndPath("minecraft", "item/template_spawn_egg"));
        withExistingParent("naginata_max_spawn_egg", ResourceLocation.fromNamespaceAndPath("minecraft", "item/template_spawn_egg"));
        withExistingParent("yari_max_spawn_egg", ResourceLocation.fromNamespaceAndPath("minecraft", "item/template_spawn_egg"));

        withExistingParent("kebiishi_leader_spawn_egg", ResourceLocation.fromNamespaceAndPath("minecraft", "item/template_spawn_egg"));
        withExistingParent("kebiishi_tachi_spawn_egg", ResourceLocation.fromNamespaceAndPath("minecraft", "item/template_spawn_egg"));
        withExistingParent("kebiishi_ootachi_spawn_egg", ResourceLocation.fromNamespaceAndPath("minecraft", "item/template_spawn_egg"));
        withExistingParent("kebiishi_naginata_spawn_egg", ResourceLocation.fromNamespaceAndPath("minecraft", "item/template_spawn_egg"));
        withExistingParent("kebiishi_yari_spawn_egg", ResourceLocation.fromNamespaceAndPath("minecraft", "item/template_spawn_egg"));
        withExistingParent("konnosuke_spawn_egg", ResourceLocation.fromNamespaceAndPath("minecraft", "item/template_spawn_egg"));

        basicItem(com.Equatorial.toukenranbu.item.ModItems.A_BITE_OF_DANGO.get());
        basicItem(com.Equatorial.toukenranbu.item.ModItems.IMMORTAL_DUMPLINGS.get());
        basicItem(com.Equatorial.toukenranbu.item.ModItems.A_STRING_OF_DANGO.get());
        basicItem(com.Equatorial.toukenranbu.item.ModItems.TURBID_SPIRITUAL_ENERGY.get());
        basicItem(com.Equatorial.toukenranbu.item.ModItems.PURE_SPIRITUAL_ENERGY.get());

        basicItem(com.Equatorial.toukenranbu.item.ModItems.VOID_SPIRITUAL_ENERGY.get());

        buttonItem(ModBlocks.WOOTZ_STEEL_BUTTON,ModBlocks.WOOTZ_STEEL_BLOCK);
        fenceItem(ModBlocks.WOOTZ_STEEL_FENCE,ModBlocks.WOOTZ_STEEL_BLOCK);
        wallItem(ModBlocks.WOOTZ_STEEL_WALL,ModBlocks.WOOTZ_STEEL_BLOCK);

        basicItem(ModBlocks.WOOTZ_STEEL_DOOR.get().asItem());

        buttonItem(ModBlocks.WHETSTONE_BUTTON,ModBlocks.WHETSTONE_BLOCK);
        fenceItem(ModBlocks.WHETSTONE_FENCE,ModBlocks.WHETSTONE_BLOCK);
        wallItem(ModBlocks.WHETSTONE_WALL,ModBlocks.WHETSTONE_BLOCK);

        buttonItem(ModBlocks.CHARRED_BUTTON, ModBlocks.CHARRED_PLANKS);
        fenceItem(ModBlocks.CHARRED_FENCE, ModBlocks.CHARRED_PLANKS);
        basicItem(ModBlocks.CHARRED_DOOR.get().asItem());

        basicItem(ModBlocks.WHETSTONE_DOOR.get().asItem());

        handheldItem(com.Equatorial.toukenranbu.item.ModItems.WOOTZ_STEEL_SWORD);
        handheldItem(com.Equatorial.toukenranbu.item.ModItems.WOOTZ_STEEL_PICKAXE);
        handheldItem(com.Equatorial.toukenranbu.item.ModItems.WOOTZ_STEEL_AXE);
        handheldItem(com.Equatorial.toukenranbu.item.ModItems.WOOTZ_STEEL_SHOVEL);
        handheldItem(com.Equatorial.toukenranbu.item.ModItems.WOOTZ_STEEL_HOE);
        handheldItem(com.Equatorial.toukenranbu.item.ModItems.WHETSTONE_SWORD);
        handheldItem(com.Equatorial.toukenranbu.item.ModItems.WHETSTONE_PICKAXE);
        handheldItem(com.Equatorial.toukenranbu.item.ModItems.WHETSTONE_AXE);
        handheldItem(com.Equatorial.toukenranbu.item.ModItems.WHETSTONE_SHOVEL);
        handheldItem(com.Equatorial.toukenranbu.item.ModItems.WHETSTONE_HOE);

        basicItem(com.Equatorial.toukenranbu.item.ModItems.WOOTZ_STEEL_HELMET.get());
        basicItem(com.Equatorial.toukenranbu.item.ModItems.WOOTZ_STEEL_CHESTPLATE.get());
        basicItem(com.Equatorial.toukenranbu.item.ModItems.WOOTZ_STEEL_LEGGINGS.get());
        basicItem(com.Equatorial.toukenranbu.item.ModItems.WOOTZ_STEEL_BOOTS.get());
        basicItem(com.Equatorial.toukenranbu.item.ModItems.WHETSTONE_HELMET.get());
        basicItem(com.Equatorial.toukenranbu.item.ModItems.WHETSTONE_CHESTPLATE.get());
        basicItem(com.Equatorial.toukenranbu.item.ModItems.WHETSTONE_LEGGINGS.get());
        basicItem(com.Equatorial.toukenranbu.item.ModItems.WHETSTONE_BOOTS.get());

        saplingItem(ModBlocks.CHARRED_SAPLING);

        basicItem(com.Equatorial.toukenranbu.item.ModItems.TROOP_CANDY.get());
        basicItem(com.Equatorial.toukenranbu.item.ModItems.SOLDIER_CANDY.get());
        basicItem(com.Equatorial.toukenranbu.item.ModItems.GOLD_OMAMORI.get());
        basicItem(com.Equatorial.toukenranbu.item.ModItems.SILVER_OMAMORI.get());
        basicItem(com.Equatorial.toukenranbu.item.ModItems.BRONZE_OMAMORI.get());

        basicItem(com.Equatorial.toukenranbu.item.ModItems.MIKAZUKI_MUNECHIKA.get());
        basicItem(com.Equatorial.toukenranbu.item.ModItems.YAMANBAGIRI_KUNIHIRO.get());
        basicItem(com.Equatorial.toukenranbu.item.ModItems.KASHUU_KIYOMITSU.get());
        basicItem(com.Equatorial.toukenranbu.item.ModItems.KASEN_KANESADA.get());
        basicItem(com.Equatorial.toukenranbu.item.ModItems.HACHISUKA_KOTETSU.get());
        basicItem(com.Equatorial.toukenranbu.item.ModItems.MUTSUNOKAMI_YOSHIYUKI.get());

        basicItem(com.Equatorial.toukenranbu.item.ModItems.OIL_TOFU.get());
        basicItem(com.Equatorial.toukenranbu.item.ModItems.DICE.get());

    }

    private ItemModelBuilder saplingItem(RegistryObject<Block> item) {
        return withExistingParent(item.getId().getPath(),
                mcLoc("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "block/" + item.getId().getPath()));
    }
private <T extends Block> void buttonItem(RegistryObject<T> Block, RegistryObject<Block> base) {
    this.withExistingParent(Block.getId().getPath(), mcLoc("block/button_inventory"))
            .texture("texture", ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID,
                    "block/" + base.getId().getPath()));
}
private <T extends Block> void fenceItem(RegistryObject<T> Block, RegistryObject<Block> base) {
    this.withExistingParent(Block.getId().getPath(), mcLoc("block/fence_inventory"))
            .texture("texture", ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID,
                    "block/" + base.getId().getPath()));
}

private <T extends Block> void wallItem(RegistryObject<T> Block, RegistryObject<Block> base) {
    this.withExistingParent(Block.getId().getPath(), mcLoc("block/wall_inventory"))
            .texture("wall", ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID,
                    "block/" + base.getId().getPath()));
}
private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
            ResourceLocation.withDefaultNamespace("item/handheld")).texture("layer0",
            ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID,"item/" + item.getId().getPath()));

    }
}