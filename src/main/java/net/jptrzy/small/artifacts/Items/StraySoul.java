package net.jptrzy.small.artifacts.Items;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Optional;

public class StraySoul extends ArtifactsItem {

    private final Rarity rarity;

//    private int max_using_tick = 60;
//    private int using_tick = max_using_tick;


    public StraySoul(Settings settings) {
        super(settings);
        this.rarity = Rarity.RARE;
    }

    //@Environment(EnvType.CLIENT)
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        System.out.println("STRAY SOUL SMALL USEEE");
        user.getItemCooldownManager().set(this, 100);

        if(!world.isClient()){
            MinecraftServer ms = world.getServer();
            ServerPlayerEntity sp = ms.getPlayerManager().getPlayer(user.getUuid());
            ServerWorld sw = user.getServer().getWorld(sp.getSpawnPointDimension());

            //ms.getPlayerManager().respawnPlayer(sp, false);

            BlockPos pos = sp.getSpawnPointPosition();

            Optional<Vec3d> opos = PlayerEntity.findRespawnPosition(sw, pos, sp.getSpawnAngle(), false, false);

            Vec3d finnal_spawn = new Vec3d(0,0,0);

            if(pos == null || !opos.isPresent()) {
                pos = sw.getSpawnPos();
                finnal_spawn = new Vec3d(pos.getX(), pos.getY(), pos.getZ());
            }else{
                finnal_spawn = opos.get();
            }

            sp.teleport(sw, finnal_spawn.x, finnal_spawn.y, finnal_spawn.z, sp.getSpawnAngle(), 0.5F);
            sw.playSound(null, pos, SoundEvents.BLOCK_PORTAL_TRAVEL, SoundCategory.PLAYERS, 0.4f, 1f);
        }

//        BlockPos pos = sp.getSpawnPointPosition();
//        sp.teleport(user.getServer().getWorld(sp.getSpawnPointDimension()), pos.getX(), pos.getX(), pos.getZ(), sp.getYaw(), sp.getPitch());
        //user.setPosition(user.findRespawnPosition().get());
        //return TypedActionResult.success(user.getStackInHand(hand), true);
        return TypedActionResult.pass(user.getStackInHand(hand));
    }


//    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
//        System.out.println("STRAY SOUL USE");
//        using_tick -= 1;
//    }
//
//    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
//        using_tick = max_using_tick;
//    }
//
//    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
//        System.out.println("STRAY SOUL FINISH USING");
//        return(stack);
//    }
//
//    public int getMaxUseTime(ItemStack stack) {
//        return using_tick;
//    }
//
//    public boolean hasGlint(ItemStack stack) {
//        return true;
//    }
//
//    public UseAction getUseAction(ItemStack stack) {
//        return UseAction.BLOCK;
//    }
}