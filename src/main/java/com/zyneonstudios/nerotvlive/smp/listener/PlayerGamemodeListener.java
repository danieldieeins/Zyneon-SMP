package com.zyneonstudios.nerotvlive.smp.listener;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

public class PlayerGamemodeListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onGamemode(PlayerGameModeChangeEvent e) {
        if(e.getNewGameMode()!=GameMode.SPECTATOR) {
            e.getPlayer().setGameMode(GameMode.SPECTATOR);
            e.setCancelled(true);
        }
    }
}