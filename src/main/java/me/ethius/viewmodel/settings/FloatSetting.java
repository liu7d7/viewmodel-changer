package me.ethius.viewmodel.settings;

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
