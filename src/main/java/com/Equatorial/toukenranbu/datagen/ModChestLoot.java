package com.Equatorial.toukenranbu.datagen;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.item.ModItems;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

public class ModChestLoot implements LootTableSubProvider {
    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> output) {

        // ========== 政府分部 ==========
        output.accept(ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "chests/gov_branch"),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(2, 4))
                                .add(LootItem.lootTableItem(ModItems.SMALL_KOBAN.get())
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 16)))
                                        .setWeight(15))
                                .add(LootItem.lootTableItem(ModItems.BRONZE_OMAMORI.get())
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3)))
                                        .setWeight(10))
                                .add(LootItem.lootTableItem(ModItems.SILVER_OMAMORI.get())
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2)))
                                        .setWeight(6))
                                .add(LootItem.lootTableItem(ModItems.GOLD_OMAMORI.get())
                                        .setWeight(2))
                        )
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(0, 1))
                                .add(LootItem.lootTableItem(ModItems.YAMANBAGIRI_KUNIHIRO.get()).setWeight(3))
                                .add(LootItem.lootTableItem(ModItems.KASHUU_KIYOMITSU.get()).setWeight(3))
                                .add(LootItem.lootTableItem(ModItems.HACHISUKA_KOTETSU.get()).setWeight(3))
                                .add(LootItem.lootTableItem(ModItems.MUTSUNOKAMI_YOSHIYUKI.get()).setWeight(3))
                                .add(LootItem.lootTableItem(ModItems.KASEN_KANESADA.get()).setWeight(3))
                                .add(LootItem.lootTableItem(ModItems.YAMANBAGIRI_CHOGI.get()).setWeight(4))
                                .add(LootItem.lootTableItem(ModItems.ICHIGO_HITOFURI.get()).setWeight(1))
                                .add(LootItem.lootTableItem(ModItems.TSURUMARU_KUNINAGA.get()).setWeight(1))
                                .add(LootItem.lootTableItem(ModItems.SHOKUDAIKIRI_MITSUTADA.get()).setWeight(2))
                                .add(LootItem.lootTableItem(ModItems.ISHIKIRIMARU.get()).setWeight(2))
                                .add(LootItem.lootTableItem(ModItems.HESHIKIRI_HASEBE.get()).setWeight(3))
                                .add(LootItem.lootTableItem(ModItems.YAMATONOKAMI_YASUSADA.get()).setWeight(3))
                                .add(LootItem.lootTableItem(ModItems.OOKURIKARA.get()).setWeight(2))
                                .add(LootItem.lootTableItem(ModItems.TONBOKIRI.get()).setWeight(2))
                                .add(LootItem.lootTableItem(ModItems.TOMOEGATA_NAGINATA.get()).setWeight(2))
                                .add(LootItem.lootTableItem(ModItems.NIKKARI_AOE.get()).setWeight(3))
                                .add(LootItem.lootTableItem(ModItems.HORIKAWA_KUNIHIRO.get()).setWeight(3))
                                .add(LootItem.lootTableItem(ModItems.IMANOTSURUGI.get()).setWeight(4))
                                .add(LootItem.lootTableItem(ModItems.GOTOU_TOUSHIROU.get()).setWeight(4))
                        )
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(1, 3))
                                .add(LootItem.lootTableItem(Items.IRON_INGOT)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 6)))
                                        .setWeight(5))
                                .add(LootItem.lootTableItem(Items.GOLD_INGOT)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4)))
                                        .setWeight(3))
                                .add(LootItem.lootTableItem(Items.EMERALD)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 5)))
                                        .setWeight(4))
                                .add(LootItem.lootTableItem(Items.BOOK)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3)))
                                        .setWeight(3))
                        )
        );

        // ========== 以后新建建筑就往下加 ==========
        // output.accept(
        //     ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "chests/你的新建筑"),
        //     LootTable.lootTable()...
        // );

    }
}