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
import com.Equatorial.toukenranbu.item.ModCreativeModeTabs;
import com.Equatorial.toukenranbu.item.ModItems;
import com.Equatorial.toukenranbu.loot.ModLootModifiers;
import com.Equatorial.toukenranbu.network.ModNetwork;
import com.Equatorial.toukenranbu.villager.ModVillagers;
import com.mojang.logging.LogUtils;
import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
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

    public static final net.minecraftforge.common.util.Lazy<net.minecraft.client.KeyMapping> SORT_INVENTORY_KEY =
            net.minecraftforge.common.util.Lazy.of(() -> new net.minecraft.client.KeyMapping(
                    "key.toukenranbu.sort_inventory",
                    net.minecraftforge.client.settings.KeyConflictContext.GUI,
                    com.mojang.blaze3d.platform.InputConstants.Type.KEYSYM,
                    org.lwjgl.glfw.GLFW.GLFW_KEY_R,
                    "key.categories.toukenranbu"
            ));

    public ToukenRanbuMod(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();

        modEventBus.addListener(this::commonSetup);

        com.Equatorial.toukenranbu.screen.EntityUIMenuType.MENUS.register(modEventBus);
        ModItems.register(modEventBus);
        ModCreativeModeTabs.register(modEventBus);
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
        // ModDimensions.register(modEventBus);

        modEventBus.addListener(this::addCreative);
        modEventBus.addListener(this::onRegisterKeyMappings);

        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        ModAdvancementTriggers.register(event);
        ModCriteriaTriggers.register();

        event.enqueueWork(() -> {
            ModNetwork.register();

            MinecraftForge.EVENT_BUS.register(com.Equatorial.toukenranbu.ToukenCombatEvents.class);

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

            // ========== 白夜之庭：无主刀男地面生成规则 ==========
            // 注意：这里不包含三日月宗近和山姥切长义
            SpawnPlacements.register(ModEntityTypes.YAMANBAGIRI_KUNIHIRO.get(),
                    SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Animal::checkAnimalSpawnRules);
            SpawnPlacements.register(ModEntityTypes.KASHUU_KIYOMITSU.get(),
                    SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Animal::checkAnimalSpawnRules);
            SpawnPlacements.register(ModEntityTypes.HACHISUKA_KOTETSU.get(),
                    SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Animal::checkAnimalSpawnRules);
            SpawnPlacements.register(ModEntityTypes.KASEN_KANESADA.get(),
                    SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Animal::checkAnimalSpawnRules);
            SpawnPlacements.register(ModEntityTypes.MUTSUNOKAMI_YOSHIYUKI.get(),
                    SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Animal::checkAnimalSpawnRules);
            SpawnPlacements.register(ModEntityTypes.ICHIGO_HITOFURI.get(),
                    SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Animal::checkAnimalSpawnRules);
            SpawnPlacements.register(ModEntityTypes.TSURUMARU_KUNINAGA.get(),
                    SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Animal::checkAnimalSpawnRules);
            SpawnPlacements.register(ModEntityTypes.SHOKUDAIKIRI_MITSUTADA.get(),
                    SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Animal::checkAnimalSpawnRules);
            SpawnPlacements.register(ModEntityTypes.ISHIKIRIMARU.get(),
                    SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Animal::checkAnimalSpawnRules);
            SpawnPlacements.register(ModEntityTypes.HESHIKIRI_HASEBE.get(),
                    SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Animal::checkAnimalSpawnRules);
            SpawnPlacements.register(ModEntityTypes.YAMATONOKAMI_YASUSADA.get(),
                    SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Animal::checkAnimalSpawnRules);
            SpawnPlacements.register(ModEntityTypes.OOKURIKARA.get(),
                    SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Animal::checkAnimalSpawnRules);
            SpawnPlacements.register(ModEntityTypes.TONBOKIRI.get(),
                    SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Animal::checkAnimalSpawnRules);
            SpawnPlacements.register(ModEntityTypes.TOMOEGATA_NAGINATA.get(),
                    SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Animal::checkAnimalSpawnRules);
            SpawnPlacements.register(ModEntityTypes.NIKKARI_AOE.get(),
                    SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Animal::checkAnimalSpawnRules);
            SpawnPlacements.register(ModEntityTypes.HORIKAWA_KUNIHIRO.get(),
                    SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Animal::checkAnimalSpawnRules);
            SpawnPlacements.register(ModEntityTypes.IMANOTSURUGI.get(),
                    SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Animal::checkAnimalSpawnRules);
            SpawnPlacements.register(ModEntityTypes.GOTOU_TOUSHIROU.get(),
                    SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Animal::checkAnimalSpawnRules);
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

    private void onRegisterKeyMappings(net.minecraftforge.client.event.RegisterKeyMappingsEvent event) {
        event.register(SORT_INVENTORY_KEY.get());
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

        LOGGER.info("HELLO from server starting");
    }

    @SubscribeEvent
    public void onMobSpawn(net.minecraftforge.event.entity.living.MobSpawnEvent.PositionCheck event) {

        if (!(event.getLevel() instanceof net.minecraft.server.level.ServerLevel serverLevel)) return;
        if (!serverLevel.dimension().equals(
                com.Equatorial.toukenranbu.world.registry.ModDimensions.WHITE_NIGHT_GARDEN_LEVEL)) {
            // 其他维度：只拦模组的刀男
            net.minecraft.resources.ResourceLocation id =
                    event.getEntity().getType().builtInRegistryHolder().key().location();
            if (id.getNamespace().equals(ToukenRanbuMod.MOD_ID)
                    && event.getEntity().getType().getCategory() == net.minecraft.world.entity.MobCategory.CREATURE
                    && !event.getEntity().getType().equals(
                    com.Equatorial.toukenranbu.entity.ModEntityTypes.KONNOSUKE.get())) {
                event.setResult(net.minecraftforge.eventbus.api.Event.Result.DENY);
            }
            return;
        }

        // 白夜之庭：白名单制
        net.minecraft.world.entity.MobCategory cat = event.getEntity().getType().getCategory();

        boolean isWhiteMikazuki = event.getEntity().getType().equals(
                com.Equatorial.toukenranbu.entity.ModEntityTypes.WHITE_MIKAZUKI_MUNECHIKA.get());

        boolean isMinion = event.getEntity().getPersistentData().getBoolean("touken_boss_minion");

        boolean allowed = cat == net.minecraft.world.entity.MobCategory.CREATURE
                || cat == net.minecraft.world.entity.MobCategory.AMBIENT
                || cat == net.minecraft.world.entity.MobCategory.WATER_CREATURE
                || cat == net.minecraft.world.entity.MobCategory.WATER_AMBIENT
                || isWhiteMikazuki
                || isMinion;

        if (!allowed) {
            event.setResult(net.minecraftforge.eventbus.api.Event.Result.DENY);
        }
    }

    @SubscribeEvent
    public void onEntityJoin(net.minecraftforge.event.entity.EntityJoinLevelEvent event) {
        if (!(event.getLevel() instanceof net.minecraft.server.level.ServerLevel serverLevel)) return;
        if (!serverLevel.dimension().equals(
                com.Equatorial.toukenranbu.world.registry.ModDimensions.WHITE_NIGHT_GARDEN_LEVEL)) return;
        if (event.getEntity() instanceof net.minecraft.world.entity.player.Player) return;

        net.minecraft.world.entity.MobCategory cat = event.getEntity().getType().getCategory();

        boolean isWhiteMikazuki = event.getEntity().getType().equals(
                com.Equatorial.toukenranbu.entity.ModEntityTypes.WHITE_MIKAZUKI_MUNECHIKA.get());

        boolean isMinion = event.getEntity().getPersistentData().getBoolean("touken_boss_minion");

        boolean allowed = cat == net.minecraft.world.entity.MobCategory.CREATURE
                || cat == net.minecraft.world.entity.MobCategory.AMBIENT
                || cat == net.minecraft.world.entity.MobCategory.WATER_CREATURE
                || cat == net.minecraft.world.entity.MobCategory.WATER_AMBIENT
                || cat == net.minecraft.world.entity.MobCategory.MISC
                || isWhiteMikazuki
                || isMinion;

        if (!allowed) {
            event.setCanceled(true);
        }
    }
}
