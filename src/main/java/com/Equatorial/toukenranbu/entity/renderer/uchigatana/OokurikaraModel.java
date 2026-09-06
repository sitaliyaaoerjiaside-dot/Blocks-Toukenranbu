package com.Equatorial.toukenranbu.entity.renderer.uchigatana;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.touken.uchigatana.OokurikaraEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class OokurikaraModel extends GeoModel<OokurikaraEntity> {
    @Override
    public ResourceLocation getModelResource(OokurikaraEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "geo/ookurikara.geo.json");
    }
    @Override
    public ResourceLocation getTextureResource(OokurikaraEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "textures/entity/ookurikara.png");
    }
    @Override
    public ResourceLocation getAnimationResource(OokurikaraEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "animations/ookurikara.animation.json");
    }
}
