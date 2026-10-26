package com.Equatorial.toukenranbu.event;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.touken.ToukenDanshiEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = ToukenRanbuMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ToukenHealthCompat {

    private static final UUID HEALTH_MULT_UUID = UUID.fromString("a1b2c3d4-e5f6-7890-abcd-ef1234567891");

    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide()) return;

        if (event.getEntity() instanceof ToukenDanshiEntity danshi) {
            // 白三日月排除
            if (danshi.getType().toString().equals("toukenranbu_mod:white_mikazuki_munechika")) {
                return;
            }

            danshi.level().getServer().execute(() -> {
                if (!danshi.isAlive()) return;

                double multiplier = 1.0;

                // 寻找最近的玩家检测标签
                Player nearestPlayer = danshi.level().getNearestPlayer(danshi, 128.0D);
                if (nearestPlayer != null) {
                    for (String tag : nearestPlayer.getTags()) {
                        // 1. 必须以 health_ 开头，且不能是 bosshealth_ 开头
                        if (tag.startsWith("health_") && !tag.startsWith("bosshealth_")) {
                            // 2. 把前缀切掉，拿到 "30_0" 或 "1_5"
                            String numberPart = tag.substring(7); // "health_" 是7个字符

                            // 3. 把下划线替换成小数点，变成 "30.0" 或 "1.5"
                            numberPart = numberPart.replace("_", ".");

                            try {
                                // 4. 严格转换数字。如果某个模组打了 "health_boost" 标签，这里转换会报错被拦截
                                double parsed = Double.parseDouble(numberPart);
                                if (parsed > 1.0) {
                                    multiplier = parsed;
                                }
                            } catch (NumberFormatException ignored) {
                                // 只要不是纯数字，直接忽略
                            }
                        }
                    }
                }
                // 只要没找到标签，或者倍率就是 1.0，那就什么都不做，保持原版血量
                if (multiplier > 1.0) {
                    var healthAttr = danshi.getAttribute(Attributes.MAX_HEALTH);
                    if (healthAttr != null) {
                        healthAttr.removeModifier(HEALTH_MULT_UUID);
                        healthAttr.addPermanentModifier(new AttributeModifier(
                                HEALTH_MULT_UUID,
                                "touken_integration_health",
                                multiplier - 1.0,
                                AttributeModifier.Operation.MULTIPLY_TOTAL
                        ));
                        danshi.setHealth(danshi.getMaxHealth());
                    }
                }
            });
        }
    }
}