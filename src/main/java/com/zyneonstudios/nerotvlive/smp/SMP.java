package com.zyneonstudios.nerotvlive.smp;

import com.zyneonstudios.api.paper.Zyneon;
import com.zyneonstudios.api.paper.configuration.Config;
import com.zyneonstudios.api.utils.Strings;
import com.zyneonstudios.api.utils.sql.MySQL;
import com.zyneonstudios.api.utils.sql.SQLite;
import com.zyneonstudios.nerotvlive.smp.command.*;
import com.zyneonstudios.nerotvlive.smp.listener.*;
import com.zyneonstudios.nerotvlive.smp.manager.WaystoneManager;
import com.zyneonstudios.nerotvlive.smp.player.SMPUser;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommandYamlParser;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

public final class SMP extends JavaPlugin {

    public static PluginManager pm = Bukkit.getPluginManager();
    public static HashMap<UUID, SMPUser> users = new HashMap<>();
    public static Config config = new Config("plugins/SMP/config.yml");
    public static boolean isMaintenance = true;
    private static SMP instance;
    public static SMP getInstance() { return instance; }
    public static Connection database;

    @Override
    public void onLoad() {
        Strings.setPrefixWord("SMP");
        instance = this;
        config.checkEntry("Settings.isMaintenance",true);
        config.checkEntry("Settings.mySQL.enable",false);
        config.checkEntry("Settings.mySQL.host","127.0.0.1");
        config.checkEntry("Settings.mySQL.port","3306");
        config.checkEntry("Settings.mySQL.user","user");
        config.checkEntry("Settings.mySQL.password","password");
        config.checkEntry("Settings.mySQL.database","database");
        isMaintenance = config.getCFG().getBoolean("Settings.isMaintenance");
    }

