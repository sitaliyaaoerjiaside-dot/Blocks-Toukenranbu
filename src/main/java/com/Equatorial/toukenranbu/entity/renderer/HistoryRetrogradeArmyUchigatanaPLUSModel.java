package com.Equatorial.toukenranbu.entity.renderer;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.custom.UchigatanaPLUSEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class HistoryRetrogradeArmyUchigatanaPLUSModel extends GeoModel<UchigatanaPLUSEntity> {
    @Override
    public ResourceLocation getModelResource(UchigatanaPLUSEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "geo/uchigatana.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(UchigatanaPLUSEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "textures/entity/uchigatanaplus_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(UchigatanaPLUSEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "animations/uchigatana.animation.json");
    }
}
