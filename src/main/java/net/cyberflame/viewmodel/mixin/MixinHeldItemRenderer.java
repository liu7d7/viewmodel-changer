package net.cyberflame.viewmodel.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.state.MapRenderState;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import static net.cyberflame.viewmodel.settings.SettingType.*;

@Mixin(ItemInHandRenderer.class)
public abstract class MixinHeldItemRenderer {

    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    @Final
    private MapRenderState mapRenderState;

    @Shadow
    private ItemStack mainHandItem;

    @Shadow
    private ItemStack offHandItem;

    @Shadow
    private float mainHandHeight;

    @Shadow
    private float oMainHandHeight;

    @Shadow
    private float offHandHeight;

    @Shadow
    private float oOffHandHeight;

    @Shadow
    @Final
    private EntityRenderDispatcher entityRenderDispatcher;

    @Shadow
    @Final
    private ItemModelResolver itemModelResolver;

    @Shadow
    protected abstract void renderPlayerArm(PoseStack matrices, SubmitNodeCollector vertexConsumers, int light, float equipProgress, float swingProgress, HumanoidArm arm);

    @Shadow
    protected abstract void renderTwoHandedMap(PoseStack matrices, SubmitNodeCollector vertexConsumers, int light, float pitch, float equipProgress, float swingProgress);

    @Shadow
    protected abstract void renderOneHandedMap(PoseStack matrices, SubmitNodeCollector vertexConsumers, int light, float equipProgress, HumanoidArm arm, float swingProgress, ItemStack stack);

    @Shadow
    protected abstract void applyItemArmTransform(PoseStack matrices, HumanoidArm arm, float equipProgress);

    @Shadow
    protected abstract void applyItemArmAttackTransform(PoseStack matrices, HumanoidArm arm, float swingProgress);

    @Shadow
    public abstract void renderItem(LivingEntity entity, ItemStack stack, ItemDisplayContext renderMode, PoseStack matrices, SubmitNodeCollector vertexConsumers, int light);

    @Shadow
    protected abstract void applyEatTransform(PoseStack matrices, float tickDelta, HumanoidArm arm, ItemStack stack, Player player);

