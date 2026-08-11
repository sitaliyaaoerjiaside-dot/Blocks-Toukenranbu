package com.Equatorial.toukenranbu.entity.renderer;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.custom.NaginataEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class HistoryRetrogradeArmyNaginataModel extends GeoModel<NaginataEntity> {
    @Override
    public ResourceLocation getModelResource(NaginataEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "geo/naginata.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(NaginataEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "textures/entity/naginata_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(NaginataEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "animations/naginata.animation.json");
    }
}
