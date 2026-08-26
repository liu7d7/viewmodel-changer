package net.cyberflame.viewmodel;

import net.cyberflame.viewmodel.config.LoadConfig;
import net.cyberflame.viewmodel.config.SaveConfig;
import net.cyberflame.viewmodel.gui.ViewmodelScreen;
import net.cyberflame.viewmodel.settings.Setting;
import net.cyberflame.viewmodel.settings.SettingType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Runtime.getRuntime;

import com.mojang.blaze3d.platform.InputConstants;

public class Viewmodel implements ModInitializer {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final List<Setting<?>> SETTINGS = Arrays.stream(SettingType.values())
            .map(SettingType::getSetting)
            .collect(Collectors.toList());
    private static final KeyMapping.Category VIEWMODEL_CATEGORY = KeyMapping.Category.register(Identifier.fromNamespaceAndPath("viewmodel", "menu"));
    private static KeyMapping keyBinding;

    @Contract(value = " -> new", pure = true)
    public static @NotNull List<Setting<?>> getSettings() {
        // Return a copy of the SETTINGS list to prevent direct modification
        return new ArrayList<>(SETTINGS);
    }

    @NonNls
    public static final String VIEWMODEL_JSON = "Viewmodel.json";


    @Override
    public final void onInitialize() {
        LOGGER.info("Loading Viewmodel!");
        try {
            new LoadConfig();
            new SaveConfig();
        } catch (IOException e) {
            LOGGER.error("Failed to load settings!", e);
        }
        keyBinding = KeyMappingHelper.registerKeyMapping(
                new KeyMapping("key.viewmodel.open", InputConstants.Type.KEYSYM,
                        GLFW.GLFW_KEY_BACKSLASH, VIEWMODEL_CATEGORY));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBinding.consumeClick()) {
                if (client.level != null) {
                    client.gui.setScreen(new ViewmodelScreen());
                }
            }
        });
        getRuntime().addShutdownHook(new Thread(SaveConfig::saveAllSettings));
    }

}
