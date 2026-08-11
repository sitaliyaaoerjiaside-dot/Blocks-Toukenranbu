package com.Equatorial.toukenranbu.event;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.ModEntityTypes;
import com.Equatorial.toukenranbu.entity.custom.KonnosukeEntity;
import com.Equatorial.toukenranbu.entity.custom.*;
import com.Equatorial.toukenranbu.entity.custom.kebiishi.*;
import com.Equatorial.toukenranbu.entity.touken.tachi.MikazukiMunechikaEntity;
import com.Equatorial.toukenranbu.entity.touken.uchigatana.*;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ToukenRanbuMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntityTypes.TANTOU.get(), TantouEntity.setAttributes());
        event.put(ModEntityTypes.WAKIZASHI.get(), WakizashiEntity.setAttributes());

        event.put(ModEntityTypes.MIKAZUKI_MUNECHIKA.get(), MikazukiMunechikaEntity.setAttributes());
        event.put(ModEntityTypes.YAMANBAGIRI_KUNIHIRO.get(), YamanbagiriKunihiroEntity.setAttributes());
        event.put(ModEntityTypes.KASHUU_KIYOMITSU.get(), KashuuKiyomitsuEntity.setAttributes());
        event.put(ModEntityTypes.HACHISUKA_KOTETSU.get(), HachisukaKotetsuEntity.setAttributes());
        event.put(ModEntityTypes.KASEN_KANESADA.get(), KasenKanesadaEntity.setAttributes());
        event.put(ModEntityTypes.MUTSUNOKAMI_YOSHIYUKI.get(), MutsunokamiYoshiyukiEntity.setAttributes());

        event.put(ModEntityTypes.UCHIGATANA.get(), UchigatanaEntity.setAttributes());
        event.put(ModEntityTypes.TACHI.get(), TachiEntity.setAttributes());
        event.put(ModEntityTypes.OOTACHI.get(), OotachiEntity.setAttributes());
        event.put(ModEntityTypes.NAGINATA.get(), NaginataEntity.setAttributes());
        event.put(ModEntityTypes.YARI.get(), YariEntity.setAttributes());

        event.put(ModEntityTypes.NAGINATA_PLUS.get(), NaginataPLUSEntity.setAttributes());
        event.put(ModEntityTypes.YARI_PLUS.get(), YariPLUSEntity.setAttributes());
        event.put(ModEntityTypes.UCHIGATANA_PLUS.get(), UchigatanaPLUSEntity.setAttributes());
        event.put(ModEntityTypes.TACHI_PLUS.get(), TachiPLUSEntity.setAttributes());
        event.put(ModEntityTypes.WAKIZASHI_PLUS.get(), WakizashiPLUSEntity.setAttributes());
        event.put(ModEntityTypes.OOTACHI_PLUS.get(), OotachiPLUSEntity.setAttributes());
        event.put(ModEntityTypes.TANTOU_PLUS.get(), TantouPLUSEntity.setAttributes());

        event.put(ModEntityTypes.TANTOU_MAX.get(), TantouMAXEntity.setAttributes());
        event.put(ModEntityTypes.UCHIGATANA_MAX.get(), UchigatanaMAXEntity.setAttributes());
        event.put(ModEntityTypes.TACHI_MAX.get(), TachiMAXEntity.setAttributes());
        event.put(ModEntityTypes.WAKIZASHI_MAX.get(), WakizashiMAXEntity.setAttributes());
        event.put(ModEntityTypes.OOTACHI_MAX.get(), OotachiMAXEntity.setAttributes());
        event.put(ModEntityTypes.NAGINATA_MAX.get(), NaginataMAXEntity.setAttributes());
        event.put(ModEntityTypes.YARI_MAX.get(), YariMAXEntity.setAttributes());

        event.put(ModEntityTypes.KEBIISHI_YARI.get(), KebiishiYariEntity.setAttributes());
        event.put(ModEntityTypes.KEBIISHI_NAGINATA.get(), KebiishiNaginataEntity.setAttributes());
        event.put(ModEntityTypes.KEBIISHI_TACHI.get(), KebiishiTachiEntity.setAttributes());
        event.put(ModEntityTypes.KEBIISHI_OOTACHI.get(), KebiishiOotachiEntity.setAttributes());
        event.put(ModEntityTypes.KEBIISHI_LEADER.get(), KebiishiLeaderEntity.setAttributes());

        event.put(ModEntityTypes.KONNOSUKE.get(), KonnosukeEntity.createAttributes().build());
    }
}