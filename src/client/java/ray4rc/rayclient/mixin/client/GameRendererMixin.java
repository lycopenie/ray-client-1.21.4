package ray4rc.rayclient.mixin.client;

import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ray4rc.rayclient.RayClientClient;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Inject(method = "tick", at=@At("HEAD"))
    public void onRender(CallbackInfo ci) {
        RayClientClient.INSTANCE.onRender();
    }
}