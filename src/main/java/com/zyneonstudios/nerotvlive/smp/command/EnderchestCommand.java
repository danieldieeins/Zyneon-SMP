package com.zyneonstudios.nerotvlive.smp.command;

import com.zyneonstudios.api.utils.Strings;
import com.zyneonstudios.nerotvlive.smp.SMP;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class EnderchestCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender s, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(s instanceof Player p) {
            if(p.hasPermission("zyneon.premium")) {
                if(args.length == 0) {
                    p.openInventory(p.getEnderChest());
                    p.playSound(p.getLocation(),Sound.BLOCK_ENDER_CHEST_OPEN,100,100);
                } else {
                    if(p.hasPermission("zyneon.team")) {
                        if(Bukkit.getPlayer(args[0])!=null) {
                            p.openInventory(Bukkit.getPlayer(args[0]).getEnderChest());
                            p.playSound(p.getLocation(),Sound.BLOCK_ENDER_CHEST_OPEN,100,100);
                        } else {
                            SMP.getUser(p).sendErrorMessage(Strings.playerNotFound(args[0]));
                        }
                    } else {
                        p.openInventory(p.getEnderChest());
                        p.playSound(p.getLocation(),Sound.BLOCK_ENDER_CHEST_OPEN,100,100);
                    }
                }
            } else {
                p.sendMessage("§6Dies ist ein Premium-Feature§8! §7Werde ein §ePatron§7 von §cnerotvlive§7 um Zugang zu diesen Features zu erhalten§8!");
                p.sendMessage("§9§nhttps://patreon.com/nerotvlive");
                p.playSound(p.getLocation(),Sound.BLOCK_NOTE_BLOCK_PLING,100,100);
            }
        } else {
            s.sendMessage(Strings.needPlayer());
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender s, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(s.hasPermission("zyneon.team")) {
            if(args.length == 1) {
                List<String> completer = new ArrayList<>();
                for(Player all:Bukkit.getOnlinePlayers()) {
                    completer.add(all.getName());
                }
                return completer;
            }
        }
        return new ArrayList<>();
    }
}