    @Override
    public void onEnable() {
        initDatabase();
        WaystoneManager.checkTable();
        initCommands();
        initListeners();
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    private void initDatabase() {
        if(config.getCFG().getBoolean("Settings.mySQL.enable")) {
            database = new MySQL(config.getCFG().getString("Settings.mySQL.host"),config.getCFG().getString("Settings.mySQL.port"),config.getCFG().getString("Settings.mySQL.database"),config.getCFG().getString("Settings.mySQL.user"),config.getCFG().getString("Settings.mySQL.password")).getConnection();
        } else {
            database = new SQLite("plugins/SMP/database.sql").getConnection();
        }
    }

    private void initCommands() {
        initCommand(new BuildCommand());
        initCommand(new EnderchestCommand());
        initCommand(new GamemodeCommand());
        initCommand(new MaintenanceCommand());
        Bukkit.getConsoleSender().sendMessage(Strings.prefix()+"§f  -> §7Lade die §eKommando-Shortcuts§8...");
        getCommand("autoclose").setExecutor(new ShortcutCommands());
        getCommand("redstone").setExecutor(new ShortcutCommands());
        initCommand(new TeleportCommand());
        initCommand(new GuideCommand());
        initCommand(new WaystoneCommand());
        initCommand(new WorldCommand());
        initCommandList();
    }

    private void initListeners() {
        Zyneon.getAPI().initListenerClass(pm,new PlayerGamemodeListener(),this);
        Zyneon.getAPI().initListenerClass(pm,new PlayerCommandListener(),this);
        Zyneon.getAPI().initListenerClass(pm,new PlayerDeathListener(),this);
        Zyneon.getAPI().initListenerClass(pm,new PlayerInventoryListener(),this);
        Zyneon.getAPI().initListenerClass(pm,new PlayerJoinListener(),this);
        Zyneon.getAPI().initListenerClass(pm,new PlayerQuitListener(),this);
        Zyneon.getAPI().initListenerClass(pm,new PlayerTeleportListener(),this);
        Zyneon.getAPI().initListenerClass(pm,new PlayerWaystoneListener(),this);
        Zyneon.getAPI().initListenerClass(pm,new WorldChangeEvent(),this);
        PlayerCommandListener.initBlocked();
    }

    private void initCommand(CommandExecutor command) {
        Bukkit.getConsoleSender().sendMessage(Strings.prefix()+"§f  -> §7Lade Kommando §e"+command.getClass().getSimpleName()+"§8...");
        getCommand(command.getClass().getSimpleName().replace("Command","").toLowerCase()).setExecutor(command);
        getCommand(command.getClass().getSimpleName().replace("Command","").toLowerCase()).setTabCompleter((TabCompleter)command);
    }

    public static SMPUser getUser(UUID uuid) {
        if(users.containsKey(uuid)) {
            return users.get(uuid);
        }
        users.put(uuid,new SMPUser(uuid));
        return getUser(uuid);
    }

    public static SMPUser getUser(Player player) {
        return getUser(player.getUniqueId());
    }

    public static Collection<String> commands = new ArrayList<>();

    public static void initCommandList() {
        for(Command all: PluginCommandYamlParser.parse(getInstance())) {
            commands.add(all.getName().toLowerCase());
            for(String aliases:all.getAliases()) {
                commands.add(aliases.toLowerCase());
            }
        }
        commands.add("party");
        commands.add("give");
        commands.add("sit");
        commands.add("crawl");
        commands.add("lay");
    }

    public static void setPrefix(Player player) {
        String Name = player.getName();
        Scoreboard Scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        if (Scoreboard.getTeam("00000Team") == null) {
            Scoreboard.registerNewTeam("00000Team");
        }
        if (Scoreboard.getTeam("01Creator") == null) {
            Scoreboard.registerNewTeam("01Creator");
        }
        if (Scoreboard.getTeam("02Premium") == null) {
            Scoreboard.registerNewTeam("02Premium");
        }
        if (Scoreboard.getTeam("03Spieler") == null) {
            Scoreboard.registerNewTeam("03Spieler");
        }
        Scoreboard.getTeam("00000Team").setPrefix("§cTeam §8● §f");
        Scoreboard.getTeam("01Creator").setPrefix("§dCreator §8● §f");
        Scoreboard.getTeam("02Premium").setPrefix("§6Premium §8● §f");
        Scoreboard.getTeam("03Spieler").setPrefix("§7User §8● §f");
        Scoreboard.getTeam("00000Team").setCanSeeFriendlyInvisibles(false);
        Scoreboard.getTeam("01Creator").setCanSeeFriendlyInvisibles(false);
        Scoreboard.getTeam("02Premium").setCanSeeFriendlyInvisibles(false);
        Scoreboard.getTeam("03Spieler").setCanSeeFriendlyInvisibles(false);
        Scoreboard.getTeam("00000Team").setOption(org.bukkit.scoreboard.Team.Option.COLLISION_RULE, Team.OptionStatus.ALWAYS);
        Scoreboard.getTeam("01Creator").setOption(org.bukkit.scoreboard.Team.Option.COLLISION_RULE, Team.OptionStatus.ALWAYS);
        Scoreboard.getTeam("02Premium").setOption(org.bukkit.scoreboard.Team.Option.COLLISION_RULE, Team.OptionStatus.ALWAYS);
        Scoreboard.getTeam("03Spieler").setOption(org.bukkit.scoreboard.Team.Option.COLLISION_RULE, Team.OptionStatus.ALWAYS);
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission("zyneon.team")) {
                Scoreboard.getTeam("00000Team").addPlayer(p);
                p.setDisplayName(Scoreboard.getTeam("00000Team").getPrefix() + Name);
            } else if (p.hasPermission("zyneon.creator")) {
                Scoreboard.getTeam("01Creator").addPlayer(p);
                p.setDisplayName(Scoreboard.getTeam("01Creator").getPrefix() + Name);
            } else if (p.hasPermission("zyneon.premium")) {
                Scoreboard.getTeam("02Premium").addPlayer(p);
                p.setDisplayName(Scoreboard.getTeam("02Premium").getPrefix() + Name);
            } else {
                Scoreboard.getTeam("03Spieler").addPlayer(p);
                p.setDisplayName(Scoreboard.getTeam("03Spieler").getPrefix() + Name);
            }
        }
        player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
    }
}
