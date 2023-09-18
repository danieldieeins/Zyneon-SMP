package com.zyneonstudios.nerotvlive.smp.manager;

import com.zyneonstudios.nerotvlive.smp.SMP;
import com.zyneonstudios.nerotvlive.smp.player.SMPUser;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.popcraft.lwctrust.LWCTrust;

import java.util.List;
import java.util.UUID;

public class InventoryManager {

    public static void openWaystoneInventory(SMPUser u) {
        Inventory inventory = Bukkit.createInventory(null, InventoryType.HOPPER,"Waystone");
        inventory.setItem(0,ItemManager.placeholder());
        inventory.setItem(1,ItemManager.placeholder());
        inventory.setItem(2,ItemManager.placeholder());
        inventory.setItem(3,ItemManager.placeholder());
        inventory.setItem(4,ItemManager.placeholder());
        if(u.hasWaystone()) {
            inventory.setItem(0,ItemManager.waystoneName(u.getPlayer()));
            inventory.setItem(2,ItemManager.waystoneOwn(u.getPlayer()));
            inventory.setItem(4,ItemManager.waystoneSpawn(u.getPlayer()));
        } else {
            inventory.setItem(1,ItemManager.waystoneName(u.getPlayer()));
            inventory.setItem(3,ItemManager.waystoneSpawn(u.getPlayer()));
        }
        u.getPlayer().openInventory(inventory);
        u.getPlayer().playSound(u.getPlayer().getLocation(),Sound.BLOCK_END_PORTAL_FRAME_FILL,100,100);
    }

    public static void openWaystoneSettings(SMPUser u) {
        Inventory inventory = Bukkit.createInventory(null, InventoryType.HOPPER,"Waystone Einstellungen");
        inventory.setItem(0,ItemManager.placeholder());
        inventory.setItem(1,ItemManager.placeholder());
        inventory.setItem(2,ItemManager.publicSetting(u.getPlayer()));
        inventory.setItem(3,ItemManager.placeholder());
        inventory.setItem(4,ItemManager.placeholder());
        u.getPlayer().openInventory(inventory);
        u.getPlayer().playSound(u.getPlayer().getLocation(),Sound.BLOCK_END_PORTAL_FRAME_FILL,100,100);
    }

    public static void onWaystoneNameInput(SMPUser u) {
        u.getPlayer().playSound(u.getPlayer().getLocation(),Sound.ENTITY_CHICKEN_EGG,100,100);
        new AnvilGUI.Builder().onComplete((player, text) -> {
            OfflinePlayer t = Bukkit.getOfflinePlayer(text);
            if(WaystoneManager.hasWaystone(t.getUniqueId())) {
                if(WaystoneManager.getPublicSetting(t.getUniqueId()).equals("yes")) {


                    if (player.getLevel() > 9) {
                        player.setLevel(player.getLevel() - 10);
                        player.teleport(WaystoneManager.getWaystoneWarp(t.getUniqueId()));
                        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 100, 100);
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_WORK_CARTOGRAPHER, 100, 100);
                        u.sendMessage("Du hast dich zu dem Waystone von §e" + text + "§7 teleportiert§8! §8(-10 Level)");
                        return AnvilGUI.Response.close();
                    } else {
                        u.sendErrorMessage("§cDazu hast du nicht genügend Level§8!");
                    }


                } else if(WaystoneManager.getPublicSetting(t.getUniqueId()).equals("semi")) {
                    List<UUID> trusted = LWCTrust.getInstance().getTrustCache().load(t.getUniqueId());
                    if(trusted.contains(player.getUniqueId())) {


                        if (player.getLevel() > 9) {
                            player.setLevel(player.getLevel() - 10);
                            player.teleport(WaystoneManager.getWaystoneWarp(t.getUniqueId()));
                            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 100, 100);
                            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_WORK_CARTOGRAPHER, 100, 100);
                            u.sendMessage("Du hast dich zu dem Waystone von §e" + text + "§7 teleportiert§8! §8(-10 Level)");
                            return AnvilGUI.Response.close();
                        } else {
                            u.sendErrorMessage("§cDazu hast du nicht genügend Level§8!");
                        }


                    } else {
                        u.sendErrorMessage("§cDieser Waystone ist privat§8!");
                    }
                } else {
                    u.sendErrorMessage("§cDieser Waystone ist privat§8!");
                }
            } else {
                return AnvilGUI.Response.text("Waystone nicht gefunden...");
            }
            return AnvilGUI.Response.close();
        }).itemLeft(new ItemStack(Material.PAPER)).text("Geb den Namen ein...").title("Gib den Namen ein...").plugin(SMP.getInstance()).open(u.getPlayer());
    }
}