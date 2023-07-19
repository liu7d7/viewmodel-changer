package net.cyberflame.viewmodel.settings;

public class BooleanSetting implements Setting<Boolean> {

    private Boolean value;
    private String name;

    public BooleanSetting(String name, boolean defaultValue) {
        this.name = name;
        this.value = defaultValue;
    }

    @Override
    public String getName() {
        return name;
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
