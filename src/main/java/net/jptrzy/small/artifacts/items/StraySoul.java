package net.jptrzy.small.artifacts.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Optional;

public class StraySoul extends ArtifactsItem {

    public StraySoul(Settings settings) {
        super(settings.rarity(Rarity.RARE));
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.getItemCooldownManager().set(this, 60);

        if(!world.isClient()){
            MinecraftServer server = world.getServer();
            ServerPlayerEntity sPlayer = server.getPlayerManager().getPlayer(user.getUuid());

            ServerWorld sWorld = server.getWorld(sPlayer.getSpawnPointDimension());
            BlockPos player_spawn = sPlayer.getSpawnPointPosition();

            Optional<Vec3d> optional = Optional.empty();

            if(player_spawn != null) { // With this check works faster, because differently findRespawnPosition use repeat itself
                // Check if player_spawn exist
                optional = PlayerEntity.findRespawnPosition(sWorld, player_spawn, sPlayer.getSpawnAngle(), false, true);
            }

            if(optional.isPresent()){
                player_spawn = new BlockPos(optional.get());
            }else{
                sWorld = server.getOverworld();
                player_spawn = sWorld.getSpawnPos();
            }

            sPlayer.teleport(sWorld, player_spawn.getX(), player_spawn.getY(), player_spawn.getZ(), sPlayer.getSpawnAngle(), 0.5F);
            sWorld.playSound(null, player_spawn, SoundEvents.BLOCK_PORTAL_TRAVEL, SoundCategory.PLAYERS, 0.4f, 1f);
        }

        return TypedActionResult.pass(user.getStackInHand(hand));
    }

    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        System.out.println("ENTITY USE");

        user.getItemCooldownManager().set(this, 60);

        if(entity instanceof PiglinEntity){
            //PiglinBrain.runAwayFrom
            entity.getBrain().forget(MemoryModuleType.ANGRY_AT);
            entity.getBrain().forget(MemoryModuleType.ATTACK_TARGET);
            entity.getBrain().forget(MemoryModuleType.WALK_TARGET);
            entity.getBrain().remember(MemoryModuleType.AVOID_TARGET, user, (long)TimeHelper.betweenSeconds(5, 20).get(entity.world.random));
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }
}