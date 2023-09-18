package com.zyneonstudios.nerotvlive.smp.command;

import com.zyneonstudios.api.utils.Strings;
import com.zyneonstudios.nerotvlive.smp.SMP;
import com.zyneonstudios.nerotvlive.smp.player.SMPUser;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TeleportCommand implements CommandExecutor, TabCompleter {

    private void sendSyntax(CommandSender s) {
        if(s instanceof Player p) {
            SMP.getUser(p).sendErrorMessage("§4Fehler: §c/tp [x y z/spieler] §7(x y z/spieler/welt)");
        } else {
            s.sendMessage("§4Fehler: §/tp [spieler] [x y z/spieler]");
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender s, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (s instanceof Player p) {
            SMPUser u = SMP.getUser(p);
            if (args.length == 0) {
                sendSyntax(p);
            } else if (args.length == 1) {
                if (args[0].contains("@")) {
                    p.performCommand("minecraft:teleport " + args[0]);
                    return false;
                }
                if (Bukkit.getPlayer(args[0]) != null) {
                    p.teleport(Bukkit.getPlayer(args[0]).getLocation());
                    u.sendMessage("Du bist nun bei §e" + Bukkit.getPlayer(args[0]).getName() + "§8.");
                } else {
                    u.sendErrorMessage(Strings.playerNotFound(args[0]));
                }
            } else if (args.length == 2) {
                if (args[0].contains("@") || args[1].contains("@")) {
                    p.performCommand("minecraft:teleport " + args[0] + " " + args[1]);
                    return false;
                }
                if (Bukkit.getPlayer(args[0]) != null) {
                    if (Bukkit.getPlayer(args[1]) != null) {
                        Player p0 = Bukkit.getPlayer(args[0]);
                        Player p1 = Bukkit.getPlayer(args[1]);
                        p0.teleport(p1.getLocation());
                        u.sendMessage("Du hast §e" + p0.getName() + "§7 zu §e" + p1.getName() + "§7 teleportiert§8.");
                        return false;
                    } else {
                        u.sendErrorMessage(Strings.playerNotFound(args[1]));
                    }
                } else {
                    u.sendErrorMessage(Strings.playerNotFound(args[0]));
                }
                sendSyntax(p);
            } else if (args.length == 3) {
                if (args[0].contains("@") || args[1].contains("@") || args[2].contains("@")) {
                    p.performCommand("minecraft:teleport " + args[0] + " " + args[1] + " " + args[2]);
                    return false;
                }
                try {
                    double x = Double.parseDouble(args[0]);
                    double y = Double.parseDouble(args[1]);
                    double z = Double.parseDouble(args[2]);
                    p.teleport(new Location(p.getWorld(), x, y, z));
                    u.sendMessage("Du bist nun bei §e" + p.getLocation().getX() + " " + p.getLocation().getY() + " " + p.getLocation().getZ() + "§7 in der Welt §e" + p.getWorld().getName() + "§8.");
                } catch (NumberFormatException e) {
                    u.sendErrorMessage("Diese Position ist ungültig§8.");
                }
            } else if (args.length == 4) {
                if (args[0].contains("@") || args[1].contains("@") || args[2].contains("@") || args[3].contains("@")) {
                    p.performCommand("minecraft:teleport " + args[0] + " " + args[1] + " " + args[2] + " " + args[3]);
                    return false;
                }
                if (Bukkit.getPlayer(args[0]) != null) {
                    try {
                        double x = Double.parseDouble(args[1]);
                        double y = Double.parseDouble(args[2]);
                        double z = Double.parseDouble(args[3]);
                        Player t = Bukkit.getPlayer(args[0]);
                        t.teleport(new Location(p.getWorld(), x, y, z));
                        u.sendMessage("Du hast §e" + p.getName() + "§7 zu §e" + t.getLocation().getX() + " " + t.getLocation().getY() + " " + t.getLocation().getZ() + "§7 in die Welt §e" + t.getWorld().getName() + "§7 teleportiert§8.");
                    } catch (NumberFormatException e) {
                        u.sendErrorMessage("Diese Position ist ungültig§8.");
                    }
                } else {
                    u.sendErrorMessage(Strings.playerNotFound(args[0]));
                }
            } else if (args.length == 5) {
                try {
                    float yaw = Float.parseFloat(args[3]);
                    float pitch = Float.parseFloat(args[4]);
                    p.performCommand("tp " + args[0] + " " + args[1] + " " + args[2]);
                    p.teleport(new Location(p.getLocation().getWorld(), p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), yaw, pitch));
                } catch (NumberFormatException e) {
                    u.sendErrorMessage("Diese Position ist ungültig§8.");
                }
            } else if (args.length == 6) {
                try {
                    float yaw = Float.parseFloat(args[4]);
                    float pitch = Float.parseFloat(args[5]);
                    if (Bukkit.getPlayer(args[0]) == null) {
                        u.sendErrorMessage(Strings.playerNotFound(args[0]));
                        return false;
                    }
                    Player t = Bukkit.getPlayer(args[0]);
                    p.performCommand("tp " + args[0] + " " + args[1] + " " + args[2] + " " + args[3]);
                    t.teleport(new Location(t.getLocation().getWorld(), t.getLocation().getX(), t.getLocation().getY(), t.getLocation().getZ(), yaw, pitch));
                } catch (NumberFormatException e) {
                    u.sendErrorMessage("Diese Position ist ungültig§8.");
                }
            } else {
                sendSyntax(p);
            }
        } else {
            StringBuilder m = new StringBuilder();
            for (String arg : args) {
                m.append(arg).append(" ");
            }
            Bukkit.dispatchCommand(s, "tp " + m);
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender s, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(s instanceof Player p) {
            List<String> completer = new ArrayList<>();
            if (args.length == 1) {
                completer.add(p.getLocation().getX()+"");
                completer.add("x");
                for(Player all:Bukkit.getOnlinePlayers()) {
                    completer.add(all.getName());
                }
            } else if(args.length == 2) {
                completer.add(p.getLocation().getY()+"");
                completer.add("y");
                completer.add(p.getLocation().getX()+"");
                completer.add("x");
                for(Player all:Bukkit.getOnlinePlayers()) {
                    completer.add(all.getName());
                }
            } else if(args.length == 3) {
                completer.add(p.getLocation().getZ()+"");
                completer.add("z");
                completer.add(p.getLocation().getY()+"");
                completer.add("y");
            } else if(args.length == 4) {
                completer.add(p.getLocation().getZ() + "");
                completer.add("z");
                completer.add(p.getLocation().getYaw()+"");
                completer.add("yaw");
            } else if(args.length == 5) {
                completer.add(p.getLocation().getPitch()+"");
                completer.add("pitch");
                completer.add(p.getLocation().getYaw()+"");
                completer.add("yaw");
            } else if(args.length == 6) {
                completer.add(p.getLocation().getPitch()+"");
                completer.add("pitch");
            }
            return completer;
        }
        return new ArrayList<>();
    }
}