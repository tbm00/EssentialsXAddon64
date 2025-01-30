package dev.tbm00.spigot.essentialsxaddon64.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitScheduler;

import dev.tbm00.spigot.essentialsxaddon64.EssentialsXAddon64;
import dev.tbm00.spigot.essentialsxaddon64.NickManager;

public class PlayerConnection implements Listener {
    private final EssentialsXAddon64 javaPlugin;
    private final NickManager nickManager;

    public PlayerConnection(EssentialsXAddon64 javaPlugin, NickManager nickManager) {
        this.javaPlugin = javaPlugin;
        this.nickManager = nickManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        BukkitScheduler scheduler = javaPlugin.getServer().getScheduler();

        scheduler.runTaskAsynchronously(javaPlugin, () -> {
            Player player = event.getPlayer();
            nickManager.loadPlayerToCache(player);
        });
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        BukkitScheduler scheduler = javaPlugin.getServer().getScheduler();
        
        scheduler.runTaskAsynchronously(javaPlugin, () -> {
            try {
                nickManager.unloadPlayerFromCache(player);
            } catch (Exception e) {
                javaPlugin.logYellow("Error processing player quit: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
}