package com.Equatorial.toukenranbu.entity.renderer.uchigatana;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.touken.uchigatana.HeshikiriHasebeEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class HeshikiriHasebeModel extends GeoModel<HeshikiriHasebeEntity> {
    @Override
    public ResourceLocation getModelResource(HeshikiriHasebeEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "geo/heshikiri_hasebe.geo.json");
    }
    @Override
    public ResourceLocation getTextureResource(HeshikiriHasebeEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "textures/entity/heshikiri_hasebe.png");
    }
    @Override
    public ResourceLocation getAnimationResource(HeshikiriHasebeEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "animations/heshikiri_hasebe.animation.json");
    }
}