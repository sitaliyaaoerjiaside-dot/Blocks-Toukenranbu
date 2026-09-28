package com.Equatorial.toukenranbu.command;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.capability.ModCapabilities;
import com.Equatorial.toukenranbu.entity.touken.ToukenDanshiEntity;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collection;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = ToukenRanbuMod.MOD_ID)
public class ToukenCommands {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        register(event.getDispatcher());
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("toukenranbu")
                .then(Commands.literal("friendlyfire")
                        .executes(ctx -> toggleFriendlyFire(ctx.getSource())))
                .then(Commands.literal("xp")
                        .executes(ctx -> toggleDanshiXp(ctx.getSource())))
                .then(Commands.literal("locate")
                        .executes(ToukenCommands::locateOwnDanshi))
                .then(Commands.literal("count")
                        .executes(ToukenCommands::countOwnDanshi)));

        dispatcher.register(Commands.literal("tr")
                .then(Commands.literal("ff")
                        .executes(ctx -> toggleFriendlyFire(ctx.getSource())))
                .then(Commands.literal("xp")
                        .executes(ctx -> toggleDanshiXp(ctx.getSource())))
                .then(Commands.literal("locate")
                        .executes(ToukenCommands::locateOwnDanshi))
                .then(Commands.literal("count")
                        .executes(ToukenCommands::countOwnDanshi)));
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

    private static int toggleDanshiXp(CommandSourceStack source) throws CommandSyntaxException {
        ServerPlayer player = source.getPlayerOrException();
        return player.getCapability(ModCapabilities.SPIRIT_POWER).map(cap -> {
            boolean newVal = !cap.isDanshiXpEnabled();
            cap.setDanshiXpEnabled(newVal);
            source.sendSuccess(() -> Component.translatable(
                    newVal
                            ? "command.toukenranbu_mod.danshi_xp.on"
                            : "command.toukenranbu_mod.danshi_xp.off"
            ), false);
            return 1;
        }).orElse(0);
    }

    /** 列出玩家名下所有刀男及其坐标 */
    private static int locateOwnDanshi(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        CommandSourceStack source = ctx.getSource();
        ServerPlayer player = source.getPlayerOrException();
        UUID ownerUUID = player.getUUID();

        Collection<ToukenDanshiEntity> all = ToukenDanshiEntity.getOwnedDanshi().get(ownerUUID);

        if (all == null || all.isEmpty()) {
            source.sendSuccess(() -> Component.translatable(
                    "command.toukenranbu_mod.touken.locate.empty"
            ).withStyle(ChatFormatting.YELLOW), false);
            return 0;
        }

        java.util.List<ToukenDanshiEntity> alive = all.stream()
                .filter(ToukenDanshiEntity::isAlive)
                .toList();

        if (alive.isEmpty()) {
            source.sendSuccess(() -> Component.translatable(
                    "command.toukenranbu_mod.touken.locate.empty"
            ).withStyle(ChatFormatting.YELLOW), false);
            return 0;
        }

        // 全部合并成一条多行消息
        net.minecraft.network.chat.MutableComponent result = Component.translatable(
                "command.toukenranbu_mod.touken.locate.header", alive.size()
        ).withStyle(ChatFormatting.GOLD);

        boolean anyCrossDim = false;
        int index = 1;
        for (ToukenDanshiEntity danshi : alive) {
            String name = danshi.getName().getString();
            String dim = danshi.level().dimension().location().toString();
            String dimShort = dim.replace("toukenranbu_mod:", "").replace("minecraft:", "");

            int x = (int) danshi.getX();
            int y = (int) danshi.getY();
            int z = (int) danshi.getZ();

            double distance = -1;
            if (danshi.level() == player.level()) {
                distance = Math.sqrt(player.distanceToSqr(danshi));
            } else {
                anyCrossDim = true;
            }

            Component line;
            if (distance >= 0) {
                int d = (int) distance;
                line = Component.translatable(
                        "command.toukenranbu_mod.touken.locate.entry_with_distance",
                        index, name, dimShort, x, y, z, d
                ).withStyle(d < 64 ? ChatFormatting.GREEN : ChatFormatting.WHITE);
            } else {
                line = Component.translatable(
                        "command.toukenranbu_mod.touken.locate.entry",
                        index, name, dimShort, x, y, z
                ).withStyle(ChatFormatting.WHITE);
            }
            result.append("\n").append(line);
            index++;
        }

        if (anyCrossDim) {
            result.append("\n").append(Component.translatable(
                    "command.toukenranbu_mod.touken.locate.cross_dim_hint"
            ).withStyle(ChatFormatting.GRAY));
        }

        final net.minecraft.network.chat.MutableComponent finalResult = result;
        source.sendSuccess(() -> finalResult, false);

        return index - 1;
    }

    /** 只统计数量 */
    private static int countOwnDanshi(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        CommandSourceStack source = ctx.getSource();
        ServerPlayer player = source.getPlayerOrException();

        Collection<ToukenDanshiEntity> all = ToukenDanshiEntity.getOwnedDanshi().get(player.getUUID());
        int count = all == null ? 0 : (int) all.stream().filter(ToukenDanshiEntity::isAlive).count();

        source.sendSuccess(() -> Component.translatable(
                "command.toukenranbu_mod.touken.count", count
        ).withStyle(ChatFormatting.GOLD), false);
        return count;
    }
}