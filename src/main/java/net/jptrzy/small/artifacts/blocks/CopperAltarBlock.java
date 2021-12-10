package net.jptrzy.small.artifacts.blocks;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;



public class CopperAltarBlock extends OxidizableBlock implements BlockEntityProvider, Waterloggable {

    public static final BooleanProperty WATERLOGGED;

    public final static VoxelShape COLLISION_SHAPE;
    public final static VoxelShape BOTTOM_SHAPE;

    public final static VoxelShape BASE_SHAPE;
    public final static VoxelShape MIDDLE_SHAPE;
    public final static VoxelShape TOP_SHAPE;

    public CopperAltarBlock(Settings settings) {
        super(OxidationLevel.UNAFFECTED, settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CopperAltarEntity(pos, state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
       return ((CopperAltarEntity) world.getBlockEntity(pos)).onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        ((CopperAltarEntity) world.getBlockEntity(pos)).onBreak(state, world, pos, player);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        return COLLISION_SHAPE;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{WATERLOGGED});
    }

    public FluidState getFluidState(BlockState state) {
        return (Boolean)state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        boolean water = fluidState.getFluid() == Fluids.WATER;
        return (BlockState)((BlockState)this.getDefaultState()).with(WATERLOGGED, water);
    }

    static{
        BASE_SHAPE = Block.createCuboidShape(2, 0, 2, 14, 3, 14);
        MIDDLE_SHAPE = Block.createCuboidShape(3, 3, 3, 13, 11, 13);
        TOP_SHAPE = Block.createCuboidShape(2, 11, 2, 14, 13, 14);

        BOTTOM_SHAPE = VoxelShapes.union(BASE_SHAPE, MIDDLE_SHAPE);
        COLLISION_SHAPE = VoxelShapes.union(BOTTOM_SHAPE, TOP_SHAPE);

        WATERLOGGED = Properties.WATERLOGGED;
    }
}
