package net.cyberflame.viewmodel.settings;

import com.google.gson.JsonElement;
import net.cyberflame.viewmodel.gui.ViewmodelGuiObj;

import java.util.Collection;

public interface Setting<T> {

    String getName();
    T getValue();
    void setValue(T val);
    void setValue(JsonElement element);
    JsonElement toJson();

    void createUIElement(Collection<? super ViewmodelGuiObj> objs, int settingCount);

}
