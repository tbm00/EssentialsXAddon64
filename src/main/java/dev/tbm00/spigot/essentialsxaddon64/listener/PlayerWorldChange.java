package dev.tbm00.spigot.essentialsxaddon64.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import me.clip.placeholderapi.PlaceholderAPI;

import dev.tbm00.spigot.essentialsxaddon64.EssentialsXAddon64;

public class PlayerWorldChange implements Listener {
    private final EssentialsXAddon64 javaPlugin;

    public PlayerWorldChange(EssentialsXAddon64 javaPlugin) {
        this.javaPlugin = javaPlugin;
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (event.getFrom().getWorld().equals(event.getTo().getWorld())) return;

        String ph = PlaceholderAPI.setPlaceholders(event.getPlayer(), "%essentials_fly%");

        if (ph.equalsIgnoreCase("yes")||ph.contains("yes")) {
            Bukkit.getScheduler().runTaskLater(javaPlugin, () -> {
                if (!event.getPlayer().isOnline()) return;

                String ph2 = PlaceholderAPI.setPlaceholders(event.getPlayer(), "%essentials_fly%");
                if (ph2.equalsIgnoreCase("no")||ph.contains("no")) {
                    Bukkit.dispatchCommand(event.getPlayer(), "fly");
                }
            }, 5);
        }
    }
}