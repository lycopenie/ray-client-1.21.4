package ray4rc.rayclient.modules.combat;

import net.minecraft.block.Blocks;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import ray4rc.rayclient.modules.Mod;

public class FastCrystal extends Mod {
    public FastCrystal() {
        super("FastCrystal", "monkey see monkey kill", Category.COMBAT);
    }

    @Override
    public void onTick() {
        if (!mc.options.useKey.isPressed()) return;

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
            if (mc.world.getBlockState(blockPos).getBlock() == Blocks.OBSIDIAN) {
                int crystalSlot = -1;
                for (int i = 0; i < 9; i++) {
                    ItemStack stack = mc.player.getInventory().getStack(i);
                    if (stack.getItem() == Items.END_CRYSTAL) {
                        crystalSlot = i;
                    }
                }
                if (crystalSlot == -1) {
                    return;
                }
                mc.player.getInventory().selectedSlot = crystalSlot;
                mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, blockHitResult);
            }
        }
        super.onTick();
    }
}
