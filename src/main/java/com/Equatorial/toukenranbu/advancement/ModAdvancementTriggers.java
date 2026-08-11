package com.Equatorial.toukenranbu.advancement;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ModAdvancementTriggers {
    public static final UseAmuletTrigger USE_AMULET = new UseAmuletTrigger("toukenranbu_mod", "use_amulet");
    public static final UseAmuletTrigger USE_SUPREME_AMULET = new UseAmuletTrigger("toukenranbu_mod", "use_supreme_amulet");
    public static final UseAmuletTrigger USE_STARTER_SWORD = new UseAmuletTrigger("toukenranbu_mod", "use_starter_sword");
    public static final UseAmuletTrigger USE_MIKAZUKI_MUNECHIKA = new UseAmuletTrigger("toukenranbu_mod", "use_mikazuki_munechika");

    public static void register(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            CriteriaTriggers.register(USE_AMULET);
            CriteriaTriggers.register(USE_SUPREME_AMULET);
            CriteriaTriggers.register(USE_STARTER_SWORD);
            CriteriaTriggers.register(USE_MIKAZUKI_MUNECHIKA);
        });
    }
}