package net.jptrzy.small.artifacts.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.jptrzy.small.artifacts.Main;
import net.jptrzy.small.artifacts.blocks.CopperAltarBlock;
import net.jptrzy.small.artifacts.blocks.CopperAltarEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;

public class BlockRegister {


    public static Map<String, Block> blocks = new HashMap<String, Block>();

    public static final Block COPPER_ALTAR = add("copper_altar", new CopperAltarBlock(FabricBlockSettings.of(Material.METAL).strength(4.0f)) );

    //public static final Block NETHERITE_SHULKERBOX_BLOCK = add("netherite_shulkerbox", new ShulkerBoxBlock(null, FabricBlockSettings.of(Material.METAL).strength(4.0f)));

    public static BlockEntityType<CopperAltarEntity> COPPER_ALTAR_ENTITY;

    public static void init() {
        COPPER_ALTAR_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, Main.MOD_ID + ":copper_altar_entity", FabricBlockEntityTypeBuilder.create(CopperAltarEntity::new, COPPER_ALTAR).build(null));

        for ( Map.Entry<String, Block> entry : blocks.entrySet() ) {
            Registry.register(Registry.BLOCK, new Identifier(Main.MOD_ID, entry.getKey()), entry.getValue());
            Registry.register(Registry.ITEM, new Identifier(Main.MOD_ID, entry.getKey()), new BlockItem(entry.getValue(), new FabricItemSettings().group(ItemGroup.DECORATIONS)));
        }
    }

    public static Block add(String key, Block block){
        blocks.put(key, block);
        return block;
    }

}
