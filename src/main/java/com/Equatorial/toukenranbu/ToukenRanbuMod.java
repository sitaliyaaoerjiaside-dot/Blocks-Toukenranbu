package com.Equatorial.toukenranbu;

import com.Equatorial.toukenranbu.advancement.ModAdvancementTriggers;
import com.Equatorial.toukenranbu.advancement.ModCriteriaTriggers;
import com.Equatorial.toukenranbu.block.ModBlocks;
import com.Equatorial.toukenranbu.effect.ModEffects;
import com.Equatorial.toukenranbu.entity.ModEntityTypes;
import com.Equatorial.toukenranbu.entity.renderer.*;
import com.Equatorial.toukenranbu.entity.renderer.KonnosukeRenderer;
import com.Equatorial.toukenranbu.entity.renderer.tachi.MikazukiMunechikaRenderer;
import com.Equatorial.toukenranbu.entity.renderer.uchigatana.*;
import com.Equatorial.toukenranbu.event.AmuletDeathHandler;
import com.Equatorial.toukenranbu.event.JikkoKillHandler;
import com.Equatorial.toukenranbu.item.ModCreativeModeTabs;
import com.Equatorial.toukenranbu.item.ModItems;
import com.Equatorial.toukenranbu.loot.ModLootModifiers;
import com.Equatorial.toukenranbu.screen.ModMenuTypes;
import com.Equatorial.toukenranbu.network.ModNetwork;
import com.Equatorial.toukenranbu.screen.ToukenDanshiScreen;
import com.Equatorial.toukenranbu.villager.ModVillagers;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;
import software.bernie.geckolib.GeckoLib;

@Mod(ToukenRanbuMod.MOD_ID)
public class ToukenRanbuMod
{

    public static final String MOD_ID = "toukenranbu_mod";

    private static final Logger LOGGER = LogUtils.getLogger();


    public ToukenRanbuMod(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();

        modEventBus.addListener(this::commonSetup);

        ModItems.register(modEventBus);
        ModCreativeModeTabs.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModLootModifiers.register(modEventBus);
        ModEffects.EFFECTS.register(modEventBus);
        // ModBlockEntities.register(modEventBus);
        ModVillagers.register(modEventBus);
        ModMenuTypes.MENUS.register(modEventBus);
        ModEntityTypes.register(modEventBus);
        GeckoLib.initialize();
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(AmuletDeathHandler.class);
        MinecraftForge.EVENT_BUS.register(new JikkoKillHandler());
        // ModDimensions.register(modEventBus);

        modEventBus.addListener(this::addCreative);

        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        ModAdvancementTriggers.register(event);
        ModCriteriaTriggers.register();

        event.enqueueWork(() -> {
            ModNetwork.register();
            SpawnPlacements.register(ModEntityTypes.TANTOU.get(),
                    SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Monster::checkMonsterSpawnRules);
            SpawnPlacements.register(ModEntityTypes.WAKIZASHI.get(),
                    SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Monster::checkMonsterSpawnRules);
            SpawnPlacements.register(ModEntityTypes.UCHIGATANA.get(),
                    SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Monster::checkMonsterSpawnRules);
            SpawnPlacements.register(ModEntityTypes.TACHI.get(),
                    SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Monster::checkMonsterSpawnRules);
            SpawnPlacements.register(ModEntityTypes.NAGINATA.get(),
                    SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Monster::checkMonsterSpawnRules);
            SpawnPlacements.register(ModEntityTypes.OOTACHI.get(),
                    SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Monster::checkMonsterSpawnRules);
            SpawnPlacements.register(ModEntityTypes.YARI.get(),
                    SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Monster::checkMonsterSpawnRules);

            SpawnPlacements.register(ModEntityTypes.TANTOU_PLUS.get(),
                    SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Monster::checkMonsterSpawnRules);
            SpawnPlacements.register(ModEntityTypes.WAKIZASHI_PLUS.get(),
                    SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Monster::checkMonsterSpawnRules);
            SpawnPlacements.register(ModEntityTypes.UCHIGATANA_PLUS.get(),
                    SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Monster::checkMonsterSpawnRules);
            SpawnPlacements.register(ModEntityTypes.TACHI_PLUS.get(),
                    SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Monster::checkMonsterSpawnRules);
            SpawnPlacements.register(ModEntityTypes.NAGINATA_PLUS.get(),
                    SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Monster::checkMonsterSpawnRules);
            SpawnPlacements.register(ModEntityTypes.OOTACHI_PLUS.get(),
                    SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Monster::checkMonsterSpawnRules);
            SpawnPlacements.register(ModEntityTypes.YARI_PLUS.get(),
                    SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Monster::checkMonsterSpawnRules);

            SpawnPlacements.register(ModEntityTypes.TANTOU_MAX.get(),
                    SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Monster::checkMonsterSpawnRules);
            SpawnPlacements.register(ModEntityTypes.WAKIZASHI_MAX.get(),
                    SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Monster::checkMonsterSpawnRules);
            SpawnPlacements.register(ModEntityTypes.UCHIGATANA_MAX.get(),
                    SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Monster::checkMonsterSpawnRules);
            SpawnPlacements.register(ModEntityTypes.TACHI_MAX.get(),
                    SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Monster::checkMonsterSpawnRules);
            SpawnPlacements.register(ModEntityTypes.NAGINATA_MAX.get(),
                    SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Monster::checkMonsterSpawnRules);
            SpawnPlacements.register(ModEntityTypes.OOTACHI_MAX.get(),
                    SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Monster::checkMonsterSpawnRules);
            SpawnPlacements.register(ModEntityTypes.YARI_MAX.get(),
                    SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Monster::checkMonsterSpawnRules);

            SpawnPlacements.register(ModEntityTypes.KONNOSUKE.get(),
                    SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Animal::checkAnimalSpawnRules);
        });

        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.WOOTZ_STEEL);
            event.accept(ModItems.COOLANT);
            event.accept(ModItems.WHETSTONE);
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

