package com.Equatorial.toukenranbu.screen;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, ToukenRanbuMod.MOD_ID);

    public static final RegistryObject<MenuType<ToukenDanshiMenu>> TOUKEN_DANSHI_MENU =
            MENUS.register("touken_danshi", () -> IForgeMenuType.create(ToukenDanshiMenu::fromNetwork));
}