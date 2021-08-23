package de.drjonah.ffa.queue.listeners;

import de.drjonah.ffa.Main;
import de.drjonah.ffa.elo.ranks.RankManager;
import de.drjonah.ffa.queue.QueueManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ConnectionListeners implements Listener {

    QueueManager queueManager = Main.getInstance().getQueueManager();
    RankManager rankManager = Main.getInstance().getRankManager();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        // Bronze Rang geben
        if (!player.getScoreboardTags().contains("ranked")) {
            rankManager.setBronze(player);
            player.addScoreboardTag("ranked");
        }

        player.getInventory().clear();
        player.getInventory().setItem(0, queueManager.queueItem);
    }

}
