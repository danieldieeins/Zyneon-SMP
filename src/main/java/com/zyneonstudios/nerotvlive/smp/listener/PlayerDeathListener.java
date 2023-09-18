package com.zyneonstudios.nerotvlive.smp.listener;

import com.destroystokyo.paper.event.player.PlayerPostRespawnEvent;
import com.zyneonstudios.api.paper.Zyneon;
import com.zyneonstudios.nerotvlive.smp.manager.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.ArrayList;

public class PlayerDeathListener implements Listener {

    ArrayList<Player> stoneBack = new ArrayList<>();

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getPlayer();
        Location location = p.getLocation();
        Zyneon.getZyneonServer().sendWarnMessage(p.getName()+" ist bei X"+location.getX()+" Y"+location.getY()+" Z"+location.getZ()+" ("+location.getWorld().getName()+") gestorben!");
        if (e.getDrops().contains(ItemManager.waystone(p))) {
            e.getDrops().remove(ItemManager.waystone(p));
            stoneBack.add(p);
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        if(e.getRespawnLocation().equals(new Location(Bukkit.getWorlds().get(0),0.5,101.0999984741211,0.5,0.0F,0.0F))) {
            e.setRespawnLocation(new Location(Bukkit.getWorlds().get(0),0.5,63,0.5,180.0F,0.0F));
        }
    }

    @EventHandler
    public void postRespawn(PlayerPostRespawnEvent e) {
        Player p = e.getPlayer();
        if (stoneBack.contains(p)) {
            stoneBack.remove(p);
            e.getPlayer().getInventory().addItem(ItemManager.waystone(p));
        }
    }
}