package com.Equatorial.toukenranbu.entity.renderer;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.custom.kebiishi.KebiishiTachiEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class KebiishiTachiModel extends GeoModel<KebiishiTachiEntity> {
    @Override
    public ResourceLocation getModelResource(KebiishiTachiEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "geo/tachi.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(KebiishiTachiEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "textures/entity/kebiishi_tachi_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(KebiishiTachiEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "animations/tachi.animation.json");
    }
}
