package me.ethius.viewmodel;

import me.ethius.viewmodel.config.LoadConfig;
import me.ethius.viewmodel.config.SaveConfig;
import me.ethius.viewmodel.settings.BooleanSetting;
import me.ethius.viewmodel.settings.FloatSetting;
import me.ethius.viewmodel.settings.Setting;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;

public class Viewmodel implements ModInitializer {

    private final Logger LOGGER = LogManager.getLogger();
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

    private static SaveConfig sconfig;
    private static LoadConfig lconfig;

    @Override
    public void onInitialize() {
        LOGGER.info("Loading Viewmodel!");
        lconfig = new LoadConfig();
        sconfig = new SaveConfig();
        Runtime.getRuntime().addShutdownHook(new Thread(SaveConfig::saveAllSettings));
    }

}
