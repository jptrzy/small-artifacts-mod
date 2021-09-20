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
}