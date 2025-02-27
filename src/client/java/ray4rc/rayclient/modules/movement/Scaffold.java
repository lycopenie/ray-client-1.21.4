package ray4rc.rayclient.modules.movement;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.Vector2f;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.*;
import org.joml.Vector2d;
import org.joml.Vector2i;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ray4rc.rayclient.modules.Mod;

import java.util.Arrays;

public class Scaffold extends Mod {
    public Scaffold() {
        super("Scaffold", "Zoomer nation.", Category.MOVEMENT);
        this.setKey(GLFW.GLFW_KEY_C);
    }

    @Override
    public void onTick() {
        assert mc.player != null;
        assert mc.world != null;

        Vec3d playerPos = mc.player.getPos();
        Vec3i playerBLockPos = new Vec3i((int) playerPos.x, (int) playerPos.y, (int) playerPos.z);
        BlockPos belowPlayer = new BlockPos(playerBLockPos.getX(), playerBLockPos.getY() - 1, playerBLockPos.getZ());
        BlockState blockBelow = mc.world.getBlockState(belowPlayer);

        if (!blockBelow.isReplaceable()) return;

        if (placeBlock(belowPlayer)) return;
        Direction[] sides = Direction.values();
        for(Direction side : sides)
        {
            BlockPos neighbor = belowPlayer.offset(side);
            if(placeBlock(neighbor))
                return;
        }

        // next to the block that's under the player
        for(Direction side : sides) {
            for(Direction side2 : Arrays.copyOfRange(sides, side.ordinal(), 6))
            {
                if(side.getOpposite().equals(side2))
                    continue;

                BlockPos neighbor = belowPlayer.offset(side).offset(side2);
                if(placeBlock(neighbor))
                    return;
            }
        }
        super.onTick();
    }



    private boolean placeBlock(BlockPos blockPos) {
        Vec3d eyesPos = mc.player.getEyePos();

        for (Direction side : Direction.values()) {
            BlockPos neighbor = blockPos.offset(side);
            Direction side2 = side.getOpposite();

            if (mc.world.getBlockState(neighbor).isAir()) continue;

            if (eyesPos.squaredDistanceTo(Vec3d.ofCenter(blockPos)) >= eyesPos.squaredDistanceTo(Vec3d.ofCenter(neighbor))) continue;
            Vec3d hitVec = Vec3d.ofCenter(neighbor).add(Vec3d.of(side2.getVector()).multiply(0.5));
            if(eyesPos.squaredDistanceTo(hitVec) > 18.0625) continue;

            Vector2f rotation = getNeededRotations(hitVec);
            float yaw = rotation.getX();
            float pitch = rotation.getY();

            mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.LookAndOnGround(yaw, pitch, mc.player.isOnGround(), mc.player.horizontalCollision));

            mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, new BlockHitResult(hitVec, side2, blockPos, false));
            return true;
        }
        return false;
    }

    public static Vector2f getNeededRotations(Vec3d vec) {
        Vec3d eyes = mc.player.getEyePos();
        double diffX = vec.x - eyes.x;
        double diffZ = vec.z - eyes.z;
        double yaw = Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        yaw = MathHelper.wrapDegrees(yaw);

        double diffY = vec.y - eyes.y;
        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        double pitch = -Math.toDegrees(Math.atan2(diffY, diffXZ));
        pitch = MathHelper.wrapDegrees(pitch);
        return new Vector2f((float) yaw, (float) pitch);
    }
}
