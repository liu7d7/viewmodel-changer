package me.ethius.viewmodel.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.ethius.viewmodel.Viewmodel;
import me.ethius.viewmodel.settings.BooleanSetting;
import me.ethius.viewmodel.settings.FloatSetting;
import me.ethius.viewmodel.settings.Setting;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author ChiquitaV2
 */
@SuppressWarnings("unchecked")
public class LoadConfig {

    public static String folderName = SaveConfig.folderName;

    public LoadConfig() {
        try {
            loadAllSettings();
        } catch (IOException e) {

        }
    }

    public void loadAllSettings() throws IOException {
        InputStreamReader inputStreamReader = null;
        try {
            if (!Files.exists(Paths.get(folderName + "Viewmodel.json"))) {
                return;
            }

            InputStream inputStream = Files.newInputStream(Paths.get(folderName + "Viewmodel.json"));
            inputStreamReader = new InputStreamReader(inputStream);
            JsonObject viewmodelObj = new JsonParser().parse(inputStreamReader).getAsJsonObject();

            for (Setting value : Viewmodel.SETTINGS) {
                JsonElement valueElement = viewmodelObj.get(value.getName());
                if (valueElement == null) continue;
                if (value instanceof BooleanSetting) {
                    value.setValue(valueElement.getAsBoolean());
                } else if (value instanceof FloatSetting) {
                    value.setValue(valueElement.getAsFloat());
                }
            }
            inputStream.close();
        } catch (IOException ignored) {

        } finally {
            if (inputStreamReader != null)
                inputStreamReader.close();
        }
    }


}
