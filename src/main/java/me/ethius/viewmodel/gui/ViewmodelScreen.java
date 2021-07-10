package me.ethius.viewmodel.gui;

import me.ethius.viewmodel.Viewmodel;
import me.ethius.viewmodel.gui.ViewmodelScreen.Slider;
import me.ethius.viewmodel.gui.ViewmodelScreen.Switch;
import me.ethius.viewmodel.gui.ViewmodelScreen.ViewmodelGuiObj;
import me.ethius.viewmodel.settings.BooleanSetting;
import me.ethius.viewmodel.settings.FloatSetting;
import me.ethius.viewmodel.settings.Setting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class ViewmodelScreen extends Screen {

    final MinecraftClient mc = MinecraftClient.getInstance();
    final List<ViewmodelGuiObj> objs = new ArrayList<>();

    public ViewmodelScreen() {
        super(Text.of("Viewmodel"));
    }

    public void init() {
        int settingCount = 0;
        for (Setting setting : Viewmodel.SETTINGS) {
            settingCount++;
            if (setting instanceof BooleanSetting) {
                objs.add(new Switch((BooleanSetting) setting, 80, 50 + settingCount * 16, 12));
            } else if (setting instanceof FloatSetting) {
                objs.add(new Slider((FloatSetting) setting, 80, 50 + settingCount * 16, 80, 12));
            }
        }
    }

    class Slider implements ViewmodelGuiObj {

        private final FloatSetting setting;
        private final int x, y, width, height;
        private final float min, max;

        public Slider(FloatSetting setting, int x, int y, int width, int height) {
            this.setting = setting;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            min = setting.min;
            max = setting.max;
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
        public void render(MatrixStack matrices, int mouseX, int mouseY) {
            mc.textRenderer.drawWithShadow(matrices, setting.getName(), x - mc.textRenderer.getWidth(setting.getName()) - 1, y + height/2f - mc.textRenderer.fontHeight/2f, -1);
            fill(matrices, x, y, (int) (x + ((setting.getValue() - min) / (max - min)) * width), y + height, -1);
            mc.textRenderer.drawWithShadow(matrices, "" + round(setting.getValue(), 1), x + width + 1, y + height/2f - mc.textRenderer.fontHeight/2f, -1);
        }

        @Override
        public boolean isWithin(double mouseX, double mouseY) {
            return mouseX > x && mouseY > y && mouseX < x + width && mouseY < y + height;
        }

    }

    class Switch implements ViewmodelGuiObj {

        private final BooleanSetting setting;
        private final int x;
        private final int y;
        private final int height;

        public Switch(BooleanSetting setting, int x, int y, int height) {
            this.setting = setting;
            this.x = x;
            this.y = y;
            this.height = height;
        }

        @Override
        public void mouseScrolled(double mx, double my, float inc) {

        }

        @Override
        public void mouseClicked(double mx, double my) {
            setting.setValue(!setting.getValue());
        }

        @Override
        public void render(MatrixStack matrices, int mouseX, int mouseY) {
            mc.textRenderer.drawWithShadow(matrices, setting.getName(), x - mc.textRenderer.getWidth(setting.getName()) - 1, y + height / 2f - mc.textRenderer.fontHeight / 2f, -1);
            fill(matrices, x, y, x + height * 2, y + height, -0x78EFEFF0);
            if (setting.getValue()) {
                fill(matrices, x + 1, y + 1, x + height - 1, y + height - 1, -1);
            } else {
                fill(matrices, x + height + 1, y + 1, x + height * 2 - 1, y + height - 1, -1);
            }
            mc.textRenderer.drawWithShadow(matrices, setting.getValue().toString(), x + height * 2 + 1, y + height/2f - mc.textRenderer.fontHeight/2f, -1);
        }

        @Override
        public boolean isWithin(double mouseX, double mouseY) {
            return mouseX > x && mouseY > y && mouseX < x + height * 2 && mouseY < y + height;
        }
    }

    interface ViewmodelGuiObj {

        void mouseScrolled(double mx, double my, float inc);

        void mouseClicked(double mx, double my);

        void render(MatrixStack matrices, int mouseX, int mouseY);

        boolean isWithin(double mouseX, double mouseY);

    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrices);
        for (ViewmodelGuiObj obj : objs) {
            obj.render(matrices, mouseX, mouseY);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (ViewmodelGuiObj obj : objs) {
            if (obj.isWithin(mouseX, mouseY)) {
                obj.mouseClicked(mouseX, mouseY);
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double multiplier) {
        for (ViewmodelGuiObj obj : objs) {
            if (obj.isWithin(mouseX, mouseY)) {
                obj.mouseScrolled(mouseX, mouseY, (float) multiplier);
            }
        }
        return super.mouseScrolled(mouseX, mouseY, multiplier);
    }

    public float round(float value, int places) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.floatValue();
    }

}
