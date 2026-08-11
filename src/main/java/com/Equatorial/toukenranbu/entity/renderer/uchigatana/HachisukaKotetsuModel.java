package com.Equatorial.toukenranbu.entity.renderer.uchigatana;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.touken.uchigatana.HachisukaKotetsuEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class HachisukaKotetsuModel extends GeoModel<HachisukaKotetsuEntity> {

    @Override
    public ResourceLocation getModelResource(HachisukaKotetsuEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "geo/hachisuka_kotetsu.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(HachisukaKotetsuEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "textures/entity/hachisuka_kotetsu.png");
    }

    @Override
    public ResourceLocation getAnimationResource(HachisukaKotetsuEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "animations/hachisuka_kotetsu.animation.json");
    }
}