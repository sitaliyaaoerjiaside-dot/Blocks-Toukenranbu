package com.Equatorial.toukenranbu.entity.renderer;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.custom.TachiEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class HistoryRetrogradeArmyTachiModel extends GeoModel<TachiEntity> {
    @Override
    public ResourceLocation getModelResource(TachiEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "geo/tachi.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TachiEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "textures/entity/tachi_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(TachiEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "animations/tachi.animation.json");
    }
}
