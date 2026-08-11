package com.Equatorial.toukenranbu.entity.renderer;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.custom.WakizashiPLUSEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class HistoryRetrogradeArmyWakizashiPLUSModel extends GeoModel<WakizashiPLUSEntity> {
    @Override
    public ResourceLocation getModelResource(WakizashiPLUSEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "geo/wakizashi.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(WakizashiPLUSEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "textures/entity/wakizashiplus_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(WakizashiPLUSEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "animations/wakizashi.animation.json");
    }
}
