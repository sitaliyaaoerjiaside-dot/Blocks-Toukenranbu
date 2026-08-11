package com.Equatorial.toukenranbu.entity.renderer.uchigatana;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.touken.uchigatana.KasenKanesadaEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class KasenKanesadaModel extends GeoModel<KasenKanesadaEntity> {

    @Override
    public ResourceLocation getModelResource(KasenKanesadaEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "geo/kasen_kanesada.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(KasenKanesadaEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "textures/entity/kasen_kanesada.png");
    }

    @Override
    public ResourceLocation getAnimationResource(KasenKanesadaEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "animations/kasen_kanesada.animation.json");
    }
}