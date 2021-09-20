package net.jptrzy.small.artifacts.mixin;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.jptrzy.small.artifacts.ItemsRegister;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.mob.AbstractPiglinEntity;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(PiglinBrain.class)
public class PiglinBrainMixin
{
    @Shadow
    protected static void tryRevenge(AbstractPiglinEntity piglin, LivingEntity target){};

    @Shadow
    private static void runAwayFrom(PiglinEntity piglin, LivingEntity target){};

    private static PiglinEntity piglin;

    @Inject(method = "create", at = @At("HEAD"), cancellable = true)
    private void create(PiglinEntity piglin, Brain<PiglinEntity> brain, CallbackInfoReturnable<Brain<?>> ci) {
        this.piglin = piglin;
    }

    @Inject(method = "wearsGoldArmor", at = @At("HEAD"), cancellable = true)
    private void wearsGoldArmor(LivingEntity entity, CallbackInfoReturnable<Boolean> ci) {
        TrinketComponent tc = TrinketsApi.getTrinketComponent(entity).get();
        if (tc.isEquipped(ItemsRegister.STRAY_SOUL)){
            //PiglinBrain pb = (PiglinBrain) (Object) this;
            runAwayFrom(this.piglin, entity);
        }

        if (tc.isEquipped(ItemsRegister.GOLD_RING)) ci.setReturnValue(true);
    }

    @Inject(method = "consumeOffHandItem", at = @At("HEAD"), cancellable = true)
    private static void consumeOffHandItem(PiglinEntity piglin, boolean barter, CallbackInfo ci) {
        ItemStack itemStack = piglin.getStackInHand(Hand.OFF_HAND);
        if(itemStack.getItem() == ItemsRegister.GOLD_RING){
            Optional<PlayerEntity> optional = piglin.getBrain().getOptionalMemory(MemoryModuleType.NEAREST_VISIBLE_PLAYER);

            if (optional.isPresent()) {
                tryRevenge(piglin, (PlayerEntity)optional.get());

                piglin.setStackInHand(Hand.OFF_HAND, ItemStack.EMPTY);
            }

            ci.cancel();
        }
    }
}
