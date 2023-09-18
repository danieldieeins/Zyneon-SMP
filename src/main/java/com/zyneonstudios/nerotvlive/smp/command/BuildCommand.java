package com.zyneonstudios.nerotvlive.smp.command;

import com.zyneonstudios.api.utils.Strings;
import com.zyneonstudios.nerotvlive.smp.SMP;
import com.zyneonstudios.nerotvlive.smp.player.SMPUser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;

public class BuildCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender s, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(s instanceof Player p) {
            SMPUser u = SMP.getUser(p);
            if(p.hasPermission("zyneon.team")) {
                u.setHasBuildPermission(!u.hasBuildPermission());
                u.sendMessage("Build-Mode steht auf§8: §e"+u.hasBuildPermission());
            } else {
                u.sendErrorMessage(Strings.noPerms());
            }
        } else {
            s.sendMessage(Strings.needPlayer());
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender s, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        return new ArrayList<>();
    }
}
