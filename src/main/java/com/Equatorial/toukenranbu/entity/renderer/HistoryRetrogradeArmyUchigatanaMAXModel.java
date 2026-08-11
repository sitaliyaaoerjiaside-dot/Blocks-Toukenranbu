package com.Equatorial.toukenranbu.entity.renderer;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.custom.UchigatanaMAXEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class HistoryRetrogradeArmyUchigatanaMAXModel extends GeoModel<UchigatanaMAXEntity> {
    @Override
    public ResourceLocation getModelResource(UchigatanaMAXEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "geo/uchigatana.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(UchigatanaMAXEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "textures/entity/uchigatanamax_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(UchigatanaMAXEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "animations/uchigatana.animation.json");
    }
}
