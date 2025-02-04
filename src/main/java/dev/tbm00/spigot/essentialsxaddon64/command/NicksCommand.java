package dev.tbm00.spigot.essentialsxaddon64.command;

import java.util.List;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import dev.tbm00.spigot.essentialsxaddon64.EssentialsXAddon64;
import dev.tbm00.spigot.essentialsxaddon64.NickManager;

public class NicksCommand implements TabExecutor {
    private final EssentialsXAddon64 javaPlugin;
    private final NickManager nickManager;
    private final String PERMISSION_RELOAD_CACHE = "essentialsxaddon64.reloadcache";
    private final String PERMISSION_NICK_LIST = "essentialsxaddon64.nicklist";
    private final String PERMISSION_WHO_IS = "essentialsxaddon64.whois";

    public NicksCommand(EssentialsXAddon64 javaPlugin, NickManager nickManager) {
        this.javaPlugin = javaPlugin;
        this.nickManager = nickManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command consoleCommand, String label, String[] args) {
        if (args.length != 1 && hasPermission(sender, PERMISSION_NICK_LIST)) {
            return runNickList(sender);
        } else if (sender.hasPermission(PERMISSION_RELOAD_CACHE) && args[0].equalsIgnoreCase("reload")) {
            nickManager.reloadCache();
            return true;
        } else if (sender.hasPermission(PERMISSION_WHO_IS)) {
            String targetName = args[0];
            return runWhoIs(sender, targetName);
        }
        return false;
    }

    private boolean runNickList(CommandSender sender) {
        nickManager.reloadCache();
        String outputHeader = (ChatColor.DARK_PURPLE + "--- " + ChatColor.LIGHT_PURPLE + "Online Players' Nicknames" + ChatColor.DARK_PURPLE + " ---");
        String outputList = "";

        try {
            for (Player player : Bukkit.getOnlinePlayers()) {
                String username = player.getName();
                String nickname = NickManager.user_map.get(username);
                if (nickname != null)
                    outputList = "\n" + ChatColor.GRAY + "- " + username + " is nicknamed " + nickname;
            }
        } catch (Exception e) {
            javaPlugin.logYellow(sender.getName() + " caught excepting reading cache map: " + e.getMessage());
        }
        
        if (!outputList.isBlank()) {
             sender.sendMessage(outputHeader + outputList);
        } else sender.sendMessage(ChatColor.LIGHT_PURPLE + "No one with a nickname is online right now!");

        return true;
    }

    private boolean runWhoIs(CommandSender sender, String targetName) {
        try {
            for (Player player : Bukkit.getOnlinePlayers()) {
                String username = player.getName();
                String nickname = NickManager.user_map.get(username);
                if (nickname != null && nickname.equalsIgnoreCase(targetName)) {
                    sender.sendMessage(ChatColor.GRAY + username + " is nicknamed " + nickname);
                    return true;
                }
            }
            sender.sendMessage(ChatColor.RED + "Could not find nickname for '" + targetName + "'!");
            return true;
        } catch (Exception e) {
            javaPlugin.logYellow(sender.getName() + " caught excepting reading cache map: " + e.getMessage());
            return false;
        }
    }

    private boolean hasPermission(CommandSender sender, String permission) {
        return sender.hasPermission(permission) || sender instanceof ConsoleCommandSender;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command consoleCommand, String alias, String[] args) {
        List<String> list = new ArrayList<>();
        if (hasPermission(sender, PERMISSION_WHO_IS) && args.length == 1) {
            list.clear();

            for (Player player : Bukkit.getOnlinePlayers()) {
                String nickname = NickManager.user_map.get(player.getName());
                if (nickname != null)
                    list.add(nickname);
            }
        }
        return list;
    }
}