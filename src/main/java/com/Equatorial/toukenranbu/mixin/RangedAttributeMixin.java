package com.Equatorial.toukenranbu.mixin;

import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RangedAttribute.class)
public class RangedAttributeMixin {

    @Shadow @Final @Mutable private double maxValue;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void toukenranbu$setMaxValue(String name, double defaultValue, double minValue, double maxValue, CallbackInfo ci) {
        // 仅在创建 MAX_HEALTH 属性时修改上限
        if (name.equals("attribute.name.generic.max_health")) {
            this.maxValue = 1000000.0D; // 新的上限
        }
    }
}