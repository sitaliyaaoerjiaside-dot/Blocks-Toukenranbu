package com.Equatorial.toukenranbu.entity.renderer.tantou;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.touken.tantou.ImanotsurugiEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ImanotsurugiModel extends GeoModel<ImanotsurugiEntity> {
    @Override
    public ResourceLocation getModelResource(ImanotsurugiEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "geo/imanotsurugi.geo.json");
    }
    @Override
    public ResourceLocation getTextureResource(ImanotsurugiEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "textures/entity/imanotsurugi.png");
    }
    @Override
    public ResourceLocation getAnimationResource(ImanotsurugiEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "animations/imanotsurugi.animation.json");
    }
}