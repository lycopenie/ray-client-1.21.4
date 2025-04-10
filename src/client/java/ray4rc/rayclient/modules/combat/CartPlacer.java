package ray4rc.rayclient.modules.combat;

import net.fabricmc.loader.impl.lib.sat4j.core.Vec;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.Pair;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.apache.commons.lang3.tuple.Triple;
import org.joml.Vector2f;
import ray4rc.rayclient.modules.Mod;
import ray4rc.rayclient.modules.ModuleManager;
import ray4rc.rayclient.modules.player.AutoSwitch;


public class CartPlacer extends Mod {
    public CartPlacer() {
        super("Cart Placer", "theobald the bird type shit", Category.COMBAT);
    }

    public static Vector2f getLookAngles(Vec3d targetPos, Vec3d eyePos) {
        // Calculate differences
        double diffX = targetPos.x - eyePos.x;
        double diffY = targetPos.y - eyePos.y;
        double diffZ = targetPos.z - eyePos.z;

        // Calculate horizontal distance
        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);

        // Calculate yaw (horizontal) and pitch (vertical) in degrees
        float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90F;
        float pitch = (float)-Math.toDegrees(Math.atan2(diffY, diffXZ));

        return new Vector2f(
                MathHelper.wrapDegrees(yaw),   // Normalize to [-180,180]
                MathHelper.wrapDegrees(pitch)  // Normalize to [-90,90]
        );
    }

    public Vec3d getArrowLandingPos(ArrowEntity arrow) {
        Vec3d pos = arrow.getPos();
        Vec3d vel = arrow.getVelocity();
        Box bbox = arrow.getBoundingBox();

        ArrowEntity arrow1 = arrow;

        for (int i = 0; i < 200; i++) {
            pos = pos.add(vel);
            bbox = bbox.offset(vel);
            vel = vel.multiply(0.99); // drag
            vel = vel.add(0, -0.05, 0); // gravity

            arrow1.setBoundingBox(bbox);

            if (mc.world.getBlockCollisions(arrow1, bbox).iterator().hasNext()) {
                break;
            }
        }
        return pos;
    }

    Vector2f angles;
    Vec3d arrowLandingPos;

    @Override
    public void onTick() {
        AutoSwitch autoSwitch = ModuleManager.INSTANCE.getModule(AutoSwitch.class);

        for (Entity entity : mc.world.getEntities()) {
            if (entity instanceof ArrowEntity) {
                ArrowEntity arrow = (ArrowEntity) entity;
                if (arrow.getOwner() == mc.player && arrow.getVelocity().lengthSquared() > 0 && arrow.age == 0) {

                    Vec3d arrowVel = arrow.getVelocity();
                    Vec3d arrowPos = arrow.getPos();

                    LOGGER.info("Arrow: " + arrowPos);

                    arrowLandingPos = getArrowLandingPos(arrow);
                    LOGGER.info("Arrow pos after: " + arrowLandingPos);

                }
            }
        }

        if (mc.options.attackKey.isPressed() && mc.player.isHolding(Items.BOW)) {
//            Vec3d eyePos = mc.player.getEyePos();
//            angles = getLookAngles(arrowLandingPos, eyePos);
//            mc.player.setYaw(angles.x);
//            mc.player.setPitch(angles.y);


            autoSwitch.SwitchToItem(Items.RAIL);

            HitResult hitResult = mc.crosshairTarget;
            if (hitResult instanceof BlockHitResult blockHit) {
                mc.interactionManager.interactBlock(
                        mc.player,
                        Hand.MAIN_HAND,
                        blockHit
                );
                autoSwitch.SwitchToItem(Items.TNT_MINECART);
            }
        }
        super.onTick();
    }
}
