package net.jptrzy.small.artifacts.blocks;

import net.jptrzy.small.artifacts.Main;
import net.jptrzy.small.artifacts.registry.BlockRegister;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CopperAltarEntity extends BlockEntity {

    private ItemStack item = new ItemStack(Items.AIR);
    private boolean crafting = false;

    public CopperAltarEntity(BlockPos pos, BlockState state) {
        super(BlockRegister.COPPER_ALTAR_ENTITY, pos, state);
    }

    public void onElectrocution(){
        Main.LOGGER.warn("Altar just works ;D.");
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            ItemStack itemStack = player.getMainHandStack();
            ItemStack clone;

            if(this.item.isEmpty() && !itemStack.isEmpty()) {
                clone = itemStack.copy();
                clone.setCount(1);

                if (!player.getAbilities().creativeMode) {
                    itemStack.decrement(1);
                }

                this.item = clone;
            }
            this.notifyListeners();
        }

        return ActionResult.SUCCESS;
    }

    public void onBreak(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        if (!world.isClient) {
            if(!this.item.isEmpty()){
                Block.dropStack(world, pos.up(1), this.item);
                this.item = new ItemStack(Items.AIR);
                this.notifyListeners();
            }
        }
    }

    public static void tick(World world, BlockPos pos, BlockState state, CopperAltarEntity be) { }

    @Override
    public void writeNbt(NbtCompound tag) {
        super.writeNbt(tag);

        tag.putBoolean("Crafing", this.crafting);
        tag.put("Item", this.item.writeNbt(new NbtCompound()));
    }

    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);

        this.crafting = tag.getBoolean("Crafing");
        this.item = ItemStack.fromNbt(tag.getCompound("Item"));
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        NbtCompound tag = super.toInitialChunkDataNbt();
        writeNbt(tag);
        return tag;
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    public void notifyListeners() {
        this.markDirty();

        if(world != null && !world.isClient())
            world.updateListeners(getPos(), getCachedState(), getCachedState(), Block.NOTIFY_ALL);
    }

    public ItemStack getItem(){return this.item;}
}