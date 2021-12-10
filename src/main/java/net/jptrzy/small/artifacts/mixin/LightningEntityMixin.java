package net.jptrzy.small.artifacts.mixin;

import net.jptrzy.small.artifacts.Main;
import net.jptrzy.small.artifacts.blocks.CopperAltarEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LightningEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(LightningEntity.class)
public class LightningEntityMixin {

    @Inject(method = "cleanOxidationAround(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)Ljava/util/Optional;", at = @At("RETURN"))
    private static void cleanOxidationAround(World world, BlockPos pos, CallbackInfoReturnable<Optional<BlockPos>> ci){
        if(ci.getReturnValue().isPresent()){
            BlockEntity entity =  world.getBlockEntity(ci.getReturnValue().get());
            if(entity instanceof CopperAltarEntity){
                // Runs multiple times
                ((CopperAltarEntity) entity).onElectrocution();
            }
        }
    }
}
