package net.jptrzy.small.artifacts.network;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.jptrzy.small.artifacts.Main;
import net.jptrzy.small.artifacts.network.message.UseEnderSackMessage;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class NetworkHandler {

    public static final Identifier PACKET_ID = new Identifier(Main.MOD_ID, Main.MOD_ID);
    private static int id = 0;

    public static void init() {
        ServerSidePacketRegistry.INSTANCE.register(PACKET_ID, (packetContext, packetByteBuf) -> {
            int id = packetByteBuf.readInt();
            switch (id) {
                case 0: {
                    UseEnderSackMessage.onMessage(UseEnderSackMessage.read(packetByteBuf), packetContext);
                    break;
                }
            }
        });
    }

    @Environment(EnvType.CLIENT)
    public static void sendToServer(UseEnderSackMessage message) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeInt(0);
        UseEnderSackMessage.write(message, buf);
        ClientSidePacketRegistry.INSTANCE.sendToServer(PACKET_ID, buf);
    }
}
