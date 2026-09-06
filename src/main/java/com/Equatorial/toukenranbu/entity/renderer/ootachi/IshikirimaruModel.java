package com.Equatorial.toukenranbu.entity.renderer.ootachi;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.touken.ootachi.IshikirimaruEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class IshikirimaruModel extends GeoModel<IshikirimaruEntity> {
    @Override
    public ResourceLocation getModelResource(IshikirimaruEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "geo/ishikirimaru.geo.json");
    }
    @Override
    public ResourceLocation getTextureResource(IshikirimaruEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "textures/entity/ishikirimaru.png");
    }
    @Override
    public ResourceLocation getAnimationResource(IshikirimaruEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "animations/ishikirimaru.animation.json");
    }
}
