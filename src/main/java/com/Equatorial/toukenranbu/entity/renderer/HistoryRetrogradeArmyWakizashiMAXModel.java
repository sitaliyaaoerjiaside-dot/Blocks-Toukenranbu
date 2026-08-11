package com.Equatorial.toukenranbu.entity.renderer;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.custom.WakizashiMAXEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class HistoryRetrogradeArmyWakizashiMAXModel extends GeoModel<WakizashiMAXEntity> {
    @Override
    public ResourceLocation getModelResource(WakizashiMAXEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "geo/wakizashi.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(WakizashiMAXEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "textures/entity/wakizashimax_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(WakizashiMAXEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "animations/wakizashi.animation.json");
    }
}
