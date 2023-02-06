package me.ethius.viewmodel.mixin;

import me.ethius.viewmodel.Viewmodel;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemRenderer.class)
public abstract class MixinHeldItemRenderer {

    @Shadow protected abstract void renderArmHoldingItem(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, float equipProgress, float swingProgress, Arm arm);

    @Shadow private ItemStack offHand;

    @Shadow protected abstract void renderMapInBothHands(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, float pitch, float equipProgress, float swingProgress);

    @Shadow protected abstract void renderMapInOneHand(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, float equipProgress, Arm arm, float swingProgress, ItemStack stack);

    @Shadow protected abstract void applyEquipOffset(MatrixStack matrices, Arm arm, float equipProgress);

    @Shadow @Final private MinecraftClient client;

    @Shadow protected abstract void applySwingOffset(MatrixStack matrices, Arm arm, float swingProgress);

    @Shadow public abstract void renderItem(LivingEntity entity, ItemStack stack, ModelTransformation.Mode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light);

    @Shadow protected abstract void applyEatOrDrinkTransformation(MatrixStack matrices, float tickDelta, Arm arm, ItemStack stack);

    @Inject(at = @At(value = "HEAD"), method = "renderFirstPersonItem", cancellable = true)
    private void renderFirstPersonItem1(AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo info) {
        if (!player.isUsingSpyglass()) {
            boolean bl = hand == Hand.MAIN_HAND;
            Arm arm = bl ? player.getMainArm() : player.getMainArm().getOpposite();
            matrices.push();
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
            if (item.isEmpty()) {
                if (bl && !player.isInvisible()) {
                    this.renderArmHoldingItem(matrices, vertexConsumers, light, equipProgress, swingProgress, arm);
                }
            } else if (item.isOf(Items.FILLED_MAP)) {
                if (bl && this.offHand.isEmpty()) {
                    this.renderMapInBothHands(matrices, vertexConsumers, light, pitch, equipProgress, swingProgress);
                } else {
                    this.renderMapInOneHand(matrices, vertexConsumers, light, equipProgress, arm, swingProgress, item);
                }
            } else {
                boolean bl4;
                float v;
                float w;
                float x;
                float y;
                if (item.isOf(Items.CROSSBOW)) {
                    bl4 = CrossbowItem.isCharged(item);
                    boolean bl3 = arm == Arm.RIGHT;
                    int i = bl3 ? 1 : -1;
                    if (player.isUsingItem() && player.getItemUseTimeLeft() > 0 && player.getActiveHand() == hand) {
                        this.applyEquipOffset(matrices, arm, equipProgress);
                        matrices.translate((float)i * -0.4785682F, -0.0943870022892952D, 0.05731530860066414D);
                        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-11.935F));
                        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((float)i * 65.3F));
                        matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion((float)i * -9.785F));
                        v = (float)item.getMaxUseTime() - ((float)this.client.player.getItemUseTimeLeft() - tickDelta + 1.0F);
                        w = v / (float)CrossbowItem.getPullTime(item);
                        if (w > 1.0F) {
                            w = 1.0F;
                        }

                        if (w > 0.1F) {
                            x = MathHelper.sin((v - 0.1F) * 1.3F);
                            y = w - 0.1F;
                            float k = x * y;
                            matrices.translate(k * 0.0F, k * 0.004F, k * 0.0F);
                        }

                        matrices.translate(w * 0.0F, w * 0.0F, w * 0.04F);
                        matrices.scale(1.0F, 1.0F, 1.0F + w * 0.2F);
                        matrices.multiply(Vec3f.NEGATIVE_Y.getDegreesQuaternion((float)i * 45.0F));
                    } else {
                        v = -0.4F * MathHelper.sin(MathHelper.sqrt(swingProgress) * 3.1415927F);
                        w = 0.2F * MathHelper.sin(MathHelper.sqrt(swingProgress) * 6.2831855F);
                        x = -0.2F * MathHelper.sin(swingProgress * 3.1415927F);
                        matrices.translate((float)i * v, w, x);
                        this.applyEquipOffset(matrices, arm, equipProgress);
                        this.applySwingOffset(matrices, arm, swingProgress);
                        if (bl4 && swingProgress < 0.001F && bl) {
                            matrices.translate((float)i * -0.641864F, 0.0D, 0.0D);
                            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((float)i * 10.0F));
                        }
                    }

                    this.renderItem(player, item, bl3 ? ModelTransformation.Mode.FIRST_PERSON_RIGHT_HAND : ModelTransformation.Mode.FIRST_PERSON_LEFT_HAND, !bl3, matrices, vertexConsumers, light);
                } else {
                    bl4 = arm == Arm.RIGHT;
                    int o;
                    float u;
                    if (player.isUsingItem() && player.getItemUseTimeLeft() > 0 && player.getActiveHand() == hand) {
                        o = bl4 ? 1 : -1;
                        switch (item.getUseAction()) {
                            case NONE, BLOCK -> this.applyEquipOffset(matrices, arm, equipProgress);
                            case EAT, DRINK -> {
                                this.applyEatOrDrinkTransformation(matrices, tickDelta, arm, item);
                                this.applyEquipOffset(matrices, arm, equipProgress);
                            }
                            case BOW -> {
                                this.applyEquipOffset(matrices, arm, equipProgress);
                                matrices.translate((float) o * -0.2785682F, 0.18344387412071228D, 0.15731531381607056D);
                                matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-13.935F));
                                matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((float) o * 35.3F));
                                matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion((float) o * -9.785F));
                                u = (float) item.getMaxUseTime() - ((float) this.client.player.getItemUseTimeLeft() - tickDelta + 1.0F);
                                v = u / 20.0F;
                                v = (v * v + v * 2.0F) / 3.0F;
                                if (v > 1.0F) {
                                    v = 1.0F;
                                }
                                if (v > 0.1F) {
                                    w = MathHelper.sin((u - 0.1F) * 1.3F);
                                    x = v - 0.1F;
                                    y = w * x;
                                    matrices.translate(y * 0.0F, y * 0.004F, y * 0.0F);
                                }
                                matrices.translate(v * 0.0F, v * 0.0F, v * 0.04F);
                                matrices.scale(1.0F, 1.0F, 1.0F + v * 0.2F);
                                matrices.multiply(Vec3f.NEGATIVE_Y.getDegreesQuaternion((float) o * 45.0F));
                            }
                            case SPEAR -> {
                                this.applyEquipOffset(matrices, arm, equipProgress);
                                matrices.translate((float) o * -0.5F, 0.699999988079071D, 0.10000000149011612D);
                                matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-55.0F));
                                matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((float) o * 35.3F));
                                matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion((float) o * -9.785F));
                                u = (float) item.getMaxUseTime() - ((float) this.client.player.getItemUseTimeLeft() - tickDelta + 1.0F);
                                v = u / 10.0F;
                                if (v > 1.0F) {
                                    v = 1.0F;
                                }
                                if (v > 0.1F) {
                                    w = MathHelper.sin((u - 0.1F) * 1.3F);
                                    x = v - 0.1F;
                                    y = w * x;
                                    matrices.translate(y * 0.0F, y * 0.004F, y * 0.0F);
                                }
                                matrices.translate(0.0D, 0.0D, v * 0.2F);
                                matrices.scale(1.0F, 1.0F, 1.0F + v * 0.2F);
                                matrices.multiply(Vec3f.NEGATIVE_Y.getDegreesQuaternion((float) o * 45.0F));
                            }
                            default -> {
                            }
                        }
                    } else if (player.isUsingRiptide()) {
                        this.applyEquipOffset(matrices, arm, equipProgress);
                        o = bl4 ? 1 : -1;
                        if (!Viewmodel.CHANGE_SWING.getValue()) {
                            matrices.translate((float) o * -0.4F, 0.800000011920929D, 0.30000001192092896D);
                        }
                        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((float)o * 65.0F));
                        matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion((float)o * -85.0F));
                    } else {
                        float aa = -0.4F * MathHelper.sin(MathHelper.sqrt(swingProgress) * 3.1415927F);
                        u = 0.2F * MathHelper.sin(MathHelper.sqrt(swingProgress) * 6.2831855F);
                        v = -0.2F * MathHelper.sin(swingProgress * 3.1415927F);
                        int ad = bl4 ? 1 : -1;
                        matrices.translate((float)ad * aa, u, v);
                        this.applyEquipOffset(matrices, arm, equipProgress);
                        this.applySwingOffset(matrices, arm, swingProgress);
                    }

                    this.renderItem(player, item, bl4 ? ModelTransformation.Mode.FIRST_PERSON_RIGHT_HAND : ModelTransformation.Mode.FIRST_PERSON_LEFT_HAND, !bl4, matrices, vertexConsumers, light);
                }
            }

            matrices.pop();
        }
        info.cancel();

    }

}
