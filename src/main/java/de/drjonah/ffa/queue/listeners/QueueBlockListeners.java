package de.drjonah.ffa.queue.listeners;

import de.drjonah.ffa.Main;
import de.drjonah.ffa.queue.QueueManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class QueueBlockListeners implements Listener {

    QueueManager manager = Main.getInstance().getQueueManager();

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (event.getBlock() == manager.leaveQueueItem) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (event.getItemDrop() == manager.queueItem || event.getItemDrop() == manager.leaveQueueItem) {
            event.setCancelled(true);
        }
    }
}
