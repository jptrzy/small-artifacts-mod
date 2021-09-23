package net.jptrzy.small.artifacts.blocks;

import net.jptrzy.small.artifacts.registry.BlockRegister;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
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

    public final static VoxelShape COLLISION_SHAPE;
    public final static VoxelShape BOTTOM_SHAPE;

    public final static VoxelShape BASE_SHAPE;
    public final static VoxelShape MIDDLE_SHAPE;
    public final static VoxelShape TOP_SHAPE;

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CopperAltarEntity(pos, state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
       return ((CopperAltarEntity) world.getBlockEntity(pos)).onUse(state, world, pos, player, hand, hit);
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
        return COLLISION_SHAPE;
    }

    static{
        BASE_SHAPE = Block.createCuboidShape(2, 0, 2, 14, 3, 14);
        MIDDLE_SHAPE = Block.createCuboidShape(3, 3, 3, 13, 11, 13);
        TOP_SHAPE = Block.createCuboidShape(2, 11, 2, 14, 13, 14);

        BOTTOM_SHAPE = VoxelShapes.union(BASE_SHAPE, MIDDLE_SHAPE);
        COLLISION_SHAPE = VoxelShapes.union(BOTTOM_SHAPE, TOP_SHAPE);
    }
}
