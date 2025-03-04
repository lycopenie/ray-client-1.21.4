package ray4rc.rayclient.modules.combat;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;
import ray4rc.rayclient.modules.Mod;

public class FastCrystal extends Mod {
    public FastCrystal() {
        super("FastCrystal", "monkey see monkey kill", Category.COMBAT);
    }

    @Override
    public void onTick() {
        if (mc.currentScreen instanceof HandledScreen)
            return;
        HitResult hitResult = mc.crosshairTarget;
        if (!(hitResult instanceof EntityHitResult eResult))
            return;
        Entity target = eResult.getEntity();

        if (!(target instanceof EndCrystalEntity))
            return;
        mc.interactionManager.attackEntity(mc.player, target);
        mc.player.swingHand(Hand.MAIN_HAND);
        super.onTick();
    }
}
