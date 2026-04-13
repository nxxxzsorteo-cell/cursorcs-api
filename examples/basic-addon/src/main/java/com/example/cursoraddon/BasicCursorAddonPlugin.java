package com.example.cursoraddon;

import org.Dwarfed.cursorCs.api.CursorCsApi;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class BasicCursorAddonPlugin extends JavaPlugin {
    private CursorCsApi cursorApi;

    @Override
    public void onEnable() {
        // Service lookup through Bukkit. This is the expected integration path.
        cursorApi = Bukkit.getServicesManager().load(CursorCsApi.class);
        if (cursorApi == null) {
            getLogger().warning("CursorCs API not found. Disabling plugin.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // You will probably use this command a lot during testing.
        // What did you expect, this is a public API demo plugin.
        CursorCommand command = new CursorCommand(cursorApi);
        if (getCommand("mycursor") != null) {
            getCommand("mycursor").setExecutor(command);
        } else {
            getLogger().warning("Command 'mycursor' is missing from plugin.yml.");
        }
    }

    @Override
    public void onDisable() {
        cursorApi = null;
    }
}
