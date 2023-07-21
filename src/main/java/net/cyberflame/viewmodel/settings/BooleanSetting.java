package net.cyberflame.viewmodel.settings;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class BooleanSetting implements Setting<Boolean> {

    private Boolean value;
    private String name;

    public BooleanSetting(String name, boolean defaultValue) {
        this.name = name;
        this.value = defaultValue;
    }

    @Override
    public JsonElement toJson() {
        return new JsonPrimitive(value);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setValue(JsonElement value) {
        if (value.isJsonPrimitive() && value.getAsJsonPrimitive().isBoolean()) {
            this.value = value.getAsBoolean();
        }

        // Potentially add an else to handle invalid JSON value for a boolean setting - perhaps one of these?
        // this.value = defaultValue; // Set default value
        // throw new IllegalArgumentException("Invalid value for boolean setting.");

    }

    @Override
    public void setValue(Boolean value) {
        this.value = value;
    }

    @Override
    public Boolean getValue() {
        return value;
    }

}
