package com.Equatorial.toukenranbu.item;

// 工具类：负责实体NBT的序列化/反序列化
import com.Equatorial.toukenranbu.util.EntityCaptureHelper;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

/**
 * 收容符物品
 * 右键生物 = 捕获（将实体完整NBT写入物品，移除原实体）
 * 右键方块 = 释放（从物品NBT重建实体，放到点击位置）
 */
public class CaptureBallItem extends Item {

    public CaptureBallItem(Properties properties) {
        super(properties);
    }

    /**
     * 右键实体时调用 —— 捕获逻辑
     */
    @Override
    public InteractionResult interactLivingEntity(
            ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {

        // 只在服务端执行，客户端直接返回SUCCESS避免不同步
        if (player.level().isClientSide) {
            return InteractionResult.SUCCESS;
        }

        // 禁止收容玩家
        if (target instanceof Player) {
            player.displayClientMessage(
                    Component.translatable("message.toukenranbu_mod.capture.fail_player"), true);
            return InteractionResult.FAIL;
        }

        // 禁止重复收容（一个符只能装一个实体）
        if (EntityCaptureHelper.hasCapturedEntity(stack)) {
            player.displayClientMessage(
                    Component.translatable("message.toukenranbu_mod.capture.fail_full"), true);
            return InteractionResult.FAIL;
        }

        // 执行捕获：序列化实体NBT → 写入物品 → 移除原实体
        ItemStack result = EntityCaptureHelper.captureEntity(target, stack.copy(), player);

        // 处理堆叠情况（虽然stacksTo(1)通常不会触发，保险起见）
        if (stack.getCount() > 1) {
            stack.shrink(1);
            if (!player.getInventory().add(result)) {
                player.drop(result, false);
            }
        } else {
            player.setItemInHand(hand, result);
        }

        player.displayClientMessage(
                Component.translatable("message.toukenranbu_mod.capture.success"), true);
        return InteractionResult.SUCCESS;
    }

    /**
     * 右键方块时调用 —— 释放逻辑
     * 核心：根据点击的面计算释放位置，防止实体卡墙/卡地底窒息
     */
    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        ItemStack stack = context.getItemInHand();

        // 只在服务端执行
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        // 空符不能释放
        if (!EntityCaptureHelper.hasCapturedEntity(stack)) {
            player.displayClientMessage(
                    Component.translatable("message.toukenranbu_mod.release.fail_empty"), true);
            return InteractionResult.FAIL;
        }

        // 主人校验：非主人无法释放（防止物品被转卖后他人使用）
        UUID itemOwner = EntityCaptureHelper.getItemOwner(stack);
        if (itemOwner != null && !itemOwner.equals(player.getUUID())) {
            player.displayClientMessage(
                    Component.translatable("message.toukenranbu_mod.release.fail_owner"), true);
            return InteractionResult.FAIL;
        }

        // ========== 释放位置计算 ==========
        // 获取点击的精确坐标和朝向的面
        Vec3 clickLoc = context.getClickLocation();
        Direction face = context.getClickedFace();

        // 向点击面的外侧偏移0.1格，确保实体出现在方块表面而不是内部
        BlockPos releasePos = BlockPos.containing(
                clickLoc.x + face.getStepX() * 0.1,
                clickLoc.y + face.getStepY() * 0.1,
                clickLoc.z + face.getStepZ() * 0.1
        );

        // 保险：如果目标位置被方块占据（比如点侧面旁边是墙），向上找空位
        if (!level.isEmptyBlock(releasePos)) {
            releasePos = releasePos.above();
        }

        // 执行释放：读取物品NBT → 重建实体 → 放入世界
        LivingEntity released = EntityCaptureHelper.releaseEntity(stack, level, releasePos);

        if (released != null) {
            player.displayClientMessage(
                    Component.translatable("message.toukenranbu_mod.release.success",
                            released.getDisplayName()), true);
        }

        return InteractionResult.SUCCESS;
    }

    /**
     * 物品悬浮提示（鼠标放在物品上显示的文字）
     */
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level,
                                List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        if (EntityCaptureHelper.hasCapturedEntity(stack)) {
            // 已收容状态：显示实体名、类型、主人
            String name = EntityCaptureHelper.getCapturedEntityName(stack);
            tooltip.add(Component.translatable("tooltip.toukenranbu_mod.capture_ball.entity", name));

            String type = EntityCaptureHelper.getCapturedEntityType(stack);
            tooltip.add(Component.translatable("tooltip.toukenranbu_mod.capture_ball.type", type));

            UUID owner = EntityCaptureHelper.getItemOwner(stack);
            if (owner != null) {
                String shortId = owner.toString().substring(0, 8) + "...";
                tooltip.add(Component.translatable("tooltip.toukenranbu_mod.capture_ball.owner", shortId));
            }
        } else {
            // 空符状态：显示用法
            tooltip.add(Component.translatable("tooltip.toukenranbu_mod.capture_ball.empty"));
            tooltip.add(Component.translatable("tooltip.toukenranbu_mod.capture_ball.usage"));
        }
    }
}