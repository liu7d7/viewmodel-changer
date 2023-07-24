package net.cyberflame.viewmodel.gui;

import net.minecraft.client.gui.DrawContext;

public interface ViewmodelGuiObj {

    void mouseScrolled(double mx, double my, float inc);

    void mouseClicked(double mx, double my);

    void render(DrawContext context, int mouseX, int mouseY);

    boolean isWithin(double mouseX, double mouseY);

}
