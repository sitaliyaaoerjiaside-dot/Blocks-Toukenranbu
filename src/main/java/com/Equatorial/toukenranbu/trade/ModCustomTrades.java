package com.Equatorial.toukenranbu.trade;

import com.Equatorial.toukenranbu.item.ModItems;
import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.villager.ModVillagers;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = ToukenRanbuMod.MOD_ID)
public class ModCustomTrades {
    @SubscribeEvent
    public static void addTrads(VillagerTradesEvent event) {
        if (event.getType() == VillagerProfession.WEAPONSMITH) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 5),
                    new ItemStack(ModItems.SPEED_UP_POTION.get(), 1),
                    10, 2, 0.08f
            ));
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 3),
                    new ItemStack(ModItems.SPEED_UP_POTION.get(), 1),
                    20, 2, 0.1f
            ));
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 2),
                    new ItemStack(ModItems.POWER_OF_ATTORNEY.get(), 1),
                    10, 2, 0.1f
            ));
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 2),
                    new ItemStack(ModItems.POWER_OF_ATTORNEY.get(), 1),
                    20, 2, 0.5f
            ));
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.COOLANT.get(), 15),
                    new ItemStack(ModItems.POWER_OF_ATTORNEY.get(), 2),
                    10, 2, 0.1f
            ));
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.COOLANT.get(), 10),
                    new ItemStack(ModItems.POWER_OF_ATTORNEY.get(), 2),
                    20, 2, 0.5f
            ));
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.COOLANT.get(), 10),
                    new ItemStack(ModItems.SPEED_UP_POTION.get(), 2),
                    10, 2, 0.1f
            ));
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.COOLANT.get(), 6),
                    new ItemStack(ModItems.SPEED_UP_POTION.get(), 2),
                    20, 2, 0.5f
            ));
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.SMALL_KOBAN.get(), 10),
                    new ItemStack(ModItems.SOLDIER_CANDY.get(), 1),
                    5, 2, 0.1f
            ));
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.SMALL_KOBAN.get(), 30),
                    new ItemStack(ModItems.TROOP_CANDY.get(), 2),
                    5, 2, 0.5f
            ));
        }

        if (event.getType() == ModVillagers.BLADESMITH.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 12),
                    new ItemStack(ModItems.AMULET.get(), 1),
                    5, 5, 0.08f
            ));
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.COOLANT.get(), 10),
                    new ItemStack(ModItems.AMULET.get(), 1),
                    5, 15, 0.05f
            ));
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.DIAMOND, 12),
                    new ItemStack(ModItems.SUPREME_AMULET.get(), 1),
                    3, 35, 0.3f
            ));
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.COOLANT.get(), 32),
                    new ItemStack(ModItems.SUPREME_AMULET.get(), 1),
                    3, 35, 0.3f
            ));
            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.COOLANT.get(), 64),
                    new ItemStack(ModItems.SUPREME_AMULET.get(), 1),
                    3, 35, 0.3f
            ));
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.DIAMOND, 24),
                    new ItemStack(ModItems.SUPREME_AMULET.get(), 1),
                    3, 35, 0.3f
            ));
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.DIAMOND, 36),
                    new ItemStack(ModItems.SUPREME_AMULET.get(), 1),
                    5, 35, 0.3f
            ));
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.SMALL_KOBAN.get(), 10),
                    new ItemStack(ModItems.BRONZE_OMAMORI.get(), 1),
                    6, 10, 0.3f
            ));
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.SMALL_KOBAN.get(), 15),
                    new ItemStack(ModItems.SILVER_OMAMORI.get(), 1),
                    6, 15, 0.3f
            ));
            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.SMALL_KOBAN.get(), 36),
                    new ItemStack(ModItems.GOLD_OMAMORI.get(), 1),
                    6, 20, 0.3f
            ));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.SMALL_KOBAN.get(), 24),
                    new ItemStack(ModItems.SUPREME_AMULET.get(), 1),
                    5, 35, 0.3f
            ));
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.SMALL_KOBAN.get(), 3),
                    new ItemStack(ModItems.A_BITE_OF_DANGO.get(), 1),
                    10, 10, 0.3f
            ));
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.SMALL_KOBAN.get(), 5),
                    new ItemStack(ModItems.A_STRING_OF_DANGO.get(), 1),
                    6, 10, 0.3f
            ));
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.SMALL_KOBAN.get(), 15),
                    new ItemStack(ModItems.IMMORTAL_DUMPLINGS.get(), 1),
                    4, 10, 0.3f
            ));
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.SMALL_KOBAN.get(), 15),
                    new ItemStack(ModItems.PURE_SPIRITUAL_ENERGY.get(), 1),
                    3, 10, 0.3f
            ));
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.SMALL_KOBAN.get(), 20),
                    new ItemStack(ModItems.CAPTURE_BALL.get(), 1),
                    3, 10, 0.3f
            ));
            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.SMALL_KOBAN.get(), 25),
                    new ItemStack(ModItems.CAPTURE_BALL.get(), 1),
                    2, 10, 0.3f
            ));
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.SMALL_KOBAN.get(), 30),
                    new ItemStack(ModItems.HORSE_TAKADONOGURO.get(), 1),
                    2, 10, 0.3f
            ));
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.SMALL_KOBAN.get(), 30),
                    new ItemStack(ModItems.HORSE_OUTEI.get(), 1),
                    2, 10, 0.3f
            ));
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.SMALL_KOBAN.get(), 30),
                    new ItemStack(ModItems.HORSE_MIKUNIGURO.get(), 1),
                    2, 10, 0.3f
            ));
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.SMALL_KOBAN.get(), 30),
                    new ItemStack(ModItems.HORSE_KOHIBARI.get(), 1),
                    2, 10, 0.3f
            ));
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.SMALL_KOBAN.get(), 30),
                    new ItemStack(ModItems.HORSE_MATSUKAZE.get(), 1),
                    2, 10, 0.3f
            ));
        }
    }
}
