package com.zyneonstudios.nerotvlive.smp.listener;

import com.zyneonstudios.api.paper.Zyneon;
import com.zyneonstudios.nerotvlive.smp.SMP;
import com.zyneonstudios.nerotvlive.smp.manager.ItemManager;
import com.zyneonstudios.nerotvlive.smp.player.SMPUser;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if(p.getGameMode().equals(GameMode.SPECTATOR)) {
            p.setGameMode(GameMode.SURVIVAL);
        }
        if(!p.hasPlayedBefore()) {
            p.getInventory().addItem(ItemManager.waystone(p));
            p.teleport(new Location(Bukkit.getWorlds().get(0),0.5,63,0.5,180,0));
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"give "+p.getName()+" written_book{pages:['[\"\",{\"text\":\"===================\\\\n\\\\n     \"},{\"text\":\"Der SMP-Guide\",\"color\":\"blue\"},{\"text\":\"     \\\\n\\\\n-------------------\\\\n\\\\n \",\"color\":\"reset\"},{\"text\":\"Allgemeines...................02\",\"color\":\"dark_blue\",\"clickEvent\":{\"action\":\"change_page\",\"value\":2}},{\"text\":\"\\\\n \",\"color\":\"dark_blue\"},{\"text\":\"Waystones.....................03\",\"color\":\"dark_blue\",\"clickEvent\":{\"action\":\"change_page\",\"value\":3}},{\"text\":\"\\\\n \",\"color\":\"dark_blue\"},{\"text\":\"Neue Strukturen.....07\",\"color\":\"dark_blue\",\"clickEvent\":{\"action\":\"change_page\",\"value\":7}},{\"text\":\" \\\\n \",\"color\":\"dark_blue\"},{\"text\":\"Neuer Nether.............08\",\"color\":\"dark_blue\",\"clickEvent\":{\"action\":\"change_page\",\"value\":8}},{\"text\":\"\\\\n \",\"color\":\"dark_blue\"},{\"text\":\"Neue Dimensionen....09\",\"color\":\"dark_blue\",\"clickEvent\":{\"action\":\"change_page\",\"value\":9}},{\"text\":\" \\\\n \",\"color\":\"dark_blue\"},{\"text\":\"Sicherungssystem..12\",\"color\":\"dark_blue\",\"clickEvent\":{\"action\":\"change_page\",\"value\":12}},{\"text\":\"\\\\n\\\\n===================\",\"color\":\"reset\"}]','[\"\",{\"text\":\"Allgemeines:\",\"bold\":true},{\"text\":\"\\\\nSMP steht für \\\\\"Survival Multiplayer\\\\\" und bezeichnet das Zusammenspiel im Überlebensmodus.\\\\nDas Zyneon SMP wird durch weitere Funktionen erweitert.\\\\nSo kann man sich mit \",\"color\":\"reset\"},{\"text\":\"/sit\",\"color\":\"dark_blue\"},{\"text\":\" hinsetzen, \",\"color\":\"reset\"},{\"text\":\"/lay\",\"color\":\"dark_blue\"},{\"text\":\" hinlegen und mit \",\"color\":\"reset\"},{\"text\":\"/crawl\",\"color\":\"dark_blue\"},{\"text\":\" kriechen.\",\"color\":\"reset\"}]','[\"\",{\"text\":\"Waystones:\",\"bold\":true},{\"text\":\"\\\\nJeder Spieler hat einen Waystone, mit welchem man standardmäßig spawnt.\\\\nSetzt man einen Waystone so ist dieser, für andere, unter dem Minecraft-Namen des Setzers zu finden.\\\\nMöchte man seinen Waystone nicht setzen, kann man diesen nur\",\"color\":\"reset\"}]','[\"\",{\"text\":\"durch das Legen in eine Kiste aus dem Inventar entfernen. Beim Todesfall geht der Waystone nicht verloren.\\\\nUm anderen Spielern zuermöglichen sich zu deinem Waystone zu teleportieren, mache \"},{\"text\":\"sneak-Rechtsklick\",\"color\":\"dark_blue\"},{\"text\":\" auf deinen plazierten Waystone und wähle\",\"color\":\"reset\"}]','[\"\",{\"text\":\"deine bevorzugte Einstellung aus.\\\\nUm dich mit dem Waystone zu teleportieren, mache \"},{\"text\":\"Rechtsklick\",\"color\":\"dark_blue\"},{\"text\":\" und wähle eine der Möglichkeiten aus. Unter \\\\\"Namen eingeben\\\\\" kannst du dich zu einem Waystone eines anderen Spielers teleportieren, in dem du einen Spielernamen\",\"color\":\"reset\"}]','[\"\",{\"text\":\"eingibst. Mit \"},{\"text\":\"sneak-Linksklick\",\"color\":\"dark_blue\"},{\"text\":\" hast du die Möglichkeit, deinen eigenen Waystone abzubauen.\\\\n\\\\nFür die \\\\\"Vertraut\\\\\"-Einstellung siehe ab \",\"color\":\"reset\"},{\"text\":\"Seite 12\",\"underlined\":true,\"color\":\"blue\",\"clickEvent\":{\"action\":\"change_page\",\"value\":12}},{\"text\":\".\",\"color\":\"reset\"}]','[\"\",{\"text\":\"Neue Strukturen:\",\"bold\":true},{\"text\":\"\\\\nIn der Oberwelt und dem Ende sind neue Strukturen mit Loot zufinden.\\\\n\\\\nIm Nether ebenfalls, siehe \",\"color\":\"reset\"},{\"text\":\"Seite 08\",\"underlined\":true,\"color\":\"blue\",\"clickEvent\":{\"action\":\"change_page\",\"value\":8}},{\"text\":\".\",\"color\":\"reset\"}]','[\"\",{\"text\":\"Neuer Nether:\",\"bold\":true},{\"text\":\"\\\\nDer Nether wurde von Grund auf überarbeitet. So sind im Nether 8 neue Biome, 10 neue Strukturen, neue Gegner und 1 neuer Boss zufinden, welche auch neue Items mit sich bringen. Einige Biome und Strukturen bringen auch neue Eigenschaften mit.\",\"color\":\"reset\"}]','[\"\",{\"text\":\"Neue Dimensionen:\",\"bold\":true},{\"text\":\"\\\\nMit dem SMP kommen 4 neue Dimensionen. Die erste ist \\\\\"The In Between\\\\\", von welcher du in die 3 anderen reisen kannst. Das Portal nach \\\\\"The In Between\\\\\" öffnet sich, wenn man ein Phantom mit einem Spektral-Pfeil tötet.\\\\nAuf den fliegenden Inseln, die \\\\\"The In Be-\",\"color\":\"reset\"}]','{\"text\":\"tween\\\\\" bilden findet man weitere Portale verstreut. Die Portale schicken dich zufällig in eine der anderen Dimensionen. So zum Beispiel in \\\\\"The Primordial Desert\\\\\", eine große Wüste mit neuen Strukturen und feindlichen Kreaturen.\\\\n\\\\\"The Buried Ocean\\\\\" liefert einen unterirdischen Ozean,\"}','{\"text\":\"welcher die schönen, aber auch die gefährlichen Aspekte der Tiefsee mit sich bringt. Und ebenfalls neue Strukturen.\\\\nDiese sind auch in der 3. Dimension, \\\\\"The Tempest\\\\\", zu finden.\\\\nHier herrscht ein düsterer Sturm, der die Kreaturen dieser Welt gefährlicher macht.\"}','[\"\",{\"text\":\"Sicherungssystem:\",\"bold\":true},{\"text\":\"\\\\nUm zu vermeiden, dass andere auf dein Eigentum zugreifen, kannst du deine Behälter mit \",\"color\":\"reset\"},{\"text\":\"/lock\",\"color\":\"dark_blue\"},{\"text\":\" sichern. Um Freunden weiterhin den Zugriff zuermöglichen, kannst du sie auf die Liste vertrauter Spieler setzen. Dies machst du mit \",\"color\":\"reset\"},{\"text\":\"/trust add [Spielername]\",\"color\":\"dark_blue\"},{\"text\":\".\",\"color\":\"reset\"}]','[\"\",{\"text\":\"Um Behälter zu entsichern mache \"},{\"text\":\"/unlock\",\"color\":\"dark_blue\"},{\"text\":\".\\\\nUm vertrauten Personen den Zugriff wieder zu entziehen mache \",\"color\":\"reset\"},{\"text\":\"/trust remove [Spielername]\",\"color\":\"dark_blue\"},{\"text\":\".\\\\nVertraute Spieler können auch deinen Waystone nutzen, ist dieser auf \\\\\"Vertraut\\\\\" gestellt, siehe ab \",\"color\":\"reset\"},{\"text\":\"Seite 03\",\"underlined\":true,\"color\":\"blue\",\"clickEvent\":{\"action\":\"change_page\",\"value\":3}},{\"text\":\".\",\"color\":\"reset\"}]','[\"\",{\"text\":\"===================\\\\n\\\\n\\\\n\\\\nSolltest du ungeklärte Fragen haben, so lese doch unsere \"},{\"text\":\"Website\",\"underlined\":true,\"color\":\"blue\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://www.zyneonstudios.com\"}},{\"text\":\" oder kontaktiere uns auf \",\"color\":\"reset\"},{\"text\":\"Discord\",\"underlined\":true,\"color\":\"blue\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://zyne.ml/dc\"}},{\"text\":\".\\\\n\\\\n\\\\n\\\\n===================\",\"color\":\"reset\"}]'],title:\"Der SMP-Guide\",author:\"Zyneon Studios\"}");
        }
        p.setOp(false);
        SMPUser u = SMP.getUser(p);
        e.setJoinMessage("");
        Zyneon.getZyneonServer().sendRawMessage("§8» §a"+u.getName());
        for(Player all:Bukkit.getOnlinePlayers()) {
            if(all.getUniqueId()!=p.getUniqueId()) {
                all.sendMessage("§8» §a"+u.getName());
            }
        }
        SMP.setPrefix(p);
        p.setGameMode(GameMode.SPECTATOR);
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        if (SMP.isMaintenance) {
            Player p = e.getPlayer();
            if(!p.hasPermission("zyneon.bypass.maintenance")||!p.hasPermission("zyneon.team")) {
                e.setKickMessage("§cDas SMP ist zurzeit in Wartungsarbeiten§8! §cVersuche es später erneut§8.");
                e.setResult(PlayerLoginEvent.Result.KICK_OTHER);
            }
        }
    }
}
