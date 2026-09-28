package com.Equatorial.toukenranbu.mixin;

import com.Equatorial.toukenranbu.entity.util.CannotBeMillstoned;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "com.github.ysbbbbbb.kaleidoscopecookery.blockentity.kitchen.MillstoneBlockEntity")
public abstract class MillstoneBindMixin {

    @Inject(
            method = "canBindEntity(Lnet/minecraft/world/entity/Mob;)Z",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private void toukenranbu$blockBlacklistedBinding(Mob mob, CallbackInfoReturnable<Boolean> cir) {
        if (mob instanceof CannotBeMillstoned) {
            cir.setReturnValue(false);
        }
    }
}