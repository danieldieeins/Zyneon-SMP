package com.zyneonstudios.nerotvlive.smp.player;

import com.zyneonstudios.api.paper.utils.user.User;
import com.zyneonstudios.nerotvlive.smp.manager.WaystoneManager;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class SMPUser extends User {

    private boolean hasBuildPermission;

    public SMPUser(UUID uuid) {
        super(uuid);
        hasBuildPermission = false;
    }

    public void setHasBuildPermission(boolean hasBuildPermission) {
        this.hasBuildPermission = hasBuildPermission;
    }

    public boolean hasWaystone() {
        return WaystoneManager.hasWaystone(this);
    }

    public boolean hasBuildPermission() {
        return hasBuildPermission;
    }

    public void destroy() {
        hasBuildPermission = false;
        disconnect();
        System.gc();
    }

    public boolean hasAvaliableSlot() {
        Inventory inv = getPlayer().getInventory();
        boolean check=false;
        for (ItemStack item: inv.getContents()) {
            if(item == null) {
                check = true;
                break;
            }
        }
        return check;
    }
}
