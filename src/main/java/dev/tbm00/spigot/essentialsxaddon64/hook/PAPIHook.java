package dev.tbm00.spigot.essentialsxaddon64.hook;

import java.util.HashMap;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import dev.tbm00.spigot.essentialsxaddon64.ConfigHandler;
import dev.tbm00.spigot.essentialsxaddon64.EssentialsXAddon64;
import dev.tbm00.spigot.essentialsxaddon64.NickManager;

public class PAPIHook extends PlaceholderExpansion {
    private final EssentialsXAddon64 javaPlugin;
    private HashMap<String, HashMap<String, String>> placeholderDefMap;
    private String nicknamePrefix;

    public PAPIHook(EssentialsXAddon64 javaPlugin, ConfigHandler configHandler) {
        this.javaPlugin = javaPlugin;
        this.placeholderDefMap = configHandler.getPlaceholderMap();
        nicknamePrefix = configHandler.getNicknamePrefix();
    }

    @Override
    public @NotNull String getIdentifier() {
        return "essentialsxaddon64";
    }

    @Override
    public @NotNull String getAuthor() {
        return "tbm00";
    }

    @Override
    public @NotNull String getVersion() {
        return "0.0";
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String identifier) {
        if (player == null) return "";

        switch (identifier.toLowerCase()) {
            case "displayname": {
                try {
                    String nickname = NickManager.user_map.get(player.getName());
                    if (nickname != null) 
                        return nicknamePrefix + nickname;
                    else return player.getDisplayName();
                } catch (Exception e) {
                    javaPlugin.logYellow(player.getName() + " caught excepting reading cache map: " + e.getMessage());
                    return player.getDisplayName();
                }
            }
            case "username": {
                try {
                    String nickname = NickManager.user_map.get(player.getName());
                    if (nickname != null) 
                        return nicknamePrefix + nickname;
                    else return player.getName();
                } catch (Exception e) {
                    javaPlugin.logYellow(player.getName() + " caught excepting reading cache map: " + e.getMessage());
                    return player.getName();
                }
            }
            default: {
                try {
                    if (placeholderDefMap.containsKey(identifier)) {
                        HashMap<String, String> replacementPairs = placeholderDefMap.get(identifier);
                        if (replacementPairs != null && !replacementPairs.isEmpty()) {

                            String originalValue=null;
                            try {originalValue = PlaceholderAPI.setPlaceholders(player, "%" + identifier + "%").trim();}
                            catch (Exception e) {
                                originalValue = "null";
                            };
                            if (originalValue.isBlank() || originalValue==null) {
                                originalValue = "null";
                            }
                            
                            String replacementValue=null;
                            try {replacementValue = replacementPairs.get(originalValue);}
                            catch (Exception e) {
                                replacementValue = "";
                            };

                            return replacementValue;
                        }
                        
                    } else return null;
                } catch (Exception e) {
                    javaPlugin.logRed("Error processing placeholder '" + "%" + identifier + "%" + "': " + e.getMessage());
                    return null;
                }
                return null;
            } 
        }
    }
}