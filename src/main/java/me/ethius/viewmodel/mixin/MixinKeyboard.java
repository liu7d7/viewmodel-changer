package me.ethius.viewmodel.mixin;

import me.ethius.viewmodel.gui.ViewmodelScreen;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
class MixinKeyboard {

    @Inject(at = @At("HEAD"), method = "onKey(JIIII)V", cancellable = true)
    private static void onOnKey(long windowHandle, int keyCode, int scanCode, int action, int modifiers, CallbackInfo ci) {
        if (MinecraftClient.getInstance().world != null && action == GLFW.GLFW_PRESS && keyCode == GLFW.GLFW_KEY_BACKSLASH) {
            MinecraftClient.getInstance().setScreen(new ViewmodelScreen());
        }
    }

}
