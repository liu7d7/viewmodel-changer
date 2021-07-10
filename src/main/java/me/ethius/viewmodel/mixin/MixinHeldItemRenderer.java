package me.ethius.viewmodel.mixin;

import me.ethius.viewmodel.Viewmodel;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemRenderer.class)
public class MixinHeldItemRenderer {

    @Inject(at = @At("INVOKE"), method = "renderFirstPersonItem")
    private void renderFirstPersonItem(AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo info) {
        if (Viewmodel.POS.getValue()) {
            matrices.translate(Viewmodel.POS_X.getValue() * 0.1, Viewmodel.POS_Y.getValue() * 0.1, Viewmodel.POS_Z.getValue() * 0.1);
        }
        if (Viewmodel.ROTATION.getValue()) {
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(Viewmodel.ROTATION_Y.getValue()));
            matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(Viewmodel.ROTATION_X.getValue()));
            matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(Viewmodel.ROTATION_Z.getValue()));
        }
        if (Viewmodel.SCALE.getValue()) {
            matrices.scale(1 - (1 - Viewmodel.SCALE_X.getValue()) * 0.1F, 1 - (1 - Viewmodel.SCALE_Y.getValue()) * 0.1F, 1 - (1 - Viewmodel.SCALE_Z.getValue()) * 0.1F);
        }
    }

}
