package com.Equatorial.toukenranbu.entity.renderer;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.custom.kebiishi.KebiishiLeaderEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class KebiishiLeaderModel extends GeoModel<KebiishiLeaderEntity> {
    @Override
    public ResourceLocation getModelResource(KebiishiLeaderEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "geo/kebiishi_leader.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(KebiishiLeaderEntity object) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "textures/entity/kebiishi_leader_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(KebiishiLeaderEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "animations/kebiishi_leader.animation.json");
    }
}
