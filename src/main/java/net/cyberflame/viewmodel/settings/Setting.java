package net.cyberflame.viewmodel.settings;

import com.google.gson.JsonElement;
import net.cyberflame.viewmodel.gui.ViewmodelGuiObj;

import java.util.Collection;

public interface Setting<T> {

    String getName();
    T getValue();
    void setValue(T value);
    void setValue(JsonElement value);
    JsonElement toJson();

    void createUIElement(Collection<ViewmodelGuiObj> objs, int settingCount);

}
