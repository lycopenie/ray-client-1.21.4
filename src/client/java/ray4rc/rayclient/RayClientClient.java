package ray4rc.rayclient;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ray4rc.rayclient.modules.Mod;
import ray4rc.rayclient.modules.ModuleManager;
import ray4rc.rayclient.ui.screens.clickgui.ClickGUI;

public class RayClientClient implements ClientModInitializer {

	public static final RayClientClient INSTANCE = new RayClientClient();

	public static final String MOD_ID = "ray-client";

	private MinecraftClient mc = MinecraftClient.getInstance();

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.


	}

	public void onKeyPress(int key, int action) {
		if (action == GLFW.GLFW_PRESS) {
			for (Mod module: ModuleManager.INSTANCE.getModules()) {
				if (key == module.getKey()) module.toggle();
			}

			if (key == GLFW.GLFW_KEY_RIGHT_SHIFT) mc.setScreen(ClickGUI.INSTANCE);
		}
	}

	public void onTick() {
		if (mc.player != null) {
			for (Mod module: ModuleManager.INSTANCE.getEnabledModules()) {
				module.onTick();
			}
		}
	}

	public void onRender() {
		if (mc.player != null) {
			for (Mod module: ModuleManager.INSTANCE.getEnabledModules()) {
				module.onRender();
			}
		}
	}
}