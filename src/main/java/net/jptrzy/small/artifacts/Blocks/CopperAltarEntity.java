package net.jptrzy.small.artifacts.Blocks;

import net.jptrzy.small.artifacts.BlockRegister;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class CopperAltarEntity extends BlockEntity {

    private boolean crafting = false;

    public CopperAltarEntity(BlockPos pos, BlockState state) {
        super(BlockRegister.COPPER_ALTAR_ENTITY, pos, state);
    }

    // Serialize the BlockEntity
    @Override
    public NbtCompound writeNbt(NbtCompound tag) {
        super.writeNbt(tag);

        // Save the current value of the number to the tag
        tag.putBoolean("crafing", crafting);

        return tag;
    }

    // Deserialize the BlockEntity
    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
        crafting = tag.getBoolean("crafing");
    }

    public static ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            player.sendMessage(new LiteralText("Hello, world!"), false);
        }

        return ActionResult.SUCCESS;
    }

    public static void tick(World world, BlockPos pos, BlockState state, CopperAltarEntity be) {
    }

}
