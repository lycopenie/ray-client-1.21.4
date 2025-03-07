package ray4rc.rayclient.modules.player;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.Entity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import org.lwjgl.glfw.GLFW;
import ray4rc.rayclient.RayClientClient;
import ray4rc.rayclient.modules.Mod;
import ray4rc.rayclient.modules.settings.BooleanSetting;
import ray4rc.rayclient.modules.settings.NumberSetting;

public class AutoPearlFlash extends Mod {
    public AutoPearlFlash() {
        super("AutoPearlFlash", "admin hes using autokb!!!", Category.PLAYER);
    }

    @Override
    public void onTick() {
        RayClientClient.LOGGER.info(String.valueOf(mc.player.getVelocity().y));
        super.onTick();
    }
}
