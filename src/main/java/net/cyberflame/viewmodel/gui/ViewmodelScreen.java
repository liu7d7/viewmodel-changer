package net.cyberflame.viewmodel.gui;

import net.cyberflame.viewmodel.Viewmodel;
import net.cyberflame.viewmodel.settings.BooleanSetting;
import net.cyberflame.viewmodel.settings.FloatSetting;
import net.cyberflame.viewmodel.settings.Setting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class ViewmodelScreen extends Screen {

    private final MinecraftClient mc = MinecraftClient.getInstance();
    private final List<ViewmodelGuiObj> objs = new ArrayList<>();

    public ViewmodelScreen() {
        super(Text.of("Viewmodel"));
    }
    
    @Override
    public void init() {
        int settingCount = 0;
        for (Setting setting : Viewmodel.SETTINGS) {
            settingCount++;
            if (setting instanceof BooleanSetting) {
                objs.add(new Switch((BooleanSetting) setting, 80, 50 + (settingCount << 4), 12));
            } else if (setting instanceof FloatSetting) {
                objs.add(new Slider((FloatSetting) setting, 80, 50 + (settingCount << 4), 80, 12));
            }
        }
    }

    class Slider implements ViewmodelGuiObj {

        private final FloatSetting setting;
        private final int x, y, width, height;
        private final float min, max;

        Slider(FloatSetting setting, int x, int y, int width, int height) {
            this.setting = setting;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            min = setting.getMin();
            max = setting.getMax();
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
            context.drawTextWithShadow(mc.textRenderer, setting.getName(), x - mc.textRenderer.getWidth(setting.getName()) - 1, (int) (y + height/2f - mc.textRenderer.fontHeight/2f), -1);
            context.fill(x, y, (int) (x + ((setting.getValue() - min) / (max - min)) * width), y + height, -1);
            context.drawTextWithShadow(mc.textRenderer, "" + round(setting.getValue(), 1), x + width + 1, (int) (y + height/2f - mc.textRenderer.fontHeight/2f), -1);
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

        Switch(BooleanSetting setting, int x, int y, int height) {
            super();
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
        public void render(DrawContext context, int mouseX, int mouseY) {
            context.drawTextWithShadow(mc.textRenderer, setting.getName(), x - mc.textRenderer.getWidth(setting.getName()) - 1, (int) (y + height / 2f - mc.textRenderer.fontHeight / 2f), -1);
            context.fill(x, y, x + (height << 1), y + height, -0x78EFEFF0);
            if (setting.getValue()) {
                context.fill(x + 1, y + 1, x + height - 1, y + height - 1, -1);
            } else {
                context.fill(x + height + 1, y + 1, x + (height << 1) - 1, y + height - 1, -1);
            }
            context.drawTextWithShadow(mc.textRenderer, setting.getValue().toString(), x + (height << 1) + 1, (int) (y + height/2f - mc.textRenderer.fontHeight/2f), -1);
        }

        @Override
        public boolean isWithin(double mouseX, double mouseY) {
            return mouseX > x && mouseY > y && mouseX < x + (height << 1) && mouseY < y + height;
        }
    }

    interface ViewmodelGuiObj {

        void mouseScrolled(double mx, double my, float inc);

        void mouseClicked(double mx, double my);

        void render(DrawContext context, int mouseX, int mouseY);

        boolean isWithin(double mouseX, double mouseY);

    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float partialTicks) {
        renderBackground(context);
        for (ViewmodelGuiObj obj : objs) {
            obj.render(context, mouseX, mouseY);
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

    private static float round(float value, int places) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.floatValue();
    }

}
