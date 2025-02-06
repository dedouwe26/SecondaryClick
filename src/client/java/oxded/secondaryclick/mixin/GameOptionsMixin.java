package oxded.secondaryclick.mixin;

import net.minecraft.client.MinecraftClient;
import oxded.secondaryclick.OptionsLoadedCallback;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class GameOptionsMixin {
    @Inject(method = "<init>(Lnet/minecraft/client/RunArgs;)V", at = @At("TAIL"))
    private void init(CallbackInfo info) {
        OptionsLoadedCallback.EVENT.invoker().interact();
    }
}
