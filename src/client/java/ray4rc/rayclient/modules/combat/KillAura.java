package ray4rc.rayclient.modules.combat;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ray4rc.rayclient.modules.Mod;
import net.minecraft.entity.Entity;

import java.util.*;

import static ray4rc.rayclient.RayClientClient.LOGGER;
import static ray4rc.rayclient.RayClientClient.MOD_ID;


public class KillAura extends Mod {
    public KillAura() {
        super("KillAura", "monkey see monkey kill", Category.COMBAT);
        this.setKey(GLFW.GLFW_KEY_R);
    }

    static class EntityAttribute {
        Entity entity;
        float distance;
        float health;
        float yaw;

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

        public EntityAttribute(Entity entity, float distance, float health, float yaw) {
            this.entity = entity;
            this.distance = distance;
            this.health = health;
            this.yaw = yaw;


        }
    }

    @Override
    public void onTick() {
        assert mc.player != null;
        assert mc.world != null;
        assert mc.interactionManager != null;

        Vec3d playerPos = mc.player.getPos();
        float playerYaw = mc.player.getHeadYaw();

        Iterable<Entity> entities = mc.world.getEntities();
        List<EntityAttribute> targets = new ArrayList<>();

        if (mc.player.getAttackCooldownProgress(0.0f) < 0.9f) { // full cooldown
            return;
        }

        if (mc.player.getVelocity().y >= -0.07f || mc.player.isOnGround()) { // criticals only
            return;
        } else {
            LOGGER.info(String.valueOf(mc.player.getVelocity().y));
        }

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
            double diffZ = entityPos.z - playerPos.z;

            float yaw = MathHelper.wrapDegrees((float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f);
            yaw = MathHelper.wrapDegrees(yaw - playerYaw);
            yaw = Math.abs(yaw);
            float health = ((LivingEntity) entity).getHealth();

            targets.add(new EntityAttribute(entity, dist, health, yaw));
        }
        if (targets.isEmpty()) {
            return;
        }

        targets.sort(Comparator.comparing(EntityAttribute::getYaw));

        Entity target = targets.getFirst().getEntity();


        mc.interactionManager.attackEntity(mc.player, target);
        mc.player.swingHand(Hand.MAIN_HAND);
    }
}
