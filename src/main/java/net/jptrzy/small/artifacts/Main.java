package net.jptrzy.small.artifacts;

import net.fabricmc.api.ModInitializer;

public class Main implements ModInitializer {

	public static final String MOD_ID = "small_artifacts";

	@Override
	public void onInitialize() {
		System.out.println();

		ItemsRegister.init();
	}
}
