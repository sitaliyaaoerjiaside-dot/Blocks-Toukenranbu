package com.Equatorial.toukenranbu.entity.renderer;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.custom.NaginataPLUSEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class HistoryRetrogradeArmyNaginataPLUSModel extends GeoModel<NaginataPLUSEntity> {
    @Override
    public ResourceLocation getModelResource(NaginataPLUSEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "geo/naginata.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(NaginataPLUSEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "textures/entity/naginataplus_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(NaginataPLUSEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "animations/naginata.animation.json");
    }
}
