package com.zyneonstudios.nerotvlive.smp.listener;

import com.zyneonstudios.nerotvlive.smp.SMP;
import com.zyneonstudios.nerotvlive.smp.manager.InventoryManager;
import com.zyneonstudios.nerotvlive.smp.manager.ItemManager;
import com.zyneonstudios.nerotvlive.smp.manager.WaystoneManager;
import com.zyneonstudios.nerotvlive.smp.player.SMPUser;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerWaystoneListener implements Listener {

    @EventHandler
    public void onWaystone(PlayerInteractEvent e) {
        int i = 0;
        if (e.getAction().equals(Action.LEFT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (e.getClickedBlock().getType().equals(Material.REINFORCED_DEEPSLATE)) {
                if (WaystoneManager.hasWaystone(e.getClickedBlock().getLocation())) {
                    e.setCancelled(true);
                    Player p = e.getPlayer();
                    SMPUser u = SMP.getUser(p);
                    if (p.isSneaking()) {
                        if (WaystoneManager.hasWaystone(u)) {
                            if (WaystoneManager.getWaystone(u).toString().equals(e.getClickedBlock().getLocation().toString())) {
                                if (e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                                    if (u.hasAvaliableSlot()) {
                                        WaystoneManager.removeWaystone(e.getClickedBlock().getLocation());
                                        e.getClickedBlock().breakNaturally();
                                        p.getInventory().addItem(ItemManager.waystone(p));
                                    } else {
                                        u.sendErrorMessage("Dein Inventar ist voll§8! §7Mache Platz für deinen Waystone§8, §7bevor du ihn abbaust§8.");
                                    }
                                } else {
                                    InventoryManager.openWaystoneSettings(u);
                                    return;
                                }
                                return;
                            }
                        }
                    }
                    InventoryManager.openWaystoneInventory(u);
                }
            }
        }
    }

    @EventHandler
    public void onWaystoneCreate(BlockPlaceEvent e) {
        if(!WorldChangeEvent.isProtected(e.getBlockPlaced().getLocation())) {
            if(e.getItemInHand().getItemMeta().getDisplayName().equals(ItemManager.waystone(e.getPlayer()).getItemMeta().getDisplayName())) {
                if (e.getItemInHand().equals(ItemManager.waystone(e.getPlayer()))) {
                    WaystoneManager.createWaystone(SMP.getUser(e.getPlayer()), e.getBlockPlaced().getLocation());
                } else {
                    SMP.getUser(e.getPlayer()).sendErrorMessage("Du kannst nur deinen eigenen Waystone setzen§8!");
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onWaystoneInventory(InventoryClickEvent e) {
        if(e.getCurrentItem()!=null) {
            if(e.getCurrentItem().getItemMeta()!=null) {
                Player p = (Player)e.getWhoClicked();
                SMPUser u = SMP.getUser(p);
                if(e.getCurrentItem().getItemMeta().hasLore()) {
                    if(e.getCurrentItem().getLore().get(0).contains("Dies ist der Waystone von")) {
                        if(!e.getCurrentItem().getLore().get(1).contains(p.getName())) {
                            e.setCancelled(true);
                        }
                    }
                }
                if(e.getCurrentItem().equals(ItemManager.waystoneName(p))) {
                    e.setCancelled(true);
                    if(p.getLevel()>9) {
                        InventoryManager.onWaystoneNameInput(u);
                    } else {
                        u.sendErrorMessage("§cDazu hast du nicht genügend Level§8!");
                    }
                } else if(e.getCurrentItem().equals(ItemManager.waystoneSpawn(p))) {
                    e.setCancelled(true);
                    if(p.getLevel()>9) {
                        p.setLevel(p.getLevel()-10);
                        p.teleport(new Location(Bukkit.getWorlds().get(0),0.5,63.0,0.5,180.0F,0.0F));
                        p.playSound(p.getLocation(),Sound.ENTITY_ENDERMAN_TELEPORT,100,100);
                        u.sendMessage("Du hast dich zum §eSpawn§7 teleportiert§8! §8(-10 Level)");
                    } else {
                        u.sendErrorMessage("§cDazu hast du nicht genügend Level§8!");
                    }
                } else if(e.getCurrentItem().equals(ItemManager.waystoneOwn(p))) {
                    e.setCancelled(true);
                    if(p.getLevel()>9) {
                        p.setLevel(p.getLevel()-10);
                        p.teleport(WaystoneManager.getWaystoneWarp(u));
                        p.playSound(p.getLocation(),Sound.ENTITY_ENDERMAN_TELEPORT,100,100);
                        u.sendMessage("Du hast dich zu deinem Waystone teleportiert§8! §8(-10 Level)");
                    } else {
                        u.sendErrorMessage("§cDazu hast du nicht genügend Level§8!");
                    }
                } else if(e.getCurrentItem().equals(ItemManager.publicSetting(p))) {
                    e.setCancelled(true);
                    String setting = WaystoneManager.getPublicSetting(p.getUniqueId());
                    if(setting!=null) {
                        if (setting.equals("yes")) {
                            WaystoneManager.updatePublicSetting(p.getUniqueId(), "semi");
                            e.getClickedInventory().setItem(e.getSlot(), ItemManager.publicSetting(p));
                            p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                        } else if (setting.equals("semi")) {
                            WaystoneManager.updatePublicSetting(p.getUniqueId(), "no");
                            e.getClickedInventory().setItem(e.getSlot(), ItemManager.publicSetting(p));
                            p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                        } else {
                            WaystoneManager.updatePublicSetting(p.getUniqueId(), "yes");
                            e.getClickedInventory().setItem(e.getSlot(), ItemManager.publicSetting(p));
                            p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                        }
                    }
                }
            }
        }
    }
}