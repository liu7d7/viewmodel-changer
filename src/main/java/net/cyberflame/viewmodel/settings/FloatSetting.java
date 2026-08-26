package net.cyberflame.viewmodel.settings;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import net.cyberflame.viewmodel.gui.Slider;
import net.cyberflame.viewmodel.gui.ViewmodelGuiObj;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public non-sealed class FloatSetting implements Setting<Float> {

    private Float value;
    private final String name;
    private final float min;
    private final float max;

    @Contract(pure = true)
    FloatSetting(String name, float defaultValue, float minVal, float maxVal) {
        super();
        this.name = name;
        this.value = defaultValue;
        this.min = minVal;
        this.max = maxVal;
    }

    @Contract(pure = true)
    @Override
    public final String getName() {
        return this.name;
    }

    @Contract(mutates = "this")
    @Override
    public final void setValue(Float val) {
        this.value = val;
    }

    @Override
    public final void setValue(@NotNull JsonElement element) {
        if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isNumber()) {
            this.value = element.getAsFloat();
        }
    }

    @Contract(value = " -> new", pure = true)
    @Override
    public final @NotNull JsonElement toJson() {
        return new JsonPrimitive(this.value);
    }

    @Contract(pure = true)
    @Override
    public final Float getValue() {
        return this.value;
    }

    @Override
    public final void createUIElement(@NotNull Collection<? super ViewmodelGuiObj> objs, int settingCount) {
        // Create UI element for FloatSetting
        objs.add(new Slider(this, 80, 50 + (settingCount << 4), 80, 12));
    }

    // Additional methods specific to FloatSetting if needed
    @Contract(pure = true)
    public final float getMin() {
        return this.min;
    }

    @Contract(pure = true)
    public final float getMax() {
        return this.max;
    }
}
