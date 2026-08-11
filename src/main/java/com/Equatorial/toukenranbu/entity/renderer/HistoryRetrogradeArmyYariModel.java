package com.Equatorial.toukenranbu.entity.renderer;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.custom.YariEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class HistoryRetrogradeArmyYariModel extends GeoModel<YariEntity> {
    @Override
    public ResourceLocation getModelResource(YariEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "geo/yari.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(YariEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "textures/entity/yari_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(YariEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "animations/yari.animation.json");
    }
}
