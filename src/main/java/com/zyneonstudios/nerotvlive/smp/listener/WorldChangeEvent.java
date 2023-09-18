package com.zyneonstudios.nerotvlive.smp.listener;

import com.destroystokyo.paper.event.block.TNTPrimeEvent;
import com.zyneonstudios.nerotvlive.smp.SMP;
import com.zyneonstudios.nerotvlive.smp.manager.InventoryManager;
import io.papermc.paper.event.player.PlayerItemFrameChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTakeLecternBookEvent;

public class WorldChangeEvent implements Listener {

    @EventHandler
    public void onExplosion(TNTPrimeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if(!SMP.getUser(e.getPlayer()).hasBuildPermission()) {
            if(e.getAction().equals(Action.PHYSICAL)||e.getAction().equals(Action.LEFT_CLICK_BLOCK)||e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if(isProtected(e.getClickedBlock().getLocation())) {
                    if(e.getClickedBlock().getType().equals(Material.REINFORCED_DEEPSLATE)) {
                        e.setCancelled(true);
                        InventoryManager.openWaystoneInventory(SMP.getUser(e.getPlayer()));
                    } else if(e.getClickedBlock().getType().toString().toLowerCase().contains("door")||e.getClickedBlock().getType().toString().toLowerCase().contains("gate")) {
                        e.setCancelled(true);
                        e.getPlayer().sendActionBar("§cEntferne dich bitte etwas weiter vom Spawn.");
                    }
                }
            }
        }
    }

    @EventHandler
    public void onItemFrame(PlayerItemFrameChangeEvent e) {
        if(!SMP.getUser(e.getPlayer()).hasBuildPermission()) {
            if (isProtected(e.getItemFrame().getLocation())) {
                e.getPlayer().sendActionBar("§cEntferne dich bitte etwas weiter vom Spawn.");
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onHangingInteract(HangingBreakByEntityEvent e) {
        if(e.getRemover() instanceof Player p) {
            if(!SMP.getUser(p).hasBuildPermission()) {
                if (isProtected(e.getEntity().getLocation())) {
                    p.sendActionBar("§cEntferne dich bitte etwas weiter vom Spawn.");
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent e) {
        if(!SMP.getUser(e.getPlayer()).hasBuildPermission()) {
            if (isProtected(e.getRightClicked().getLocation())) {
                e.getPlayer().sendActionBar("§cEntferne dich bitte etwas weiter vom Spawn.");
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInteractAtEntity(PlayerInteractAtEntityEvent e) {
        if(!SMP.getUser(e.getPlayer()).hasBuildPermission()) {
            if (isProtected(e.getRightClicked().getLocation())) {
                e.getPlayer().sendActionBar("§cEntferne dich bitte etwas weiter vom Spawn.");
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if(!SMP.getUser(e.getPlayer()).hasBuildPermission()) {
            if (isProtected(e.getBlock().getLocation())) {
                e.getPlayer().sendActionBar("§cEntferne dich bitte etwas weiter vom Spawn.");
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockPlaceEvent e) {
        if(!SMP.getUser(e.getPlayer()).hasBuildPermission()) {
            if (isProtected(e.getBlock().getLocation())) {
                e.getPlayer().sendActionBar("§cEntferne dich bitte etwas weiter vom Spawn.");
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onExplosion(ExplosionPrimeEvent e) {
        if(isProtected(e.getEntity().getLocation())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onTake(PlayerTakeLecternBookEvent e) {
        if(!SMP.getUser(e.getPlayer()).hasBuildPermission()) {
            if (isProtected(e.getLectern().getLocation())) {
                e.getPlayer().sendActionBar("§cEntferne dich bitte etwas weiter vom Spawn.");
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if(isProtected(e.getEntity().getLocation())) {
            e.setCancelled(true);
        }
    }

    public static boolean isProtected(Location location) {
        if(location.getWorld().getName().equals("welt")) {
            return location.distance(Bukkit.getWorlds().get(0).getSpawnLocation()) < 51;
        } else {
            return false;
        }
    }
}