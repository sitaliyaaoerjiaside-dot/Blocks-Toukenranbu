package com.Equatorial.toukenranbu.entity.renderer;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.custom.TantouEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class HistoryRetrogradeArmyTantouModel extends GeoModel<TantouEntity> {
    @Override
    public ResourceLocation getModelResource(TantouEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "geo/tantou.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TantouEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "textures/entity/tantou_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(TantouEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "animations/tantou.animation.json");
    }
}
