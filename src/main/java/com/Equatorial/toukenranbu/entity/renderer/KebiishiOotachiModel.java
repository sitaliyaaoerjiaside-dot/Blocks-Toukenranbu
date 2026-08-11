package com.Equatorial.toukenranbu.entity.renderer;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.custom.kebiishi.KebiishiOotachiEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class KebiishiOotachiModel extends GeoModel<KebiishiOotachiEntity> {
    @Override
    public ResourceLocation getModelResource(KebiishiOotachiEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "geo/ootachi.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(KebiishiOotachiEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "textures/entity/kebiishi_ootachi_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(KebiishiOotachiEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "animations/ootachi.animation.json");
    }
}
