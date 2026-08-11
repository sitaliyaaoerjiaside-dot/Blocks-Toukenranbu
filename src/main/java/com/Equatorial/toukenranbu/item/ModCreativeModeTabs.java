package com.Equatorial.toukenranbu.item;

import com.Equatorial.toukenranbu.block.ModBlocks;
import com.Equatorial.toukenranbu.ToukenRanbuMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ToukenRanbuMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> TOUKENRANBU_TAB =
            CREATIVE_MODE_TABS.register("toukenranbu_tab", () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.MIKAZUKI_MUNECHIKA.get()))
                    .title(Component.translatable("itemGroup.toukenranbu_tab"))
                    .displayItems((pParameters, pOutput) -> {

                        //pOutput.accept(ModBlocks.SWORD_FORGE.get());

                        pOutput.accept(ModBlocks.CHARRED_GRASS_BLOCK.get());
                        pOutput.accept(ModBlocks.CHARRED_DIRT.get());

                        pOutput.accept(ModBlocks.WHETSTONE_ORE.get());
                        pOutput.accept(ModBlocks.WHETSTONE_BLOCK.get());
                        pOutput.accept(ModBlocks.WHETSTONE_STAIRS.get());
                        pOutput.accept(ModBlocks.WHETSTONE_SLAB.get());
                        pOutput.accept(ModBlocks.WHETSTONE_BUTTON.get());
                        pOutput.accept(ModBlocks.WHETSTONE_PRESSURE_PLATE.get());
                        pOutput.accept(ModBlocks.WHETSTONE_FENCE.get());
                        pOutput.accept(ModBlocks.WHETSTONE_FENCE_GATE.get());
                        pOutput.accept(ModBlocks.WHETSTONE_WALL.get());
                        pOutput.accept(ModBlocks.WHETSTONE_DOOR.get());
                        pOutput.accept(ModBlocks.WHETSTONE_TRAPDOOR.get());
                        pOutput.accept(ModBlocks.WOOTZ_STEEL_ORE.get());
                        pOutput.accept(ModBlocks.WOOTZ_STEEL_BLOCK.get());
                        pOutput.accept(ModBlocks.WOOTZ_STEEL_STAIRS.get());
                        pOutput.accept(ModBlocks.WOOTZ_STEEL_SLAB.get());
                        pOutput.accept(ModBlocks.WOOTZ_STEEL_BUTTON.get());
                        pOutput.accept(ModBlocks.WOOTZ_STEEL_PRESSURE_PLATE.get());
                        pOutput.accept(ModBlocks.WOOTZ_STEEL_FENCE.get());
                        pOutput.accept(ModBlocks.WOOTZ_STEEL_FENCE_GATE.get());
                        pOutput.accept(ModBlocks.WOOTZ_STEEL_WALL.get());
                        pOutput.accept(ModBlocks.WOOTZ_STEEL_DOOR.get());
                        pOutput.accept(ModBlocks.WOOTZ_STEEL_TRAPDOOR.get());
                        pOutput.accept(ModBlocks.COOLANT_ORE.get());
                        pOutput.accept(ModBlocks.COOLANT_BLOCK.get());
                        pOutput.accept(ModBlocks.CHARRED_SAPLING.get());
                        pOutput.accept(ModBlocks.CHARRED_LOG.get());
                        pOutput.accept(ModBlocks.CHARRED_WOOD.get());
                        pOutput.accept(ModBlocks.CHARRED_PLANKS.get());
                        pOutput.accept(ModBlocks.CHARRED_LEAVES.get());
                        pOutput.accept(ModBlocks.STRIPPED_CHARRED_LOG.get());
                        pOutput.accept(ModBlocks.STRIPPED_CHARRED_WOOD.get());
                        pOutput.accept(ModBlocks.CHARRED_SAPLING.get());
                        pOutput.accept(ModBlocks.CHARRED_STAIRS.get());
                        pOutput.accept(ModBlocks.CHARRED_SLAB.get());
                        pOutput.accept(ModBlocks.CHARRED_FENCE.get());
                        pOutput.accept(ModBlocks.CHARRED_FENCE_GATE.get());
                        pOutput.accept(ModBlocks.CHARRED_DOOR.get());
                        pOutput.accept(ModBlocks.CHARRED_TRAPDOOR.get());
                        pOutput.accept(ModBlocks.CHARRED_BUTTON.get());
                        pOutput.accept(ModBlocks.CHARRED_PRESSURE_PLATE.get());

                        pOutput.accept(ModItems.WOOTZ_STEEL.get());
                        pOutput.accept(ModItems.WHETSTONE.get());
                        pOutput.accept(ModItems.COOLANT.get());
                        pOutput.accept(ModItems.AMULET.get());
                        pOutput.accept(ModItems.SUPREME_AMULET.get());
                        pOutput.accept(ModItems.A_SET_OF_PAPER_AND_PEN.get());
                        pOutput.accept(ModItems.POWER_OF_ATTORNEY.get());
                        pOutput.accept(ModItems.SMALL_KOBAN.get());
                        pOutput.accept(ModItems.SPEED_UP_POTION.get());
                        pOutput.accept(ModItems.DAMAGED_SWORD_FRAGMENTS.get());
                        pOutput.accept(ModItems.DICE.get());

                        pOutput.accept(ModItems.MIKAZUKI_MUNECHIKA.get());
                        pOutput.accept(ModItems.YAMANBAGIRI_KUNIHIRO.get());
                        pOutput.accept(ModItems.KASHUU_KIYOMITSU.get());
                        pOutput.accept(ModItems.KASEN_KANESADA.get());
                        pOutput.accept(ModItems.HACHISUKA_KOTETSU.get());
                        pOutput.accept(ModItems.MUTSUNOKAMI_YOSHIYUKI.get());

                        pOutput.accept(ModItems.KONNOSUKE_SPAWN_EGG.get());

                        pOutput.accept(ModItems.TANTOU_SPAWN_EGG.get());
                        pOutput.accept(ModItems.WAKIZASHI_SPAWN_EGG.get());
                        pOutput.accept(ModItems.UCHIGATANA_SPAWN_EGG.get());
                        pOutput.accept(ModItems.TACHI_SPAWN_EGG.get());
                        pOutput.accept(ModItems.NAGINATA_SPAWN_EGG.get());
                        pOutput.accept(ModItems.OOTACHI_SPAWN_EGG.get());
                        pOutput.accept(ModItems.YARI_SPAWN_EGG.get());

                        pOutput.accept(ModItems.TANTOU_PLUS_SPAWN_EGG.get());
                        pOutput.accept(ModItems.WAKIZASHI_PLUS_SPAWN_EGG.get());
                        pOutput.accept(ModItems.UCHIGATANA_PLUS_SPAWN_EGG.get());
                        pOutput.accept(ModItems.TACHI_PLUS_SPAWN_EGG.get());
                        pOutput.accept(ModItems.NAGINATA_PLUS_SPAWN_EGG.get());
                        pOutput.accept(ModItems.OOTACHI_PLUS_SPAWN_EGG.get());
                        pOutput.accept(ModItems.YARI_PLUS_SPAWN_EGG.get());

                        pOutput.accept(ModItems.TANTOU_MAX_SPAWN_EGG.get());
                        pOutput.accept(ModItems.WAKIZASHI_MAX_SPAWN_EGG.get());
                        pOutput.accept(ModItems.UCHIGATANA_MAX_SPAWN_EGG.get());
                        pOutput.accept(ModItems.TACHI_MAX_SPAWN_EGG.get());
                        pOutput.accept(ModItems.NAGINATA_MAX_SPAWN_EGG.get());
                        pOutput.accept(ModItems.OOTACHI_MAX_SPAWN_EGG.get());
                        pOutput.accept(ModItems.YARI_MAX_SPAWN_EGG.get());

                        pOutput.accept(ModItems.KEBIISHI_TACHI_SPAWN_EGG.get());
                        pOutput.accept(ModItems.KEBIISHI_OOTACHI_SPAWN_EGG.get());
                        pOutput.accept(ModItems.KEBIISHI_YARI_SPAWN_EGG.get());
                        pOutput.accept(ModItems.KEBIISHI_NAGINATA_SPAWN_EGG.get());
                        pOutput.accept(ModItems.KEBIISHI_LEADER_SPAWN_EGG.get());

//分割线。上面是材料物品，下面是食物燃料
                        pOutput.accept(ModItems.A_STRING_OF_DANGO.get());
                        pOutput.accept(ModItems.A_BITE_OF_DANGO.get());
                        pOutput.accept(ModItems.IMMORTAL_DUMPLINGS.get());
                        pOutput.accept(ModItems.TURBID_SPIRITUAL_ENERGY.get());
                        pOutput.accept(ModItems.PURE_SPIRITUAL_ENERGY.get());
                        pOutput.accept(ModItems.VOID_SPIRITUAL_ENERGY.get());
                        pOutput.accept(ModItems.SOLDIER_CANDY.get());
                        pOutput.accept(ModItems.TROOP_CANDY.get());
                        pOutput.accept(ModItems.OIL_TOFU.get());

//工具
                        pOutput.accept(ModItems.WOOTZ_STEEL_SWORD.get());
                        pOutput.accept(ModItems.WOOTZ_STEEL_PICKAXE.get());
                        pOutput.accept(ModItems.WOOTZ_STEEL_AXE.get());
                        pOutput.accept(ModItems.WOOTZ_STEEL_SHOVEL.get());
                        pOutput.accept(ModItems.WOOTZ_STEEL_HOE.get());

//工具
                        pOutput.accept(ModItems.WHETSTONE_SWORD.get());
                        pOutput.accept(ModItems.WHETSTONE_PICKAXE.get());
                        pOutput.accept(ModItems.WHETSTONE_AXE.get());
                        pOutput.accept(ModItems.WHETSTONE_SHOVEL.get());
                        pOutput.accept(ModItems.WHETSTONE_HOE.get());

//分割线。上面是方块，下面是盔甲
                        pOutput.accept(ModItems.WOOTZ_STEEL_HELMET.get());
                        pOutput.accept(ModItems.WOOTZ_STEEL_CHESTPLATE.get());
                        pOutput.accept(ModItems.WOOTZ_STEEL_LEGGINGS.get());
                        pOutput.accept(ModItems.WOOTZ_STEEL_BOOTS.get());
                        pOutput.accept(ModItems.WHETSTONE_HELMET.get());
                        pOutput.accept(ModItems.WHETSTONE_CHESTPLATE.get());
                        pOutput.accept(ModItems.WHETSTONE_LEGGINGS.get());
                        pOutput.accept(ModItems.WHETSTONE_BOOTS.get());

                        pOutput.accept(ModItems.BRONZE_OMAMORI.get());
                        pOutput.accept(ModItems.SILVER_OMAMORI.get());
                        pOutput.accept(ModItems.GOLD_OMAMORI.get());

                    }).build());

public static void register(IEventBus eventBus) {
    CREATIVE_MODE_TABS.register(eventBus);
  }
}

