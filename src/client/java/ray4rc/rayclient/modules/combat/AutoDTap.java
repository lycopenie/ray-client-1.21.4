package ray4rc.rayclient.modules.combat;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.math.Vector2f;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EmptyBlockView;
import org.lwjgl.glfw.GLFW;
import ray4rc.rayclient.modules.Mod;
import ray4rc.rayclient.modules.settings.BooleanSetting;
import ray4rc.rayclient.modules.settings.NumberSetting;


public class AutoDTap extends Mod {
    public AutoDTap() {
        super("AutoDTap", "monkey see monkey kill", Category.COMBAT);
        addSettings();
    }

    public NumberSetting range = new NumberSetting("Range", 0, 5, 5, 0.1);
    public int blockDelay = 5;

    @Override
    public void onTick() {
        Iterable<Entity> entities = mc.world.getEntities();

        for (Entity entity : entities) {
            if (!entity.isAlive() || entity == mc.player || !(entity instanceof LivingEntity)) {
                continue;
            }
            float dist = entity.distanceTo(mc.player);
            if (dist > range.getValue()) {
                continue;
            }



//            if (((LivingEntity) entity).hurtTime != 9) {
//                continue;
//            }
//
//            Vec3d targetPos = entity.getPos();
//            Vec3d targetVel = entity.getVelocity();
//            targetPos = new Vec3d(
//                    targetPos.x + targetVel.x * 9 * Math.pow(0.98, 9) + 1,
//                    targetPos.y + 1,
//                    targetPos.z + targetVel.z * 9 * Math.pow(0.98, 9)
//            );
//            BlockPos belowTarget = BlockPos.ofFloored(targetPos).down();
//            BlockState blockBelow = mc.world.getBlockState(belowTarget);
//            if (blockBelow.isReplaceable()){
//                int obsidianSlot = -1;
//                int crystalSlot = -1;
//                for(int i = 0; i < 9; i++)
//                {
//                    ItemStack stack = mc.player.getInventory().getStack(i);
//                    if (stack.getItem() == Items.OBSIDIAN) {
//                        obsidianSlot = i;
//                    } else if (stack.getItem() == Items.END_CRYSTAL) {
//                        crystalSlot = i;
//                    }
//                }
//                if(crystalSlot == -1)
//                    return;
//
//            }
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
