package net.jptrzy.small.artifacts;

import dev.emi.trinkets.api.TrinketsApi;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.jptrzy.small.artifacts.blocks.CopperAltarEntityRenderer;
import net.jptrzy.small.artifacts.items.EnderSack;
import net.jptrzy.small.artifacts.network.NetworkHandler;
import net.jptrzy.small.artifacts.network.message.UseEnderSackMessage;
import net.jptrzy.small.artifacts.registry.BlockRegister;
import net.jptrzy.small.artifacts.registry.ItemsRegister;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class Client implements ClientModInitializer {

    private static KeyBinding ender_sack_key;

    @Override
    public void onInitializeClient() {

        BlockEntityRendererRegistry.INSTANCE.register(BlockRegister.COPPER_ALTAR_ENTITY, CopperAltarEntityRenderer::new);

        ender_sack_key = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key."+ Main.MOD_ID+".ender_sack",
                InputUtil.Type.KEYSYM, InputUtil.GLFW_KEY_X,
                "category."+Main.MOD_ID+".small_artifacts"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (ender_sack_key.wasPressed()) {
                if(TrinketsApi.getTrinketComponent(client.player).get().isEquipped(ItemsRegister.ENDER_POUCH)){
                    EnderSack.PlayOpenSound(client.world, client.player);
                    NetworkHandler.sendToServer(new UseEnderSackMessage());
                }
            }
        });
    }



}