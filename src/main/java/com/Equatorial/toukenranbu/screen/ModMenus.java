package com.Equatorial.toukenranbu.screen;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenus {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, ToukenRanbuMod.MOD_ID);
    public static final RegistryObject<MenuType<ToukenContainerMenu>> TOUKEN_MENU = MENUS.register("touken_menu", () -> IForgeMenuType.create(ToukenContainerMenu::new));
}