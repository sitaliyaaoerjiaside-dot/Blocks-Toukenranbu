package com.Equatorial.toukenranbu;

import com.Equatorial.toukenranbu.screen.ModMenus;
import com.Equatorial.toukenranbu.screen.ToukenContainerMenu;
import com.Equatorial.toukenranbu.screen.ToukenScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = ToukenRanbuMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            net.minecraft.client.gui.screens.MenuScreens.<ToukenContainerMenu, ToukenScreen>register(
                    ModMenus.TOUKEN_MENU.get(),
                    ToukenScreen::new
            );
        });
    }
}