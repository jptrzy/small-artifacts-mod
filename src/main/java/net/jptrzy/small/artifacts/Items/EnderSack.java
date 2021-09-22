package net.jptrzy.small.artifacts.Items;

import net.jptrzy.small.artifacts.Main;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.EnderChestBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EnderChestInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class EnderSack extends ArtifactsItem {

    static final Text CONTAINER_NAME = new TranslatableText("container." + Main.MOD_ID + ".ender_sack");

    public EnderSack(Settings settings) {
        super(settings.rarity(Rarity.RARE));
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, @Nullable Hand hand) {
        EnderChestInventory enderChestInventory = player.getEnderChestInventory();

        if(!world.isClient()){
            player.openHandledScreen(new SimpleNamedScreenHandlerFactory((syncId, inventory, playerx) -> {
                return GenericContainerScreenHandler.createGeneric9x3(syncId, inventory, enderChestInventory);
            }, CONTAINER_NAME));
            player.incrementStat(Stats.OPEN_ENDERCHEST);
        }

        return TypedActionResult.success(player.getStackInHand(hand), true);
    }
}