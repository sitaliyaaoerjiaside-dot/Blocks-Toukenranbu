package com.Equatorial.toukenranbu.entity.renderer.tachi;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.touken.tachi.MikazukiMunechikaEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class MikazukiMunechikaModel extends GeoModel<MikazukiMunechikaEntity> {

    @Override
    public ResourceLocation getModelResource(MikazukiMunechikaEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "geo/mikazuki_munechika.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MikazukiMunechikaEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "textures/entity/mikazuki_munechika.png");
    }

    @Override
    public ResourceLocation getAnimationResource(MikazukiMunechikaEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "animations/mikazuki_munechika.animation.json");
    }
}