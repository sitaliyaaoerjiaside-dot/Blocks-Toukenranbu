package com.Equatorial.toukenranbu.entity.renderer;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.custom.YariMAXEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class HistoryRetrogradeArmyYariMAXModel extends GeoModel<YariMAXEntity> {
    @Override
    public ResourceLocation getModelResource(YariMAXEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "geo/yari.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(YariMAXEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "textures/entity/yarimax_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(YariMAXEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "animations/yari.animation.json");
    }
}
