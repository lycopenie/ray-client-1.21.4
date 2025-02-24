package ray4rc.rayclient.modules.movement;

import org.lwjgl.glfw.GLFW;
import ray4rc.rayclient.modules.Mod;

public class Sprint extends Mod {
    public Sprint() {
        super("Sprint", "Allows flying.", Category.MOVEMENT);
        this.setKey(GLFW.GLFW_KEY_G);
    }

    @Override
    public void onTick() {
        assert mc.player != null;
        mc.player.setSprinting(true);
        super.onTick();
    }
}
