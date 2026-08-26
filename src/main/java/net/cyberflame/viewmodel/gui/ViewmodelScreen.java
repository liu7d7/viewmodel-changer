package net.cyberflame.viewmodel.gui;

import net.cyberflame.viewmodel.Viewmodel;
import net.cyberflame.viewmodel.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.stream.IntStream.range;

public class ViewmodelScreen extends Screen {

    static final Minecraft mc = Minecraft.getInstance();
    private final Collection<ViewmodelGuiObj> objs = new ArrayList<>(3);

    public ViewmodelScreen() {
        super(Component.nullToEmpty("Viewmodel"));
    }

    @Override
    public final void init() {
        this.objs.clear();
        List<Setting<?>> settingsList = Viewmodel.getSettings();
        range(0, settingsList.size()).forEachOrdered(i -> {
            var setting = settingsList.get(i);
            setting.createUIElement(this.objs, i);
        });

    }

    @Override
    public final void extractRenderState(GuiGraphicsExtractor context, int mouseX, int mouseY, float delta) {
        context.fill(0, 0, this.width, this.height, 0xAA000000); // semi-transparent black
        this.objs.forEach(obj -> obj.render(context, mouseX, mouseY));
    }

    @Override
    public final boolean mouseClicked(MouseButtonEvent mouseButtonEvent, boolean doubleClick) {
        for (ViewmodelGuiObj obj : this.objs) {
            if (obj.isWithin(mouseButtonEvent.x(), mouseButtonEvent.y())) {
                obj.mouseClicked(mouseButtonEvent.x(), mouseButtonEvent.y());
            }
        }
        return super.mouseClicked(mouseButtonEvent, doubleClick);
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
