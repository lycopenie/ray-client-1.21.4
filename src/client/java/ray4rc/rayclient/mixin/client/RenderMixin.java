package ray4rc.rayclient.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ray4rc.rayclient.RayClientClient;

@Mixin(MinecraftClient.class)
public class RenderMixin {
    @Inject(method = "render", at = @At("HEAD"))
    public void onRender(CallbackInfo ci) {
        RayClientClient.INSTANCE.onRender();
    }
}