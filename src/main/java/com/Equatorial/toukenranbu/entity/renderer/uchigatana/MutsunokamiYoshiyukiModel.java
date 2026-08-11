package com.Equatorial.toukenranbu.entity.renderer.uchigatana;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.touken.uchigatana.MutsunokamiYoshiyukiEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class MutsunokamiYoshiyukiModel extends GeoModel<MutsunokamiYoshiyukiEntity> {

    @Override
    public ResourceLocation getModelResource(MutsunokamiYoshiyukiEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "geo/mutsunokami_yoshiyuki.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MutsunokamiYoshiyukiEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "textures/entity/mutsunokami_yoshiyuki.png");
    }

    @Override
    public ResourceLocation getAnimationResource(MutsunokamiYoshiyukiEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "animations/mutsunokami_yoshiyuki.animation.json");
    }
}