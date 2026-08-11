package com.Equatorial.toukenranbu.entity.renderer;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.custom.OotachiEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class HistoryRetrogradeArmyOotachiModel extends GeoModel<OotachiEntity> {
    @Override
    public ResourceLocation getModelResource(OotachiEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "geo/ootachi.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(OotachiEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "textures/entity/ootachi_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(OotachiEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "animations/ootachi.animation.json");
    }
}
