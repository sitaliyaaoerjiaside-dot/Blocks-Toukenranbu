package com.Equatorial.toukenranbu.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

public class EntityCaptureHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger("EntityCaptureHelper");
    private static final String TAG_ENTITY_DATA = "CapturedEntityData";
    private static final String TAG_ENTITY_TYPE = "CapturedEntityType";
    private static final String TAG_ITEM_OWNER = "ItemOwnerUUID";
    private static final String TAG_ENTITY_NAME = "CapturedEntityName";

    public static ItemStack captureEntity(LivingEntity target, ItemStack container, @Nullable Player capturer) {
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

        ResourceLocation entityId = EntityType.getKey(target.getType());

        CompoundTag itemTag = container.getOrCreateTag();
        itemTag.putString(TAG_ENTITY_TYPE, entityId.toString());
        itemTag.put(TAG_ENTITY_DATA, entityTag);

        if (capturer != null) {
            itemTag.putUUID(TAG_ITEM_OWNER, capturer.getUUID());
        }

        if (target.hasCustomName()) {
            itemTag.putString(TAG_ENTITY_NAME, target.getCustomName().getString());
        }

        container.setHoverName(
                Component.translatable("item.toukenranbu_mod.capture_ball.prefix.captured")
                        .append(target.getDisplayName())
        );

        target.discard();
        return container;
    }

    @Nullable
    public static LivingEntity releaseEntity(ItemStack item, Level level, BlockPos pos) {
        if (level.isClientSide || !hasCapturedEntity(item)) {
            return null;
        }

        CompoundTag itemTag = item.getTag();
        if (itemTag == null) return null;

        String entityTypeId = itemTag.getString(TAG_ENTITY_TYPE);
        CompoundTag entityData = itemTag.getCompound(TAG_ENTITY_DATA);

        Optional<EntityType<?>> optionalType = EntityType.byString(entityTypeId);
        if (optionalType.isEmpty()) {
            LOGGER.error("Unknown entity type: {}", entityTypeId);
            return null;
        }

        EntityType<?> type = optionalType.get();
        Entity entity = type.create(level);
        if (!(entity instanceof LivingEntity)) {
            if (entity != null) entity.discard();
            return null;
        }

        LivingEntity living = (LivingEntity) entity;

        // 先加载数据（load 内部会因为没有 Pos 而把实体放到 (0,0,0)）
        living.load(entityData);

        // 必须在 load 之后重新设置位置，否则实体卡在 (0,0,0) 地下窒息死
        living.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
        living.setYRot(level.random.nextFloat() * 360.0F);
        living.setXRot(0.0F);

        // 保险：手动恢复手持装备（防止某些自定义实体 load 不完整）
        if (entityData.contains("HandItems", 9)) {
            ListTag handItems = entityData.getList("HandItems", 10);
            living.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.of(handItems.getCompound(0)));
            living.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.of(handItems.getCompound(1)));
        }

        // 保险：手动恢复盔甲
        if (entityData.contains("ArmorItems", 9)) {
            ListTag armorItems = entityData.getList("ArmorItems", 10);
            living.setItemSlot(EquipmentSlot.FEET, ItemStack.of(armorItems.getCompound(0)));
            living.setItemSlot(EquipmentSlot.LEGS, ItemStack.of(armorItems.getCompound(1)));
            living.setItemSlot(EquipmentSlot.CHEST, ItemStack.of(armorItems.getCompound(2)));
            living.setItemSlot(EquipmentSlot.HEAD, ItemStack.of(armorItems.getCompound(3)));
        }

        // 保险：手动恢复主人
        if (entityData.contains("OwnerUUID") && living instanceof TamableAnimal tamable) {
            tamable.setOwnerUUID(entityData.getUUID("OwnerUUID"));
        }

        level.addFreshEntity(living);

        // 清除物品数据
        itemTag.remove(TAG_ENTITY_TYPE);
        itemTag.remove(TAG_ENTITY_DATA);
        itemTag.remove(TAG_ITEM_OWNER);
        itemTag.remove(TAG_ENTITY_NAME);
        item.resetHoverName();

        return living;
    }

    public static boolean hasCapturedEntity(ItemStack item) {
        return item.hasTag() && item.getTag().contains(TAG_ENTITY_DATA);
    }

    @Nullable
    public static UUID getItemOwner(ItemStack item) {
        if (!hasCapturedEntity(item)) return null;
        CompoundTag tag = item.getTag();
        if (tag != null && tag.hasUUID(TAG_ITEM_OWNER)) {
            return tag.getUUID(TAG_ITEM_OWNER);
        }
        return null;
    }

    public static String getCapturedEntityName(ItemStack item) {
        if (!hasCapturedEntity(item)) return "";
        CompoundTag tag = item.getTag();
        if (tag.contains(TAG_ENTITY_NAME)) {
            return tag.getString(TAG_ENTITY_NAME);
        }
        return tag.getString(TAG_ENTITY_TYPE);
    }

    public static String getCapturedEntityType(ItemStack item) {
        if (!hasCapturedEntity(item)) return "empty";
        return item.getTag().getString(TAG_ENTITY_TYPE);
    }
}