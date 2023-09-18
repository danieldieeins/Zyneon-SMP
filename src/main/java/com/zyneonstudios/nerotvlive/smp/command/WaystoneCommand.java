package com.zyneonstudios.nerotvlive.smp.command;

import com.zyneonstudios.api.utils.Strings;
import com.zyneonstudios.nerotvlive.smp.SMP;
import com.zyneonstudios.nerotvlive.smp.manager.ItemManager;
import com.zyneonstudios.nerotvlive.smp.player.SMPUser;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class WaystoneCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender s, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(s instanceof Player p) {
            SMPUser u = SMP.getUser(p);
            if(p.isOp()) {
                if(args.length == 1) {
                    if(Bukkit.getPlayer(args[0])!=null) {
                        p.getInventory().addItem(ItemManager.waystone(Bukkit.getPlayer(args[0])));
                    } else {
                        u.sendErrorMessage(Strings.playerNotFound(args[0]));
                    }
                    return false;
                }
                p.getInventory().addItem(ItemManager.waystone(p));
                u.sendMessage("Du hast deinen Waystone erhalten§8.");
            } else {
                u.sendErrorMessage(Strings.noPerms());
            }
        } else {
            s.sendMessage(Strings.needPlayer());
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender s, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        return new ArrayList<>();
    }
}
