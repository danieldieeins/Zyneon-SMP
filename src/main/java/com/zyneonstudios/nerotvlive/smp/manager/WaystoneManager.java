package com.zyneonstudios.nerotvlive.smp.manager;

import com.zyneonstudios.nerotvlive.smp.SMP;
import com.zyneonstudios.nerotvlive.smp.player.SMPUser;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class WaystoneManager {

    public static boolean checkTable() {
        boolean rtn;
        try {
            PreparedStatement ps = SMP.database.prepareStatement("CREATE TABLE IF NOT EXISTS waystones (UUID TEXT,WORLD TEXT,SPAWN TEXT,LOCATION TEXT,PUBLIC TEXT)");
            ps.executeUpdate();
            rtn = true;
        } catch (SQLException e) {
            rtn = false;
        }
        return rtn;
    }

    public static String encryptLocation(Location location) {
        return "W:"+location.getWorld().getName()+",X:"+location.getX()+",Y:"+location.getY()+",Z:"+location.getZ()+",y:"+location.getYaw()+",p:"+location.getPitch()+"-.";
    }

    public static Location decryptLocation(String encryptedLocation) {
        String sW = encryptedLocation.substring(encryptedLocation.indexOf("W:") + 1);
        sW = sW.substring(0, sW.indexOf(",X:")).replace(":","");
        String sX = encryptedLocation.substring(encryptedLocation.indexOf(",X:") + 1);
        sX = sX.substring(0, sX.indexOf(",Y:")).replace("X:","");
        String sY = encryptedLocation.substring(encryptedLocation.indexOf(",Y:") + 1);
        sY = sY.substring(0, sY.indexOf(",Z:")).replace("Y:","");
        String sZ = encryptedLocation.substring(encryptedLocation.indexOf(",Z:") + 1);
        sZ = sZ.substring(0, sZ.indexOf(",y:")).replace("Z:","");
        String sy = encryptedLocation.substring(encryptedLocation.indexOf(",y:") + 1);
        sy = sy.substring(0, sy.indexOf(",p:")).replace("y:","");
        String sp = encryptedLocation.substring(encryptedLocation.indexOf(",p:") + 1);
        sp = sp.substring(0, sp.indexOf("-.")).replace("p:","");
        return new Location(Bukkit.getWorld(sW), Double.parseDouble(sX), Double.parseDouble(sY), Double.parseDouble(sZ), (float) Double.parseDouble(sy), (float) Double.parseDouble(sp));
    }

    public static boolean hasWaystone(Location location) {
        if(checkTable()) {
            try {
                PreparedStatement ps = SMP.database.prepareStatement("SELECT LOCATION FROM waystones WHERE LOCATION = ?");
                ps.setString(1, encryptLocation(location));
                ResultSet rs = ps.executeQuery();
                return rs.next();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean hasWaystone(UUID uuid) {
        if(checkTable()) {
            try {
                PreparedStatement ps = SMP.database.prepareStatement("SELECT LOCATION FROM waystones WHERE UUID = ?");
                ps.setString(1, uuid.toString());
                ResultSet rs = ps.executeQuery();
                return rs.next();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean hasWaystone(SMPUser user) {
        return hasWaystone(user.getUUID());
    }

    public static Location getWaystone(Location location) {
        if(checkTable()) {
            Location data;
            if (hasWaystone(location)) {
                try {
                    PreparedStatement ps = SMP.database.prepareStatement("SELECT LOCATION FROM waystones WHERE LOCATION = ?");
                    ps.setString(1, encryptLocation(location));
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        data = decryptLocation(rs.getString("LOCATION"));
                    } else {
                        return null;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    return null;
                }
            } else {
                return null;
            }
            return data;
        } else {
            return null;
        }
    }

    public static Location getWaystoneWarp(Location location) {
        if(checkTable()) {
            Location data;
            if (hasWaystone(location)) {
                try {
                    PreparedStatement ps = SMP.database.prepareStatement("SELECT SPAWN FROM waystones WHERE LOCATION = ?");
                    ps.setString(1, encryptLocation(location));
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        data = decryptLocation(rs.getString("SPAWN"));
                    } else {
                        return null;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    return null;
                }
            } else {
                return null;
            }
            return data;
        } else {
            return null;
        }
    }

    public static Location getWaystone(SMPUser user) {
        return getWaystone(user.getUUID());
    }

    public static Location getWaystone(UUID uuid) {
        if(checkTable()) {
            Location data;
            if (hasWaystone(uuid)) {
                try {
                    PreparedStatement ps = SMP.database.prepareStatement("SELECT LOCATION FROM waystones WHERE UUID = ?");
                    ps.setString(1, uuid.toString());
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        data = decryptLocation(rs.getString("LOCATION"));
                    } else {
                        return null;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    return null;
                }
            } else {
                return null;
            }
            return data;
        } else {
            return null;
        }
    }

    public static String getPublicSetting(UUID uuid) {
        if(checkTable()) {
            String data;
            if (hasWaystone(uuid)) {
                try {
                    PreparedStatement ps = SMP.database.prepareStatement("SELECT PUBLIC FROM waystones WHERE UUID = ?");
                    ps.setString(1, uuid.toString());
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        data = rs.getString("PUBLIC");
                    } else {
                        return null;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    return null;
                }
            } else {
                return null;
            }
            return data;
        } else {
            return null;
        }
    }

    public static Location getWaystoneWarp(SMPUser user) {
        return getWaystoneWarp(user.getUUID());
    }

    public static Location getWaystoneWarp(UUID uuid) {
        if(checkTable()) {
            Location data;
            if (hasWaystone(uuid)) {
                try {
                    PreparedStatement ps = SMP.database.prepareStatement("SELECT SPAWN FROM waystones WHERE UUID = ?");
                    ps.setString(1, uuid.toString());
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        data = decryptLocation(rs.getString("SPAWN"));
                    } else {
                        return null;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    return null;
                }
            } else {
                return null;
            }
            return data;
        } else {
            return null;
        }
    }

    public static void createWaystone(SMPUser user, Location location) {
        if(checkTable()) {
            try {
                if(!hasWaystone(user)) {
                    if(!hasWaystone(location)) {
                        PreparedStatement ps = SMP.database.prepareStatement("INSERT INTO waystones (UUID,WORLD,SPAWN,LOCATION,PUBLIC) VALUES (?,?,?,?,?)");
                        ps.setString(1, user.getUUID().toString());
                        ps.setString(2, user.getPlayer().getWorld().getName());
                        ps.setString(3, encryptLocation(user.getPlayer().getLocation()));
                        ps.setString(4, encryptLocation(location));
                        ps.setString(5, "no");
                        ps.executeUpdate();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void updatePublicSetting(SMPUser user, String value) {
        updatePublicSetting(user.getUUID(),value);
    }

    public static void updatePublicSetting(UUID uuid, String value) {
        try {
            if (!checkTable()) {
                return;
            }
            if (!hasWaystone(uuid)) {
                return;
            }
            PreparedStatement ps = SMP.database.prepareStatement("UPDATE waystones SET PUBLIC = ? WHERE UUID = ?");
            ps.setString(1, value);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("An error occurred while updating the public setting for waystone " + uuid + ": " + e.getMessage());
        }
    }

    public static void removeWaystone(Location location) {
        try {
            if(hasWaystone(location)) {
                PreparedStatement ps = SMP.database.prepareStatement("DELETE FROM waystones WHERE LOCATION = ?");
                ps.setString(1, encryptLocation(location));
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}