    /**
     * @author CyberFlame
     * @reason The inject would always cancel and therefore can cause incompatibilities with other mods.
     */
    @Overwrite
    public void submitArmWithItem(@NotNull AbstractClientPlayer player, float tickDelta, float pitch, InteractionHand hand, float swingProgress, ItemStack item, float equipProgress, PoseStack matrices, SubmitNodeCollector vertexConsumers, int light) {
        if (!player.isScoping()) {
            boolean mainHand = InteractionHand.MAIN_HAND == hand;
            HumanoidArm arm = mainHand ? player.getMainArm() : player.getMainArm().getOpposite();
            matrices.pushPose();
            if (POS.isTrue()) {
                matrices.translate(POS_X.getFloatValue() * 0.1, POS_Y.getFloatValue() * 0.1, POS_Z.getFloatValue() * 0.1);
            }
            if (ROTATION.isTrue()) {
                matrices.mulPose(Axis.YP.rotationDegrees(ROTATION_Y.getFloatValue()));
                matrices.mulPose(Axis.XP.rotationDegrees(ROTATION_X.getFloatValue()));
                matrices.mulPose(Axis.ZP.rotationDegrees(ROTATION_Z.getFloatValue()));
            }
            if (SCALE.isTrue()) {
                matrices.scale(1 - (1 - SCALE_X.getFloatValue()) * 0.1F, 1 - (1 - SCALE_Y.getFloatValue()) * 0.1F, 1 - (1 - SCALE_Z.getFloatValue()) * 0.1F);
            }
            if (item.isEmpty()) {
                if (mainHand && !player.isInvisible()) {
                    this.renderPlayerArm(matrices, vertexConsumers, light, equipProgress, swingProgress, arm);
                }
            } else if (item.is(Items.FILLED_MAP)) {
                if (mainHand && this.offHandItem.isEmpty()) {
                    this.renderTwoHandedMap(matrices, vertexConsumers, light, pitch, equipProgress, swingProgress);
                } else {
                    this.renderOneHandedMap(matrices, vertexConsumers, light, equipProgress, arm, swingProgress, item);
                }
            } else {
                boolean bl4;
                float v;
                float w;
                float x;
                float y;
                if (item.is(Items.CROSSBOW)) {
                    bl4 = CrossbowItem.isCharged(item);
                    boolean bl3 = HumanoidArm.RIGHT == arm;
                    int i = bl3 ? 1 : -1;
                    if (player.isUsingItem() && 0 < player.getUseItemRemainingTicks() && player.getUsedItemHand() == hand) {
                        this.applyItemArmTransform(matrices, arm, equipProgress);
                        matrices.translate((float)i * -0.4785682F, -0.0943870022892952D, 0.05731530860066414D);
                        matrices.mulPose(Axis.XP.rotationDegrees(-11.935F));
                        matrices.mulPose(Axis.YP.rotationDegrees(i * 65.3F));
                        matrices.mulPose(Axis.ZP.rotationDegrees(i * -9.785F));
                        LivingEntity playerEntity = this.minecraft.player;
                        if (playerEntity == null) {
                            throw new IllegalStateException("minecraft.player was null while rendering a charged crossbow");
                        }
                        v = item.getUseDuration(playerEntity) - (playerEntity.getUseItemRemainingTicks() - tickDelta + 1.0F);
                        w = v / CrossbowItem.getChargeDuration(item, playerEntity);
                        if (1.0F < w) {
                            w = 1.0F;
                        }

                        if (0.1F < w) {
                            x = Mth.sin((v - 0.1F) * 1.3F);
                            y = w - 0.1F;
                            float k = x * y;
                            matrices.translate(k * 0.0F, k * 0.004F, k * 0.0F);
                        }

                        matrices.translate(w * 0.0F, w * 0.0F, w * 0.04F);
                        matrices.scale(1.0F, 1.0F, 1.0F + w * 0.2F);
                        matrices.mulPose(Axis.YN.rotationDegrees(i * 45.0F));
                    } else {
                        v = -0.4F * Mth.sin(Mth.sqrt(swingProgress) * 3.1415927F);
                        w = 0.2F * Mth.sin(Mth.sqrt(swingProgress) * 6.2831855F);
                        x = -0.2F * Mth.sin(swingProgress * 3.1415927F);
                        matrices.translate(i * v, w, x);
                        this.applyItemArmTransform(matrices, arm, equipProgress);
                        this.applyItemArmAttackTransform(matrices, arm, swingProgress);
                        if (bl4 && 0.001F > swingProgress && mainHand) {
                            matrices.translate((float) i * -0.641864F, 0.0D, 0.0D);
                            matrices.mulPose(Axis.YP.rotationDegrees(i * 10.0F));
                        }
                    }

                    this.renderItem(player, item, bl3 ? ItemDisplayContext.FIRST_PERSON_RIGHT_HAND : ItemDisplayContext.FIRST_PERSON_LEFT_HAND, matrices, vertexConsumers, light);
                } else {
                    bl4 = HumanoidArm.RIGHT == arm;
                    int o;
                    float u;
                    if (player.isUsingItem() && 0 < player.getUseItemRemainingTicks() && player.getUsedItemHand() == hand) {
                        o = bl4 ? 1 : -1;
                        switch (item.getUseAnimation()) {
                            case NONE, BLOCK -> this.applyItemArmTransform(matrices, arm, equipProgress);
                            case EAT, DRINK -> {
                                this.applyEatTransform(matrices, tickDelta, arm, item, player);
                                this.applyItemArmTransform(matrices, arm, equipProgress);
                            }
                            case BOW -> {
                                this.applyItemArmTransform(matrices, arm, equipProgress);
                                matrices.translate((float) o * -0.2785682F, 0.18344387412071228D, 0.15731531381607056D);
                                matrices.mulPose(Axis.XP.rotationDegrees(-13.935F));
                                u = getU(tickDelta, item, matrices, o, this.minecraft);
                                v = u / 20.0F;
                                v = (v * v + v * 2.0F) / 3.0F;
                                v = getV(matrices, v, u);
                                matrices.translate(v * 0.0F, v * 0.0F, v * 0.04F);
                                matrices.scale(1.0F, 1.0F, 1.0F + v * 0.2F);
                                matrices.mulPose(Axis.YN.rotationDegrees((float) o * 45.0F));
                            }
                            case SPEAR -> {
                                this.applyItemArmTransform(matrices, arm, equipProgress);
                                matrices.translate((float) o * -0.5F, 0.699999988079071D, 0.10000000149011612D);
                                matrices.mulPose(Axis.XP.rotationDegrees(-55.0F));
                                u = getU(tickDelta, item, matrices, o, this.minecraft);
                                v = u / 10.0F;
                                v = getV(matrices, v, u);
                                matrices.translate(0.0D, 0.0D, v * 0.2F);
                                matrices.scale(1.0F, 1.0F, 1.0F + v * 0.2F);
                                matrices.mulPose(Axis.YN.rotationDegrees((float) o * 45.0F));
                            }
                            default -> {
                            }
                        }
                    } else if (player.isAutoSpinAttack()) {
                        this.applyItemArmTransform(matrices, arm, equipProgress);
                        o = bl4 ? 1 : -1;
                        if (!CHANGE_SWING.isTrue()) {
                            matrices.translate((float) o * -0.4F, 0.800000011920929D, 0.30000001192092896D);
                        }
                        matrices.mulPose(Axis.YP.rotationDegrees((float) o * 65.0F));
                        matrices.mulPose(Axis.ZP.rotationDegrees((float) o * -85.0F));
                    } else {
                        float aa = -0.4F * Mth.sin(Mth.sqrt(swingProgress) * 3.1415927F);
                        u = 0.2F * Mth.sin(Mth.sqrt(swingProgress) * 6.2831855F);
                        v = -0.2F * Mth.sin(swingProgress * 3.1415927F);
                        int ad = bl4 ? 1 : -1;
                        matrices.translate(ad * aa, u, v);
                        this.applyItemArmTransform(matrices, arm, equipProgress);
                        this.applyItemArmAttackTransform(matrices, arm, swingProgress);
                    }

                    this.renderItem(player, item, bl4 ? ItemDisplayContext.FIRST_PERSON_RIGHT_HAND : ItemDisplayContext.FIRST_PERSON_LEFT_HAND, matrices, vertexConsumers, light);
                }
            }

            matrices.popPose();
        }

    }

    @Unique
    private static float getV(PoseStack matrices, float v, float u) {
        float w;
        float x;
        float y;
        if (1.0F < v) {
            v = 1.0F;
        }
        if (0.1F < v) {
            w = Mth.sin((u - 0.1F) * 1.3F);
            x = v - 0.1F;
            y = w * x;
            matrices.translate(y * 0.0F, y * 0.004F, y * 0.0F);
        }
        return v;
    }

    @Unique
    private static float getU(float tickDelta, @NotNull ItemStack item, @NotNull PoseStack matrices, float o, @NotNull Minecraft client) {
        matrices.mulPose(Axis.YP.rotationDegrees(o * 35.3F));
        matrices.mulPose(Axis.ZP.rotationDegrees(o * -9.785F));
        LivingEntity playerEntity = client.player;
        if (playerEntity == null) {
            throw new IllegalStateException("minecraft.player was null while rendering a bow or spear");
        }
        return (float) item.getUseDuration(playerEntity) - (playerEntity.getUseItemRemainingTicks() - tickDelta + 1.0F);
    }

}
