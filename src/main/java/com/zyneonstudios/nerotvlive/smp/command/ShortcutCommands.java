package com.zyneonstudios.nerotvlive.smp.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ShortcutCommands implements CommandExecutor {

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(command.getName().equalsIgnoreCase("autoclose")) {
            Bukkit.dispatchCommand(sender,"lwc flag autoclose on");
        } else if(command.getName().equalsIgnoreCase("redstone")) {
            Bukkit.dispatchCommand(sender, "lwc flag redstone on");
        }
        return false;
    }
}
