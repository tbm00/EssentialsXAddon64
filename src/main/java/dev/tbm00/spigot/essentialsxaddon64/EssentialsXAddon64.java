package dev.tbm00.spigot.essentialsxaddon64;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.earth2me.essentials.Essentials;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import dev.tbm00.spigot.essentialsxaddon64.command.NicksCommand;
import dev.tbm00.spigot.essentialsxaddon64.hook.PAPIHook;
import dev.tbm00.spigot.essentialsxaddon64.listener.PlayerConnection;
import dev.tbm00.spigot.essentialsxaddon64.listener.PlayerWorldChange;

public class EssentialsXAddon64 extends JavaPlugin {
    private NickManager nickManager;
    private ConfigHandler configHandler;
    private PAPIHook papiHook;
    private Essentials essHook;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        final PluginDescriptionFile pdf = getDescription();

		log(
            ChatColor.DARK_PURPLE + "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-",
            pdf.getName() + " v" + pdf.getVersion() + " created by tbm00",
            ChatColor.DARK_PURPLE + "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-"
		);

        configHandler = new ConfigHandler(this);
        setupHooks();

        if (configHandler.isNicknamesEnabled()) {
            nickManager = new NickManager(this, configHandler, papiHook);
            getCommand("nicks").setExecutor(new NicksCommand(this, nickManager));
            getServer().getPluginManager().registerEvents(new PlayerConnection(this, nickManager), this);
            logGreen("Nickname extension enabled.");
        } else {
            logYellow("Nickname extension disabled.");
        }

        if (configHandler.isWorldChangeListenerEnabled()) {
            getServer().getPluginManager().registerEvents(new PlayerWorldChange(this, essHook), this);
            logGreen("World change listener enabled.");
        } else {
            logYellow("World change listener disabled.");
        }
    }

    private void setupHooks() {
        if (!setupPlaceholders()) {
            logRed("PAPI hook failed -- disabling plugin!");
            disablePlugin();
            return;
        }

        if (!setupEssentials()) {
            logRed("Essentials hook failed -- disabling plugin!");
            disablePlugin();
            return;
        }
    }

    private boolean setupEssentials() {
        if (!isPluginAvailable("Essentials")) return false;

        Plugin essp = Bukkit.getPluginManager().getPlugin("Essentials");
        if (essp.isEnabled() && essp instanceof Essentials)
            essHook = (Essentials) essp;
        else return false;

        logGreen( "Essentials hooked.");
        return true;
    }

    private boolean setupPlaceholders() {
        if (!isPluginAvailable("PlaceholderAPI")) return false;

        papiHook = new PAPIHook(this, configHandler);
        papiHook.register();

        logGreen("PlaceholderAPI hooked.");
        return true;
    }

    private boolean isPluginAvailable(String pluginName) {
		final Plugin plugin = getServer().getPluginManager().getPlugin(pluginName);
		return plugin != null && plugin.isEnabled();
	}

    public void log(String... strings) {
		for (String s : strings)
            getServer().getConsoleSender().sendMessage("[EssentialsXAddon64] " + ChatColor.LIGHT_PURPLE + s);
	}

    public void logGreen(String... strings) {
		for (String s : strings)
            getServer().getConsoleSender().sendMessage("[EssentialsXAddon64] " + ChatColor.GREEN + s);
	}

    public void logWhite(String... strings) {
		for (String s : strings)
            getServer().getConsoleSender().sendMessage("[EssentialsXAddon64] " + ChatColor.WHITE + s);
	}

    public void logYellow(String... strings) {
		for (String s : strings)
            getServer().getConsoleSender().sendMessage("[EssentialsXAddon64] " + ChatColor.YELLOW + s);
	}

    public void logRed(String... strings) {
		for (String s : strings)
            getServer().getConsoleSender().sendMessage("[EssentialsXAddon64] " + ChatColor.RED + s);
	}

    private void disablePlugin() {
        getServer().getPluginManager().disablePlugin(this);
        logRed("EssentialsXAddon64 disabled..!");
    }
}