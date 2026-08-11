package com.Equatorial.toukenranbu.entity.renderer;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.custom.UchigatanaEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class HistoryRetrogradeArmyUchigatanaModel extends GeoModel<UchigatanaEntity> {
    @Override
    public ResourceLocation getModelResource(UchigatanaEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "geo/uchigatana.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(UchigatanaEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "textures/entity/uchigatana_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(UchigatanaEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "animations/uchigatana.animation.json");
    }
}
