package net.jptrzy.small.artifacts.Blocks;

import net.jptrzy.small.artifacts.BlockRegister;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;



public class CopperAltarBlock extends Block implements BlockEntityProvider {

    public CopperAltarBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CopperAltarEntity(pos, state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
       return CopperAltarEntity.onUse(state, world, pos, player, hand, hit);
    }

    @Nullable
    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> checkType(BlockEntityType<A> givenType, BlockEntityType<E> expectedType, BlockEntityTicker<? super E> ticker) {
        return expectedType == givenType ? (BlockEntityTicker<A>) ticker : null;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, BlockRegister.COPPER_ALTAR_ENTITY, (world1, pos, state1, be) -> CopperAltarEntity.tick(world1, pos, state1, be));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        return VoxelShapes.cuboid(0f, 0f, 0f, 1f, 0.7f, 1f);
    }
}
