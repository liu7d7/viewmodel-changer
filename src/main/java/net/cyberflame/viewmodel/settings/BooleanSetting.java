package net.cyberflame.viewmodel.settings;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import net.cyberflame.viewmodel.gui.Switch;
import net.cyberflame.viewmodel.gui.ViewmodelGuiObj;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class BooleanSetting implements Setting<Boolean> {

    private Boolean value;
    private final String name;

    BooleanSetting(String settingName, boolean defaultValue) {
        super();
        this.name = settingName;
        this.value = defaultValue;
    }

    @Override
    public final JsonElement toJson() {
        return new JsonPrimitive(this.value);
    }

    @Override
    public final String getName() {
        return this.name;
    }

    @Override
    public final void setValue(JsonElement element) {
        if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isBoolean()) {
            this.value = element.getAsBoolean();
        }

        // Potentially add an else to handle invalid JSON element for a boolean setting - perhaps one of these?
        // this.element = defaultValue; // Set default element
        // throw new IllegalArgumentException("Invalid element for boolean setting.");

    }

    @Override
    public final void setValue(Boolean value) {
        this.value = value;
    }

    @Override
    public final Boolean getValue() {
        return this.value;
    }

    @Override
    public final void createUIElement(@NotNull Collection<? super ViewmodelGuiObj> objs, int settingCount) {
        objs.add(new Switch(this, 80, 50 + (settingCount << 4), 12));
    }


}
