package net.cyberflame.viewmodel.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.*;

import java.util.Objects;

import static net.cyberflame.viewmodel.settings.SettingType.*;

@Mixin(HeldItemRenderer.class)
public abstract class MixinHeldItemRenderer {

    @Shadow
    protected abstract void renderArmHoldingItem(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, float equipProgress, float swingProgress, Arm arm);

    @Shadow
    private ItemStack offHand;

    @Shadow
    protected abstract void renderMapInBothHands(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, float pitch, float equipProgress, float swingProgress);

    @Shadow
    protected abstract void renderMapInOneHand(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, float equipProgress, Arm arm, float swingProgress, ItemStack stack);

    @Shadow protected abstract void applyEquipOffset(MatrixStack matrices, Arm arm, float equipProgress);

    @Shadow
    @Final
    private MinecraftClient client;

    @Shadow
    protected abstract void applySwingOffset(MatrixStack matrices, Arm arm, float swingProgress);

    @Shadow
    public abstract void renderItem(LivingEntity entity, ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light);

    @Shadow
    protected abstract void applyEatOrDrinkTransformation(MatrixStack matrices, float tickDelta, Arm arm, ItemStack stack);

    /**
     * @author CyberFlame
     * @reason The inject would always cancel and therefore can cause incompatibilities with other mods.
     */
    @Overwrite
    private void renderFirstPersonItem(@NotNull AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        if (!player.isUsingSpyglass()) {
            boolean bl = Hand.MAIN_HAND == hand;
            Arm arm = bl ? player.getMainArm() : player.getMainArm().getOpposite();
            matrices.push();
            if (POS.isTrue()) {
                matrices.translate(POS_X.getFloatValue() * 0.1, POS_Y.getFloatValue() * 0.1, POS_Z.getFloatValue() * 0.1);
            }
            if (ROTATION.isTrue()) {
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(ROTATION_Y.getFloatValue()));
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(ROTATION_X.getFloatValue()));
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(ROTATION_Z.getFloatValue()));
            }
            if (SCALE.isTrue()) {
                matrices.scale(1 - (1 - SCALE_X.getFloatValue()) * 0.1F, 1 - (1 - SCALE_Y.getFloatValue()) * 0.1F, 1 - (1 - SCALE_Z.getFloatValue()) * 0.1F);
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
                    boolean bl3 = Arm.RIGHT == arm;
                    int i = bl3 ? 1 : -1;
                    if (player.isUsingItem() && 0 < player.getItemUseTimeLeft() && player.getActiveHand() == hand) {
                        this.applyEquipOffset(matrices, arm, equipProgress);
                        matrices.translate((float)i * -0.4785682F, -0.0943870022892952D, 0.05731530860066414D);
                        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-11.935F));
                        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(i * 65.3F));
                        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(i * -9.785F));
                        assert null != this.client.player;
                        v = item.getMaxUseTime() - (Objects.requireNonNull(this.client.player).getItemUseTimeLeft() - tickDelta + 1.0F);
                        w = v / CrossbowItem.getPullTime(item);
                        if (1.0F < w) {
                            w = 1.0F;
                        }

                        if (0.1F < w) {
                            x = MathHelper.sin((v - 0.1F) * 1.3F);
                            y = w - 0.1F;
                            float k = x * y;
                            matrices.translate(k * 0.0F, k * 0.004F, k * 0.0F);
                        }

                        matrices.translate(w * 0.0F, w * 0.0F, w * 0.04F);
                        matrices.scale(1.0F, 1.0F, 1.0F + w * 0.2F);
                        matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(i * 45.0F));
                    } else {
                        v = -0.4F * MathHelper.sin(MathHelper.sqrt(swingProgress) * 3.1415927F);
                        w = 0.2F * MathHelper.sin(MathHelper.sqrt(swingProgress) * 6.2831855F);
                        x = -0.2F * MathHelper.sin(swingProgress * 3.1415927F);
                        matrices.translate(i * v, w, x);
                        this.applyEquipOffset(matrices, arm, equipProgress);
                        this.applySwingOffset(matrices, arm, swingProgress);
                        if (bl4 && 0.001F > swingProgress && bl) {
                            matrices.translate((float) i * -0.641864F, 0.0D, 0.0D);
                            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(i * 10.0F));
                        }
                    }

                    this.renderItem(player, item, bl3 ? ModelTransformationMode.FIRST_PERSON_RIGHT_HAND : ModelTransformationMode.FIRST_PERSON_LEFT_HAND, !bl3, matrices, vertexConsumers, light);
                } else {
                    bl4 = Arm.RIGHT == arm;
                    int o;
                    float u;
                    if (player.isUsingItem() && 0 < player.getItemUseTimeLeft() && player.getActiveHand() == hand) {
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
                                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-13.935F));
                                u = getU(tickDelta, item, matrices, o, this.client);
                                v = u / 20.0F;
                                v = (v * v + v * 2.0F) / 3.0F;
                                v = getV(matrices, v, u);
                                matrices.translate(v * 0.0F, v * 0.0F, v * 0.04F);
                                matrices.scale(1.0F, 1.0F, 1.0F + v * 0.2F);
                                matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees((float) o * 45.0F));
                            }
                            case SPEAR -> {
                                this.applyEquipOffset(matrices, arm, equipProgress);
                                matrices.translate((float) o * -0.5F, 0.699999988079071D, 0.10000000149011612D);
                                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-55.0F));
                                u = getU(tickDelta, item, matrices, o, this.client);
                                v = u / 10.0F;
                                v = getV(matrices, v, u);
                                matrices.translate(0.0D, 0.0D, v * 0.2F);
                                matrices.scale(1.0F, 1.0F, 1.0F + v * 0.2F);
                                matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees((float) o * 45.0F));
                            }
                            default -> {
                            }
                        }
                    } else if (player.isUsingRiptide()) {
                        this.applyEquipOffset(matrices, arm, equipProgress);
                        o = bl4 ? 1 : -1;
                        if (!CHANGE_SWING.isTrue()) {
                            matrices.translate((float) o * -0.4F, 0.800000011920929D, 0.30000001192092896D);
                        }
                        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)o * 65.0F));
                        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float)o * -85.0F));
                    } else {
                        float aa = -0.4F * MathHelper.sin(MathHelper.sqrt(swingProgress) * 3.1415927F);
                        u = 0.2F * MathHelper.sin(MathHelper.sqrt(swingProgress) * 6.2831855F);
                        v = -0.2F * MathHelper.sin(swingProgress * 3.1415927F);
                        int ad = bl4 ? 1 : -1;
                        matrices.translate(ad * aa, u, v);
                        this.applyEquipOffset(matrices, arm, equipProgress);
                        this.applySwingOffset(matrices, arm, swingProgress);
                    }

                    this.renderItem(player, item, bl4 ? ModelTransformationMode.FIRST_PERSON_RIGHT_HAND : ModelTransformationMode.FIRST_PERSON_LEFT_HAND, !bl4, matrices, vertexConsumers, light);
                }
            }

            matrices.pop();
        }

    }

    @Unique
    @SuppressWarnings("strictfp")
    private static strictfp float getV(MatrixStack matrices, float v, float u) {
        float v1 = v;
        float w;
        float x;
        float y;
        if (1.0F < v1) {
            v1 = 1.0F;
        }
        if (0.1F < v1) {
            w = MathHelper.sin((u - 0.1F) * 1.3F);
            x = v1 - 0.1F;
            y = w * x;
            matrices.translate(y * 0.0F, y * 0.004F, y * 0.0F);
        }
        return v1;
    }

    @Unique
    private static float getU(float tickDelta, @NotNull ItemStack item, @NotNull MatrixStack matrices, float o, @NotNull MinecraftClient client) {
        float u;
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(o * 35.3F));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(o * -9.785F));
        assert null != client.player;
        u = (float) item.getMaxUseTime() - ((float) client.player.getItemUseTimeLeft() - tickDelta + 1.0F);
        return u;
    }

}
