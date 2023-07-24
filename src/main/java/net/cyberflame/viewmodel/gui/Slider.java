package net.cyberflame.viewmodel.gui;

import net.cyberflame.viewmodel.settings.FloatSetting;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Slider implements ViewmodelGuiObj {

    private final FloatSetting setting;
    private final int x, y, width, height;
    private final float min, max;

    public Slider(@NotNull FloatSetting setting, int x, int y, int width, int height) {
        super();
        this.setting = setting;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.min = setting.getMin();
        this.max = setting.getMax();
    }

    private static float round(float value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(1, RoundingMode.HALF_UP);
        return bd.floatValue();
    }

    @Override
    public final void mouseScrolled(double mx, double my, float inc) {
        this.setting.setValue(MathHelper.clamp(this.setting.getValue() + inc * 0.1F, this.min, this.max));
    }

    @Override
    public final void mouseClicked(double mx, double my) {
        this.setting.setValue(this.min + (float) ((this.max - this.min) / this.width * (mx - this.x)));
    }

    @Override
    public final void render(@NotNull DrawContext context, int mouseX, int mouseY) {
        // Cache the FloatSetting and its properties
        FloatSetting floatSetting = this.setting;
        String settingName = floatSetting.getName();
        float settingValue = floatSetting.getValue();
        float minValue = floatSetting.getMin();
        float maxValue = floatSetting.getMax();

        context.drawTextWithShadow(ViewmodelScreen.mc.textRenderer, settingName, this.x - ViewmodelScreen.mc.textRenderer.getWidth(settingName) - 1, (int) (this.y + this.height / 2.0f - ViewmodelScreen.mc.textRenderer.fontHeight / 2.0f), -1);

        // Calculate the filled width based on the cached settingValue, minValue, and maxValue
        int filledWidth = (int) (this.x + ((settingValue - minValue) / (maxValue - minValue)) * this.width);
        context.fill(this.x, this.y, filledWidth, this.y + this.height, -1);

        context.drawTextWithShadow(ViewmodelScreen.mc.textRenderer, String.valueOf(round(settingValue)), this.x + this.width + 1, (int) (this.y + this.height / 2.0f - ViewmodelScreen.mc.textRenderer.fontHeight / 2.0f), -1);
    }


    @Override
    public final boolean isWithin(double mouseX, double mouseY) {
        return mouseX > this.x && mouseY > this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
    }

}
