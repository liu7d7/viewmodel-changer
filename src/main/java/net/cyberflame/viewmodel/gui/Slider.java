package net.cyberflame.viewmodel.gui;

import net.cyberflame.viewmodel.settings.FloatSetting;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.math.MathHelper;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Slider implements ViewmodelGuiObj {

    private final FloatSetting setting;
    private final int x, y, width, height;
    private final float min, max;

    public Slider(FloatSetting setting, int x, int y, int width, int height) {
        super();
        this.setting = setting;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        min = setting.getMin();
        max = setting.getMax();
    }

    static float round(float value, int places) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.floatValue();
    }

    @Override
    public void mouseScrolled(double mx, double my, float inc) {
        setting.setValue(MathHelper.clamp(setting.getValue() + inc * 0.1F, min, max));
    }

    @Override
    public void mouseClicked(double mx, double my) {
        setting.setValue(min + (float) ((max - min) / width * (mx - x)));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY) {
        context.drawTextWithShadow(ViewmodelScreen.mc.textRenderer, setting.getName(), x - ViewmodelScreen.mc.textRenderer.getWidth(setting.getName()) - 1, (int) (y + height / 2f - ViewmodelScreen.mc.textRenderer.fontHeight / 2f), -1);
        context.fill(x, y, (int) (x + ((setting.getValue() - min) / (max - min)) * width), y + height, -1);
        context.drawTextWithShadow(ViewmodelScreen.mc.textRenderer, "" + round(setting.getValue(), 1), x + width + 1, (int) (y + height / 2f - ViewmodelScreen.mc.textRenderer.fontHeight / 2f), -1);
    }

    @Override
    public boolean isWithin(double mouseX, double mouseY) {
        return mouseX > x && mouseY > y && mouseX < x + width && mouseY < y + height;
    }

}
