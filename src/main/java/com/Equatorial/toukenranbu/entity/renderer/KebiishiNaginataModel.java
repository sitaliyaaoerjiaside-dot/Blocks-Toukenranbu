package com.Equatorial.toukenranbu.entity.renderer;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.custom.kebiishi.KebiishiNaginataEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class KebiishiNaginataModel extends GeoModel<KebiishiNaginataEntity> {
    @Override
    public ResourceLocation getModelResource(KebiishiNaginataEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "geo/naginata.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(KebiishiNaginataEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "textures/entity/kebiishi_naginata_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(KebiishiNaginataEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "animations/naginata.animation.json");
    }
}
