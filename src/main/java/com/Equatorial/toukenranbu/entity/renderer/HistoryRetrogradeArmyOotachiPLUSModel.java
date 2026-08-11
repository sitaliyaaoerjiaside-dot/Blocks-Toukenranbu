package com.Equatorial.toukenranbu.entity.renderer;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.custom.OotachiPLUSEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class HistoryRetrogradeArmyOotachiPLUSModel extends GeoModel<OotachiPLUSEntity> {
    @Override
    public ResourceLocation getModelResource(OotachiPLUSEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "geo/ootachi.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(OotachiPLUSEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "textures/entity/ootachiplus_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(OotachiPLUSEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "animations/ootachi.animation.json");
    }
}
