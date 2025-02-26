package ray4rc.rayclient.modules.combat;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;
import ray4rc.rayclient.modules.Mod;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AimAssist extends Mod {
    public AimAssist() {
        super("AimAssist", "radiant 101", Category.COMBAT);
        this.setKey(GLFW.GLFW_KEY_U);
    }

    static class EntityAttribute {
        Entity entity;
        float distance;
        float health;
        float yaw;
        float pitch;

        public Entity getEntity() {
            return entity;
        }

        public float getDistance() {
            return distance;
        }

        public float getHealth() {
            return health;
        }

        public float getYaw() {
            return yaw;
        }

        public float getPitch() {
            return pitch;
        }

        public EntityAttribute(Entity entity, float distance, float health, float yaw, float pitch) {
            this.entity = entity;
            this.distance = distance;
            this.health = health;
            this.yaw = yaw;
            this.pitch = pitch;
        }
    }

    @Override
    public void onRender() {
        assert mc.player != null;
        assert mc.world != null;
        assert mc.interactionManager != null;

        Vec3d playerPos = mc.player.getPos();
        float playerYaw = mc.player.getHeadYaw();
        float playerPitch = mc.player.getPitch();

        Iterable<Entity> entities = mc.world.getEntities();
        List<AimAssist.EntityAttribute> targets = new ArrayList<>();

        for (Entity entity : entities) {
            if (!entity.isAlive() || entity == mc.player || !(entity instanceof LivingEntity)) {
                continue;
            }
            float dist = entity.distanceTo(mc.player);
            if (dist > 5.0f) {
                continue;
            }

            Vec3d entityPos = entity.getPos();
            double diffX = entityPos.x - playerPos.x;
            double diffY = entityPos.y - playerPos.y;
            double diffZ = entityPos.z - playerPos.z;
            double horizontalDistance = Math.sqrt(diffX * diffX + diffZ * diffZ);

            float yaw = MathHelper.wrapDegrees((float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f);
            yaw = MathHelper.wrapDegrees(yaw);

            float pitch = (float) -Math.toDegrees(Math.atan2(diffY, horizontalDistance));
            pitch = MathHelper.wrapDegrees(pitch);

            float health = ((LivingEntity) entity).getHealth();

            targets.add(new AimAssist.EntityAttribute(entity, dist, health, yaw, pitch));
        }
        if (targets.isEmpty()) {
            return;
        }

        targets.sort(Comparator.comparing(AimAssist.EntityAttribute::getYaw));
        EntityAttribute targetAttribute = targets.getFirst();
        Entity target = targetAttribute.getEntity();

        mc.player.setYaw(targetAttribute.getYaw());
        mc.player.setPitch(targetAttribute.getPitch());
        super.onTick();
    }
}
