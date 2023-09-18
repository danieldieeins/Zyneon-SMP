package com.zyneonstudios.nerotvlive.smp.command;

import com.zyneonstudios.api.paper.Zyneon;
import com.zyneonstudios.api.utils.Strings;
import com.zyneonstudios.nerotvlive.smp.SMP;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MaintenanceCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender s, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(s.hasPermission("zyneon.leading")) {
            SMP.isMaintenance = !SMP.isMaintenance;
            SMP.config.set("Settings.isMaintenance",SMP.isMaintenance);
            if(s instanceof Player p) {
                SMP.getUser(p).sendMessage("Der Wartungsmodus steht nun auf§8: §e"+SMP.isMaintenance);
                return false;
            }
            Zyneon.getZyneonServer().sendMessage("Der Wartungsmodus steht nun auf§8: §e"+SMP.isMaintenance);
        } else {
            if(s instanceof Player p) {
                SMP.getUser(p).sendErrorMessage(Strings.noPerms());
                return false;
            }
            Zyneon.getZyneonServer().sendErrorMessage(Strings.noPerms());
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender s, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        return new ArrayList<>();
    }
}
