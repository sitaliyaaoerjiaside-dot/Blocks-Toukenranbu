package com.Equatorial.toukenranbu.item;

import com.Equatorial.toukenranbu.entity.touken.ToukenDanshiEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.UUID;

public class EmaItem extends Item {

    private static final String TAG_BOUND_UUID = "BoundUUID";
    private static final String TAG_LAST_DIM = "LastDim";
    private static final String TAG_LAST_X = "LastX";
    private static final String TAG_LAST_Y = "LastY";
    private static final String TAG_LAST_Z = "LastZ";
    private static final String TAG_EMA_ID = "EmaId";

    private static final String DANSHI_BOUND_EMA = "BoundEmaId";

    private static final int CHARGE_TICKS = 40;

    public EmaItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player,
                                                  LivingEntity target, InteractionHand hand) {
        stack = player.getItemInHand(hand);
        if (!(target instanceof ToukenDanshiEntity danshi)) return InteractionResult.PASS;
        if (player.level().isClientSide) return InteractionResult.SUCCESS;

        if (player.isShiftKeyDown()) {
            return unbindDanshi(danshi, player);
        }

        if (hasBound(stack)) {
            player.displayClientMessage(
                    Component.translatable("message.toukenranbu_mod.ema.already_bound")
                            .withStyle(ChatFormatting.YELLOW), true);
            return InteractionResult.FAIL;
        }

        CompoundTag danshiData = danshi.getPersistentData();
        UUID myEmaId = getOrCreateEmaId(stack);
        if (danshiData.hasUUID(DANSHI_BOUND_EMA)) {
            UUID boundEmaId = danshiData.getUUID(DANSHI_BOUND_EMA);
            if (!boundEmaId.equals(myEmaId)) {
                player.displayClientMessage(
                        Component.translatable("message.toukenranbu_mod.ema.target_already_bound")
                                .withStyle(ChatFormatting.RED), true);
                return InteractionResult.FAIL;
            }
        }

        UUID ownerId = danshi.getOwnerUUID();

        if (ownerId == null) {
            danshi.setOwnerUUID(player.getUUID());
            danshi.setTame(true);
            danshi.setFollowing(true);
            danshi.setOrderedToSit(false);
            bindTo(stack, danshi);
            player.displayClientMessage(
                    Component.translatable("message.toukenranbu_mod.ema.claim_and_bind",
                            danshi.getName()).withStyle(ChatFormatting.GOLD), false);
            return InteractionResult.SUCCESS;
        }

        if (!ownerId.equals(player.getUUID())) {
            player.displayClientMessage(
                    Component.translatable("message.toukenranbu_mod.ema.not_yours")
                            .withStyle(ChatFormatting.RED), true);
            return InteractionResult.FAIL;
        }

        bindTo(stack, danshi);
        player.displayClientMessage(
                Component.translatable("message.toukenranbu_mod.ema.bind_success",
                        danshi.getName()).withStyle(ChatFormatting.GOLD), false);
        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!hasBound(stack)) return InteractionResultHolder.pass(stack);
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public void onUseTick(Level level, LivingEntity living, ItemStack stack, int remainingTicks) {
        if (level.isClientSide) return;
        if (!(living instanceof ServerPlayer player)) return;

        int elapsed = getUseDuration(stack) - remainingTicks;
        if (elapsed < CHARGE_TICKS) return;

        if (hasBound(stack)) {
            summonToOwner(stack, player);
            player.stopUsingItem();
        }
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity living, int timeLeft) {
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return CHARGE_TICKS + 1;
    }

    public static boolean hasBound(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        return tag != null && tag.hasUUID(TAG_BOUND_UUID);
    }

    private static UUID getOrCreateEmaId(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        if (!tag.hasUUID(TAG_EMA_ID)) {
            tag.putUUID(TAG_EMA_ID, UUID.randomUUID());
        }
        return tag.getUUID(TAG_EMA_ID);
    }

    private static void bindTo(ItemStack stack, ToukenDanshiEntity danshi) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putUUID(TAG_BOUND_UUID, danshi.getUUID());
        tag.putString(TAG_LAST_DIM, danshi.level().dimension().location().toString());
        tag.putDouble(TAG_LAST_X, danshi.getX());
        tag.putDouble(TAG_LAST_Y, danshi.getY());
        tag.putDouble(TAG_LAST_Z, danshi.getZ());

        danshi.getPersistentData().putUUID(DANSHI_BOUND_EMA, getOrCreateEmaId(stack));

        stack.setHoverName(
                Component.translatable("item.toukenranbu_mod.ema.bound",
                        danshi.getName()).withStyle(ChatFormatting.GOLD));
    }

    private static void clearBoundData(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.remove(TAG_BOUND_UUID);
        tag.remove(TAG_LAST_DIM);
        tag.remove(TAG_LAST_X);
        tag.remove(TAG_LAST_Y);
        tag.remove(TAG_LAST_Z);
        tag.remove(TAG_EMA_ID);
        stack.resetHoverName();
    }

    private static InteractionResult unbindDanshi(ToukenDanshiEntity danshi, Player player) {
        UUID danshiId = danshi.getUUID();

        boolean hadBound = danshi.getPersistentData().hasUUID(DANSHI_BOUND_EMA);
        danshi.getPersistentData().remove(DANSHI_BOUND_EMA);

        boolean foundEma = false;
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack invStack = player.getInventory().getItem(i);
            if (!(invStack.getItem() instanceof EmaItem)) continue;
            if (!hasBound(invStack)) continue;
            CompoundTag tag = invStack.getTag();
            if (tag != null && tag.hasUUID(TAG_BOUND_UUID)
                    && tag.getUUID(TAG_BOUND_UUID).equals(danshiId)) {
                clearBoundData(invStack);
                foundEma = true;
            }
        }

        if (hadBound || foundEma) {
            player.displayClientMessage(
                    Component.translatable("message.toukenranbu_mod.ema.unbound")
                            .withStyle(ChatFormatting.YELLOW), true);
        } else {
            player.displayClientMessage(
                    Component.translatable("message.toukenranbu_mod.ema.not_bound")
                            .withStyle(ChatFormatting.GRAY), true);
        }
        return InteractionResult.SUCCESS;
    }

    private static void summonToOwner(ItemStack stack, ServerPlayer player) {
        ItemStack realStack = stack;
        CompoundTag tag = realStack.getOrCreateTag();
        if (!tag.hasUUID(TAG_BOUND_UUID)) return;

        UUID danshiId = tag.getUUID(TAG_BOUND_UUID);
        UUID emaId = getOrCreateEmaId(realStack);
        MinecraftServer server = player.server;

        // 1. 所有已加载维度里按 UUID 精确查
        for (ServerLevel level : server.getAllLevels()) {
            Entity e = level.getEntity(danshiId);
            if (e instanceof ToukenDanshiEntity danshi) {
                finishSummon(realStack, player, danshi, level);
                return;
            }
        }

        // 2. UUID 失效兜底：按绘马 ID 在玩家名下刀男里反查
        //    覆盖"刀男进出过收容符，UUID 已变化"的情况
        var owned = ToukenDanshiEntity.getOwnedDanshi().get(player.getUUID());
        if (owned != null) {
            for (ToukenDanshiEntity d : owned) {
                if (d.isAlive()
                        && d.getPersistentData().hasUUID(DANSHI_BOUND_EMA)
                        && d.getPersistentData().getUUID(DANSHI_BOUND_EMA).equals(emaId)) {
                    tag.putUUID(TAG_BOUND_UUID, d.getUUID());
                    ServerLevel dLevel = (ServerLevel) d.level();
                    finishSummon(realStack, player, d, dLevel);
                    return;
                }
            }
        }

        // 3. 都失败，按坐标加载区块 + 搜索
        ResourceLocation dimLoc = ResourceLocation.tryParse(tag.getString(TAG_LAST_DIM));
        if (dimLoc == null) { fail(player); return; }

        ServerLevel boundLevel = server.getLevel(ResourceKey.create(Registries.DIMENSION, dimLoc));
        if (boundLevel == null) { fail(player); return; }

        double lx = tag.getDouble(TAG_LAST_X);
        double ly = tag.getDouble(TAG_LAST_Y);
        double lz = tag.getDouble(TAG_LAST_Z);

        final double RADIUS = 32.0;

        int minCx = ((int) Math.floor(lx - RADIUS)) >> 4;
        int maxCx = ((int) Math.floor(lx + RADIUS)) >> 4;
        int minCz = ((int) Math.floor(lz - RADIUS)) >> 4;
        int maxCz = ((int) Math.floor(lz + RADIUS)) >> 4;
        for (int cx = minCx; cx <= maxCx; cx++) {
            for (int cz = minCz; cz <= maxCz; cz++) {
                boundLevel.getChunk(cx, cz);
            }
        }

        final ServerPlayer fPlayer = player;
        final ServerLevel fLevel = boundLevel;
        final ItemStack fStack = realStack;
        final UUID fDanshiId = danshiId;
        final UUID fEmaId = emaId;

        server.execute(() -> {
            Entity e = fLevel.getEntity(fDanshiId);
            if (e instanceof ToukenDanshiEntity danshi) {
                finishSummon(fStack, fPlayer, danshi, fLevel);
                return;
            }

            AABB box = new AABB(
                    lx - RADIUS, ly - RADIUS, lz - RADIUS,
                    lx + RADIUS, ly + RADIUS, lz + RADIUS);
            var list = fLevel.getEntitiesOfClass(ToukenDanshiEntity.class, box);

            // 按 UUID 兜底
            for (ToukenDanshiEntity d : list) {
                if (d.getUUID().equals(fDanshiId)) {
                    finishSummon(fStack, fPlayer, d, fLevel);
                    return;
                }
            }

            // 按绘马 ID 兜底
            for (ToukenDanshiEntity d : list) {
                if (d.getPersistentData().hasUUID(DANSHI_BOUND_EMA)
                        && d.getPersistentData().getUUID(DANSHI_BOUND_EMA).equals(fEmaId)) {
                    CompoundTag t = fStack.getOrCreateTag();
                    t.putUUID(TAG_BOUND_UUID, d.getUUID());
                    finishSummon(fStack, fPlayer, d, fLevel);
                    return;
                }
            }

            fail(fPlayer);
        });
    }

    private static void fail(ServerPlayer player) {
        player.displayClientMessage(
                Component.translatable("message.toukenranbu_mod.ema.summon_fail")
                        .withStyle(ChatFormatting.RED), true);
    }

    private static void finishSummon(ItemStack stack, ServerPlayer player,
                                     ToukenDanshiEntity danshi, ServerLevel boundLevel) {
        // 直接调用实体类里的封装方法
        danshi.forceSummonTo(player);

        CompoundTag tag = stack.getOrCreateTag();
        tag.putUUID(TAG_BOUND_UUID, danshi.getUUID());
        tag.putString(TAG_LAST_DIM, danshi.level().dimension().location().toString());
        tag.putDouble(TAG_LAST_X, danshi.getX());
        tag.putDouble(TAG_LAST_Y, danshi.getY());
        tag.putDouble(TAG_LAST_Z, danshi.getZ());

        player.displayClientMessage(
                Component.translatable("message.toukenranbu_mod.ema.summon_success",
                        danshi.getName()).withStyle(ChatFormatting.GREEN), false);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (level.isClientSide) return;
        if (!(entity instanceof ServerPlayer player)) return;
        if (!hasBound(stack)) return;
        if (level.getGameTime() % 20 != 0) return;

        CompoundTag tag = stack.getTag();
        if (tag == null) return;
        UUID danshiId = tag.getUUID(TAG_BOUND_UUID);
        UUID emaId = getOrCreateEmaId(stack);

        // 1. 按 UUID 在所有已加载维度查找
        for (ServerLevel serverLevel : player.server.getAllLevels()) {
            Entity e = serverLevel.getEntity(danshiId);
            if (e instanceof ToukenDanshiEntity danshi) {
                tag.putString(TAG_LAST_DIM, serverLevel.dimension().location().toString());
                tag.putDouble(TAG_LAST_X, danshi.getX());
                tag.putDouble(TAG_LAST_Y, danshi.getY());
                tag.putDouble(TAG_LAST_Z, danshi.getZ());
                return;
            }
        }

        // 2. UUID 失效时按绘马 ID 反查，并自愈 UUID
        var owned = ToukenDanshiEntity.getOwnedDanshi().get(player.getUUID());
        if (owned != null) {
            for (ToukenDanshiEntity d : owned) {
                if (d.isAlive()
                        && d.getPersistentData().hasUUID(DANSHI_BOUND_EMA)
                        && d.getPersistentData().getUUID(DANSHI_BOUND_EMA).equals(emaId)) {
                    tag.putUUID(TAG_BOUND_UUID, d.getUUID());
                    tag.putString(TAG_LAST_DIM, d.level().dimension().location().toString());
                    tag.putDouble(TAG_LAST_X, d.getX());
                    tag.putDouble(TAG_LAST_Y, d.getY());
                    tag.putDouble(TAG_LAST_Z, d.getZ());
                    return;
                }
            }
        }
    }
}