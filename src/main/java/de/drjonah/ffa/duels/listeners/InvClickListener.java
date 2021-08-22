package de.drjonah.ffa.duels.listeners;

import de.drjonah.ffa.duels.DuelManager;
import de.drjonah.ffa.duels.commands.DuelCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.io.IOException;

public class InvClickListener implements Listener {


    @EventHandler
    public void onClick(InventoryClickEvent event) throws IOException {

        Player player = (Player) event.getWhoClicked();

        if (event.getCurrentItem() == null) {
            return;
        }

        if (event.getInventory().equals(DuelCommand.inv)) {

            event.setCancelled(true);

            if (event.getCurrentItem().getItemMeta().getDisplayName().equals("§aGo")) {
                // Runde startet
                 DuelManager.start(DuelCommand.requester, DuelCommand.empfänger);

                DuelCommand.requester.closeInventory();
                DuelCommand.empfänger.closeInventory();
            }
        }
    }
}
