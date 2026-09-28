package com.Equatorial.toukenranbu.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class GroupCaptureBallItem extends Item {

    private static final String TAG_ENTITIES = "CapturedEntities";
    private static final String TAG_ITEM_OWNER = "ItemOwnerUUID";
    private static final int CAPTURE_RANGE = 32;

    public GroupCaptureBallItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        ItemStack stack = context.getItemInHand();

        if (level.isClientSide) return InteractionResult.SUCCESS;
        if (player == null) return InteractionResult.FAIL;

        if (player.isShiftKeyDown()) {
            releaseAll(stack, level, context, player);
        } else {
            captureNearby(stack, level, player);
        }
        return InteractionResult.SUCCESS;
    }

    private void captureNearby(ItemStack stack, Level level, Player player) {
        // 主人校验
        UUID itemOwner = getItemOwner(stack);
        if (itemOwner == null) {
            stack.getOrCreateTag().putUUID(TAG_ITEM_OWNER, player.getUUID());
        } else if (!itemOwner.equals(player.getUUID())) {
            player.displayClientMessage(
                    Component.translatable("message.toukenranbu_mod.group_capture.fail_owner"), true);
            return;
        }

        AABB area = player.getBoundingBox().inflate(CAPTURE_RANGE);
        List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, area, entity -> {
            if (!(entity instanceof TamableAnimal tamable)) return false;
            if (!tamable.isTame()) return false;
            return tamable.isOwnedBy(player);
        });

        if (targets.isEmpty()) {
            player.displayClientMessage(
                    Component.translatable("message.toukenranbu_mod.group_capture.none"), true);
            return;
        }

        CompoundTag itemTag = stack.getOrCreateTag();
        ListTag entitiesList = itemTag.getList(TAG_ENTITIES, 10);

        int captured = 0;
        for (LivingEntity target : targets) {
            CompoundTag entityTag = new CompoundTag();
            target.save(entityTag);
            entityTag.remove("UUID");
            entityTag.remove("Pos");
            entityTag.remove("Motion");
            entityTag.remove("Rotation");
            entityTag.remove("FallDistance");
            entityTag.remove("Fire");
            entityTag.remove("OnGround");
            entityTag.remove("PortalCooldown");
            entityTag.remove("Passengers");
            entityTag.remove("Leash");

            CompoundTag entry = new CompoundTag();
            entry.putString("Type", EntityType.getKey(target.getType()).toString());
            entry.put("Data", entityTag);
            if (target.hasCustomName()) {
                entry.putString("Name", target.getCustomName().getString());
            }
            if (target instanceof TamableAnimal tamable && tamable.getOwnerUUID() != null) {
                entry.putUUID("Owner", tamable.getOwnerUUID());
            }

            entitiesList.add(entry);
            captured++;
            target.discard();
        }

        itemTag.put(TAG_ENTITIES, entitiesList);
        updateHoverName(stack, entitiesList.size());

        player.displayClientMessage(
                Component.translatable("message.toukenranbu_mod.group_capture.captured", captured), true);
    }

    private void releaseAll(ItemStack stack, Level level, UseOnContext context, Player player) {
        if (!stack.hasTag()) {
            player.displayClientMessage(
                    Component.translatable("message.toukenranbu_mod.release.fail_empty"), true);
            return;
        }
        CompoundTag itemTag = stack.getTag();
        if (itemTag == null || !itemTag.contains(TAG_ENTITIES, 9)) {
            player.displayClientMessage(
                    Component.translatable("message.toukenranbu_mod.release.fail_empty"), true);
            return;
        }

        UUID itemOwner = getItemOwner(stack);
        if (itemOwner != null && !itemOwner.equals(player.getUUID())) {
            player.displayClientMessage(
                    Component.translatable("message.toukenranbu_mod.release.fail_owner"), true);
            return;
        }

        ListTag entitiesList = itemTag.getList(TAG_ENTITIES, 10);
        if (entitiesList.isEmpty()) {
            player.displayClientMessage(
                    Component.translatable("message.toukenranbu_mod.release.fail_empty"), true);
            return;
        }

        Vec3 clickLoc = context.getClickLocation();
        Direction face = context.getClickedFace();
        BlockPos basePos = BlockPos.containing(
                clickLoc.x + face.getStepX() * 0.1,
                clickLoc.y + face.getStepY() * 0.1,
                clickLoc.z + face.getStepZ() * 0.1
        );

        // 把列表拷贝出来，避免遍历时修改
        ListTag copy = entitiesList.copy();
        itemTag.remove(TAG_ENTITIES);
        stack.resetHoverName();

        int released = 0;
        for (int i = 0; i < copy.size(); i++) {
            CompoundTag entry = copy.getCompound(i);
            String typeId = entry.getString("Type");
            CompoundTag data = entry.getCompound("Data");

            // 螺旋展开，避免实体堆在一起
            double angle = i * 0.7;
            double radius = 0.5 + i * 0.35;
            double offsetX = Math.cos(angle) * radius;
            double offsetZ = Math.sin(angle) * radius;

            BlockPos releasePos = BlockPos.containing(
                    basePos.getX() + 0.5 + offsetX,
                    basePos.getY(),
                    basePos.getZ() + 0.5 + offsetZ
            );
            if (!level.isEmptyBlock(releasePos)) {
                releasePos = releasePos.above();
            }

            final BlockPos finalPos = releasePos;
            UUID itemOwnerUUID = getItemOwner(stack);   // 👈 提前取出物品主人

            EntityType.byString(typeId).ifPresent(type -> {
                Entity entity = type.create(level);
                if (entity instanceof LivingEntity living) {
                    living.load(data);
                    living.setPos(finalPos.getX() + 0.5, finalPos.getY(), finalPos.getZ() + 0.5);
                    living.setYRot(level.random.nextFloat() * 360.0F);
                    living.setXRot(0.0F);

                    if (living instanceof TamableAnimal tamable) {
                        // 1. 优先用条目里记录的原主人
                        UUID ownerToSet = null;
                        if (entry.hasUUID("Owner")) {
                            ownerToSet = entry.getUUID("Owner");
                        }
                        // 2. 条目里没有，用物品主人兜底
                        else if (itemOwnerUUID != null) {
                            ownerToSet = itemOwnerUUID;
                        }
                        // 3. 两个都没有 → 本来就是无主，正常
                        if (ownerToSet != null) {
                            tamable.setOwnerUUID(ownerToSet);
                            tamable.setTame(true);
                        }
                    }
                    level.addFreshEntity(living);
                }
            });
            released++;
        }

        player.displayClientMessage(
                Component.translatable("message.toukenranbu_mod.group_capture.released", released), true);
    }

    private static void updateHoverName(ItemStack stack, int count) {
        stack.setHoverName(
                Component.translatable("item.toukenranbu_mod.group_capture_ball.prefix", count)
        );
    }

    @Nullable
    public static UUID getItemOwner(ItemStack item) {
        if (!item.hasTag()) return null;
        CompoundTag tag = item.getTag();
        if (tag != null && tag.hasUUID(TAG_ITEM_OWNER)) {
            return tag.getUUID(TAG_ITEM_OWNER);
        }
        return null;
    }

    public static int getCapturedCount(ItemStack item) {
        if (!item.hasTag()) return 0;
        CompoundTag tag = item.getTag();
        if (tag != null && tag.contains(TAG_ENTITIES, 9)) {
            return tag.getList(TAG_ENTITIES, 10).size();
        }
        return 0;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level,
                                List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        int count = getCapturedCount(stack);
        if (count > 0) {
            tooltip.add(Component.translatable("tooltip.toukenranbu_mod.group_capture_ball.count", count));
            UUID owner = getItemOwner(stack);
            if (owner != null) {
                tooltip.add(Component.translatable("tooltip.toukenranbu_mod.capture_ball.owner",
                        owner.toString().substring(0, 8) + "..."));
            }
        } else {
            tooltip.add(Component.translatable("tooltip.toukenranbu_mod.group_capture_ball.empty"));
            tooltip.add(Component.translatable("tooltip.toukenranbu_mod.group_capture_ball.usage"));
        }
    }
}