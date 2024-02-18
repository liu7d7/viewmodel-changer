package net.cyberflame.viewmodel.mixin;

import net.cyberflame.viewmodel.gui.ViewmodelScreen;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class MixinKeyboard {

    @Inject(at = @At("HEAD"), method = "onKey(JIIII)V")
    private void onOnKey(long windowHandle, int keyCode, int scanCode, int action, int modifiers, CallbackInfo ci) {
        if (null != MinecraftClient.getInstance().world && GLFW.GLFW_PRESS == action && GLFW.GLFW_KEY_BACKSLASH == keyCode) {
            MinecraftClient.getInstance().setScreen(new ViewmodelScreen());
        }
    }

}
