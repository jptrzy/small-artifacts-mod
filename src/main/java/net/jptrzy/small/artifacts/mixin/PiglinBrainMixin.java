package net.jptrzy.small.artifacts.mixin;

import dev.emi.trinkets.api.TrinketsApi;
import net.jptrzy.small.artifacts.ItemsRegister;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.PiglinBrain;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PiglinBrain.class)
public class PiglinBrainMixin
{
    @Inject(method = "wearsGoldArmor", at = @At("HEAD"), cancellable = true)
    private static void wearsGoldArmor(LivingEntity entity, CallbackInfoReturnable<Boolean> ci)
    {
        if (TrinketsApi.getTrinketComponent(entity).get().isEquipped(ItemsRegister.GOLD_RING)) ci.setReturnValue(true);
    }
}
