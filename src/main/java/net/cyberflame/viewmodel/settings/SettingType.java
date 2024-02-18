package net.cyberflame.viewmodel.settings;

import org.jetbrains.annotations.Contract;

public enum SettingType {
    CHANGE_SWING("Change Swing", false),
    POS("Position", false),
    POS_X("Position X", 0, -2, 2),
    POS_Y("Position Y", 0, -2, 2),
    POS_Z("Position Z", 0, -2, 2),
    ROTATION("Rotation", false),
    ROTATION_X("Rotation X", 0, -180, 180),
    ROTATION_Y("Rotation Y", 0, -180, 180),
    ROTATION_Z("Rotation Z", 0, -180, 180),
    SCALE("Scale", false),
    SCALE_X("Scale X", 1, 0, 3),
    SCALE_Y("Scale Y", 1, 0, 3),
    SCALE_Z("Scale Z", 1, 0, 3);

    private final String name;
    private final Setting<?> setting;

    @Contract(pure = true)
    SettingType(String name, boolean defaultValue) {
        this.name = name;
        this.setting = new BooleanSetting(name, defaultValue);
    }

    @Contract(pure = true)
    SettingType(String name, float defaultValue, float minValue, float maxValue) {
        this.name = name;
        this.setting = new FloatSetting(name, defaultValue, minValue, maxValue);
    }

    @Contract(pure = true)
    public String getName() {
        return this.name;
    }

    @Contract(pure = true)
    public Setting<?> getSetting() {
        return this.setting;
    }

    public boolean isTrue() {
        if (this.setting instanceof BooleanSetting) {
            return ((BooleanSetting) this.setting).getValue();
        } else {
            throw new IllegalStateException("Setting is not a BooleanSetting.");
        }
    }

    // Method to get the float value directly from SettingType
    public float getFloatValue() {
        if (this.setting instanceof FloatSetting) {
            return ((FloatSetting) this.setting).getValue();
        } else {
            throw new IllegalStateException("Setting is not a FloatSetting.");
        }
    }
}
