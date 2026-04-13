# Tutorial: Start, Switch Group, Stop

This is the most common flow for CursorCs addons.

## Goal

From your plugin command:
1. start cursor for the player
2. switch to a UI group
3. stop cursor on demand

## Example command executor

```java
package com.example.cursoraddon;

import org.Dwarfed.cursorCs.api.CursorCsApi;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class CursorCommand implements CommandExecutor {
    private final CursorCsApi api;

    public CursorCommand(CursorCsApi api) {
        this.api = api;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Player only.");
            return true;
        }

        if (args.length == 0) {
            player.sendMessage("/mycursor <start|switch|stop> [group]");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "start" -> {
                boolean ok = api.startCursor(player.getUniqueId(), true, "main");
                player.sendMessage(ok ? "Cursor started." : "Could not start cursor.");
            }
            case "switch" -> {
                if (args.length < 2) {
                    player.sendMessage("Usage: /mycursor switch <group>");
                    return true;
                }
                boolean ok = api.switchGroup(player.getUniqueId(), args[1]);
                player.sendMessage(ok ? "Group changed." : "Could not change group.");
            }
            case "stop" -> {
                boolean ok = api.stopCursor(player.getUniqueId());
                player.sendMessage(ok ? "Cursor stopped." : "No active cursor session.");
            }
            default -> player.sendMessage("Unknown subcommand.");
        }
        return true;
    }
}
```

## Service bootstrap (plugin enable)

```java
CursorCsApi api = Bukkit.getServicesManager().load(CursorCsApi.class);
if (api == null) {
    getLogger().warning("CursorCs API not available. Disabling addon.");
    getServer().getPluginManager().disablePlugin(this);
    return;
}

getCommand("mycursor").setExecutor(new CursorCommand(api));
```

## Practical tips

- Cache the API reference at enable time.
- Treat `false` responses as valid outcomes and message the user clearly.
- Keep your addon behavior stable when CursorCs is absent by failing gracefully.
