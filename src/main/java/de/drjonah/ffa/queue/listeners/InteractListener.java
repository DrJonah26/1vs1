package de.drjonah.ffa.queue.listeners;

import de.drjonah.ffa.Main;
import de.drjonah.ffa.queue.QueueManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractListener implements Listener {

    QueueManager manager = Main.getInstance().getQueueManager();

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {

        Player player = event.getPlayer();

        if (event.getItem() == null) {
            return;
        }

        if (event.getItem().equals(manager.queueItem)) {
            manager.joinQueue(player);
            try {
                manager.findMatch(player);
            } catch (Exception x) {
                x.printStackTrace();
            }

        }

        if (event.getItem().equals(manager.leaveQueueItem)) {
            manager.leaveQueue(player);
        }

    }

}
