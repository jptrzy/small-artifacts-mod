package net.jptrzy.small.artifacts.mixin;

import dev.emi.trinkets.api.TrinketsApi;
import net.jptrzy.small.artifacts.ItemsRegister;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EndermanEntity.class)
public class EndermanEntityMixin
{
    @Inject(method = "isPlayerStaring", at = @At("HEAD"), cancellable = true)
    private void isPlayerStaring(PlayerEntity player, CallbackInfoReturnable<Boolean> ci){
        if (TrinketsApi.getTrinketComponent(player).get().isEquipped(ItemsRegister.ENDER_EYE)) ci.setReturnValue(false);
    }
}
