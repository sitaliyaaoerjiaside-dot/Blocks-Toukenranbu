package com.Equatorial.toukenranbu.entity.renderer.tachi;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.touken.tachi.TsurumaruKuninagaEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TsurumaruKuninagaModel extends GeoModel<TsurumaruKuninagaEntity> {
    @Override
    public ResourceLocation getModelResource(TsurumaruKuninagaEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "geo/tsurumaru_kuninaga.geo.json");
    }
    @Override
    public ResourceLocation getTextureResource(TsurumaruKuninagaEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "textures/entity/tsurumaru_kuninaga.png");
    }
    @Override
    public ResourceLocation getAnimationResource(TsurumaruKuninagaEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "animations/tsurumaru_kuninaga.animation.json");
    }
}