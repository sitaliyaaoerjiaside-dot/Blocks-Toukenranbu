package com.Equatorial.toukenranbu.entity.renderer;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.custom.TachiPLUSEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class HistoryRetrogradeArmyTachiPLUSModel extends GeoModel<TachiPLUSEntity> {
    @Override
    public ResourceLocation getModelResource(TachiPLUSEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "geo/tachi.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TachiPLUSEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "textures/entity/tachiplus_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(TachiPLUSEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "animations/tachi.animation.json");
    }
}
