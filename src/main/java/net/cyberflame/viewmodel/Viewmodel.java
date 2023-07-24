package net.cyberflame.viewmodel;

import net.cyberflame.viewmodel.config.LoadConfig;
import net.cyberflame.viewmodel.config.SaveConfig;
import net.cyberflame.viewmodel.settings.BooleanSetting;
import net.cyberflame.viewmodel.settings.FloatSetting;
import net.cyberflame.viewmodel.settings.Setting;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NonNls;

import java.util.Arrays;
import java.util.List;

import static java.lang.Runtime.getRuntime;

public class Viewmodel implements ModInitializer {

    private static final Logger LOGGER = LogManager.getLogger();
    public static final BooleanSetting SCALE = new BooleanSetting("Scale", false);
    public static final FloatSetting SCALE_X = new FloatSetting("Scale X", 1, 0, 3);
    public static final FloatSetting SCALE_Y = new FloatSetting("Scale Y", 1, 0, 3);
    public static final FloatSetting SCALE_Z = new FloatSetting("Scale Z", 1, 0, 3);
    public static final BooleanSetting POS = new BooleanSetting("Position", false);
    public static final FloatSetting POS_X = new FloatSetting("Position X", 0, -2, 2);
    public static final FloatSetting POS_Y = new FloatSetting("Position Y", 0, -2, 2);
    public static final FloatSetting POS_Z = new FloatSetting("Position Z", 0, -2, 2);
    public static final BooleanSetting ROTATION = new BooleanSetting("Rotation", false);
    public static final FloatSetting ROTATION_X = new FloatSetting("Rotation X", 0, -180, 180);
    public static final FloatSetting ROTATION_Y = new FloatSetting("Rotation Y", 0, -180, 180);
    public static final FloatSetting ROTATION_Z = new FloatSetting("Rotation Z", 0, -180, 180);
    public static final BooleanSetting CHANGE_SWING = new BooleanSetting("Change Swing", false);
    public static final List<Setting<?>> SETTINGS = Arrays.asList(SCALE, SCALE_X, SCALE_Y, SCALE_Z, POS, POS_X, POS_Y, POS_Z, ROTATION, ROTATION_X, ROTATION_Y, ROTATION_Z, CHANGE_SWING);
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
