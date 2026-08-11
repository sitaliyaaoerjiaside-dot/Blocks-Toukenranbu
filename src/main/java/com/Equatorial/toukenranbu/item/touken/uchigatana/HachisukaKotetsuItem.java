package com.Equatorial.toukenranbu.item.touken.uchigatana;

import com.Equatorial.toukenranbu.advancement.ModAdvancementTriggers;
import com.Equatorial.toukenranbu.entity.ModEntityTypes;
import com.Equatorial.toukenranbu.entity.touken.uchigatana.HachisukaKotetsuEntity;
import com.Equatorial.toukenranbu.util.StarterSwordHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class HachisukaKotetsuItem extends Item {

    public HachisukaKotetsuItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (!level.isClientSide && context.getPlayer() != null) {

            HachisukaKotetsuEntity danshi = new HachisukaKotetsuEntity(ModEntityTypes.HACHISUKA_KOTETSU.get(), level);
            danshi.setTame(true);
            danshi.setOwnerUUID(context.getPlayer().getUUID());

            var pos = context.getClickedPos().above().getCenter();
            double ex = pos.x;
            double ey = pos.y;
            double ez = pos.z;

            // 计算面向玩家的角度：刀剑男士 → 玩家
            double dx = context.getPlayer().getX() - ex;
            double dz = context.getPlayer().getZ() - ez;
            float faceYaw = (float) (Math.toDegrees(Math.atan2(dz, dx))) - 90.0F;

            danshi.moveTo(ex, ey, ez, faceYaw, 0);
            danshi.yBodyRot = faceYaw;
            danshi.yHeadRot = faceYaw;
            danshi.yRotO = faceYaw;      // 防止客户端插值导致第一帧跳变
            danshi.yBodyRotO = faceYaw;
            danshi.yHeadRotO = faceYaw;

            level.addFreshEntity(danshi);

            // 触发成就
            if (context.getPlayer() instanceof ServerPlayer serverPlayer) {
                ModAdvancementTriggers.USE_STARTER_SWORD.trigger(serverPlayer);
            }

            // ===== 没被使用的会被清除掉，后续在探索中也是能获取的不用担心 =====
            if (StarterSwordHelper.isStarterSword(context.getItemInHand())) {
                StarterSwordHelper.clearOtherStarterSwords(context.getPlayer(), context.getHand());
            }
            // ===========================================================

            if (!context.getPlayer().isCreative()) {
                context.getItemInHand().shrink(1);
            }

            context.getPlayer().sendSystemMessage(
                    Component.translatable("message.toukenranbu_mod.summon_hachisuka_kotetsu")
            );
        }
        return InteractionResult.SUCCESS;
    }
}
