package de.drjonah.ffa.duels.commands;

import de.drjonah.ffa.Main;
import de.drjonah.ffa.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AcceptCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return false;
        }

        Player player = (Player) sender;

        if (!DuelCommand.invited.contains(player)) {
            player.sendMessage(Main.DUELPREFIX() + "§cDu wurdest zu keinem Duell eingeladen!");
            return false;
        }

        DuelCommand.duel.add(player);
        DuelCommand.invited.remove(player);

        if (DuelCommand.duel.contains(DuelCommand.empfänger)) {
            DuelCommand.inv.setItem(21, DuelCommand.getPlayerHead(DuelCommand.requester));
            DuelCommand.inv.setItem(23, DuelCommand.getPlayerHead(DuelCommand.empfänger));
            ItemStack item = new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).setDisplayName("§aGo").
                    setLore("§aKLICKE§7, um zu §astarten").build();
            DuelCommand.inv.setItem(34, item);

            // Inventar öffnen
            DuelCommand.requester.sendMessage("Der Spieler §9" + DuelCommand.empfänger.getName() + "§7 hat das Duell angenommen!");
            DuelCommand.empfänger.sendMessage(Main.DUELPREFIX() + "Du hast das Duell angenommen");
            DuelCommand.requester.openInventory(DuelCommand.inv);
            DuelCommand.empfänger.openInventory(DuelCommand.inv);
        }

        return false;
    }
}
