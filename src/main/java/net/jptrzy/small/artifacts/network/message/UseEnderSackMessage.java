package net.jptrzy.small.artifacts.network.message;

import dev.emi.trinkets.api.TrinketsApi;
import net.fabricmc.fabric.api.network.PacketContext;
import net.jptrzy.small.artifacts.registry.ItemsRegister;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public class UseEnderSackMessage {
    public static UseEnderSackMessage read(PacketByteBuf buffer) {
        return new UseEnderSackMessage();
    }

    public static void write(UseEnderSackMessage message, PacketByteBuf buffer) {

    }

    public static void onMessage(UseEnderSackMessage message, PacketContext context) {
        context.getTaskQueue().execute(() -> {
            ServerPlayerEntity player = (ServerPlayerEntity) context.getPlayer();
            if (player != null) {
                if(TrinketsApi.getTrinketComponent(player).get().isEquipped(ItemsRegister.ENDER_POUCH)) {
                    ItemsRegister.ENDER_POUCH.use(player.world, player, null);
                }
            }
        });
    }
}
