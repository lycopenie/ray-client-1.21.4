package ray4rc.rayclient.modules.player;

import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;
import org.lwjgl.glfw.GLFW;
import ray4rc.rayclient.RayClientClient;
import ray4rc.rayclient.modules.Mod;

import java.util.List;

public class AutoTotem extends Mod {
    public AutoTotem() {
        super("AutoTotem", "Anti-quickdrop measures", Category.PLAYER);
    }

    @Override
    public void onTick() {
        Inventory inventory = mc.player.getInventory();
        boolean offhandEmpty = (inventory.getStack(40).isEmpty());

        if (offhandEmpty) {
            for (int i = 0; i < 9; i++) {
                ItemStack itemStack = inventory.getStack(i);
                if (itemStack.getItem() == Items.TOTEM_OF_UNDYING) {
                    mc.player.getInventory().selectedSlot = i;
                }
            }
        }

        if (mc.currentScreen instanceof InventoryScreen) {
            int syncId = ((InventoryScreen) mc.currentScreen).getScreenHandler().syncId;
            int hotbar = -1;
            for (int i = 0; i < 9; i++) {
                ItemStack itemStack = inventory.getStack(i);
                if (itemStack.getItem() == Items.TOTEM_OF_UNDYING) {
                    hotbar = -1;
                    break;
                }
                if (itemStack.isEmpty()) {
                    hotbar = i;
                }
            }



            for (int i = 9; i < 36; i++) {
                ItemStack itemStack = inventory.getStack(i);
                if (itemStack.isEmpty() || !ItemStack.areItemsAndComponentsEqual(itemStack, Items.TOTEM_OF_UNDYING.getDefaultStack()))
                    continue;

                if (offhandEmpty){
                    mc.interactionManager.clickSlot(syncId, i, 40, SlotActionType.SWAP, mc.player);
                    continue;
                }
                if (hotbar != -1) {
                    mc.interactionManager.clickSlot(syncId, i, hotbar, SlotActionType.SWAP, mc.player);
                    break;
                }
            }
        }

        super.onTick();
    }
}