package me.ethius.viewmodel.settings;

public interface Setting<T> {

    String getName();
    T getValue();
    void setValue(T value);

}
