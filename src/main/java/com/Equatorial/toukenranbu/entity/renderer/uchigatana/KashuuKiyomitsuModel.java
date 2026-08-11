package com.Equatorial.toukenranbu.entity.renderer.uchigatana;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.touken.uchigatana.KashuuKiyomitsuEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class KashuuKiyomitsuModel extends GeoModel<KashuuKiyomitsuEntity> {

    @Override
    public ResourceLocation getModelResource(KashuuKiyomitsuEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "geo/kashuu_kiyomitsu.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(KashuuKiyomitsuEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "textures/entity/kashuu_kiyomitsu.png");
    }

    @Override
    public ResourceLocation getAnimationResource(KashuuKiyomitsuEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "animations/kashuu_kiyomitsu.animation.json");
    }
}