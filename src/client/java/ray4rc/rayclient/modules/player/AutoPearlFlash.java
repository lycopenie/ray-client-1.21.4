package ray4rc.rayclient.modules.player;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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
        double yVel = mc.player.getVelocity().y;
        Inventory inventory = mc.player.getInventory();

        float oldPitch = mc.player.getPitch();
        int oldSlot = mc.player.getInventory().selectedSlot;

        if (yVel > 0.3 && mc.player.hurtTime > 8) {
            int hotbar = -1;
            for (int i = 0; i < 9; i++) {
                ItemStack itemStack = inventory.getStack(i);
                if (itemStack.getItem() == Items.ENDER_PEARL) {
                    hotbar = i;
                    break;
                }
            }
            if (hotbar != -1) {
                mc.player.getInventory().selectedSlot = hotbar;
                mc.player.setPitch(90f);
                mc.interactionManager.interactItem(mc.player, Hand.MAIN_HAND);
                mc.player.getInventory().selectedSlot = oldSlot;
                mc.player.setPitch(oldPitch);
            }
        }

        super.onTick();

    }
}
