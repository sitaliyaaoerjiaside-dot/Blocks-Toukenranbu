package com.Equatorial.toukenranbu.entity.renderer.yari;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.touken.yari.TonbokiriEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TonbokiriModel extends GeoModel<TonbokiriEntity> {
    @Override
    public ResourceLocation getModelResource(TonbokiriEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "geo/tonbokiri.geo.json");
    }
    @Override
    public ResourceLocation getTextureResource(TonbokiriEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "textures/entity/tonbokiri.png");
    }
    @Override
    public ResourceLocation getAnimationResource(TonbokiriEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "animations/tonbokiri.animation.json");
    }
}

