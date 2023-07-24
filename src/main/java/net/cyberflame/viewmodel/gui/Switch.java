package net.cyberflame.viewmodel.gui;

import net.cyberflame.viewmodel.settings.BooleanSetting;
import net.minecraft.client.gui.DrawContext;

public class Switch implements ViewmodelGuiObj {

    private final BooleanSetting setting;
    private final int x;
    private final int y;
    private final int height;

    public Switch(BooleanSetting setting, int x, int y, int height) {
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
    public final void mouseClicked(double mx, double my) {
        this.setting.setValue(!this.setting.getValue());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY) {
        context.drawTextWithShadow(ViewmodelScreen.mc.textRenderer, this.setting.getName(), this.x - ViewmodelScreen.mc.textRenderer.getWidth(this.setting.getName()) - 1, (int) (this.y + this.height / 2.0f - ViewmodelScreen.mc.textRenderer.fontHeight / 2.0f), -1);
        context.fill(this.x, this.y, this.x + (this.height << 1), this.y + this.height, -0x78EFEFF0);
        if (this.setting.getValue()) {
            context.fill(this.x + 1, this.y + 1, this.x + this.height - 1, this.y + this.height - 1, -1);
        } else {
            context.fill(this.x + this.height + 1, this.y + 1, this.x + (this.height << 1) - 1, this.y + this.height - 1, -1);
        }
        context.drawTextWithShadow(ViewmodelScreen.mc.textRenderer, this.setting.getValue().toString(), this.x + (this.height << 1) + 1, (int) (this.y + this.height / 2.0f - ViewmodelScreen.mc.textRenderer.fontHeight / 2.0f), -1);
    }

    @Override
    public boolean isWithin(double mouseX, double mouseY) {
        return mouseX > this.x && mouseY > this.y && mouseX < this.x + (this.height << 1) && mouseY < this.y + this.height;
    }
}
