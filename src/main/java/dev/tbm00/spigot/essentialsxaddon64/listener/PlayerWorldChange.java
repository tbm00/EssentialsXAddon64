package dev.tbm00.spigot.essentialsxaddon64.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import com.earth2me.essentials.Essentials;

import dev.tbm00.spigot.essentialsxaddon64.EssentialsXAddon64;
import me.clip.placeholderapi.PlaceholderAPI;

public class PlayerWorldChange implements Listener {
    private final EssentialsXAddon64 javaPlugin;
    private final Essentials essHook;

    public PlayerWorldChange(EssentialsXAddon64 javaPlugin, Essentials essHook) {
        this.javaPlugin = javaPlugin;
        this.essHook = essHook;
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (event.getFrom().getWorld().equals(event.getTo().getWorld())) return;

        String ph = PlaceholderAPI.setPlaceholders(event.getPlayer(), "%essentials_fly%");

        javaPlugin.logGreen("Player " + event.getPlayer() + " is teleporting between worlds: " 
                    + event.getFrom().getWorld().getName() + " -> " + event.getTo().getWorld().getName());
        javaPlugin.logGreen("Player's placeholder: " +ph);

        if (ph.equalsIgnoreCase("yes")||ph.contains("yes")) {
            Bukkit.getScheduler().runTaskLater(javaPlugin, () -> {
                if (!event.getPlayer().isOnline()) return;
                Bukkit.dispatchCommand(event.getPlayer(), "fly");
                javaPlugin.logGreen("Player's flight enabled!:" +essHook.getUser(event.getPlayer()).getBase().getAllowFlight());
            }, 5);
        }
    }
}