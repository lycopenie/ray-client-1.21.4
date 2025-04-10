package ray4rc.rayclient.modules.combat;

import net.minecraft.block.Blocks;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import ray4rc.rayclient.RayClientClient;
import ray4rc.rayclient.modules.Mod;
import ray4rc.rayclient.modules.settings.NumberSetting;
import ray4rc.rayclient.ui.screens.clickgui.setting.Slider;

import java.util.Set;

public class FastCrystal extends Mod {
    public NumberSetting speed = new NumberSetting("Speed", 1, 20, 1, 1);

    public FastCrystal() {
        super("FastCrystal", "monkey see monkey kill", Category.COMBAT);
        addSettings(speed);
    }

    Set<Item> whitelistedItems = Set.of(Items.OBSIDIAN, Items.END_CRYSTAL, Items.NETHERITE_SWORD, Items.TOTEM_OF_UNDYING);

    boolean previousState = false;
    int oldSlot = -1;


    int ticksSinceEnabled = 0;

    @Override
    public void onTick() {
        if (mc.options.useKey.isPressed()) {
            if (!previousState)
                oldSlot = mc.player.getInventory().selectedSlot;
            ItemStack currentStack = mc.player.getInventory().getMainHandStack();
            if (!whitelistedItems.contains(currentStack.getItem())) return;

            if (mc.currentScreen instanceof HandledScreen) return;
            HitResult hitResult = mc.crosshairTarget;
            if (hitResult instanceof EntityHitResult eResult) {
                Entity target = eResult.getEntity();
                if (target instanceof EndCrystalEntity && ticksSinceEnabled % speed.getValueInt() == 0) {
                    mc.interactionManager.attackEntity(mc.player, target);
                    mc.player.swingHand(Hand.MAIN_HAND);
                }
            } else if (hitResult.getType() == HitResult.Type.BLOCK) {
                BlockHitResult blockHitResult = (BlockHitResult) hitResult;
                BlockPos blockPos = blockHitResult.getBlockPos();

                int crystalSlot = -1;
                int obsidianSlot = -1;
                for (int i = 0; i < 9; i++) {
                    ItemStack stack = mc.player.getInventory().getStack(i);
                    if (stack.getItem() == Items.END_CRYSTAL)
                        crystalSlot = i;
                    else if (stack.getItem() == Items.OBSIDIAN)
                        obsidianSlot = i;
                }

                if (mc.world.getBlockState(blockPos).getBlock() != Blocks.OBSIDIAN) {
                    mc.player.getInventory().selectedSlot = obsidianSlot;
                    mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, blockHitResult);
                }
                if (crystalSlot == -1) {
                    return;
                }
                mc.player.getInventory().selectedSlot = crystalSlot;
                mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, blockHitResult);

            }
            ticksSinceEnabled += 1;
        }
        else if (previousState && !mc.options.useKey.isPressed()) {
            mc.player.getInventory().selectedSlot = oldSlot;
            ticksSinceEnabled = 0;
            
            HitResult hitResult = mc.crosshairTarget;
            if (hitResult instanceof EntityHitResult eResult) {
                Entity target = eResult.getEntity();
                if (target instanceof EndCrystalEntity && ticksSinceEnabled % speed.getValueInt() == 0) {
                    mc.interactionManager.attackEntity(mc.player, target);
                    mc.player.swingHand(Hand.MAIN_HAND);
                }
            }
        }
        super.onTick();
        previousState = mc.options.useKey.isPressed();
    }
}
