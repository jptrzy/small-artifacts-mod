package net.jptrzy.small.artifacts;

import dev.emi.trinkets.api.TrinketsApi;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.jptrzy.small.artifacts.Main;
import net.jptrzy.small.artifacts.network.NetworkHandler;
import net.jptrzy.small.artifacts.network.message.UseEnderSackMessage;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class Client implements ClientModInitializer {

    private static KeyBinding ender_sack_key;

    @Override
    public void onInitializeClient() {
        ender_sack_key = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key."+ Main.MOD_ID+".ender_sack",
                InputUtil.Type.KEYSYM, InputUtil.GLFW_KEY_X,
                "category."+Main.MOD_ID+".small_artifacts"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (ender_sack_key.wasPressed()) {
                if(TrinketsApi.getTrinketComponent(client.player).get().isEquipped(ItemsRegister.ENDER_POUCH)){
                    NetworkHandler.sendToServer(new UseEnderSackMessage());
                }
            }
        });
    }



}