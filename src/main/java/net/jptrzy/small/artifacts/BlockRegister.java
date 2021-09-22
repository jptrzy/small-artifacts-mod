package net.jptrzy.small.artifacts;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.jptrzy.small.artifacts.Blocks.CopperAltarBlock;
import net.jptrzy.small.artifacts.Blocks.CopperAltarEntity;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
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
    public static BlockEntityType<CopperAltarEntity> COPPER_ALTAR_ENTITY;

    public static void init() {
        COPPER_ALTAR_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, Main.MOD_ID + ":copper_altar_entity", FabricBlockEntityTypeBuilder.create(CopperAltarEntity::new, COPPER_ALTAR).build(null));

        for ( Map.Entry<String, Block> entry : blocks.entrySet() ) {
            Registry.register(Registry.BLOCK, new Identifier(Main.MOD_ID, entry.getKey()), entry.getValue());
            Registry.register(Registry.ITEM, new Identifier(Main.MOD_ID, entry.getKey()), new BlockItem(entry.getValue(), new FabricItemSettings().group(ItemGroup.MISC)));
        }
    }

    public static Block add(String key, Block block){
        blocks.put(key, block);
        return block;
    }

}