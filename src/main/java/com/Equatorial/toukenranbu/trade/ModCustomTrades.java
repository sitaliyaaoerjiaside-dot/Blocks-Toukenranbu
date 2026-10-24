//如果有什么需要调整的村民交易在这边调，不要再问小判还有没有什么其他兑换了，想要什么自己加。
//对于整合包：如果想把小判作为通用货币，请自行来这边添加以适配整合包的其他兑换条目。
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
                    new ItemStack(Items.DIAMOND, 18),
                    new ItemStack(ModItems.SUPREME_AMULET.get(), 1),
                    3, 35, 0.3f
            ));
            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.DIAMOND, 24),
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
                    new ItemStack(ModItems.SMALL_KOBAN.get(), 15),
                    new ItemStack(ModItems.PURE_SPIRITUAL_ENERGY.get(), 1),
                    3, 10, 0.3f
            ));
            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
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
            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.SMALL_KOBAN.get(), 30),
                    new ItemStack(ModItems.HORSE_OUTEI.get(), 1),
                    2, 10, 0.3f
            ));
            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.SMALL_KOBAN.get(), 30),
                    new ItemStack(ModItems.HORSE_MIKUNIGURO.get(), 1),
                    2, 10, 0.3f
            ));
            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.SMALL_KOBAN.get(), 30),
                    new ItemStack(ModItems.HORSE_KOHIBARI.get(), 1),
                    2, 10, 0.3f
            ));
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.SMALL_KOBAN.get(), 30),
                    new ItemStack(ModItems.HORSE_MATSUKAZE.get(), 1),
                    2, 10, 0.3f
            ));
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.SMALL_KOBAN.get(), 30),
                    new ItemStack(ModItems.HORSE_KAKOJI.get(), 1),
                    2, 10, 0.3f
            ));
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.SMALL_KOBAN.get(), 30),
                    new ItemStack(ModItems.HORSE_SEIHAKU.get(), 1),
                    2, 10, 0.3f
            ));
            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.SMALL_KOBAN.get(), 30),
                    new ItemStack(ModItems.HORSE_MOCHIZUKI.get(), 1),
                    2, 10, 0.3f
            ));
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.SMALL_KOBAN.get(), 30),
                    new ItemStack(ModItems.HORSE_AOGE.get(), 1),
                    2, 10, 0.3f
            ));
            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.SMALL_KOBAN.get(), 30),
                    new ItemStack(ModItems.HORSE_KAGE.get(), 1),
                    2, 10, 0.3f
            ));
            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.SMALL_KOBAN.get(), 30),
                    new ItemStack(ModItems.HORSE_SHIROGE.get(), 1),
                    2, 10, 0.3f
            ));
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.SMALL_KOBAN.get(), 30),
                    new ItemStack(ModItems.HORSE_UMAGOU.get(), 1),
                    2, 10, 0.3f
            ));

            // ===== 附魔之瓶兑换 =====
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.SMALL_KOBAN.get(), 8),
                    new ItemStack(Items.EXPERIENCE_BOTTLE, 3),
                    16, 2, 0.15f
            ));
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.SMALL_KOBAN.get(), 16),
                    new ItemStack(Items.EXPERIENCE_BOTTLE, 5),
                    12, 5, 0.15f
            ));
            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.SMALL_KOBAN.get(), 28),
                    new ItemStack(Items.EXPERIENCE_BOTTLE, 8),
                    8, 10, 0.15f
            ));

            // ===== 新增：小判换绿宝石 / 钻石 / 下界合金碎片 =====
            // 48个小判换2个绿宝石
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.SMALL_KOBAN.get(), 48),
                    new ItemStack(Items.EMERALD, 2),
                    16, 5, 0.05f
            ));
            // 64个小判换1个钻石
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.SMALL_KOBAN.get(), 64),
                    new ItemStack(Items.DIAMOND, 1),
                    12, 10, 0.05f
            ));
            // 16个钻石换1个下界合金碎片
            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.DIAMOND, 16),
                    new ItemStack(Items.NETHERITE_SCRAP, 1),
                    8, 15, 0.1f
            ));
        }
    }

    @SubscribeEvent
    public static void addWanderingTraderTrades(net.minecraftforge.event.village.WandererTradesEvent event) {
        // ===== 1. 普通交易 (Generic Trades) =====
        List<VillagerTrades.ItemListing> genericTrades = event.getGenericTrades();

        // 1个绿宝石换8个小判
        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 1),
                new ItemStack(ModItems.SMALL_KOBAN.get(), 8),
                16, 1, 0.05f
        ));

        // 1个绿宝石换2个一口团子
        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 1),
                new ItemStack(ModItems.A_BITE_OF_DANGO.get(), 2),
                12, 1, 0.05f
        ));

        // 2个绿宝石换1个纯净灵力
        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 2),
                new ItemStack(ModItems.PURE_SPIRITUAL_ENERGY.get(), 1),
                8, 2, 0.05f
        ));

        // ===== 2. 稀有交易 (Rare Trades) =====
        List<VillagerTrades.ItemListing> rareTrades = event.getRareTrades();

        // 5个绿宝石换1个金刀装 (稀有)
        rareTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 5),
                new ItemStack(ModItems.GOLD_OMAMORI.get(), 1),
                3, 10, 0.1f
        ));

        // 3个绿宝石换1个御守 (稀有)
        rareTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 3),
                new ItemStack(ModItems.AMULET.get(), 1),
                3, 10, 0.1f
        ));

        // 1个钻石换32个小判 (稀有，方便玩家快速获取小判)
        rareTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.DIAMOND, 1),
                new ItemStack(ModItems.SMALL_KOBAN.get(), 32),
                5, 5, 0.05f
        ));

        // 1个下界合金碎片换1个极御守 (超级稀有)
        rareTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.NETHERITE_SCRAP, 1),
                new ItemStack(ModItems.SUPREME_AMULET.get(), 1),
                1, 30, 0.2f
        ));
    }
}