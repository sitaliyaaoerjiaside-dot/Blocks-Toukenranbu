package com.Equatorial.toukenranbu.entity.renderer.tantou;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.touken.tantou.GotouToushirouEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class GotouToushirouModel extends GeoModel<GotouToushirouEntity> {
    @Override
    public ResourceLocation getModelResource(GotouToushirouEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "geo/gotou_toushirou.geo.json");
    }
    @Override
    public ResourceLocation getTextureResource(GotouToushirouEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "textures/entity/gotou_toushirou.png");
    }
    @Override
    public ResourceLocation getAnimationResource(GotouToushirouEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "animations/gotou_toushirou.animation.json");
    }
}
