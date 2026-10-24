package com.Equatorial.toukenranbu.client;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.touken.ToukenDanshiEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderNameTagEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ToukenRanbuMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ToukenNameTagHandler {

    @SubscribeEvent
    public static void onRenderNameTag(RenderNameTagEvent event) {
        if (event.getEntity() instanceof ToukenDanshiEntity) {
            // 刀剑男士的头顶名字牌彻底隐藏，文字交给 BubbleRenderLayer 去画
            event.setResult(Event.Result.DENY);
        }
    }
}