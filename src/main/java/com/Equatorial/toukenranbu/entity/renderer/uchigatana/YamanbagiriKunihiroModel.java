package com.Equatorial.toukenranbu.entity.renderer.uchigatana;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.touken.uchigatana.YamanbagiriKunihiroEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class YamanbagiriKunihiroModel extends GeoModel<YamanbagiriKunihiroEntity> {

    @Override
    public ResourceLocation getModelResource(YamanbagiriKunihiroEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "geo/yamanbagiri_kunihiro.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(YamanbagiriKunihiroEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "textures/entity/yamanbagiri_kunihiro.png");
    }

    @Override
    public ResourceLocation getAnimationResource(YamanbagiriKunihiroEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "animations/yamanbagiri_kunihiro.animation.json");
    }
}