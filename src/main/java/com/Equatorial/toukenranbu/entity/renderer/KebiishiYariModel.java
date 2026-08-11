package com.Equatorial.toukenranbu.entity.renderer;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.custom.kebiishi.KebiishiYariEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class KebiishiYariModel extends GeoModel<KebiishiYariEntity> {
    @Override
    public ResourceLocation getModelResource(KebiishiYariEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "geo/yari.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(KebiishiYariEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "textures/entity/kebiishi_yari_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(KebiishiYariEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "animations/yari.animation.json");
    }
}
