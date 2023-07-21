package net.cyberflame.viewmodel.settings;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class FloatSetting implements Setting<Float> {

    private Float value;
    private String name;
    private float min;
    private float max;

    public FloatSetting(String name, float defaultValue, float min, float max) {
        this.name = name;
        this.value = defaultValue;
        this.min = min;
        this.max = max;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setValue(Float value) {
        this.value = value;
    }

    @Override
    public void setValue(JsonElement value) {
        if (value.isJsonPrimitive() && value.getAsJsonPrimitive().isNumber()) {
            this.value = value.getAsFloat();
        }
    }

    @Override
    public JsonElement toJson() {
        return new JsonPrimitive(value);
    }

    @Override
    public Float getValue() {
        return value;
    }

    // Additional methods specific to FloatSetting if needed
    public float getMin() {
        return min;
    }

    public float getMax() {
        return max;
    }
}
