package dev.tbm00.spigot.essentialsxaddon64;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import dev.tbm00.spigot.essentialsxaddon64.hook.PAPIHook;
import me.clip.placeholderapi.PlaceholderAPI;

public class NickManager {
    private final EssentialsXAddon64 javaPlugin;
    private final ConfigHandler configHandler;
    //private final PAPIHook papiHook;
    public static Map<String, String> user_map = new HashMap<>();; // key = username, value = nickname
    

    public NickManager(EssentialsXAddon64 javaPlugin, ConfigHandler configHandler, PAPIHook papiHook) {
        this.javaPlugin = javaPlugin;
        this.configHandler = configHandler;
        //this.papiHook = papiHook;
        startCacheSchedule();
    }

    private void startCacheSchedule() {
        boolean enabled = configHandler.isAutoCacheReloaderEnabled();
        int ticksBetween = configHandler.getAutoCacheReloaderTickBetween();
        if (enabled == false) return;
        new BukkitRunnable() {
            @Override
            public void run() {
                javaPlugin.logWhite("[auto] Clearing and reloading cache...");
                try {
                    reloadCache();
                    javaPlugin.logWhite("[auto] Cache reloaded!");
                } catch (Exception e) {
                    javaPlugin.logYellow("[auto] Exception... could not reload cache!");
                    e.printStackTrace();
                }
            }
        }.runTaskTimer(javaPlugin, 0L, ticksBetween);
        javaPlugin.logWhite("Started autoCacheReloader!");
    }

    // refresh caches with information for online players
    public void reloadCache() {
        user_map.clear();
        
        for (Player player : Bukkit.getOnlinePlayers()) {
            loadPlayerToCache(player);
        }

        //papiHook.clearPlayerPHCache();
    }

    public void loadPlayerToCache(Player player) {
        try {
            String nickname = getNickname(player);
            if (nickname!=null) {
                user_map.put(player.getName(), nickname);
                javaPlugin.logWhite(player.getName() + "'s nickname stored in cache as: " + nickname);
            } else { //delete
                javaPlugin.logWhite(player.getName() + " has no nickname..: " + nickname);
            }
        } catch (Exception e) {
            javaPlugin.logYellow(player.getName() + " caught excepting loading to cache map: " + e.getMessage());
        }
    }

    public void unloadPlayerFromCache(Player player) {
        try {
            String nickname = getNickname(player);
            if (nickname!=null) {
                user_map.remove(player.getName());
                javaPlugin.logWhite(player.getName() + "'s nickname removed from cache: " + nickname);
            } else { //delete
                javaPlugin.logWhite(player.getName() + " has no nickname..: " + nickname);
            }
        } catch (Exception e) {
            javaPlugin.logYellow(player.getName() + " caught excepting unloading from cache map: " + e.getMessage());
        }
    }

    public String getNickname(Player player) {
        //String playerNamePH = "%player_name%";
        String playerName = player.getName();
        String nickNamePH = "%essentials_nickname%";
        
        //playerNamePH = PlaceholderAPI.setPlaceholders(player, playerNamePH);
        nickNamePH = PlaceholderAPI.setPlaceholders(player, nickNamePH);

        javaPlugin.logWhite(playerName + " as " + nickNamePH);

        if (!playerName.equals(nickNamePH)) {
            return nickNamePH;
        } else return null;
    }
}