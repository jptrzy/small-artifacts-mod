package net.jptrzy.small.artifacts;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.jptrzy.small.artifacts.network.NetworkHandler;
import net.jptrzy.small.artifacts.registry.BlockRegister;
import net.jptrzy.small.artifacts.registry.ItemsRegister;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
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

	@Override
	public void onInitialize() {
		//DON'T WORK
		if(DEBUG){ setLevel(LOGGER, Level.DEBUG); }

		ItemsRegister.init();
		BlockRegister.init();

		NetworkHandler.init();

//		LOGGER.debug("DEBUG FUCK DEBUG");
//		LOGGER.error("ERROR");
//		LOGGER.warn("WARN");

		LOGGER.info("Initialize" + LOGGER.isDebugEnabled());
	}

	public static void debug(String text){
		if(DEBUG) LOGGER.warn(text);
	}

	public static void setLevel(Logger logger, Level level) {
		final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		final Configuration config = ctx.getConfiguration();

		LoggerConfig loggerConfig = config.getLoggerConfig(logger.getName());
		LoggerConfig specificConfig = loggerConfig;

		if (!loggerConfig.getName().equals(logger.getName())) {
			specificConfig = new LoggerConfig(logger.getName(), level, true);
			specificConfig.setParent(loggerConfig);
			config.addLogger(logger.getName(), specificConfig);
		}
		specificConfig.setLevel(level);
		ctx.updateLoggers();
	}


}
