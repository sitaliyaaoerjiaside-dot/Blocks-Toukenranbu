package com.Equatorial.toukenranbu.entity.renderer.naginata;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.touken.naginata.TomoegataNaginataEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TomoegataNaginataModel extends GeoModel<TomoegataNaginataEntity> {
    @Override
    public ResourceLocation getModelResource(TomoegataNaginataEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "geo/tomoegata_naginata.geo.json");
    }
    @Override
    public ResourceLocation getTextureResource(TomoegataNaginataEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "textures/entity/tomoegata_naginata.png");
    }
    @Override
    public ResourceLocation getAnimationResource(TomoegataNaginataEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "animations/tomoegata_naginata.animation.json");
    }
}