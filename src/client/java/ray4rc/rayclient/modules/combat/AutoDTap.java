package ray4rc.rayclient.modules.combat;

import org.lwjgl.glfw.GLFW;
import ray4rc.rayclient.modules.Mod;
import ray4rc.rayclient.modules.settings.BooleanSetting;
import ray4rc.rayclient.modules.settings.NumberSetting;

import javax.swing.text.html.parser.Entity;

public class AutoDTap extends Mod {
    public AutoDTap() {
        super("AutoDTap", "monkey see monkey kill", Category.COMBAT);
        this.setKey(GLFW.GLFW_KEY_R);
    }

    public BooleanSetting autoHit = new BooleanSetting("AutoHit", false);
    public NumberSetting range = new NumberSetting("Range", 0, 5, 5, 0.1);

    @Override
    public void onTick() {
        super.onTick();
    }
}
