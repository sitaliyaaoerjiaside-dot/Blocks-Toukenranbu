package com.Equatorial.toukenranbu.advancement;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class UseAmuletTrigger extends SimpleCriterionTrigger<UseAmuletTrigger.Instance> {
    private final ResourceLocation id;

    public UseAmuletTrigger(String modid, String name) {
        this.id = ResourceLocation.fromNamespaceAndPath(modid, name);
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public Instance createInstance(JsonObject json, ContextAwarePredicate player, DeserializationContext context) {
        return new Instance(id, player);
    }

    // 这个方法你在御守生效代码里调用
    public void trigger(ServerPlayer player) {
        this.trigger(player, instance -> true);
    }

    public static class Instance extends AbstractCriterionTriggerInstance {
        public Instance(ResourceLocation id, ContextAwarePredicate player) {
            super(id, player);
        }
    }
}