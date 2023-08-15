package net.cyberflame.viewmodel.gui;

import net.cyberflame.viewmodel.settings.BooleanSetting;
import net.minecraft.client.gui.DrawContext;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public record Switch(BooleanSetting setting, int x, int y, int height) implements ViewmodelGuiObj {

    @Override
    public void mouseClicked(double mx, double my) {
        this.setting.setValue(!this.setting.getValue());
    }

    @Override
    public void render(@NotNull DrawContext context, int mouseX, int mouseY) {
        // Cache the BooleanSetting and its properties
        BooleanSetting booleanSetting = this.setting;
        String settingName = booleanSetting.getName();
        boolean settingValue = booleanSetting.getValue();

        context.drawTextWithShadow(ViewmodelScreen.mc.textRenderer, settingName, this.x - ViewmodelScreen.mc.textRenderer.getWidth(settingName) - 1, (int) (this.y + this.height / 2.0f - ViewmodelScreen.mc.textRenderer.fontHeight / 2.0f), -1);
        context.fill(this.x, this.y, this.x + (this.height << 1), this.y + this.height, -0x78EFEFF0);

        // Use the cached settingValue variable instead of calling this.setting.getValue() multiple times
        if (settingValue) {
            context.fill(this.x + 1, this.y + 1, this.x + this.height - 1, this.y + this.height - 1, -1);
        } else {
            context.fill(this.x + this.height + 1, this.y + 1, this.x + (this.height << 1) - 1, this.y + this.height - 1, -1);
        }

        context.drawTextWithShadow(ViewmodelScreen.mc.textRenderer, Boolean.toString(settingValue), this.x + (this.height << 1) + 1, (int) (this.y + this.height / 2.0f - ViewmodelScreen.mc.textRenderer.fontHeight / 2.0f), -1);
    }

    @Contract(pure = true)
    @Override
    public boolean isWithin(double mouseX, double mouseY) {
        return mouseX > this.x && mouseY > this.y && mouseX < this.x + (this.height << 1) && mouseY < this.y + this.height;
    }
}
