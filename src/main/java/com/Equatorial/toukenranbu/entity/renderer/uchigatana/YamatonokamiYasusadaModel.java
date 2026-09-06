package com.Equatorial.toukenranbu.entity.renderer.uchigatana;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.touken.uchigatana.YamatonokamiYasusadaEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class YamatonokamiYasusadaModel extends GeoModel<YamatonokamiYasusadaEntity> {
    @Override
    public ResourceLocation getModelResource(YamatonokamiYasusadaEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "geo/yamatonokami_yasusada.geo.json");
    }
    @Override
    public ResourceLocation getTextureResource(YamatonokamiYasusadaEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "textures/entity/yamatonokami_yasusada.png");
    }
    @Override
    public ResourceLocation getAnimationResource(YamatonokamiYasusadaEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "animations/yamatonokami_yasusada.animation.json");
    }
}