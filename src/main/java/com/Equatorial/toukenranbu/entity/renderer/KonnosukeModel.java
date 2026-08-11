package com.Equatorial.toukenranbu.entity.renderer;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.custom.KonnosukeEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class KonnosukeModel extends GeoModel<KonnosukeEntity> {

    @Override
    public ResourceLocation getModelResource(KonnosukeEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "geo/konnosuke.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(KonnosukeEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "textures/entity/konnosuke.png");
    }

    @Override
    public ResourceLocation getAnimationResource(KonnosukeEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "animations/konnosuke.animation.json");
    }
}