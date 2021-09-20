package net.jptrzy.small.artifacts;

import net.jptrzy.small.artifacts.Items.ArtifactsItem;
import net.jptrzy.small.artifacts.Items.StraySoul;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class ItemsRegister {

    public static Map<String, Item> items = new HashMap<String, Item>();

    public static final Item GOLD_RING = add("gold_ring", new ArtifactsItem( new Item.Settings() ));
    public static final Item ENDER_EYE = add("ender_eye", new ArtifactsItem( new Item.Settings() ));
    public static final Item SCUTE_CAPE = add("scute_cape", new ArtifactsItem( new Item.Settings() ));
    public static final Item STRAY_SOUL = add("stray_soul", new StraySoul( new Item.Settings() ));

    public TypedActionResult<ItemStack> USE_STRAY_SOUL(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        return TypedActionResult.pass(user.getStackInHand(hand));
    }

    public static void init() {
        for ( Map.Entry<String, Item> entry : items.entrySet() ) {
            Registry.register(Registry.ITEM, new Identifier(Main.MOD_ID, entry.getKey()), entry.getValue());
        }
    }

    public static Item add(String key, Item item){
        items.put(key, item);
        return item;
    }

}
