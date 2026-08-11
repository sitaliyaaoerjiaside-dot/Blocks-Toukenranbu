package com.Equatorial.toukenranbu.entity.renderer;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.custom.TantouMAXEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class HistoryRetrogradeArmyTantouMAXModel extends GeoModel<TantouMAXEntity> {
    @Override
    public ResourceLocation getModelResource(TantouMAXEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "geo/tantou.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TantouMAXEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "textures/entity/tantoumax_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(TantouMAXEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "animations/tantou.animation.json");
    }
}
