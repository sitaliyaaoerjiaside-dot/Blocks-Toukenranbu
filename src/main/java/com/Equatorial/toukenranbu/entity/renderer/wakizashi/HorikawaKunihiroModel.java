package com.Equatorial.toukenranbu.entity.renderer.wakizashi;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.touken.wakizashi.HorikawaKunihiroEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class HorikawaKunihiroModel extends GeoModel<HorikawaKunihiroEntity> {
    @Override
    public ResourceLocation getModelResource(HorikawaKunihiroEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "geo/horikawa_kunihiro.geo.json");
    }
    @Override
    public ResourceLocation getTextureResource(HorikawaKunihiroEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "textures/entity/horikawa_kunihiro.png");
    }
    @Override
    public ResourceLocation getAnimationResource(HorikawaKunihiroEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "animations/horikawa_kunihiro.animation.json");
    }
}
