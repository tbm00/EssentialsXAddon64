package dev.tbm00.spigot.essentialsxaddon64;

import java.util.HashMap;

import org.bukkit.configuration.ConfigurationSection;

public class ConfigHandler {
    private final EssentialsXAddon64 javaPlugin;
    private boolean nicknamesEnabled;
    private boolean autoCacheReloaderEnabled;
    private int autoCacheReloaderTickBetween;
    private String nicknamePrefix = "";

    /**
     * Placeholder Map:
     *   key = placeholder (e.g. '%afk%'),
     *   value = map of original->replacement (e.g. true->"*AFK*").
     */
    private static HashMap<String,HashMap<String,String>> placeholder_map = new HashMap<>();

    public ConfigHandler(EssentialsXAddon64 javaPlugin) {
        this.javaPlugin = javaPlugin;
        try {
            ConfigurationSection nicknameSection = javaPlugin.getConfig().getConfigurationSection("nicknameExtension");
            if (nicknameSection != null) {
                // Load nicknames enabled
                nicknamesEnabled = nicknameSection.getBoolean("enabled", false);

                // Load auto Cache Reloader
                ConfigurationSection cacheSection = nicknameSection.getConfigurationSection("autoCacheReloader");
                if (cacheSection != null) {
                    loadCacheReloader(cacheSection);
                }

                // Load nickname Prefix
                nicknamePrefix = nicknameSection.getString("nicknamePrefix", "");
            }

            // Load new placeholders
            ConfigurationSection placeholderSection = javaPlugin.getConfig().getConfigurationSection("newPlaceholders");
            if (placeholderSection != null) {
                loadPlaceholders(placeholderSection);
            }
        } catch (Exception e) {
            javaPlugin.logRed("Caught exception loading config: ");
            javaPlugin.getLogger().warning(e.getMessage());
        }
    }

    private void loadCacheReloader(ConfigurationSection cacheSection) {
        autoCacheReloaderEnabled = cacheSection.getBoolean("enabled");
        autoCacheReloaderTickBetween = cacheSection.getInt("ticksBetween");
    }

    private void loadPlaceholders(ConfigurationSection placeholdersSection) {
        for (String newPlaceholder : placeholdersSection.getKeys(false)) {
            ConfigurationSection newPlaceholderSec = placeholdersSection.getConfigurationSection(newPlaceholder);
            if (newPlaceholderSec == null) {
                continue;
            } else try {
                HashMap<String,String> replacementPairs = new HashMap<>();
                for (String originalOutput : newPlaceholderSec.getKeys(false)) { 
                    String newOutput = newPlaceholderSec.getString(originalOutput, "");
                    replacementPairs.put(originalOutput, newOutput);
                }
                if (!replacementPairs.isEmpty()) {
                    placeholder_map.put(newPlaceholder.toLowerCase(), replacementPairs);
                } else {
                    javaPlugin.logRed("Placeholder '" + newPlaceholder + "' has no replacement pairs defined.");
                }
            } catch (Exception e) {
                javaPlugin.logRed("Caught exception while loading new placeholder: " + newPlaceholder);
            }
        }
    }

    public boolean isNicknamesEnabled() {
        return nicknamesEnabled;
    }

    public boolean isAutoCacheReloaderEnabled() {
        return autoCacheReloaderEnabled;
    }

    public int getAutoCacheReloaderTickBetween() {
        return autoCacheReloaderTickBetween;
    }

    public String getNicknamePrefix() {
        return nicknamePrefix;
    }

    public HashMap<String, HashMap<String, String>> getPlaceholderMap() {
        return placeholder_map;
    }
}