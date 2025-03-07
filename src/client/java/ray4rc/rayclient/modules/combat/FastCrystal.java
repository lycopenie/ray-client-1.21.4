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

import java.util.Set;

public class FastCrystal extends Mod {
    public FastCrystal() {
        super("FastCrystal", "monkey see monkey kill", Category.COMBAT);
    }

    Set<Item> whitelistedItems = Set.of(Items.OBSIDIAN, Items.END_CRYSTAL, Items.NETHERITE_SWORD, Items.TOTEM_OF_UNDYING);

    boolean previousState = false;
    int oldSlot = -1;

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
                if (target instanceof EndCrystalEntity) {
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
        }
        else if (previousState && !mc.options.useKey.isPressed()) {
            mc.player.getInventory().selectedSlot = oldSlot;
        }
        super.onTick();
        previousState = mc.options.useKey.isPressed();
    }
}
