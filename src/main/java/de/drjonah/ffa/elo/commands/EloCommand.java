package de.drjonah.ffa.elo.commands;

import de.drjonah.ffa.Main;
import de.drjonah.ffa.elo.EloManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EloCommand implements CommandExecutor {

    EloManager manager = Main.getInstance().getEloManager();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return false;
        }

        Player player = (Player) sender;

        int elo = manager.getElo(player);
        player.sendMessage(Main.PREFIX() + "Dein Elo: §9" + elo);

        return false;
    }
}
