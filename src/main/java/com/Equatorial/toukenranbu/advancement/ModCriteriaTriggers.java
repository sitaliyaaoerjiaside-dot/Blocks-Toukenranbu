package com.Equatorial.toukenranbu.advancement;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.google.gson.JsonObject;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class ModCriteriaTriggers {
    public static final KebiishiKillTrigger KEBIISHI_KILL = new KebiishiKillTrigger();
    public static final KebiishiSpawnTrigger KEBIISHI_SPAWN = new KebiishiSpawnTrigger();

    public static void register() {
        CriteriaTriggers.register(KEBIISHI_KILL);
        CriteriaTriggers.register(KEBIISHI_SPAWN);
    }

    public static class KebiishiKillTrigger extends SimpleCriterionTrigger<KebiishiKillTrigger.TriggerInstance> {
        public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "kebiishi_kill");

        @Override
        public ResourceLocation getId() {
            return ID;
        }

        @Override
        public TriggerInstance createInstance(JsonObject json, ContextAwarePredicate player, DeserializationContext context) {
            return new TriggerInstance(ID, player);
        }

        public void trigger(ServerPlayer player) {
            this.trigger(player, instance -> true);
        }

        public static class TriggerInstance extends AbstractCriterionTriggerInstance {
            public TriggerInstance(ResourceLocation criterion, ContextAwarePredicate player) {
                super(criterion, player);
            }
        }
    }

    public static class KebiishiSpawnTrigger extends SimpleCriterionTrigger<KebiishiSpawnTrigger.TriggerInstance> {
        public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "kebiishi_spawn");

        @Override
        public ResourceLocation getId() {
            return ID;
        }

        @Override
        public TriggerInstance createInstance(JsonObject json, ContextAwarePredicate player, DeserializationContext context) {
            return new TriggerInstance(ID, player);
        }

        public void trigger(ServerPlayer player) {
            this.trigger(player, instance -> true);
        }

        public static class TriggerInstance extends AbstractCriterionTriggerInstance {
            public TriggerInstance(ResourceLocation criterion, ContextAwarePredicate player) {
                super(criterion, player);
            }
        }
    }
}