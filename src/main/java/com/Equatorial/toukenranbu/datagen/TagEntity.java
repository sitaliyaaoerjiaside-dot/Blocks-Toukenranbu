package com.Equatorial.toukenranbu.datagen;

import com.Equatorial.toukenranbu.entity.ModEntityTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeEntityTypeTagsProvider;

import java.util.concurrent.CompletableFuture;

public class TagEntity extends ForgeEntityTypeTagsProvider {

    public static final TagKey<EntityType<?>> IMMUNE_TO_GORGON_STONE = TagKey.create(
            Registries.ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath("iceandfire", "immune_to_gorgon_stone")
    );
    public static final TagKey<EntityType<?>> BLINDED = TagKey.create(
            Registries.ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath("iceandfire", "blinded")
    );

    public TagEntity(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, existingFileHelper);
    }

    @Override
    public void addTags(HolderLookup.Provider lookupProvider) {
        tag(IMMUNE_TO_GORGON_STONE)
                .add(ModEntityTypes.MIKAZUKI_MUNECHIKA.get())
                .add(ModEntityTypes.YAMANBAGIRI_KUNIHIRO.get())
                .add(ModEntityTypes.KASHUU_KIYOMITSU.get())
                .add(ModEntityTypes.HACHISUKA_KOTETSU.get())
                .add(ModEntityTypes.KASEN_KANESADA.get())
                .add(ModEntityTypes.MUTSUNOKAMI_YOSHIYUKI.get())
                .add(ModEntityTypes.ICHIGO_HITOFURI.get())
                .add(ModEntityTypes.TSURUMARU_KUNINAGA.get())
                .add(ModEntityTypes.SHOKUDAIKIRI_MITSUTADA.get())
                .add(ModEntityTypes.ISHIKIRIMARU.get())
                .add(ModEntityTypes.HESHIKIRI_HASEBE.get())
                .add(ModEntityTypes.YAMATONOKAMI_YASUSADA.get())
                .add(ModEntityTypes.YAMANBAGIRI_CHOGI.get())
                .add(ModEntityTypes.OOKURIKARA.get())
                .add(ModEntityTypes.TONBOKIRI.get())
                .add(ModEntityTypes.TOMOEGATA_NAGINATA.get())
                .add(ModEntityTypes.NIKKARI_AOE.get())
                .add(ModEntityTypes.HORIKAWA_KUNIHIRO.get())
                .add(ModEntityTypes.IMANOTSURUGI.get())
                .add(ModEntityTypes.GOTOU_TOUSHIROU.get())
                .add(ModEntityTypes.KONNOSUKE.get())
                .add(ModEntityTypes.WHITE_MIKAZUKI_MUNECHIKA.get());

        tag(BLINDED)
                .add(ModEntityTypes.MIKAZUKI_MUNECHIKA.get())
                .add(ModEntityTypes.YAMANBAGIRI_KUNIHIRO.get())
                .add(ModEntityTypes.KASHUU_KIYOMITSU.get())
                .add(ModEntityTypes.HACHISUKA_KOTETSU.get())
                .add(ModEntityTypes.KASEN_KANESADA.get())
                .add(ModEntityTypes.MUTSUNOKAMI_YOSHIYUKI.get())
                .add(ModEntityTypes.ICHIGO_HITOFURI.get())
                .add(ModEntityTypes.TSURUMARU_KUNINAGA.get())
                .add(ModEntityTypes.SHOKUDAIKIRI_MITSUTADA.get())
                .add(ModEntityTypes.ISHIKIRIMARU.get())
                .add(ModEntityTypes.HESHIKIRI_HASEBE.get())
                .add(ModEntityTypes.YAMATONOKAMI_YASUSADA.get())
                .add(ModEntityTypes.YAMANBAGIRI_CHOGI.get())
                .add(ModEntityTypes.OOKURIKARA.get())
                .add(ModEntityTypes.TONBOKIRI.get())
                .add(ModEntityTypes.TOMOEGATA_NAGINATA.get())
                .add(ModEntityTypes.NIKKARI_AOE.get())
                .add(ModEntityTypes.HORIKAWA_KUNIHIRO.get())
                .add(ModEntityTypes.IMANOTSURUGI.get())
                .add(ModEntityTypes.GOTOU_TOUSHIROU.get())
                .add(ModEntityTypes.KONNOSUKE.get())
                .add(ModEntityTypes.WHITE_MIKAZUKI_MUNECHIKA.get());
    }
}