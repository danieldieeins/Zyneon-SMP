package com.zyneonstudios.nerotvlive.smp.command;

import com.zyneonstudios.api.utils.Strings;
import com.zyneonstudios.nerotvlive.smp.SMP;
import com.zyneonstudios.nerotvlive.smp.player.SMPUser;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class WorldCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender s, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(s instanceof Player p) {
            SMPUser u = SMP.getUser(p);
            if(p.hasPermission("zyneon.team")) {
                if(args.length == 0) {
                    u.sendMessage("§7Du bist in der Welt §e"+p.getWorld().getName()+"§8.");
                    u.sendMessage("§fAndere Möglichkeiten: §7/world [welt_name] §8o. §7/world load§8/§7create [welt_name]");
                } else if(args.length == 1) {
                    if(Bukkit.getWorld(args[0])!=null) {
                        p.teleport(Bukkit.getWorld(args[0]).getSpawnLocation());
                        u.sendMessage("Du bist nun am Spawn von §e"+p.getWorld().getName()+"§8.");
                    } else {
                        u.sendErrorMessage("§cDiese Welt gibt es nicht§8!");
                    }
                } else {
                    if(args[0].equalsIgnoreCase("create")||args[0].equalsIgnoreCase("load")) {
                        if(Bukkit.getWorld(args[1])==null) {
                            u.sendWarnMessage("Die Welt wird erstellt, dies kann ein wenig dauern!");
                            Bukkit.createWorld(new WorldCreator(args[1]));
                            p.teleport(Bukkit.getWorld(args[1]).getSpawnLocation());
                            u.sendMessage("Du bist nun am Spawn von §e"+p.getWorld().getName()+"§8.");
                        } else {
                            u.sendErrorMessage("§cDiese Welt gibt es bereits§8!");
                        }
                    } else {
                        p.performCommand("world");
                    }
                }
            } else {
                u.sendErrorMessage(Strings.noPerms());
            }
        } else {
            if(args.length == 2) {
                if(args[0].equalsIgnoreCase("load")||args[0].equalsIgnoreCase("create")) {
                    if(Bukkit.getWorld(args[1])==null) {
                        s.sendMessage("§eDie Welt wird erstellt, dies kann ein wenig dauern!");
                        Bukkit.createWorld(new WorldCreator(args[1]));
                        s.sendMessage("§7Die Welt §e"+args[1]+"§7 wurde erfolgreich erstellt§8.");
                    } else {
                        s.sendMessage("§4Fehler: §cDiese Welt gibt es bereits§8!");
                    }
                    return false;
                }
            }
            s.sendMessage("§4Fehler: §c/world create/load [welt_name]");
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender s, Command cmd, String label, String[] args) {
        List<String> completer = new ArrayList<>();
        if(s.hasPermission("zyneon.team")) {
            if (args.length == 1) {
                completer.add("load");
                completer.add("create");
                for (World worlds : Bukkit.getWorlds()) {
                    completer.add(worlds.getName());
                }
            }
        }
        return completer;
    }
}
