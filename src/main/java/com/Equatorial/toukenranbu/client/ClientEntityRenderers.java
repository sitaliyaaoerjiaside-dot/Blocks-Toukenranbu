package com.Equatorial.toukenranbu.client;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.ModEntityTypes;
import com.Equatorial.toukenranbu.entity.boss.WhiteMikazukiEntity;
import com.Equatorial.toukenranbu.entity.renderer.*;
import com.Equatorial.toukenranbu.entity.renderer.boss.WhiteMikazukiRenderer;
import com.Equatorial.toukenranbu.entity.renderer.naginata.TomoegataNaginataRenderer;
import com.Equatorial.toukenranbu.entity.renderer.ootachi.IshikirimaruRenderer;
import com.Equatorial.toukenranbu.entity.renderer.tachi.IchigoHitofuriRenderer;
import com.Equatorial.toukenranbu.entity.renderer.tachi.MikazukiMunechikaRenderer;
import com.Equatorial.toukenranbu.entity.renderer.tachi.ShokudaikiriMitsutadaRenderer;
import com.Equatorial.toukenranbu.entity.renderer.tachi.TsurumaruKuninagaRenderer;
import com.Equatorial.toukenranbu.entity.renderer.tantou.GotouToushirouRenderer;
import com.Equatorial.toukenranbu.entity.renderer.tantou.ImanotsurugiRenderer;
import com.Equatorial.toukenranbu.entity.renderer.uchigatana.*;
import com.Equatorial.toukenranbu.entity.renderer.wakizashi.HorikawaKunihiroRenderer;
import com.Equatorial.toukenranbu.entity.renderer.wakizashi.NikkariAoeRenderer;
import com.Equatorial.toukenranbu.entity.renderer.yari.TonbokiriRenderer;
import com.Equatorial.toukenranbu.screen.EntityUIMenuType;
import com.lowdragmc.lowdraglib2.gui.holder.ModularUIContainerScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = ToukenRanbuMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEntityRenderers {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(ModEntityTypes.TANTOU.get(), HistoryRetrogradeArmyTantouRenderer::new);
        EntityRenderers.register(ModEntityTypes.WAKIZASHI.get(), HistoryRetrogradeArmyWakizashiRenderer::new);

        EntityRenderers.register(ModEntityTypes.MIKAZUKI_MUNECHIKA.get(), ctx -> new MikazukiMunechikaRenderer(ctx));
        EntityRenderers.register(ModEntityTypes.YAMANBAGIRI_KUNIHIRO.get(), ctx -> new YamanbagiriKunihiroRenderer(ctx));
        EntityRenderers.register(ModEntityTypes.KASHUU_KIYOMITSU.get(), ctx -> new KashuuKiyomitsuRenderer(ctx));
        EntityRenderers.register(ModEntityTypes.HACHISUKA_KOTETSU.get(), ctx -> new HachisukaKotetsuRenderer(ctx));
        EntityRenderers.register(ModEntityTypes.KASEN_KANESADA.get(), ctx -> new KasenKanesadaRenderer(ctx));
        EntityRenderers.register(ModEntityTypes.MUTSUNOKAMI_YOSHIYUKI.get(), ctx -> new MutsunokamiYoshiyukiRenderer(ctx));

        EntityRenderers.register(ModEntityTypes.ICHIGO_HITOFURI.get(), ctx -> new IchigoHitofuriRenderer(ctx));
        EntityRenderers.register(ModEntityTypes.TSURUMARU_KUNINAGA.get(), ctx -> new TsurumaruKuninagaRenderer(ctx));
        EntityRenderers.register(ModEntityTypes.SHOKUDAIKIRI_MITSUTADA.get(), ctx -> new ShokudaikiriMitsutadaRenderer(ctx));
        EntityRenderers.register(ModEntityTypes.ISHIKIRIMARU.get(), ctx -> new IshikirimaruRenderer(ctx));
        EntityRenderers.register(ModEntityTypes.HESHIKIRI_HASEBE.get(), ctx -> new HeshikiriHasebeRenderer(ctx));
        EntityRenderers.register(ModEntityTypes.YAMATONOKAMI_YASUSADA.get(), ctx -> new YamatonokamiYasusadaRenderer(ctx));
        EntityRenderers.register(ModEntityTypes.YAMANBAGIRI_CHOGI.get(), ctx -> new YamanbagiriChogiRenderer(ctx));
        EntityRenderers.register(ModEntityTypes.OOKURIKARA.get(), ctx -> new OokurikaraRenderer(ctx));
        EntityRenderers.register(ModEntityTypes.TONBOKIRI.get(), ctx -> new TonbokiriRenderer(ctx));
        EntityRenderers.register(ModEntityTypes.TOMOEGATA_NAGINATA.get(), ctx -> new TomoegataNaginataRenderer(ctx));
        EntityRenderers.register(ModEntityTypes.NIKKARI_AOE.get(), ctx -> new NikkariAoeRenderer(ctx));
        EntityRenderers.register(ModEntityTypes.HORIKAWA_KUNIHIRO.get(), ctx -> new HorikawaKunihiroRenderer(ctx));
        EntityRenderers.register(ModEntityTypes.IMANOTSURUGI.get(), ctx -> new ImanotsurugiRenderer(ctx));
        EntityRenderers.register(ModEntityTypes.GOTOU_TOUSHIROU.get(), ctx -> new GotouToushirouRenderer(ctx));

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
        MenuScreens.register(EntityUIMenuType.ENTITY_UI.get(), ModularUIContainerScreen::new);

        EntityRenderers.register(ModEntityTypes.WHITE_MIKAZUKI_MUNECHIKA.get(), WhiteMikazukiRenderer::new);
    }
}