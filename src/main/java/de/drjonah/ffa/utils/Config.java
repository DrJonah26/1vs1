package de.drjonah.ffa.utils;

import de.drjonah.ffa.Main;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Config {

    private static FileConfiguration fileConfiguration = new FileConfiguration() {
        @Override
        public String saveToString() {
            return null;
        }

        @Override
        public void loadFromString(String contents) throws InvalidConfigurationException {

        }

        @Override
        protected String buildHeader() {
            return null;
        }
    };
    private static File customConfigFile;
    public static YamlConfiguration config;


    public static void createCustomConfig() {
        customConfigFile = new File(Main.getInstance().getDataFolder(), "config.yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            Main.getInstance().saveResource("config.yml", false);
        }

        config = new YamlConfiguration();

        try {
            fileConfiguration.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            config.save(customConfigFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static YamlConfiguration getConfig() {
        return config;
    }
}
