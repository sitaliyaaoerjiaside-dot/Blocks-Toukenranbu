package com.Equatorial.toukenranbu.entity.renderer;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.custom.TantouEntity;
import com.Equatorial.toukenranbu.entity.custom.WakizashiEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class HistoryRetrogradeArmyWakizashiModel extends GeoModel<WakizashiEntity> {
    @Override
    public ResourceLocation getModelResource(WakizashiEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "geo/wakizashi.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(WakizashiEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "textures/entity/wakizashi_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(WakizashiEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "animations/wakizashi.animation.json");
    }
}
