package com.zyneonstudios.nerotvlive.smp.listener;

import com.zyneonstudios.nerotvlive.smp.manager.ItemManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class PlayerInventoryListener implements Listener {

    @EventHandler
    public void onInventory(InventoryClickEvent e) {
        Player p = (Player)e.getWhoClicked();
        if(e.getCurrentItem()!=null) {
            if(e.getCurrentItem().getItemMeta()!=null) {
                if(e.getCurrentItem().equals(ItemManager.placeholder())) {
                    e.setCancelled(true);
                }
            }
        }
    }
}