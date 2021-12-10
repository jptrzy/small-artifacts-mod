package net.jptrzy.small.artifacts.mixin;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.jptrzy.small.artifacts.Main;
import net.jptrzy.small.artifacts.registry.ItemsRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
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

        if ( (LivingEntity) (Object) this instanceof PlayerEntity && entity instanceof ArrowEntity) {
            PlayerEntity p = (PlayerEntity) (Object) this;
            Optional<TrinketComponent> optional = TrinketsApi.getTrinketComponent((LivingEntity) (Object) this);
            if(optional.isPresent() && optional.get().isEquipped(ItemsRegister.SCUTE_CAPE) && p.getHungerManager().getFoodLevel() > 0){
                if(!p.getEntityWorld().isClient()){
//                   p.getHungerManager().setExhaustion( p.getHungerManager().getExhaustion() + 8 );
                    int hunger = p.getHungerManager().getFoodLevel() - 3;
                    if(hunger < 0){
                        hunger = 0;
                    }
                    p.getHungerManager().setFoodLevel(hunger);
                }
                ci.setReturnValue(true);
            }
        }

    }

}
