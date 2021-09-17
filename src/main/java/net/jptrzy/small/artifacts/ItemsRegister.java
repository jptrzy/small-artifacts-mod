package net.jptrzy.small.artifacts;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;

public class ItemsRegister {

    public static Map<String, Item> items = new HashMap<String, Item>();

    public static final Item GOLD_RING = add("gold_ring", new Item.Settings().group(ItemGroup.MISC).maxCount(1).rarity(Rarity.UNCOMMON));
    public static final Item ENDER_EYE = add("ender_eye", new Item.Settings().group(ItemGroup.MISC).maxCount(1).rarity(Rarity.UNCOMMON));

    public static void init() {

        for ( Map.Entry<String, Item> entry : items.entrySet() ) {
            Registry.register(Registry.ITEM, new Identifier(Main.MOD_ID, entry.getKey()), entry.getValue());
        }
    }

    public static Item add(String item_id, Item.Settings settings){
        Item item = new Item(settings);
        items.put(item_id, item);
        return item;
    }

}
