package com.Equatorial.toukenranbu.entity.renderer.tachi;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.touken.tachi.ShokudaikiriMitsutadaEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ShokudaikiriMitsutadaModel extends GeoModel<ShokudaikiriMitsutadaEntity> {
    @Override
    public ResourceLocation getModelResource(ShokudaikiriMitsutadaEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "geo/shokudaikiri_mitsutada.geo.json");
    }
    @Override
    public ResourceLocation getTextureResource(ShokudaikiriMitsutadaEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "textures/entity/shokudaikiri_mitsutada.png");
    }
    @Override
    public ResourceLocation getAnimationResource(ShokudaikiriMitsutadaEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "animations/shokudaikiri_mitsutada.animation.json");
    }
}

