package ray4rc.rayclient.modules.combat;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import ray4rc.rayclient.modules.Mod;
import ray4rc.rayclient.modules.ModuleManager;
import ray4rc.rayclient.modules.player.AutoSwitch;

public class AttributeSwap extends Mod {
    public AttributeSwap() {
        super("AttributeSwap", "cooldown trick", Category.COMBAT);
    }

    boolean previousState = false;
    int slot = 0;
    boolean hasHit = false;

    @Override
    public void onTick() {
        AutoSwitch autoSwitch = ModuleManager.INSTANCE.getModule(AutoSwitch.class);

        if (mc.options.attackKey.isPressed() && !previousState) {
            if (mc.crosshairTarget instanceof EntityHitResult eResult) {
                Entity target = eResult.getEntity();
                boolean blocking;
                if (target instanceof PlayerEntity) {
                    blocking = ((PlayerEntity) target).isBlocking();
                } else {
                    blocking = false;
                }

                slot = mc.player.getInventory().selectedSlot;
                if (blocking) {
                    hasHit = autoSwitch.switchToAxe();
                } else {
                    hasHit = autoSwitch.switchToMace();
                }
            }
        } else if (hasHit) {
            mc.player.getInventory().selectedSlot = slot;
            hasHit = false;
        }

        previousState = mc.options.attackKey.isPressed();
        super.onTick();
    }
}
