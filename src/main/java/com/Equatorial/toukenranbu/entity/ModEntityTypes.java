package com.Equatorial.toukenranbu.entity;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.boss.WhiteMikazukiEntity;
import com.Equatorial.toukenranbu.entity.custom.KonnosukeEntity;
import com.Equatorial.toukenranbu.entity.custom.*;
import com.Equatorial.toukenranbu.entity.custom.kebiishi.*;
import com.Equatorial.toukenranbu.entity.touken.naginata.TomoegataNaginataEntity;
import com.Equatorial.toukenranbu.entity.touken.ootachi.IshikirimaruEntity;
import com.Equatorial.toukenranbu.entity.touken.tachi.IchigoHitofuriEntity;
import com.Equatorial.toukenranbu.entity.touken.tachi.MikazukiMunechikaEntity;
import com.Equatorial.toukenranbu.entity.touken.tachi.ShokudaikiriMitsutadaEntity;
import com.Equatorial.toukenranbu.entity.touken.tachi.TsurumaruKuninagaEntity;
import com.Equatorial.toukenranbu.entity.touken.tantou.GotouToushirouEntity;
import com.Equatorial.toukenranbu.entity.touken.tantou.ImanotsurugiEntity;
import com.Equatorial.toukenranbu.entity.touken.uchigatana.*;
import com.Equatorial.toukenranbu.entity.touken.wakizashi.HorikawaKunihiroEntity;
import com.Equatorial.toukenranbu.entity.touken.wakizashi.NikkariAoeEntity;
import com.Equatorial.toukenranbu.entity.touken.yari.TonbokiriEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ToukenRanbuMod.MOD_ID);

    public static final RegistryObject<EntityType<TantouEntity>> TANTOU =
            ENTITY_TYPES.register("tantou",
                    () -> EntityType.Builder.of(TantouEntity::new, MobCategory.MONSTER)
                            .sized(1.0f, 1.0f)
                            .build(ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "tantou").toString()));
    public static final RegistryObject<EntityType<WakizashiEntity>> WAKIZASHI =
            ENTITY_TYPES.register("wakizashi",
                    () -> EntityType.Builder.of(WakizashiEntity::new, MobCategory.MONSTER)
                            .sized(1.4f, 1.8f)
                            .build(ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "wakizashi").toString()));

    public static final RegistryObject<EntityType<MikazukiMunechikaEntity>> MIKAZUKI_MUNECHIKA =
            ENTITY_TYPES.register("mikazuki_munechika",
                    () -> EntityType.Builder.of(MikazukiMunechikaEntity::new, MobCategory.CREATURE)
                            .sized(0.6F, 1.8F)
                            .build(ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "mikazuki_munechika").toString()));
    public static final RegistryObject<EntityType<YamanbagiriKunihiroEntity>> YAMANBAGIRI_KUNIHIRO =
            ENTITY_TYPES.register("yamanbagiri_kunihiro",
                    () -> EntityType.Builder.of(YamanbagiriKunihiroEntity::new, MobCategory.CREATURE)
                            .sized(0.6F, 1.8F)
                            .build(ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "yamanbagiri_kunihiro").toString()));
    public static final RegistryObject<EntityType<KashuuKiyomitsuEntity>> KASHUU_KIYOMITSU =
            ENTITY_TYPES.register("kashuu_kiyomitsu",
                    () -> EntityType.Builder.of(KashuuKiyomitsuEntity::new, MobCategory.CREATURE)
                            .sized(0.6F, 1.8F)
                            .build(ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "kashuu_kiyomitsu").toString()));
    public static final RegistryObject<EntityType<HachisukaKotetsuEntity>> HACHISUKA_KOTETSU =
            ENTITY_TYPES.register("hachisuka_kotetsu",
                    () -> EntityType.Builder.of(HachisukaKotetsuEntity::new, MobCategory.CREATURE)
                            .sized(0.6F, 1.8F)
                            .build(ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "hachisuka_kotetsu").toString()));
    public static final RegistryObject<EntityType<KasenKanesadaEntity>> KASEN_KANESADA =
            ENTITY_TYPES.register("kasen_kanesada",
                    () -> EntityType.Builder.of(KasenKanesadaEntity::new, MobCategory.CREATURE)
                            .sized(0.6F, 1.8F)
                            .build(ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "kasen_kanesada").toString()));
    public static final RegistryObject<EntityType<MutsunokamiYoshiyukiEntity>> MUTSUNOKAMI_YOSHIYUKI =
            ENTITY_TYPES.register("mutsunokami_yoshiyuki",
                    () -> EntityType.Builder.of(MutsunokamiYoshiyukiEntity::new, MobCategory.CREATURE)
                            .sized(0.6F, 1.8F)
                            .build(ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "mutsunokami_yoshiyuki").toString()));

    public static final RegistryObject<EntityType<UchigatanaEntity>> UCHIGATANA =
            ENTITY_TYPES.register("uchigatana",
                    () -> EntityType.Builder.of(UchigatanaEntity::new, MobCategory.MONSTER)
                            .sized(0.6f, 1.8f)
                            .build(ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "uchigatana").toString()));
    public static final RegistryObject<EntityType<TachiEntity>> TACHI =
            ENTITY_TYPES.register("tachi",
                    () -> EntityType.Builder.of(TachiEntity::new, MobCategory.MONSTER)
                            .sized(0.8f, 2.0f)
                            .build(ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "tachi").toString()));
    public static final RegistryObject<EntityType<OotachiEntity>> OOTACHI =
            ENTITY_TYPES.register("ootachi",
                    () -> EntityType.Builder.of(OotachiEntity::new, MobCategory.MONSTER)
                            .sized(1.5f, 3.0f)
                            .build(ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "ootachi").toString()));
    public static final RegistryObject<EntityType<NaginataEntity>> NAGINATA =
            ENTITY_TYPES.register("naginata",
                    () -> EntityType.Builder.of(NaginataEntity::new, MobCategory.MONSTER)
                            .sized(1.5f, 2.5f)
                            .build(ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "naginata").toString()));
    public static final RegistryObject<EntityType<YariEntity>> YARI =
            ENTITY_TYPES.register("yari",
                    () -> EntityType.Builder.of(YariEntity::new, MobCategory.MONSTER)
                            .sized(1.5f, 2.2f)
                            .build(ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "yari").toString()));

    public static final RegistryObject<EntityType<TantouPLUSEntity>> TANTOU_PLUS =
            ENTITY_TYPES.register("tantou_plus",
                    () -> EntityType.Builder.of(TantouPLUSEntity::new, MobCategory.MONSTER)
                            .sized(1.0f, 1.0f)
                            .build(ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "tantou_plus").toString()));
    public static final RegistryObject<EntityType<WakizashiPLUSEntity>> WAKIZASHI_PLUS =
            ENTITY_TYPES.register("wakizashi_plus",
                    () -> EntityType.Builder.of(WakizashiPLUSEntity::new, MobCategory.MONSTER)
                            .sized(1.4f, 1.8f)
                            .build(ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "wakizashi_plus").toString()));
    public static final RegistryObject<EntityType<UchigatanaPLUSEntity>> UCHIGATANA_PLUS =
            ENTITY_TYPES.register("uchigatana_plus",
                    () -> EntityType.Builder.of(UchigatanaPLUSEntity::new, MobCategory.MONSTER)
                            .sized(0.6f, 1.8f)
                            .build(ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "uchigatana_plus").toString()));
    public static final RegistryObject<EntityType<TachiPLUSEntity>> TACHI_PLUS =
            ENTITY_TYPES.register("tachi_plus",
                    () -> EntityType.Builder.of(TachiPLUSEntity::new, MobCategory.MONSTER)
                            .sized(0.8f, 2.0f)
                            .build(ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "tachi_plus").toString()));
    public static final RegistryObject<EntityType<OotachiPLUSEntity>> OOTACHI_PLUS =
            ENTITY_TYPES.register("ootachi_plus",
                    () -> EntityType.Builder.of(OotachiPLUSEntity::new, MobCategory.MONSTER)
                            .sized(1.5f, 3.0f)
                            .build(ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "ootachi_plus").toString()));
    public static final RegistryObject<EntityType<NaginataPLUSEntity>> NAGINATA_PLUS =
            ENTITY_TYPES.register("naginata_plus",
                    () -> EntityType.Builder.of(NaginataPLUSEntity::new, MobCategory.MONSTER)
                            .sized(1.5f, 2.5f)
                            .build(ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "naginata_plus").toString()));
    public static final RegistryObject<EntityType<YariPLUSEntity>> YARI_PLUS =
            ENTITY_TYPES.register("yari_plus",
                    () -> EntityType.Builder.of(YariPLUSEntity::new, MobCategory.MONSTER)
                            .sized(1.5f, 2.2f)
                            .build(ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "yari_plus").toString()));

    public static final RegistryObject<EntityType<TantouMAXEntity>> TANTOU_MAX =
            ENTITY_TYPES.register("tantou_max",
                    () -> EntityType.Builder.of(TantouMAXEntity::new, MobCategory.MONSTER)
                            .sized(1.0f, 1.0f)
                            .build(ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "tantou_max").toString()));
    public static final RegistryObject<EntityType<WakizashiMAXEntity>> WAKIZASHI_MAX =
            ENTITY_TYPES.register("wakizashi_max",
                    () -> EntityType.Builder.of(WakizashiMAXEntity::new, MobCategory.MONSTER)
                            .sized(1.4f, 1.8f)
                            .build(ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "wakizashi_max").toString()));
    public static final RegistryObject<EntityType<UchigatanaMAXEntity>> UCHIGATANA_MAX =
            ENTITY_TYPES.register("uchigatana_max",
                    () -> EntityType.Builder.of(UchigatanaMAXEntity::new, MobCategory.MONSTER)
                            .sized(0.6f, 1.8f)
                            .build(ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "uchigatana_max").toString()));
    public static final RegistryObject<EntityType<TachiMAXEntity>> TACHI_MAX =
            ENTITY_TYPES.register("tachi_max",
                    () -> EntityType.Builder.of(TachiMAXEntity::new, MobCategory.MONSTER)
                            .sized(0.8f, 2.0f)
                            .build(ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "tachi_max").toString()));
    public static final RegistryObject<EntityType<OotachiMAXEntity>> OOTACHI_MAX =
            ENTITY_TYPES.register("ootachi_max",
                    () -> EntityType.Builder.of(OotachiMAXEntity::new, MobCategory.MONSTER)
                            .sized(1.5f, 3.0f)
                            .build(ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "ootachi_max").toString()));
    public static final RegistryObject<EntityType<NaginataMAXEntity>> NAGINATA_MAX =
            ENTITY_TYPES.register("naginata_max",
                    () -> EntityType.Builder.of(NaginataMAXEntity::new, MobCategory.MONSTER)
                            .sized(1.5f, 2.5f)
                            .build(ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "naginata_max").toString()));
    public static final RegistryObject<EntityType<YariMAXEntity>> YARI_MAX =
            ENTITY_TYPES.register("yari_max",
                    () -> EntityType.Builder.of(YariMAXEntity::new, MobCategory.MONSTER)
                            .sized(1.5f, 2.2f)
                            .build(ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "yari_max").toString()));

    public static final RegistryObject<EntityType<KebiishiLeaderEntity>> KEBIISHI_LEADER =
            ENTITY_TYPES.register("kebiishi_leader",
                    () -> EntityType.Builder.of(KebiishiLeaderEntity::new, MobCategory.MONSTER)
                            .sized(1.8f, 2.2f)
                            .build(ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "kebiishi_leader").toString()));
    public static final RegistryObject<EntityType<KebiishiTachiEntity>> KEBIISHI_TACHI =
            ENTITY_TYPES.register("kebiishi_tachi",
                    () -> EntityType.Builder.of(KebiishiTachiEntity::new, MobCategory.MONSTER)
                            .sized(0.8f, 2.0f)
                            .build(ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "kebiishi_tachi").toString()));
    public static final RegistryObject<EntityType<KebiishiOotachiEntity>> KEBIISHI_OOTACHI =
            ENTITY_TYPES.register("kebiishi_ootachi",
                    () -> EntityType.Builder.of(KebiishiOotachiEntity::new, MobCategory.MONSTER)
                            .sized(1.5f, 3.0f)
                            .build(ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "kebiishi_ootachi").toString()));
    public static final RegistryObject<EntityType<KebiishiNaginataEntity>> KEBIISHI_NAGINATA =
            ENTITY_TYPES.register("kebiishi_naginata",
                    () -> EntityType.Builder.of(KebiishiNaginataEntity::new, MobCategory.MONSTER)
                            .sized(1.5f, 2.5f)
                            .build(ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "kebiishi_naginata").toString()));
    public static final RegistryObject<EntityType<KebiishiYariEntity>> KEBIISHI_YARI =
            ENTITY_TYPES.register("kebiishi_yari",
                    () -> EntityType.Builder.of(KebiishiYariEntity::new, MobCategory.MONSTER)
                            .sized(1.5f, 2.2f)
                            .build(ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "kebiishi_yari").toString()));

    // 狐之助
    public static final RegistryObject<EntityType<KonnosukeEntity>> KONNOSUKE =
            ENTITY_TYPES.register("konnosuke",
                    () -> EntityType.Builder.of(KonnosukeEntity::new, MobCategory.CREATURE)
                            .sized(0.6F, 0.7F).build("konnosuke"));

    public static final RegistryObject<EntityType<IchigoHitofuriEntity>> ICHIGO_HITOFURI =
            ENTITY_TYPES.register("ichigo_hitofuri",
                    () -> EntityType.Builder.of(IchigoHitofuriEntity::new, MobCategory.CREATURE)
                            .sized(0.6F, 1.9F)
                            .build(ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "ichigo_hitofuri").toString()));
    public static final RegistryObject<EntityType<TsurumaruKuninagaEntity>> TSURUMARU_KUNINAGA =
            ENTITY_TYPES.register("tsurumaru_kuninaga",
                    () -> EntityType.Builder.of(TsurumaruKuninagaEntity::new, MobCategory.CREATURE)
                            .sized(0.6F, 1.9F)
                            .build(ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "tsurumaru_kuninaga").toString()));
    public static final RegistryObject<EntityType<ShokudaikiriMitsutadaEntity>> SHOKUDAIKIRI_MITSUTADA =
            ENTITY_TYPES.register("shokudaikiri_mitsutada",
                    () -> EntityType.Builder.of(ShokudaikiriMitsutadaEntity::new, MobCategory.CREATURE)
                            .sized(0.6F, 1.9F)
                            .build(ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "shokudaikiri_mitsutada").toString()));
    public static final RegistryObject<EntityType<IshikirimaruEntity>> ISHIKIRIMARU =
            ENTITY_TYPES.register("ishikirimaru",
                    () -> EntityType.Builder.of(IshikirimaruEntity::new, MobCategory.CREATURE)
                            .sized(0.9F, 2.2F)
                            .build(ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "ishikirimaru").toString()));
    public static final RegistryObject<EntityType<HeshikiriHasebeEntity>> HESHIKIRI_HASEBE =
            ENTITY_TYPES.register("heshikiri_hasebe",
                    () -> EntityType.Builder.of(HeshikiriHasebeEntity::new, MobCategory.CREATURE)
                            .sized(0.6F, 2.0F)
                            .build(ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "heshikiri_hasebe").toString()));
    public static final RegistryObject<EntityType<YamatonokamiYasusadaEntity>> YAMATONOKAMI_YASUSADA =
            ENTITY_TYPES.register("yamatonokami_yasusada",
                    () -> EntityType.Builder.of(YamatonokamiYasusadaEntity::new, MobCategory.CREATURE)
                            .sized(0.6F, 1.8F)
                            .build(ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "yamatonokami_yasusada").toString()));
    public static final RegistryObject<EntityType<YamanbagiriChogiEntity>> YAMANBAGIRI_CHOGI =
            ENTITY_TYPES.register("yamanbagiri_chogi",
                    () -> EntityType.Builder.of(YamanbagiriChogiEntity::new, MobCategory.CREATURE)
                            .sized(0.6F, 1.8F)
                            .build(ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "yamanbagiri_chogi").toString()));
    public static final RegistryObject<EntityType<OokurikaraEntity>> OOKURIKARA =
            ENTITY_TYPES.register("ookurikara",
                    () -> EntityType.Builder.of(OokurikaraEntity::new, MobCategory.CREATURE)
                            .sized(0.6F, 2.0F)
                            .build(ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "ookurikara").toString()));
    public static final RegistryObject<EntityType<TonbokiriEntity>> TONBOKIRI =
            ENTITY_TYPES.register("tonbokiri",
                    () -> EntityType.Builder.of(TonbokiriEntity::new, MobCategory.CREATURE)
                            .sized(0.9F, 2.3F)
                            .build(ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "tonbokiri").toString()));
    public static final RegistryObject<EntityType<TomoegataNaginataEntity>> TOMOEGATA_NAGINATA =
            ENTITY_TYPES.register("tomoegata_naginata",
                    () -> EntityType.Builder.of(TomoegataNaginataEntity::new, MobCategory.CREATURE)
                            .sized(0.9F, 2.3F)
                            .build(ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "tomoegata_naginata").toString()));
    public static final RegistryObject<EntityType<NikkariAoeEntity>> NIKKARI_AOE =
            ENTITY_TYPES.register("nikkari_aoe",
                    () -> EntityType.Builder.of(NikkariAoeEntity::new, MobCategory.CREATURE)
                            .sized(0.6F, 1.9F)
                            .build(ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "nikkari_aoe").toString()));
    public static final RegistryObject<EntityType<HorikawaKunihiroEntity>> HORIKAWA_KUNIHIRO =
            ENTITY_TYPES.register("horikawa_kunihiro",
                    () -> EntityType.Builder.of(HorikawaKunihiroEntity::new, MobCategory.CREATURE)
                            .sized(0.5F, 1.6F)
                            .build(ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "horikawa_kunihiro").toString()));
    public static final RegistryObject<EntityType<ImanotsurugiEntity>> IMANOTSURUGI =
            ENTITY_TYPES.register("imanotsurugi",
                    () -> EntityType.Builder.of(ImanotsurugiEntity::new, MobCategory.CREATURE)
                            .sized(0.4F, 1.4F)
                            .build(ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "imanotsurugi").toString()));
    public static final RegistryObject<EntityType<GotouToushirouEntity>> GOTOU_TOUSHIROU =
            ENTITY_TYPES.register("gotou_toushirou",
                    () -> EntityType.Builder.of(GotouToushirouEntity::new, MobCategory.CREATURE)
                            .sized(0.4F, 1.4F)
                            .build(ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "gotou_toushirou").toString()));

    public static final RegistryObject<EntityType<WhiteMikazukiEntity>> WHITE_MIKAZUKI_MUNECHIKA =
            ENTITY_TYPES.register("white_mikazuki_munechika",
                    () -> EntityType.Builder.of(WhiteMikazukiEntity::new, MobCategory.MONSTER)
                            .sized(0.6F, 1.8F)
                            .build(ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "white_mikazuki_munechika").toString()));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }

    public static void registerSpawnPlacements() {
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
    }
}