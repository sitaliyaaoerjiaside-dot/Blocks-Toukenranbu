package com.Equatorial.toukenranbu.entity.renderer;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.custom.TantouPLUSEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class HistoryRetrogradeArmyTantouPLUSModel extends GeoModel<TantouPLUSEntity> {
    @Override
    public ResourceLocation getModelResource(TantouPLUSEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "geo/tantou.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TantouPLUSEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "textures/entity/tantouplus_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(TantouPLUSEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "animations/tantou.animation.json");
    }
}
