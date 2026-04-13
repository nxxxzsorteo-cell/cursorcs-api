package com.example.cursoraddon;

import org.Dwarfed.cursorCs.api.CursorCsApi;
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
                // Start in fake mode and immediately open "main" group.
                boolean ok = api.startCursor(player.getUniqueId(), true, "main");
                player.sendMessage(ok ? "Cursor started." : "Could not start cursor.");
            }
            case "switch" -> {
                if (args.length < 2) {
                    player.sendMessage("Usage: /mycursor switch <group>");
                    return true;
                }

                // Switch to any valid group/alias known by CursorCs.
                boolean ok = api.switchGroup(player.getUniqueId(), args[1]);
                player.sendMessage(ok ? "Group changed." : "Could not change group.");
            }
            case "stop" -> {
                // Stop current cursor session for this player.
                boolean ok = api.stopCursor(player.getUniqueId());
                player.sendMessage(ok ? "Cursor stopped." : "No active cursor session.");
            }
            default -> player.sendMessage("Unknown subcommand.");
        }

        return true;
    }
}
