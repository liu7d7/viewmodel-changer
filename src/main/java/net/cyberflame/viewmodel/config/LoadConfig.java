package net.cyberflame.viewmodel.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.cyberflame.viewmodel.Viewmodel;
import net.cyberflame.viewmodel.settings.Setting;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author ChiquitaV2
 */
@SuppressWarnings("unchecked")
public class LoadConfig {

    private static String folderName = SaveConfig.folderName;

    public LoadConfig() {
        try {
            loadAllSettings();
        } catch (final IOException ignored) {

        }
    }

    private static void loadAllSettings() throws IOException {
        InputStreamReader inputStreamReader = null;
        try {
            if (!Files.exists(Paths.get(folderName + "Viewmodel.json"))) {
                return;
            }

            InputStream inputStream = Files.newInputStream(Paths.get(folderName + "Viewmodel.json"));
            inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            JsonObject viewmodelObj = JsonParser.parseReader(inputStreamReader).getAsJsonObject();

            for (Setting value : Viewmodel.SETTINGS) {
                JsonElement valueElement = viewmodelObj.get(value.getName());
                if (null == valueElement) continue;
                value.setValue(valueElement); // Polymorphism handles the specific type internally
            }

            inputStream.close();
        } catch (final IOException ignored) {

        } finally {
            if (null != inputStreamReader)
                inputStreamReader.close();
        }
    }


}
