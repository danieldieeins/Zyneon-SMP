package com.zyneonstudios.nerotvlive.smp.listener;

import com.zyneonstudios.nerotvlive.smp.SMP;
import com.zyneonstudios.nerotvlive.smp.player.SMPUser;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        SMPUser u = SMP.getUser(p);
        e.setQuitMessage("");
        Bukkit.broadcastMessage("§8« §c"+p.getName());
        u.destroy(); SMP.users.remove(p.getUniqueId());
    }
}
