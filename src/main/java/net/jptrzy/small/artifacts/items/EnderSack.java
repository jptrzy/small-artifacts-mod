package net.jptrzy.small.artifacts.items;

import net.jptrzy.small.artifacts.Main;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EnderChestInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.*;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

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