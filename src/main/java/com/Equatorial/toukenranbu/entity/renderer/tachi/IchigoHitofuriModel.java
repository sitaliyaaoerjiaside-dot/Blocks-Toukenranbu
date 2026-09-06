package com.Equatorial.toukenranbu.entity.renderer.tachi;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.touken.tachi.IchigoHitofuriEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class IchigoHitofuriModel extends GeoModel<IchigoHitofuriEntity> {
    @Override
    public ResourceLocation getModelResource(IchigoHitofuriEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "geo/ichigo_hitofuri.geo.json");
    }
    @Override
    public ResourceLocation getTextureResource(IchigoHitofuriEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "textures/entity/ichigo_hitofuri.png");
    }
    @Override
    public ResourceLocation getAnimationResource(IchigoHitofuriEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "animations/ichigo_hitofuri.animation.json");
    }
}
