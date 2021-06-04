package me.ethius.viewmodel.settings;

public class FloatSetting extends Setting<Float> {

    public float min, max;

    public FloatSetting(String name, float defaultValue, float min, float max) {
        super(name, defaultValue);
        this.min = min;
        this.max = max;
    }

    public String getName() {
        return name;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public Float getValue() {
        return value;
    }

}
