package com.Equatorial.toukenranbu.item;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.ModEntityTypes;
import com.Equatorial.toukenranbu.item.custom.DiceItem;
import com.Equatorial.toukenranbu.item.custom.CustomArmorItem;
import com.Equatorial.toukenranbu.item.custom.ModFuelItem;
import com.Equatorial.toukenranbu.item.touken.uchigatana.*;
import com.Equatorial.toukenranbu.item.touken.tachi.MikazukiMunechikaItem;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, ToukenRanbuMod.MOD_ID);

    //物品、材料
    public static final RegistryObject<Item> WOOTZ_STEEL =
            ITEMS.register("wootz_steel",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> COOLANT =
            ITEMS.register("coolant",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> WHETSTONE =
            ITEMS.register("whetstone",() -> new Item(new Item.Properties()));

    //7.3：御守和极御守功能正式实装
    public static final RegistryObject<Item> AMULET =
            ITEMS.register("amulet",() -> new Item(new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.EPIC)
                    .fireResistant()));
    public static final RegistryObject<Item> SUPREME_AMULET =
            ITEMS.register("supreme_amulet",() -> new Item(new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.EPIC)
                    .fireResistant()));

    public static final RegistryObject<Item> A_SET_OF_PAPER_AND_PEN =
            ITEMS.register("a_set_of_paper_and_pen",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DAMAGED_SWORD_FRAGMENTS =
            ITEMS.register("damaged_sword_fragments",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> POWER_OF_ATTORNEY =
            ITEMS.register("power_of_attorney",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SPEED_UP_POTION =
            ITEMS.register("speed_up_potion",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SMALL_KOBAN =
            ITEMS.register("small_koban",() -> new Item(new Item.Properties()));

    //食物
    public static final RegistryObject<Item> A_BITE_OF_DANGO =
            ITEMS.register("a_bite_of_dango",() -> new Item(new Item.Properties().food(ModFoods.A_BITE_OF_DANGO)));
    public static final RegistryObject<Item> IMMORTAL_DUMPLINGS =
            ITEMS.register("immortal_dumplings",() -> new Item(new Item.Properties().food(ModFoods.IMMORTAL_DUMPLINGS)));
    public static final RegistryObject<Item> A_STRING_OF_DANGO =
            ITEMS.register("a_string_of_dango",() -> new Item(new Item.Properties().food(ModFoods.A_STRING_OF_DANGO)));
    public static final RegistryObject<Item> TURBID_SPIRITUAL_ENERGY =
            ITEMS.register("turbid_spiritual_energy",() -> new Item(new Item.Properties().food(ModFoods.TURBID_SPIRITUAL_ENERGY)));
    public static final RegistryObject<Item> PURE_SPIRITUAL_ENERGY =
            ITEMS.register("pure_spiritual_energy",() -> new Item(new Item.Properties().food(ModFoods.PURE_SPIRITUAL_ENERGY)));

    //燃料
    public static final RegistryObject<Item> VOID_SPIRITUAL_ENERGY =
            ITEMS.register("void_spiritual_energy",() -> new ModFuelItem(new Item.Properties(), 12000));

    //工具、武器
    public static final RegistryObject<Item> WOOTZ_STEEL_SWORD = ITEMS.register("wootz_steel_sword",
            () -> new SwordItem(ModToolTiers.WOOTZ_STEEL,8, 3, new Item.Properties()));
    public static final RegistryObject<Item> WOOTZ_STEEL_PICKAXE = ITEMS.register("wootz_steel_pickaxe",
            () -> new PickaxeItem(ModToolTiers.WOOTZ_STEEL,1, 1, new Item.Properties()));
    public static final RegistryObject<Item> WOOTZ_STEEL_AXE = ITEMS.register("wootz_steel_axe",
            () -> new AxeItem(ModToolTiers.WOOTZ_STEEL,7, 1, new Item.Properties()));
    public static final RegistryObject<Item> WOOTZ_STEEL_SHOVEL = ITEMS.register("wootz_steel_shovel",
            () -> new ShovelItem(ModToolTiers.WOOTZ_STEEL,1, 1, new Item.Properties()));
    public static final RegistryObject<Item> WOOTZ_STEEL_HOE = ITEMS.register("wootz_steel_hoe",
            () -> new HoeItem(ModToolTiers.WOOTZ_STEEL,1, 1, new Item.Properties()));
    //分割线分一下，不然我看的头晕
    public static final RegistryObject<Item> WHETSTONE_SWORD = ITEMS.register("whetstone_sword",
            () -> new SwordItem(ModToolTiers.WOOTZ_STEEL,8, 3, new Item.Properties()));
    public static final RegistryObject<Item> WHETSTONE_PICKAXE = ITEMS.register("whetstone_pickaxe",
            () -> new PickaxeItem(ModToolTiers.WOOTZ_STEEL,1, 1, new Item.Properties()));
    public static final RegistryObject<Item> WHETSTONE_AXE = ITEMS.register("whetstone_axe",
            () -> new AxeItem(ModToolTiers.WOOTZ_STEEL,7, 1, new Item.Properties()));
    public static final RegistryObject<Item> WHETSTONE_SHOVEL = ITEMS.register("whetstone_shovel",
            () -> new ShovelItem(ModToolTiers.WOOTZ_STEEL,1, 1, new Item.Properties()));
    public static final RegistryObject<Item> WHETSTONE_HOE = ITEMS.register("whetstone_hoe",
            () -> new HoeItem(ModToolTiers.WOOTZ_STEEL,1, 1, new Item.Properties()));

    public static final RegistryObject<Item> WOOTZ_STEEL_HELMET = ITEMS.register("wootz_steel_helmet",
            () -> new CustomArmorItem(ModArmorMaterials.WOOTZ_STEEL, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> WOOTZ_STEEL_CHESTPLATE = ITEMS.register("wootz_steel_chestplate",
            () -> new ArmorItem(ModArmorMaterials.WOOTZ_STEEL, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> WOOTZ_STEEL_LEGGINGS = ITEMS.register("wootz_steel_leggings",
            () -> new ArmorItem(ModArmorMaterials.WOOTZ_STEEL, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> WOOTZ_STEEL_BOOTS = ITEMS.register("wootz_steel_boots",
            () -> new ArmorItem(ModArmorMaterials.WOOTZ_STEEL, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<Item> WHETSTONE_HELMET = ITEMS.register("whetstone_helmet",
            () -> new CustomArmorItem(ModArmorMaterials.WHETSTONE, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> WHETSTONE_CHESTPLATE = ITEMS.register("whetstone_chestplate",
            () -> new ArmorItem(ModArmorMaterials.WHETSTONE, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> WHETSTONE_LEGGINGS = ITEMS.register("whetstone_leggings",
            () -> new ArmorItem(ModArmorMaterials.WHETSTONE, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> WHETSTONE_BOOTS = ITEMS.register("whetstone_boots",
            () -> new ArmorItem(ModArmorMaterials.WHETSTONE, ArmorItem.Type.BOOTS, new Item.Properties()));

//刷怪蛋这一块：
    public static final RegistryObject<Item> TANTOU_SPAWN_EGG = ITEMS.register("tantou_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityTypes.TANTOU,0x000000,0x2e8b57,
                    new Item.Properties()));
    public static final RegistryObject<Item> WAKIZASHI_SPAWN_EGG = ITEMS.register("wakizashi_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityTypes.WAKIZASHI,0x000000,0xbcb88a,
                    new Item.Properties()));

    public static final RegistryObject<Item> GOLD_OMAMORI = ITEMS.register("gold_omamori",
            () -> new OmamoriItem(5.0, 10.0, 0.1, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> SILVER_OMAMORI = ITEMS.register("silver_omamori",
            () -> new OmamoriItem(3.0, 6.0, 0.05, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> BRONZE_OMAMORI = ITEMS.register("bronze_omamori",
            () -> new OmamoriItem(1.0, 2.0, 0.02, new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> SOLDIER_CANDY = ITEMS.register("soldier_candy",
            () -> new ExpFoodItem(100, new Item.Properties().food(ModFoods.SOLDIER_CANDY)));
    public static final RegistryObject<Item> TROOP_CANDY = ITEMS.register("troop_candy",
            () -> new ExpFoodItem(1000, new Item.Properties().food(ModFoods.TROOP_CANDY)));

    public static final RegistryObject<Item> MIKAZUKI_MUNECHIKA = ITEMS.register("mikazuki_munechika",
            () -> new MikazukiMunechikaItem(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> YAMANBAGIRI_KUNIHIRO = ITEMS.register("yamanbagiri_kunihiro",
            () -> new YamanbagiriKunihiroItem(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> KASHUU_KIYOMITSU = ITEMS.register("kashuu_kiyomitsu",
            () -> new KashuuKiyomitsuItem(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> HACHISUKA_KOTETSU = ITEMS.register("hachisuka_kotetsu",
            () -> new HachisukaKotetsuItem(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> KASEN_KANESADA = ITEMS.register("kasen_kanesada",
            () -> new KasenKanesadaItem(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> MUTSUNOKAMI_YOSHIYUKI = ITEMS.register("mutsunokami_yoshiyuki",
            () -> new MutsunokamiYoshiyukiItem(new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> UCHIGATANA_SPAWN_EGG = ITEMS.register("uchigatana_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityTypes.UCHIGATANA,0x000000,0x8c7853,
                    new Item.Properties()));
    public static final RegistryObject<Item> TACHI_SPAWN_EGG = ITEMS.register("tachi_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityTypes.TACHI,0x000000,0x228b22,
                    new Item.Properties()));
    public static final RegistryObject<Item> NAGINATA_SPAWN_EGG = ITEMS.register("naginata_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityTypes.NAGINATA,0x000000,0x006400,
                    new Item.Properties()));
    public static final RegistryObject<Item> OOTACHI_SPAWN_EGG = ITEMS.register("ootachi_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityTypes.OOTACHI,0x000000,0xeded95,
                    new Item.Properties()));
    public static final RegistryObject<Item> YARI_SPAWN_EGG = ITEMS.register("yari_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityTypes.YARI,0x000000,0x008000,
                    new Item.Properties()));

    public static final RegistryObject<Item> TANTOU_PLUS_SPAWN_EGG = ITEMS.register("tantou_plus_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityTypes.TANTOU_PLUS,0x000000,0x9400d3,
                    new Item.Properties()));
    public static final RegistryObject<Item> WAKIZASHI_PLUS_SPAWN_EGG = ITEMS.register("wakizashi_plus_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityTypes.WAKIZASHI_PLUS,0x000000,0x8a2be2,
                    new Item.Properties()));
    public static final RegistryObject<Item> UCHIGATANA_PLUS_SPAWN_EGG = ITEMS.register("uchigatana_plus_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityTypes.UCHIGATANA_PLUS,0x000000,0xee82ee,
                    new Item.Properties()));
    public static final RegistryObject<Item> TACHI_PLUS_SPAWN_EGG = ITEMS.register("tachi_plus_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityTypes.TACHI_PLUS,0x000000,0xdda0dd,
                    new Item.Properties()));
    public static final RegistryObject<Item> OOTACHI_PLUS_SPAWN_EGG = ITEMS.register("ootachi_plus_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityTypes.OOTACHI_PLUS,0x000000,0x801dae,
                    new Item.Properties()));
    public static final RegistryObject<Item> YARI_PLUS_SPAWN_EGG = ITEMS.register("yari_plus_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityTypes.YARI_PLUS,0x000000,0xba79b1,
                    new Item.Properties()));
    public static final RegistryObject<Item> NAGINATA_PLUS_SPAWN_EGG = ITEMS.register("naginata_plus_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityTypes.NAGINATA_PLUS,0x000000,0x815476,
                    new Item.Properties()));

    public static final RegistryObject<Item> TANTOU_MAX_SPAWN_EGG = ITEMS.register("tantou_max_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityTypes.TANTOU_MAX,0x000000,0xdc143c,
                    new Item.Properties()));
    public static final RegistryObject<Item> WAKIZASHI_MAX_SPAWN_EGG = ITEMS.register("wakizashi_max_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityTypes.WAKIZASHI_MAX,0x000000,0x9b2423,
                    new Item.Properties()));
    public static final RegistryObject<Item> UCHIGATANA_MAX_SPAWN_EGG = ITEMS.register("uchigatana_max_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityTypes.UCHIGATANA_MAX,0x000000,0x9b2321,
                    new Item.Properties()));
    public static final RegistryObject<Item> TACHI_MAX_SPAWN_EGG = ITEMS.register("tachi_max_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityTypes.TACHI_MAX,0x000000,0x861a22,
                    new Item.Properties()));
    public static final RegistryObject<Item> OOTACHI_MAX_SPAWN_EGG = ITEMS.register("ootachi_max_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityTypes.OOTACHI_MAX,0x000000,0x59191f,
                    new Item.Properties()));
    public static final RegistryObject<Item> YARI_MAX_SPAWN_EGG = ITEMS.register("yari_max_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityTypes.YARI_MAX,0x000000,0x792423,
                    new Item.Properties()));
    public static final RegistryObject<Item> NAGINATA_MAX_SPAWN_EGG = ITEMS.register("naginata_max_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityTypes.NAGINATA_MAX,0x000000,0x972e25,
                    new Item.Properties()));

    public static final RegistryObject<Item> KEBIISHI_TACHI_SPAWN_EGG = ITEMS.register("kebiishi_tachi_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityTypes.KEBIISHI_TACHI,0x000000,0x1e90ff,
                    new Item.Properties()));
    public static final RegistryObject<Item> KEBIISHI_OOTACHI_SPAWN_EGG = ITEMS.register("kebiishi_ootachi_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityTypes.KEBIISHI_OOTACHI,0x000000,0x3299cc,
                    new Item.Properties()));
    public static final RegistryObject<Item> KEBIISHI_YARI_SPAWN_EGG = ITEMS.register("kebiishi_yari_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityTypes.KEBIISHI_YARI,0x000000,0x23238e,
                    new Item.Properties()));
    public static final RegistryObject<Item> KEBIISHI_NAGINATA_SPAWN_EGG = ITEMS.register("kebiishi_naginata_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityTypes.KEBIISHI_NAGINATA,0x000000,0x00008b,
                    new Item.Properties()));
    public static final RegistryObject<Item> KEBIISHI_LEADER_SPAWN_EGG = ITEMS.register("kebiishi_leader_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityTypes.KEBIISHI_LEADER,0x000000,0x002fa7,
                    new Item.Properties()));

    public static final RegistryObject<Item> KONNOSUKE_SPAWN_EGG = ITEMS.register("konnosuke_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityTypes.KONNOSUKE,0xffff33,0xff3300,
                    new Item.Properties()));
    public static final RegistryObject<Item> OIL_TOFU =
            ITEMS.register("oil_tofu",() -> new Item(new Item.Properties().food(ModFoods.OIL_TOFU)));

    public static final RegistryObject<Item> DICE = ITEMS.register("dice",
            () -> new DiceItem(new Item.Properties().stacksTo(1)));

    public static void register(IEventBus eventBus){

        ITEMS.register(eventBus);
    }
}
