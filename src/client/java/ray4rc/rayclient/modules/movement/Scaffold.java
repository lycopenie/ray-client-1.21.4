package ray4rc.rayclient.modules.movement;

import org.lwjgl.glfw.GLFW;
import ray4rc.rayclient.modules.Mod;

public class Scaffold extends Mod {
    public Scaffold() {
        super("Scaffold", "Zoomer nation.", Category.MOVEMENT);
        this.setKey(GLFW.GLFW_KEY_C);
    }

    @Override
    public void onTick() {
        assert mc.player != null;
        
        super.onTick();
    }
}
