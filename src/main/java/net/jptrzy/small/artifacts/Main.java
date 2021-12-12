package net.jptrzy.small.artifacts;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.jptrzy.small.artifacts.network.NetworkHandler;
import net.jptrzy.small.artifacts.registry.BlockRegister;
import net.jptrzy.small.artifacts.registry.ItemsRegister;
import net.jptrzy.small.artifacts.registry.TriggerRegister;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;

public class Main implements ModInitializer {

	public static final String MOD_ID = "small_artifacts";

	public static final Logger LOGGER = LogManager.getFormatterLogger(MOD_ID);

	public static final boolean DEBUG = false;

	public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(
			id("general"),
			() -> new ItemStack(ItemsRegister.GOLD_RING));

	@Override
	public void onInitialize() {

		ItemsRegister.init();
		BlockRegister.init();
		NetworkHandler.init();
		TriggerRegister.init();

		LOGGER.info("Initialize" + LOGGER.isDebugEnabled());
	}

	public static void debug(String text){
		if(DEBUG) LOGGER.warn(text);
	}

	public static Identifier id(String path) {
		return new Identifier(MOD_ID, path);
	}
}
