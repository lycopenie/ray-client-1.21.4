package ray4rc.rayclient.modules.combat;

import org.lwjgl.glfw.GLFW;
import ray4rc.rayclient.modules.Mod;

public class AutoDTap extends Mod {
    public AutoDTap() {
        super("AutoDTap", "monkey see monkey kill", Category.COMBAT);
        this.setKey(GLFW.GLFW_KEY_R);
    }
}
