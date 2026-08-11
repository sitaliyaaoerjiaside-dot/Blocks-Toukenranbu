package com.Equatorial.toukenranbu.datagen;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.block.ModBlocks;
import com.Equatorial.toukenranbu.item.ModItems;
import com.Equatorial.toukenranbu.loot.AddItemModifier;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

public class ModGlobalLootModifiersProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifiersProvider(PackOutput output) {
        super(output, ToukenRanbuMod.MOD_ID);
    }

    @Override
    protected void start() {
        // ========== 矿石 ==========
        add("bronze_omamori_from_whetstone_ore", new AddItemModifier(new LootItemCondition[] {
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.WHETSTONE_ORE.get()).build(),
                LootItemRandomChanceCondition.randomChance(0.1f).build()}, ModItems.BRONZE_OMAMORI.get(),1,1));
        add("bronze_omamori_from_wootz_steel_ore", new AddItemModifier(new LootItemCondition[] {
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.WOOTZ_STEEL_ORE.get()).build(),
                LootItemRandomChanceCondition.randomChance(0.1f).build()}, ModItems.BRONZE_OMAMORI.get(),1,1));
        add("bronze_omamori_from_coolant_ore", new AddItemModifier(new LootItemCondition[] {
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.COOLANT_ORE.get()).build(),
                LootItemRandomChanceCondition.randomChance(0.1f).build()}, ModItems.BRONZE_OMAMORI.get(),1,1));

        // ========== 小判 ==========
        addChest("small_koban_from_jungle_temple", "chests/jungle_temple", ModItems.SMALL_KOBAN.get(), 1, 3, 0.35f);
        addChest("small_koban_from_desert_pyramid", "chests/desert_pyramid", ModItems.SMALL_KOBAN.get(), 2, 3, 0.35f);
        addChest("small_koban_from_igloo", "chests/igloo_chest", ModItems.SMALL_KOBAN.get(), 1, 3, 1.0f);
        // 原 ocean_monument 不存在，替换为水下废墟
        addChest("small_koban_from_underwater_ruin_big", "chests/underwater_ruin_big", ModItems.SMALL_KOBAN.get(), 2, 3, 0.4f);
        addChest("small_koban_from_underwater_ruin_small", "chests/underwater_ruin_small", ModItems.SMALL_KOBAN.get(), 1, 2, 0.4f);

        String[] shipwrecks = {"shipwreck_map", "shipwreck_supply", "shipwreck_treasure"};
        for (String s : shipwrecks) {
            addChest("small_koban_from_" + s, "chests/" + s, ModItems.SMALL_KOBAN.get(), 1, 2, 1.0f);
        }

        String[] strongholds = {"stronghold_corridor", "stronghold_crossing", "stronghold_library"};
        for (String s : strongholds) {
            addChest("small_koban_from_" + s, "chests/" + s, ModItems.SMALL_KOBAN.get(), 3, 6, 1.0f);
        }

        String[] villages = {
                "chests/village/village_plains_house",
                "chests/village/village_desert_house",
                "chests/village/village_savanna_house",
                "chests/village/village_snowy_house",
                "chests/village/village_taiga_house",
                "chests/village/village_weaponsmith",
                "chests/village/village_toolsmith",
                "chests/village/village_tannery",
                "chests/village/village_shepherd",
                "chests/village/village_fletcher",
                "chests/village/village_fisher",
                "chests/village/village_butcher",
                "chests/village/village_cartographer",
                "chests/village/village_mason",
                "chests/village/village_armorer",
                "chests/village/village_temple"
        };
        for (String v : villages) {
            String id = v.substring(v.lastIndexOf('/') + 1);
            addChest("small_koban_from_" + id, v, ModItems.SMALL_KOBAN.get(), 5, 10, 1.0f);
        }

        addChest("small_koban_from_woodland_mansion", "chests/woodland_mansion", ModItems.SMALL_KOBAN.get(), 4, 5, 0.35f);
        addChest("small_koban_from_buried_treasure", "chests/buried_treasure", ModItems.SMALL_KOBAN.get(), 2, 3, 1.0f);
        addChest("small_koban_from_ancient_city", "chests/ancient_city", ModItems.SMALL_KOBAN.get(), 5, 8, 1.0f);
        addChest("small_koban_from_nether_bridge", "chests/nether_bridge", ModItems.SMALL_KOBAN.get(), 4, 6, 1.0f);
        addChest("small_koban_from_ruined_portal", "chests/ruined_portal", ModItems.SMALL_KOBAN.get(), 3, 5, 0.35f);

        String[] bastions = {"bastion_bridge", "bastion_hoglin_stable", "bastion_other", "bastion_treasure"};
        for (String b : bastions) {
            addChest("small_koban_from_" + b, "chests/" + b, ModItems.SMALL_KOBAN.get(), 4, 5, 0.50f);
        }

        addChest("small_koban_from_end_city_treasure", "chests/end_city_treasure", ModItems.SMALL_KOBAN.get(), 5, 7, 0.35f);

        // ========== 刀装 ==========
        addChest("bronze_omamori_from_jungle_temple", "chests/jungle_temple", ModItems.BRONZE_OMAMORI.get(), 1, 1, 0.30f);
        addChest("bronze_omamori_from_desert_pyramid", "chests/desert_pyramid", ModItems.BRONZE_OMAMORI.get(), 1, 2, 0.30f);
        addChest("bronze_omamori_from_igloo", "chests/igloo_chest", ModItems.BRONZE_OMAMORI.get(), 1, 1, 1.0f);
        // 原 ocean_monument 不存在，替换为水下废墟
        addChest("silver_omamori_from_underwater_ruin_big", "chests/underwater_ruin_big", ModItems.SILVER_OMAMORI.get(), 1, 1, 0.4f);

        for (String s : shipwrecks) {
            addChest("bronze_omamori_from_" + s, "chests/" + s, ModItems.BRONZE_OMAMORI.get(), 1, 1, 1.0f);
        }

        for (String s : strongholds) {
            addChest("silver_omamori_from_" + s, "chests/" + s, ModItems.SILVER_OMAMORI.get(), 1, 1, 1.0f);
        }

        for (String v : villages) {
            String id = v.substring(v.lastIndexOf('/') + 1);
            addChest("bronze_omamori_from_" + id, v, ModItems.BRONZE_OMAMORI.get(), 1, 2, 1.0f);
        }

        addChest("silver_omamori_from_woodland_mansion", "chests/woodland_mansion", ModItems.SILVER_OMAMORI.get(), 1, 2, 0.35f);
        addChest("gold_omamori_from_buried_treasure", "chests/buried_treasure", ModItems.GOLD_OMAMORI.get(), 1, 2, 0.10f);
        addChest("gold_omamori_from_ancient_city", "chests/ancient_city", ModItems.GOLD_OMAMORI.get(), 3, 3, 0.35f);
        addChest("silver_omamori_from_nether_bridge", "chests/nether_bridge", ModItems.SILVER_OMAMORI.get(), 4, 6, 1.0f);
        addChest("silver_omamori_from_ruined_portal", "chests/ruined_portal", ModItems.SILVER_OMAMORI.get(), 1, 1, 0.10f);

        for (String b : bastions) {
            addChest("gold_omamori_from_" + b, "chests/" + b, ModItems.GOLD_OMAMORI.get(), 3, 5, 0.35f);
        }

        addChest("gold_omamori_from_end_city_treasure", "chests/end_city_treasure", ModItems.GOLD_OMAMORI.get(), 1, 2, 0.30f);

        // ========== 新增：召唤物品加入战利品 ==========
        // 地牢箱子有几率刷各种各样的刀
        addChest("summon_yamanbagiri_from_dungeon", "chests/simple_dungeon",
                ModItems.YAMANBAGIRI_KUNIHIRO.get(), 1, 1, 0.15f);
        addChest("summon_kiyomitsu_from_dungeon", "chests/simple_dungeon",
                ModItems.KASHUU_KIYOMITSU.get(), 1, 1, 0.15f);
        addChest("summon_hachisuka_from_dungeon", "chests/simple_dungeon",
                ModItems.HACHISUKA_KOTETSU.get(), 1, 1, 0.15f);
        addChest("summon_mutsunokami_from_dungeon", "chests/simple_dungeon",
                ModItems.MUTSUNOKAMI_YOSHIYUKI.get(),1,1,0.15f);
        addChest("summon_kasen_from_dungeon", "chests/simple_dungeon",
                ModItems.KASEN_KANESADA.get(), 1, 1, 0.15f);

        // 末地城宝箱有几率刷三日月宗近
        addChest("summon_mikazuki_from_end_city", "chests/end_city_treasure",
                ModItems.MIKAZUKI_MUNECHIKA.get(), 1, 1, 0.08f);

        //时之政府分部的奖励箱会刷战利品。（自定义建筑的我就不写这了）

        // ========== 召唤物品加入废弃下界传送门 ==========
        addChest("summon_yamanbagiri_from_ruined_portal", "chests/ruined_portal", ModItems.YAMANBAGIRI_KUNIHIRO.get(), 1, 1, 0.15f);
        addChest("summon_kiyomitsu_from_ruined_portal", "chests/ruined_portal", ModItems.KASHUU_KIYOMITSU.get(), 1, 1, 0.15f);
        addChest("summon_hachisuka_from_ruined_portal", "chests/ruined_portal", ModItems.HACHISUKA_KOTETSU.get(), 1, 1, 0.15f);
        addChest("summon_mutsunokami_from_ruined_portal", "chests/ruined_portal", ModItems.MUTSUNOKAMI_YOSHIYUKI.get(), 1, 1, 0.15f);
        addChest("summon_kasen_from_ruined_portal", "chests/ruined_portal", ModItems.KASEN_KANESADA.get(), 1, 1, 0.15f);

        // ========== 召唤物品加入沙漠神殿 ==========
        addChest("summon_yamanbagiri_from_desert_pyramid", "chests/desert_pyramid", ModItems.YAMANBAGIRI_KUNIHIRO.get(), 1, 1, 0.15f);
        addChest("summon_kiyomitsu_from_desert_pyramid", "chests/desert_pyramid", ModItems.KASHUU_KIYOMITSU.get(), 1, 1, 0.15f);
        addChest("summon_hachisuka_from_desert_pyramid", "chests/desert_pyramid", ModItems.HACHISUKA_KOTETSU.get(), 1, 1, 0.15f);
        addChest("summon_mutsunokami_from_desert_pyramid", "chests/desert_pyramid", ModItems.MUTSUNOKAMI_YOSHIYUKI.get(), 1, 1, 0.15f);
        addChest("summon_kasen_from_desert_pyramid", "chests/desert_pyramid", ModItems.KASEN_KANESADA.get(), 1, 1, 0.15f);

        // ========== 召唤物品加入丛林神殿 ==========
        addChest("summon_yamanbagiri_from_jungle_temple", "chests/jungle_temple", ModItems.YAMANBAGIRI_KUNIHIRO.get(), 1, 1, 0.15f);
        addChest("summon_kiyomitsu_from_jungle_temple", "chests/jungle_temple", ModItems.KASHUU_KIYOMITSU.get(), 1, 1, 0.15f);
        addChest("summon_hachisuka_from_jungle_temple", "chests/jungle_temple", ModItems.HACHISUKA_KOTETSU.get(), 1, 1, 0.15f);
        addChest("summon_mutsunokami_from_jungle_temple", "chests/jungle_temple", ModItems.MUTSUNOKAMI_YOSHIYUKI.get(), 1, 1, 0.15f);
        addChest("summon_kasen_from_jungle_temple", "chests/jungle_temple", ModItems.KASEN_KANESADA.get(), 1, 1, 0.15f);

    }

    private void addChest(String name, String path, Item item, int min, int max, float chance) {
        LootItemCondition[] conditions;
        if (chance >= 1.0f) {
            conditions = new LootItemCondition[] {
                    LootTableIdCondition.builder(ResourceLocation.fromNamespaceAndPath("minecraft", path)).build()
            };
        } else {
            conditions = new LootItemCondition[] {
                    LootTableIdCondition.builder(ResourceLocation.fromNamespaceAndPath("minecraft", path)).build(),
                    LootItemRandomChanceCondition.randomChance(chance).build()
            };
        }
        add(name, new AddItemModifier(conditions, item, min, max));
    }
}