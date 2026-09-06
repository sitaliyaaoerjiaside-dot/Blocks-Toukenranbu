package com.Equatorial.toukenranbu.command;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.capability.ModCapabilities;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ToukenRanbuMod.MOD_ID)
public class ToukenCommands {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        register(event.getDispatcher());
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("toukenranbu")
                .then(Commands.literal("friendlyfire")
                        .executes(ctx -> toggleFriendlyFire(ctx.getSource()))));

        dispatcher.register(Commands.literal("tr")
                .then(Commands.literal("ff")
                        .executes(ctx -> toggleFriendlyFire(ctx.getSource()))));
    }

    private static int toggleFriendlyFire(CommandSourceStack source) throws CommandSyntaxException {
        ServerPlayer player = source.getPlayerOrException();

        return player.getCapability(ModCapabilities.SPIRIT_POWER).map(cap -> {
            boolean newVal = !cap.isFriendlyFireEnabled();
            cap.setFriendlyFireEnabled(newVal);
            source.sendSuccess(() -> Component.translatable(
                    newVal
                            ? "command.toukenranbu_mod.friendlyfire.on"
                            : "command.toukenranbu_mod.friendlyfire.off"
            ), false);
            return 1;
        }).orElse(0);
    }
}