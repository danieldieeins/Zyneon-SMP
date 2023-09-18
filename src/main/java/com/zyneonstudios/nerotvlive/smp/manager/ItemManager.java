package com.zyneonstudios.nerotvlive.smp.manager;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.ArrayList;

public class ItemManager {

    public static ItemStack placeholder() {
        ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName("§0");
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack waystoneName(Player player) {
        ItemStack item = new ItemStack(Material.NAME_TAG);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName("Namen eingeben");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§7Geb einen Spielernamen ein,");
        lore.add("§7um zu seinem/ihrem Waystone");
        lore.add("§7zu gelangen.");
        lore.add("§0");
        if(player.getLevel()>9) {
            lore.add("§2Kosten: §a10 Level");
        } else {
            lore.add("§4Kosten: §c10 Level");
        }
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack waystoneOwn(Player player) {
        ItemStack item = new ItemStack(Material.NETHER_STAR);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName("Dein Waystone");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§7Klicke hier, um zu deinem");
        lore.add("§7eigenen Waystone zu");
        lore.add("§7gelangen.");
        lore.add("§0");
        if(player.getLevel()>9) {
            lore.add("§2Kosten: §a10 Level");
        } else {
            lore.add("§4Kosten: §c10 Level");
        }
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack waystoneSpawn(Player player) {
        ItemStack item = new ItemStack(Material.COMPASS);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName("Spawn");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§7Klicke hier, um zum Spawn");
        lore.add("§7zu gelangen.");
        lore.add("§0");
        if(player.getLevel()>9) {
            lore.add("§2Kosten: §a10 Level");
        } else {
            lore.add("§4Kosten: §c10 Level");
        }
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack waystone(Player player) {
        ItemStack item = new ItemStack(Material.REINFORCED_DEEPSLATE);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName("§bWaystone");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§7Dies ist der Waystone von");
        lore.add("§e"+player.getName()+"§7.");
        lore.add("§0");
        lore.add("§7Platziere den Waystone, um");
        lore.add("§7ihn einzustellen und zu");
        lore.add("§7aktivieren§7.");
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack publicSetting(Player player) {
        String setting = WaystoneManager.getPublicSetting(player.getUniqueId());
        ItemStack item;
        ItemMeta itemMeta;
        ArrayList<String> lore = new ArrayList<>();
        if(setting!=null) {
            if (setting.equals("yes")) {
                item = new ItemStack(Material.LIME_CONCRETE);
                itemMeta = item.getItemMeta();
                lore.add("§aÖffentlich§8: §7Jeder");
                lore.add("§7kann sich zu diesem");
                lore.add("§7Waystone teleportieren.");
                lore.add("§0");
                lore.add("§fKlicke zum Ändern der Einstellung");
            } else if (setting.equals("semi")) {
                item = new ItemStack(Material.YELLOW_CONCRETE);
                itemMeta = item.getItemMeta();
                lore.add("§eVertraut§8: §7Jeder");
                lore.add("§7der auf deiner §eTrust");
                lore.add("§eListe §7steht, kann");
                lore.add("§7sich zu diesem");
                lore.add("§7Waystone teleportieren.");
                lore.add("§0");
                lore.add("§fKlicke zum Ändern der Einstellung");
            } else {
                item = new ItemStack(Material.RED_CONCRETE);
                itemMeta = item.getItemMeta();
                lore.add("§cPrivat§8: §7Nur du");
                lore.add("§7kannst dich zu diesem");
                lore.add("§7Waystone teleportieren.");
                lore.add("§0");
                lore.add("§fKlicke zum Ändern der Einstellung");
            }
        } else {
            return null;
        }
        itemMeta.setDisplayName("Öffentlichkeit");
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        return item;
    }
}