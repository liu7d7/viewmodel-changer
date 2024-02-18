package net.cyberflame.viewmodel.gui;

import net.minecraft.client.gui.DrawContext;

public interface ViewmodelGuiObj {

    default void mouseScrolled(double mx, double my, float incx, float incy) {
    }

    void mouseClicked(double mx, double my);

    void render(DrawContext context, int mouseX, int mouseY);

    boolean isWithin(double mouseX, double mouseY);

}
