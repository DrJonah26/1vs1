package de.drjonah.ffa;

import de.drjonah.ffa.duels.DuelManager;
import de.drjonah.ffa.duels.commands.AcceptCommand;
import de.drjonah.ffa.duels.commands.DuelCommand;
import de.drjonah.ffa.duels.listeners.InvClickListener;
import de.drjonah.ffa.elo.EloManager;
import de.drjonah.ffa.elo.commands.EloCommand;
import de.drjonah.ffa.elo.listeners.WinListener;
import de.drjonah.ffa.elo.ranks.RankManager;
import de.drjonah.ffa.kiteditor.KitManager;
import de.drjonah.ffa.kiteditor.enchanting.EnchantListener;
import de.drjonah.ffa.kiteditor.listener.BlockListeners;
import de.drjonah.ffa.kiteditor.listener.KitListener;
import de.drjonah.ffa.queue.listeners.ConnectionListeners;
import de.drjonah.ffa.queue.QueueManager;
import de.drjonah.ffa.queue.listeners.InteractListener;
import de.drjonah.ffa.queue.listeners.QueueBlockListeners;
import de.drjonah.ffa.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main instance;
    private DuelManager duelManager;
    private QueueManager queueManager;
    private EloManager eloManager;
    private RankManager rankManager;

    @Override
    public void onLoad() {
        Bukkit.getConsoleSender().sendMessage("§aDas Kit1vs1 Plugin wird enabled...Bitte warte kurz..");
    }

    //Todo
    //     -ranks

    @Override
    public void onEnable() {
        instance = this;
        duelManager = new DuelManager();
        queueManager = new QueueManager();
        eloManager = new EloManager();
        rankManager = new RankManager();

        //config
        Config.createCustomConfig();

        getCommand("duel").setExecutor(new DuelCommand());
        getCommand("accept").setExecutor(new AcceptCommand());
        getCommand("elo").setExecutor(new EloCommand());

        Bukkit.getPluginManager().registerEvents(new KitManager(), this);
        Bukkit.getPluginManager().registerEvents(new KitListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockListeners(), this);
        Bukkit.getPluginManager().registerEvents(new InvClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new ConnectionListeners(), this);
        Bukkit.getPluginManager().registerEvents(new InteractListener(), this);
        Bukkit.getPluginManager().registerEvents(new EnchantListener(), this);
        Bukkit.getPluginManager().registerEvents(new QueueBlockListeners(), this);
        Bukkit.getPluginManager().registerEvents(new WinListener(), this);

    }

    @Override
    public void onDisable() {
        Config.save();
    }

    public static String PREFIX() {
        return "§7[§61vs1§7] ";
    }

    public static String DUELPREFIX() {
        return "§7[§6Duel§7] ";
    }

    public static String QUEUEPREFIX() {
        return "§7[§6Queue§7] ";
    }

    public RankManager getRankManager() {
        return rankManager;
    }

    public static Main getInstance() {
        return instance;
    }

    public YamlConfiguration getCfg() {
        return Config.config;
    }

    public EloManager getEloManager() {
        return eloManager;
    }

    public QueueManager getQueueManager() {
        return queueManager;
    }
}
