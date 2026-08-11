package com.Equatorial.toukenranbu.entity.renderer;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.custom.YariPLUSEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class HistoryRetrogradeArmyYariPLUSModel extends GeoModel<YariPLUSEntity> {
    @Override
    public ResourceLocation getModelResource(YariPLUSEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "geo/yari.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(YariPLUSEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "textures/entity/yariplus_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(YariPLUSEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "animations/yari.animation.json");
    }
}
