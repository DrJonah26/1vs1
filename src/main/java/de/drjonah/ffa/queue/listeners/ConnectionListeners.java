package de.drjonah.ffa.queue.listeners;

import de.drjonah.ffa.Main;
import de.drjonah.ffa.queue.QueueManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ConnectionListeners implements Listener {

    QueueManager manager = Main.getInstance().getQueueManager();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        player.getInventory().clear();
        player.getInventory().setItem(0, manager.queueItem);
    }

}
