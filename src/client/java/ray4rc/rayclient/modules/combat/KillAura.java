package ray4rc.rayclient.modules.combat;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import org.lwjgl.glfw.GLFW;
import ray4rc.rayclient.modules.Mod;
import net.minecraft.entity.Entity;

public class KillAura extends Mod {
    public KillAura() {
        super("KillAura", "monkey see monkey kill", Category.MOVEMENT);
        this.setKey(GLFW.GLFW_KEY_R);
    }

    @Override
    public void onTick() {
        assert mc.player != null;
        Entity[] targets;
    }
}
