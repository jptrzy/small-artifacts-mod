package net.jptrzy.small.artifacts.mixin;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.jptrzy.small.artifacts.ItemsRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(method = "blockedByShield", at = @At("HEAD"), cancellable = true)
    private void blockedByShield(DamageSource source, CallbackInfoReturnable<Boolean> ci) {
        Entity entity = source.getSource();

        if (entity instanceof ArrowEntity) {
            Optional<TrinketComponent> optional = TrinketsApi.getTrinketComponent((LivingEntity) (Object) this);
            if(optional.isPresent() && optional.get().isEquipped(ItemsRegister.SCUTE_CAPE)){
                ci.setReturnValue(true);
            }
        }

    }

}
