package com.Equatorial.toukenranbu;

import com.Equatorial.toukenranbu.advancement.ModAdvancementTriggers;
import com.Equatorial.toukenranbu.advancement.ModCriteriaTriggers;
import com.Equatorial.toukenranbu.block.ModBlocks;
import com.Equatorial.toukenranbu.effect.ModEffects;
import com.Equatorial.toukenranbu.entity.ModEntityTypes;
import com.Equatorial.toukenranbu.entity.renderer.*;
import com.Equatorial.toukenranbu.entity.renderer.uchigatana.*;
import com.Equatorial.toukenranbu.event.AmuletDeathHandler;
import com.Equatorial.toukenranbu.event.JikkoKillHandler;
import com.Equatorial.toukenranbu.event.ToukenHealthCompat;
import com.Equatorial.toukenranbu.item.ModCreativeModeTabs;
import com.Equatorial.toukenranbu.item.ModItems;
import com.Equatorial.toukenranbu.loot.ModLootModifiers;
import com.Equatorial.toukenranbu.network.ModNetwork;
import com.Equatorial.toukenranbu.villager.ModVillagers;
import com.mojang.logging.LogUtils;
import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
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
        com.Equatorial.toukenranbu.screen.ModMenus.MENUS.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModLootModifiers.register(modEventBus);
        ModEffects.EFFECTS.register(modEventBus);
        // ModBlockEntities.register(modEventBus);
        ModVillagers.register(modEventBus);
        ModEntityTypes.register(modEventBus);
        com.Equatorial.toukenranbu.particle.ModParticleTypes.register(modEventBus);
        GeckoLib.initialize();
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(AmuletDeathHandler.class);
        MinecraftForge.EVENT_BUS.register(new JikkoKillHandler());
        MinecraftForge.EVENT_BUS.register(ToukenHealthCompat.class);

        MinecraftForge.EVENT_BUS.register(com.Equatorial.toukenranbu.event.ToukenSpawnHandler.class);

        MinecraftForge.EVENT_BUS.addListener((net.minecraftforge.event.TickEvent.ServerTickEvent event) -> {
            if (event.phase != net.minecraftforge.event.TickEvent.Phase.END) return;
            for (net.minecraft.server.level.ServerLevel level : event.getServer().getAllLevels()) {
                com.Equatorial.toukenranbu.entity.bubble.BubbleQueueManager.tick(level);
            }
        });

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

            MinecraftForge.EVENT_BUS.register(com.Equatorial.toukenranbu.ToukenCombatEvents.class);
            MinecraftForge.EVENT_BUS.register(com.Equatorial.toukenranbu.util.InstantDeathHelper.class);

            // 搬过去的实体生成规则方法
            ModEntityTypes.registerSpawnPlacements();

        });

        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));

        CustomPortalBuilder.beginPortal()
                .frameBlock(ModBlocks.WHITE_NIGHT_PORTAL_FRAME.get())
                .lightWithItem(ModItems.MOON_PHASE.get())
                .forcedSize(2,3)
                .destDimID(ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID,"white_night_garden"))
                .tintColor(0xFFFFFF)
                .registerPortal();
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
}