        LOGGER.info("HELLO from server starting");
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {

            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
            EntityRenderers.register(ModEntityTypes.TANTOU.get(), HistoryRetrogradeArmyTantouRenderer::new);
            EntityRenderers.register(ModEntityTypes.WAKIZASHI.get(), HistoryRetrogradeArmyWakizashiRenderer::new);

            EntityRenderers.register(ModEntityTypes.MIKAZUKI_MUNECHIKA.get(), ctx -> new MikazukiMunechikaRenderer(ctx));
            EntityRenderers.register(ModEntityTypes.YAMANBAGIRI_KUNIHIRO.get(), ctx -> new YamanbagiriKunihiroRenderer(ctx));
            EntityRenderers.register(ModEntityTypes.KASHUU_KIYOMITSU.get(), ctx -> new KashuuKiyomitsuRenderer(ctx));
            EntityRenderers.register(ModEntityTypes.HACHISUKA_KOTETSU.get(), ctx -> new HachisukaKotetsuRenderer(ctx));
            EntityRenderers.register(ModEntityTypes.KASEN_KANESADA.get(), ctx -> new KasenKanesadaRenderer(ctx));
            EntityRenderers.register(ModEntityTypes.MUTSUNOKAMI_YOSHIYUKI.get(), ctx -> new MutsunokamiYoshiyukiRenderer(ctx));
            EntityRenderers.register(ModEntityTypes.UCHIGATANA.get(), HistoryRetrogradeArmyUchigatanaRenderer::new);
            EntityRenderers.register(ModEntityTypes.TACHI.get(), HistoryRetrogradeArmyTachiRenderer::new);
            EntityRenderers.register(ModEntityTypes.NAGINATA.get(), HistoryRetrogradeArmyNaginataRenderer::new);
            EntityRenderers.register(ModEntityTypes.OOTACHI.get(), HistoryRetrogradeArmyOotachiRenderer::new);
            EntityRenderers.register(ModEntityTypes.YARI.get(), HistoryRetrogradeArmyYariRenderer::new);

            EntityRenderers.register(ModEntityTypes.TANTOU_PLUS.get(), HistoryRetrogradeArmyTantouPLUSRenderer::new);
            EntityRenderers.register(ModEntityTypes.WAKIZASHI_PLUS.get(), HistoryRetrogradeArmyWakizashiPLUSRenderer::new);
            EntityRenderers.register(ModEntityTypes.UCHIGATANA_PLUS.get(), HistoryRetrogradeArmyUchigatanaPLUSRenderer::new);
            EntityRenderers.register(ModEntityTypes.TACHI_PLUS.get(), HistoryRetrogradeArmyTachiPLUSRenderer::new);
            EntityRenderers.register(ModEntityTypes.OOTACHI_PLUS.get(), HistoryRetrogradeArmyOotachiPLUSRenderer::new);
            EntityRenderers.register(ModEntityTypes.NAGINATA_PLUS.get(), HistoryRetrogradeArmyNaginataPLUSRenderer::new);
            EntityRenderers.register(ModEntityTypes.YARI_PLUS.get(), HistoryRetrogradeArmyYariPLUSRenderer::new);

            EntityRenderers.register(ModEntityTypes.TANTOU_MAX.get(), HistoryRetrogradeArmyTantouMAXRenderer::new);
            EntityRenderers.register(ModEntityTypes.WAKIZASHI_MAX.get(), HistoryRetrogradeArmyWakizashiMAXRenderer::new);
            EntityRenderers.register(ModEntityTypes.UCHIGATANA_MAX.get(), HistoryRetrogradeArmyUchigatanaMAXRenderer::new);
            EntityRenderers.register(ModEntityTypes.TACHI_MAX.get(), HistoryRetrogradeArmyTachiMAXRenderer::new);
            EntityRenderers.register(ModEntityTypes.OOTACHI_MAX.get(), HistoryRetrogradeArmyOotachiMAXRenderer::new);
            EntityRenderers.register(ModEntityTypes.NAGINATA_MAX.get(), HistoryRetrogradeArmyNaginataMAXRenderer::new);
            EntityRenderers.register(ModEntityTypes.YARI_MAX.get(), HistoryRetrogradeArmyYariMAXRenderer::new);

            EntityRenderers.register(ModEntityTypes.KEBIISHI_LEADER.get(), KebiishiLeaderRenderer::new);
            EntityRenderers.register(ModEntityTypes.KEBIISHI_TACHI.get(), KebiishiTachiRenderer::new);
            EntityRenderers.register(ModEntityTypes.KEBIISHI_OOTACHI.get(), KebiishiOotachiRenderer::new);
            EntityRenderers.register(ModEntityTypes.KEBIISHI_NAGINATA.get(), KebiishiNaginataRenderer::new);
            EntityRenderers.register(ModEntityTypes.KEBIISHI_YARI.get(), KebiishiYariRenderer::new);

            EntityRenderers.register(ModEntityTypes.KONNOSUKE.get(), KonnosukeRenderer::new);

            //MenuScreens.register(ModMenuTypes.SWORD_FORGE_MENU.get(), SwordForgeScreen::new);
            MenuScreens.register(ModMenuTypes.TOUKEN_DANSHI_MENU.get(), ToukenDanshiScreen::new);
        }
    }
}
