package net.jptrzy.small.artifacts.registry;

import net.jptrzy.small.artifacts.Main;
import net.jptrzy.small.artifacts.items.ArtifactsItem;
import net.jptrzy.small.artifacts.items.EnderSack;
import net.jptrzy.small.artifacts.items.StraySoul;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;

public class ItemsRegister {

    public static Map<String, Item> items = new HashMap<String, Item>();

    public static final Item GOLD_RING = add("gold_ring", new ArtifactsItem( new Item.Settings() ));
    public static final Item ENDER_EYE = add("ender_eye", new ArtifactsItem( new Item.Settings() ));
    public static final Item LOOSE_SCUTE_CAPE = add("loose_scute_cape", new ArtifactsItem( new Item.Settings() ));
    public static final Item SCUTE_CAPE = add("scute_cape", new ArtifactsItem( new Item.Settings() ));
    public static final Item STRAY_SOUL = add("stray_soul", new StraySoul( new Item.Settings() ));
    //public static final Item FISH_EYE_DROPS = add("fish_eye_drops", new ArtifactsItem( new Item.Settings() ));
    //public static final Item FRIENDLY_MIMIC = add("friendly_mimic", new ArtifactsItem( new Item.Settings() ));
    //public static final Item FRIENDLY_VEX = add("friendly_vex", new ArtifactsItem( new Item.Settings() ));
    public static final Item ENDER_POUCH = add("ender_sack", new EnderSack( new Item.Settings() ));


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
