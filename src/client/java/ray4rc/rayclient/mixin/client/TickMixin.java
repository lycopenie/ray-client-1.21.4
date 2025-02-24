package ray4rc.rayclient.mixin.client;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ray4rc.rayclient.RayClientClient;

@Mixin(MinecraftClient.class)
public class TickMixin {
	@Inject(method = "tick", at = @At("HEAD"))
	public void onTick(CallbackInfo ci) {
		RayClientClient.INSTANCE.onTick();
	}
}