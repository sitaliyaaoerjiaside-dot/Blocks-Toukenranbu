package com.Equatorial.toukenranbu.entity.renderer;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.custom.TachiMAXEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class HistoryRetrogradeArmyTachiMAXModel extends GeoModel<TachiMAXEntity> {
    @Override
    public ResourceLocation getModelResource(TachiMAXEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "geo/tachi.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TachiMAXEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "textures/entity/tachimax_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(TachiMAXEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "animations/tachi.animation.json");
    }
}
