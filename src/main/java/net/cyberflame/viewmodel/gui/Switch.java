package net.cyberflame.viewmodel.gui;

import net.cyberflame.viewmodel.settings.BooleanSetting;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public record Switch(BooleanSetting setting, int x, int y, int height) implements ViewmodelGuiObj {

    @Override
    public void mouseClicked(double mx, double my) {
        this.setting.setValue(!this.setting.getValue());
    }

    @Override
    public void render(@NotNull GuiGraphicsExtractor context, int mouseX, int mouseY) {
        // Cache the BooleanSetting and its properties
        BooleanSetting booleanSetting = this.setting;
        String settingName = booleanSetting.getName();
        boolean settingValue = booleanSetting.getValue();

        context.text(ViewmodelScreen.mc.font, settingName, this.x - ViewmodelScreen.mc.font.width(settingName) - 1, (int) (this.y + this.height / 2.0f - ViewmodelScreen.mc.font.lineHeight / 2.0f), -1);
        context.fill(this.x, this.y, this.x + (this.height << 1), this.y + this.height, -0x78EFEFF0);

        // Use the cached settingValue variable instead of calling this.setting.getValue() multiple times
        if (settingValue) {
            context.fill(this.x + 1, this.y + 1, this.x + this.height - 1, this.y + this.height - 1, -1);
        } else {
            context.fill(this.x + this.height + 1, this.y + 1, this.x + (this.height << 1) - 1, this.y + this.height - 1, -1);
        }

        context.text(ViewmodelScreen.mc.font, Boolean.toString(settingValue), this.x + (this.height << 1) + 1, (int) (this.y + this.height / 2.0f - ViewmodelScreen.mc.font.lineHeight / 2.0f), -1);
    }

    @Contract(pure = true)
    @Override
    public boolean isWithin(double mouseX, double mouseY) {
        return mouseX > this.x && mouseY > this.y && mouseX < this.x + (this.height << 1) && mouseY < this.y + this.height;
    }
}
