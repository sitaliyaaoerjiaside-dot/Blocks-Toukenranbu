package com.Equatorial.toukenranbu.entity.renderer.wakizashi;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.touken.wakizashi.NikkariAoeEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class NikkariAoeModel extends GeoModel<NikkariAoeEntity> {
    @Override
    public ResourceLocation getModelResource(NikkariAoeEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "geo/nikkari_aoe.geo.json");
    }
    @Override
    public ResourceLocation getTextureResource(NikkariAoeEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "textures/entity/nikkari_aoe.png");
    }
    @Override
    public ResourceLocation getAnimationResource(NikkariAoeEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "animations/nikkari_aoe.animation.json");
    }
}