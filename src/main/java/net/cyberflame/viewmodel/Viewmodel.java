package net.cyberflame.viewmodel;

import net.cyberflame.viewmodel.config.LoadConfig;
import net.cyberflame.viewmodel.config.SaveConfig;
import net.cyberflame.viewmodel.settings.Setting;
import net.cyberflame.viewmodel.settings.SettingType;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Runtime.getRuntime;

public class Viewmodel implements ModInitializer {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final List<Setting<?>> SETTINGS = Arrays.stream(SettingType.values())
            .map(SettingType::getSetting)
            .collect(Collectors.toList());

    @Contract(value = " -> new", pure = true)
    public static @NotNull List<Setting<?>> getSettings() {
        // Return a copy of the SETTINGS list to prevent direct modification
        return new ArrayList<>(SETTINGS);
    }
    @NonNls
    public static final String VIEWMODEL_JSON = "Viewmodel.json";

    private static SaveConfig sconfig;
    private static LoadConfig lconfig;

    @Override
    public final void onInitialize() {
        LOGGER.info("Loading Viewmodel!");
        lconfig = new LoadConfig();
        sconfig = new SaveConfig();
        getRuntime().addShutdownHook(new Thread(SaveConfig::saveAllSettings));
    }

}
