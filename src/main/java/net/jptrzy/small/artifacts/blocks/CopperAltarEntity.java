package net.jptrzy.small.artifacts.blocks;

import net.jptrzy.small.artifacts.Main;
import net.jptrzy.small.artifacts.registry.BlockRegister;
import net.jptrzy.small.artifacts.registry.ItemsRegister;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.util.ParticleUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class CopperAltarEntity extends BlockEntity {

    private ItemStack item = new ItemStack(Items.AIR);
    private int crafting = 0;

    public CopperAltarEntity(BlockPos pos, BlockState state) {
        super(BlockRegister.COPPER_ALTAR_ENTITY, pos, state);
    }

    public void onElectrocution(){
        Main.LOGGER.warn("Altar just works ;D.");
        if(!this.item.isEmpty() && isCraftable(this.item)) {
            this.crafting = 60;
            world.playSound(null, this.getPos(), SoundEvents.ENTITY_GUARDIAN_ATTACK, SoundCategory.BLOCKS, .9f, .6f);
            this.notifyListeners();
        }
    }

    public void onCrafted(){
        if(this.item.isOf(ItemsRegister.LOOSE_SCUTE_CAPE)) {
            this.item = new ItemStack(ItemsRegister.SCUTE_CAPE);
        }
    }

    public static boolean isCraftable(ItemStack item){
        if(item.isOf(ItemsRegister.LOOSE_SCUTE_CAPE)){
            return true;
        }
        return false;
    }

    public static void tick(World world, BlockPos pos, BlockState state, CopperAltarEntity entity) {
        if(entity.crafting != 0){
            entity.crafting -= 1;

            if(entity.crafting > 14){
                Random r = new Random();
                world.addParticle(ParticleTypes.ENCHANT, (double) pos.getX() + 0.5D, (double) pos.getY() + 1.2D, (double) pos.getZ() + 0.5D,
                        (r.nextBoolean() ? -1 : 1) * r.nextFloat(),
                        1,
                        (r.nextBoolean() ? -1 : 1) * r.nextFloat());
            }

            if(entity.crafting <= 0 && !world.isClient()){
                entity.onCrafted();
                world.playSound(null, pos, SoundEvents.ENTITY_EVOKER_PREPARE_SUMMON, SoundCategory.BLOCKS, .8f, .8f);
                entity.crafting = 0;
                entity.notifyListeners();
            }
        }
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
            if(!this.item.isEmpty() && crafting == 0){
                Block.dropStack(world, pos.up(1), this.item);
                this.item = new ItemStack(Items.AIR);
                this.notifyListeners();
            }
        }
    }

    @Override
    public void writeNbt(NbtCompound tag) {
        super.writeNbt(tag);

        tag.putInt("Crafing", this.crafting);
        tag.put("Item", this.item.writeNbt(new NbtCompound()));
    }

    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);

        this.crafting = tag.getInt("Crafing");
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