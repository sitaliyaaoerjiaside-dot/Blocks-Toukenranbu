package com.Equatorial.toukenranbu.entity.renderer.boss;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.boss.WhiteMikazukiEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class WhiteMikazukiModel extends GeoModel<WhiteMikazukiEntity> {

    @Override
    public ResourceLocation getModelResource(WhiteMikazukiEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "geo/white_mikazuki_munechika.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(WhiteMikazukiEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "textures/entity/white_mikazuki_munechika.png");
    }

    @Override
    public ResourceLocation getAnimationResource(WhiteMikazukiEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "animations/white_mikazuki_munechika.animation.json");
    }
}