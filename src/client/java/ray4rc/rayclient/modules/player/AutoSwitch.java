package ray4rc.rayclient.modules.player;

import net.minecraft.item.*;
import ray4rc.rayclient.modules.Mod;

public class AutoSwitch extends Mod {
    public AutoSwitch() {
        super("AutoSwitch", "scroll wheel just doesnt suffice.", Category.PLAYER);
    }

    public boolean SwitchToItem(Item item) {
        int slot = -1;
        for (int i = 0; i < 9; i++) {
            assert mc.player != null;
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (stack.getItem() == item)
                slot = i;
        }
        if (slot == -1)
            return false;
        else {
            mc.player.getInventory().selectedSlot = slot;
            return true;
        }
    }

    public boolean switchToTool(Class<?> toolClass) {
        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (toolClass.isInstance(stack.getItem())) {
                mc.player.getInventory().selectedSlot = i;
                return true;
            }
        }
        return false;
    }

    public boolean switchToSword() {
        return switchToTool(SwordItem.class);
    }

    public boolean switchToAxe() {
        return switchToTool(AxeItem.class);
    }

    public boolean switchToPickaxe() {
        return switchToTool(PickaxeItem.class);
    }

    public boolean switchToShovel() {
        return switchToTool(ShovelItem.class);
    }

    public boolean switchToHoe() {
        return switchToTool(HoeItem.class);
    }

    public boolean switchToRod() {
        return switchToTool(FishingRodItem.class);
    }

    public boolean switchToMace() {
        return switchToTool(MaceItem.class);
    }

}
