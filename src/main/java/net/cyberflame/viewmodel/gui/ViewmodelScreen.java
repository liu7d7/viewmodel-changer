package net.cyberflame.viewmodel.gui;

import net.cyberflame.viewmodel.Viewmodel;
import net.cyberflame.viewmodel.settings.Setting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.stream.IntStream.range;

public class ViewmodelScreen extends Screen {

    static final MinecraftClient mc = MinecraftClient.getInstance();
    private final Collection<ViewmodelGuiObj> objs = new ArrayList<>(3);

    public ViewmodelScreen() {
        super(Text.of("Viewmodel"));
    }

    @Override
    public final void init() {
        List<Setting<?>> settingsList = Viewmodel.getSettings();
        range(0, settingsList.size()).forEachOrdered(i -> {
            var setting = settingsList.get(i);
            setting.createUIElement(this.objs, i);
        });
    }

    @Override
    public final void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        this.objs.forEach(obj -> obj.render(context, mouseX, mouseY));
    }

    @Override
    public final boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (ViewmodelGuiObj obj : this.objs) {
            if (obj.isWithin(mouseX, mouseY)) {
                obj.mouseClicked(mouseX, mouseY);
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public final boolean mouseScrolled(double mouseX, double mouseY, double amountX, double amountY) {
        for (ViewmodelGuiObj obj : this.objs) {
            if (obj.isWithin(mouseX, mouseY)) {
                obj.mouseScrolled(mouseX, mouseY, (float) amountX, (float) amountY);
            }
        }
        return super.mouseScrolled(mouseX, mouseY, amountX, amountY);
    }

}
