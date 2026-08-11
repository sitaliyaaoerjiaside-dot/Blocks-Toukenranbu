package com.Equatorial.toukenranbu.datagen;

import com.Equatorial.toukenranbu.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItemModelGen extends ModItemModelsProvider{

    public static final String EGG_TEMPLATE = "item/template_spawn_egg";

    public ModItemModelGen(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        eggItem(ModItems.TANTOU_SPAWN_EGG.get());
        eggItem(ModItems.WAKIZASHI_SPAWN_EGG.get());
        basicItem(ModItems.UCHIGATANA_SPAWN_EGG.get());
        basicItem(ModItems.TACHI_SPAWN_EGG.get());
        basicItem(ModItems.NAGINATA_SPAWN_EGG.get());
        basicItem(ModItems.OOTACHI_SPAWN_EGG.get());
        basicItem(ModItems.YARI_SPAWN_EGG.get());

        basicItem(ModItems.TANTOU_PLUS_SPAWN_EGG.get());
        basicItem(ModItems.WAKIZASHI_PLUS_SPAWN_EGG.get());
        basicItem(ModItems.UCHIGATANA_PLUS_SPAWN_EGG.get());
        basicItem(ModItems.TACHI_PLUS_SPAWN_EGG.get());
        basicItem(ModItems.NAGINATA_PLUS_SPAWN_EGG.get());
        basicItem(ModItems.OOTACHI_PLUS_SPAWN_EGG.get());
        basicItem(ModItems.YARI_PLUS_SPAWN_EGG.get());

        basicItem(ModItems.TANTOU_MAX_SPAWN_EGG.get());
        basicItem(ModItems.WAKIZASHI_MAX_SPAWN_EGG.get());
        basicItem(ModItems.UCHIGATANA_MAX_SPAWN_EGG.get());
        basicItem(ModItems.TACHI_MAX_SPAWN_EGG.get());
        basicItem(ModItems.NAGINATA_MAX_SPAWN_EGG.get());
        basicItem(ModItems.OOTACHI_MAX_SPAWN_EGG.get());
        basicItem(ModItems.YARI_MAX_SPAWN_EGG.get());

        basicItem(ModItems.KEBIISHI_TACHI_SPAWN_EGG.get());
        basicItem(ModItems.KEBIISHI_OOTACHI_SPAWN_EGG.get());
        basicItem(ModItems.KEBIISHI_YARI_SPAWN_EGG.get());
        basicItem(ModItems.KEBIISHI_NAGINATA_SPAWN_EGG.get());
        basicItem(ModItems.KEBIISHI_LEADER_SPAWN_EGG.get());

        basicItem(ModItems.KONNOSUKE_SPAWN_EGG.get());
    }

    private void eggItem(Item item) {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(item);
        if (id == null) return;
        withExistingParent(id.getPath(), EGG_TEMPLATE);
    }
}
