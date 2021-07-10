package me.ethius.viewmodel.config;

import com.google.gson.*;
import me.ethius.viewmodel.Viewmodel;
import me.ethius.viewmodel.settings.BooleanSetting;
import me.ethius.viewmodel.settings.FloatSetting;
import me.ethius.viewmodel.settings.Setting;
import me.ethius.viewmodel.util.Stopwatch;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author  ChiquitaV2
 */
public class SaveConfig {

    private Gson gson;
    public static Stopwatch saveTimer;

    public SaveConfig() {
        try {
            gson = new GsonBuilder().setPrettyPrinting().create();
            saveConfig();
            saveAllSettings();
            saveTimer = new Stopwatch();
            timedSave();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String folderName = "Viewmodel/";

    public void saveConfig() throws IOException {
        if (!Files.exists(Paths.get(folderName))) {
            Files.createDirectories(Paths.get(folderName));
        }
    }

    public void saveAllSettings() {
        try {
            makeFile(null, "Viewmodel");

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            OutputStreamWriter fileOutputStreamWriter = new OutputStreamWriter(new FileOutputStream(folderName + "Viewmodel.json"), StandardCharsets.UTF_8);
            JsonObject viewmodelObj = new JsonObject();

            for (Setting value : Viewmodel.SETTINGS) {
                if (value instanceof BooleanSetting) {
                    viewmodelObj.add(value.getName(), new JsonPrimitive((Boolean) value.getValue()));
                } else if (value instanceof FloatSetting) {
                    viewmodelObj.add(value.getName(), new JsonPrimitive((Float) value.getValue()));
                }
            }

            String jsonString = gson.toJson(new JsonParser().parse(viewmodelObj.toString()));
            fileOutputStreamWriter.write(jsonString);
            fileOutputStreamWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void makeFile(String location, String name) throws IOException {
        if (location != null) {
            if (!Files.exists(Paths.get(folderName + location + name + ".json"))) {
                Files.createFile(Paths.get(folderName + location + name + ".json"));
            }
            else {
                File file = new File(folderName + location + name + ".json");

                if (file.delete()) {
                    Files.createFile(Paths.get(folderName + location + name + ".json"));
                }
            }
        } else {
            if (Files.exists(Paths.get(folderName + name + ".json"))) {
                File file = new File(folderName + name + ".json");

                file.delete();
            }
            Files.createFile(Paths.get(folderName + name + ".json"));
        }

    }

    public void timedSave() {
        if (saveTimer.passed(5000)) {
            saveAllSettings();
            saveTimer.reset();
        }
    }
}
