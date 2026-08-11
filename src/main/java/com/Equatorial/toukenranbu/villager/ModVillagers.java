package com.Equatorial.toukenranbu.villager;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.block.ModBlocks;
import com.google.common.collect.ImmutableSet;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModVillagers {
    public static final DeferredRegister<PoiType> POI_TYPES =
            DeferredRegister.create(ForgeRegistries.POI_TYPES, ToukenRanbuMod.MOD_ID);
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS =
            DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, ToukenRanbuMod.MOD_ID);

    public static final RegistryObject<PoiType> KNIFE_FORFING_FURNACE = POI_TYPES.register("knife_forging_furnace",
            () -> new PoiType(ImmutableSet.copyOf(ModBlocks.COOLANT_BLOCK.get().getStateDefinition().getPossibleStates()),1,1));
    public static final RegistryObject<VillagerProfession> BLADESMITH = VILLAGER_PROFESSIONS.register("bladesmith",
            () -> new VillagerProfession("bladesmith",
                    p -> p.get() == KNIFE_FORFING_FURNACE.get(),p -> p.get() == KNIFE_FORFING_FURNACE.get(),
                    ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_ARMORER));

    public static void register(IEventBus eventBus) {
        POI_TYPES.register(eventBus);
        VILLAGER_PROFESSIONS.register(eventBus);
    }
}
