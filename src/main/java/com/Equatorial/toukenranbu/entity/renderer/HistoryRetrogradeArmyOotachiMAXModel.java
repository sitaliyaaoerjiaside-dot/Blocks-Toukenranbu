package com.Equatorial.toukenranbu.entity.renderer;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.custom.OotachiMAXEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class HistoryRetrogradeArmyOotachiMAXModel extends GeoModel<OotachiMAXEntity> {
    @Override
    public ResourceLocation getModelResource(OotachiMAXEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "geo/ootachi.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(OotachiMAXEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "textures/entity/ootachimax_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(OotachiMAXEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "animations/ootachi.animation.json");
    }
}
