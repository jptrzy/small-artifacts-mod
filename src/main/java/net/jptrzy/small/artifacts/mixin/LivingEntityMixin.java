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
import java.util.Random;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(method = "blockedByShield", at = @At("HEAD"), cancellable = true)
    private void blockedByShield(DamageSource source, CallbackInfoReturnable<Boolean> ci) {
        Entity entity = source.getSource();

        if ( (LivingEntity) (Object) this instanceof PlayerEntity && entity instanceof ArrowEntity) {
            PlayerEntity p = (PlayerEntity) (Object) this;
            Optional<TrinketComponent> optional = TrinketsApi.getTrinketComponent((LivingEntity) (Object) this);
            if(optional.isPresent() && p.getHungerManager().getFoodLevel() > 0){
                if(optional.get().isEquipped(ItemsRegister.SCUTE_CAPE) || optional.get().isEquipped(ItemsRegister.LOOSE_SCUTE_CAPE)) {
                    if (!p.getEntityWorld().isClient()) {
                        //                   p.getHungerManager().setExhaustion( p.getHungerManager().getExhaustion() + 8 );
                        int hunger = 2;
                        if(optional.get().isEquipped(ItemsRegister.LOOSE_SCUTE_CAPE)) {
                            if(new Random().nextBoolean()){
                                return;
                            }
                            hunger = 4;
                        }
                        hunger = p.getHungerManager().getFoodLevel() - hunger;
                        if (hunger < 0) { hunger = 0; }
                        p.getHungerManager().setFoodLevel(hunger);
                        ci.setReturnValue(true);
                    }
                }
            }
        }

    }

}
