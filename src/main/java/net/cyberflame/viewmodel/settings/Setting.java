package net.cyberflame.viewmodel.settings;

import com.google.gson.JsonElement;

public interface Setting<T> {

    String getName();
    T getValue();
    void setValue(T value);
    void setValue(JsonElement value);
    JsonElement toJson();


}
