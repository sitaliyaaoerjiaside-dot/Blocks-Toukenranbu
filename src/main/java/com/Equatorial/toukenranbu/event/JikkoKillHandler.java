package com.Equatorial.toukenranbu.event;

import com.Equatorial.toukenranbu.advancement.ModCriteriaTriggers;
import com.Equatorial.toukenranbu.data.JikkoKillCounter;
import com.Equatorial.toukenranbu.entity.ModEntityTypes;
import com.Equatorial.toukenranbu.entity.custom.kebiishi.KebiishiEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class JikkoKillHandler {

    private static final int CHECK_INTERVAL = 20;          // 每秒检查一次（20 ticks）
    private static final int WARNING_INTERVAL = 6000;      // 每5分钟提示一次
    private static final int SPAWN_DELAY = 600;            // 聚集后30秒延迟生成
    private static final double SPAWN_CHANCE_MULTIPLIER = 15; // tier * 15%

    // ===== 1. 击杀计数 =====
    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {
        if (!(event.getEntity() instanceof com.Equatorial.toukenranbu.entity.custom.JikkoEntity)) return;
        if (!(event.getSource().getEntity() instanceof Player)) return;
        if (!(event.getEntity().level() instanceof ServerLevel serverLevel)) return;

        JikkoKillCounter counter = JikkoKillCounter.get(serverLevel);
        int oldTier = counter.getKebiishiTier();
        counter.addKill();
        int newTier = counter.getKebiishiTier();

        if (newTier > oldTier && newTier > 0) {
            serverLevel.getServer().getPlayerList().broadcastSystemMessage(
                    Component.translatable("message.toukenranbu_mod.kebiishi_tier_up", newTier),
                    false
            );
        }
    }

    // ===== 2. 定时生成：30分钟冷却 + 聚集警告30秒 + 附近检测暂停 =====
    @SubscribeEvent
    public void onLevelTick(TickEvent.LevelTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (!(event.level instanceof ServerLevel serverLevel)) return;
        if (serverLevel.dimension() != Level.OVERWORLD) return;
        if (serverLevel.getGameTime() % CHECK_INTERVAL != 0) return;

        JikkoKillCounter counter = JikkoKillCounter.get(serverLevel);
        if (!counter.isSpawningEnabled()) return;

        int tier = counter.getKebiishiTier();
        if (tier <= 0) return;

        ServerPlayer targetPlayer = serverLevel.getRandomPlayer();
        if (targetPlayer == null) return;

        // 附近有检非违使时暂停一切（冷却和警告都不走）
        boolean hasNearbyKebiishi = !serverLevel.getEntitiesOfClass(
                Mob.class,
                targetPlayer.getBoundingBox().inflate(80.0),
                entity -> entity instanceof KebiishiEntity
        ).isEmpty();

        if (hasNearbyKebiishi) return;

        long spawnCd = counter.getSpawnCooldown();
        long warnCd = counter.getWarningCooldown();

        // ===== 阶段1：处理"聚集后延迟生成" =====
        if (warnCd > 0) {
            counter.setWarningCooldown(warnCd - CHECK_INTERVAL);
            if (counter.getWarningCooldown() <= 0 && counter.isPendingSpawn()) {
                executePendingSpawn(serverLevel, counter, tier);
            }
            return;
        }

        // ===== 阶段2：冷却倒计时中 =====
        if (spawnCd > 0) {
            counter.setSpawnCooldown(spawnCd - CHECK_INTERVAL);
            long remaining = counter.getSpawnCooldown();
            if (remaining > 0 && remaining % WARNING_INTERVAL == 0) {
                int minutes = (int) (remaining / 1200); // 1200 ticks = 1分钟
                broadcastToAllPlayers(serverLevel,
                        Component.translatable("message.toukenranbu_mod.kebiishi.countdown", minutes)
                                .withStyle(ChatFormatting.GRAY)
                );
            }
            return;
        }

        // ===== 阶段3：冷却结束，尝试触发生成 =====
        // 先重置为30分钟，避免触发失败后连续尝试
        counter.resetCooldown();
        tryTriggerSpawn(serverLevel, counter, targetPlayer, tier);
    }

    private void tryTriggerSpawn(ServerLevel level, JikkoKillCounter counter, ServerPlayer target, int tier) {
        RandomSource random = level.getRandom();
        if (random.nextInt(100) >= tier * SPAWN_CHANCE_MULTIPLIER) return;

        BlockPos spawnPos = findSpawnPos(level, target);
        if (spawnPos == null) return;

        broadcastToAllPlayers(level,
                Component.translatable("message.toukenranbu_mod.kebiishi.warning", target.getDisplayName().getString())
                        .withStyle(ChatFormatting.RED)
        );

        counter.setPendingSpawn(true);
        counter.setWarningCooldown(SPAWN_DELAY);
    }

    private void executePendingSpawn(ServerLevel level, JikkoKillCounter counter, int tier) {
        counter.setPendingSpawn(false);
        ServerPlayer target = level.getRandomPlayer();
        if (target == null) {
            counter.resetCooldown();
            return;
        }
        spawnKebiishiSquad(level, target, tier);
        counter.resetCooldown();
    }

    // ===== 3. 聊天指令开关 =====
    @SubscribeEvent
    public void onServerChat(ServerChatEvent event) {
        String msg = event.getMessage().getString().trim().toLowerCase();
        ServerPlayer player = event.getPlayer();

        if (!player.hasPermissions(2) && !player.getServer().isSingleplayer()) return;

        if (!(player.level() instanceof ServerLevel serverLevel)) return;
        JikkoKillCounter counter = JikkoKillCounter.get(serverLevel);
        boolean handled = false;

        if (msg.equals("#kebiishi on") || msg.equals("#检非违使开启")) {
            counter.setSpawningEnabled(true);
            player.sendSystemMessage(Component.translatable("message.toukenranbu_mod.kebiishi.on"));
            handled = true;
        } else if (msg.equals("#kebiishi off") || msg.equals("#检非违使关闭")) {
            counter.setSpawningEnabled(false);
            player.sendSystemMessage(Component.translatable("message.toukenranbu_mod.kebiishi.off"));
            handled = true;
        } else if (msg.equals("#kebiishi status") || msg.equals("#检非违使状态")) {
            boolean on = counter.isSpawningEnabled();
            long cd = counter.getSpawnCooldown();
            String cdStr = String.format("%.1f", cd / 20.0f / 60.0f);

            String statusKey = on
                    ? "message.toukenranbu_mod.kebiishi.status.on"
                    : "message.toukenranbu_mod.kebiishi.status.off";

            player.sendSystemMessage(Component.translatable(
                    "message.toukenranbu_mod.kebiishi.status.format",
                    Component.translatable(statusKey),
                    cdStr
            ));
            handled = true;
        }

        if (handled) {
            event.setCanceled(true);
        }
    }

    // ===== 4. 生成小队 =====
    private void spawnKebiishiSquad(ServerLevel level, ServerPlayer player, int tier) {
        RandomSource random = level.getRandom();
        BlockPos center = findSpawnPos(level, player);

        // 10次都找不到陆地，跳过本次生成
        if (center == null) return;

        int squadSize = 2 + tier;
        boolean hasLeader = tier >= 2;

        if (hasLeader) {
            spawnKebiishi(level, center, "leader", random);
        }

        spawnKebiishi(level, center.offset(random.nextInt(4) - 2, 0, random.nextInt(4) - 2), "yari", random);
        spawnKebiishi(level, center.offset(random.nextInt(4) - 2, 0, random.nextInt(4) - 2), "yari", random);

        for (int i = 0; i < squadSize - 2 - (hasLeader ? 1 : 0); i++) {
            String[] types = {"tachi", "ootachi", "naginata"};
            String type = types[random.nextInt(types.length)];
            spawnKebiishi(level, center.offset(random.nextInt(6) - 3, 0, random.nextInt(6) - 3), type, random);
        }

        level.getServer().getPlayerList().broadcastSystemMessage(
                Component.translatable("message.toukenranbu_mod.kebiishi_spawn", player.getDisplayName()),
                false
        );
        // ★★★ 新增：触发"三方会晤"成就 ★★★
        ModCriteriaTriggers.KEBIISHI_SPAWN.trigger(player);
    }

    private void spawnKebiishi(ServerLevel level, BlockPos pos, String type, RandomSource random) {
        EntityType<? extends Monster> entityType;

        switch (type) {
            case "leader":
                entityType = ModEntityTypes.KEBIISHI_LEADER.get();
                break;
            case "tachi":
                entityType = ModEntityTypes.KEBIISHI_TACHI.get();
                break;
            case "ootachi":
                entityType = ModEntityTypes.KEBIISHI_OOTACHI.get();
                break;
            case "yari":
                entityType = ModEntityTypes.KEBIISHI_YARI.get();
                break;
            case "naginata":
                entityType = ModEntityTypes.KEBIISHI_NAGINATA.get();
                break;
            default:
                return;
        }

        Monster mob = entityType.create(level);
        if (mob != null) {
            mob.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, random.nextFloat() * 360, 0);
            level.addFreshEntity(mob);
        }
    }

    private BlockPos findSpawnPos(ServerLevel level, ServerPlayer player) {
        RandomSource random = level.getRandom();
        BlockPos playerPos = player.blockPosition();

        for (int attempt = 0; attempt < 10; attempt++) {
            int angle = random.nextInt(360);
            int distance = 48 + random.nextInt(33);
            int x = playerPos.getX() + (int) (Math.cos(Math.toRadians(angle)) * distance);
            int z = playerPos.getZ() + (int) (Math.sin(Math.toRadians(angle)) * distance);
            int y = level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z);
            BlockPos pos = new BlockPos(x, y, z);
            BlockPos below = pos.below();

            // 不能是水/岩浆，脚下必须是实心方块
            if (!level.getBlockState(pos).is(Blocks.WATER) &&
                    !level.getBlockState(pos).is(Blocks.LAVA) &&
                    !level.getBlockState(below).is(Blocks.WATER) &&
                    !level.getBlockState(below).is(Blocks.LAVA) &&
                    !level.getBlockState(below).isAir()) {
                return pos;
            }
        }
        return null;
    }

    private void broadcastToAllPlayers(ServerLevel level, Component message) {
        for (ServerPlayer p : level.players()) {
            p.sendSystemMessage(message);
        }
    }
}