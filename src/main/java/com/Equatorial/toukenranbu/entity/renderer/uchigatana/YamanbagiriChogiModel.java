package com.Equatorial.toukenranbu.entity.renderer.uchigatana;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.touken.uchigatana.YamanbagiriChogiEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class YamanbagiriChogiModel extends GeoModel<YamanbagiriChogiEntity> {
    @Override
    public ResourceLocation getModelResource(YamanbagiriChogiEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "geo/yamanbagiri_chogi.geo.json");
    }
    @Override
    public ResourceLocation getTextureResource(YamanbagiriChogiEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "textures/entity/yamanbagiri_chogi.png");
    }
    @Override
    public ResourceLocation getAnimationResource(YamanbagiriChogiEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "animations/yamanbagiri_chogi.animation.json");
    }
}