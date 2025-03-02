package ray4rc.rayclient.modules.combat;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.Entity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import org.lwjgl.glfw.GLFW;
import ray4rc.rayclient.modules.Mod;

public class TriggerBot extends Mod {
    public TriggerBot() {
       super("TriggerBot", "solista reference", Category.COMBAT);
       this.setKey(GLFW.GLFW_NO_API);
    }

    @Override
    public void onTick() {
        if (mc.currentScreen instanceof HandledScreen)
            return;
        if (!(mc.crosshairTarget instanceof EntityHitResult eResult))
            return;

        if (mc.player.getAttackCooldownProgress(0.0f) <= 0.9f) { // full cooldown
            return;
        }

        if (mc.player.getVelocity().y >= -0.08f || mc.player.isOnGround()) { // criticals only
            return;
        }

        Entity target = eResult.getEntity();

        mc.interactionManager.attackEntity(mc.player, target);
        mc.player.swingHand(Hand.MAIN_HAND);

        super.onTick();

    }
}
