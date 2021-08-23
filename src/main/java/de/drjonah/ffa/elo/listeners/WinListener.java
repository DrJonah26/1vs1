package de.drjonah.ffa.elo.listeners;

import de.drjonah.ffa.Main;
import de.drjonah.ffa.elo.EloManager;
import de.drjonah.ffa.elo.ranks.RankManager;
import de.drjonah.ffa.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class WinListener implements Listener {

    EloManager eloManager = Main.getInstance().getEloManager();
    RankManager rankManager = Main.getInstance().getRankManager();

    @EventHandler
    public void onWin(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player killer = player.getKiller();


        if (killer == null) {
            return;
        }

        int killerElo = Config.getConfig().getInt("elo." + killer.getUniqueId());

        if (player.getScoreboardTags().contains("inMatch")) {

            killer.sendMessage(Main.PREFIX() + "Der Spieler §a" + killer.getName() + "§7 hat den Spieler §c" +
                    player.getName() + "getötet!");
            player.sendMessage(Main.PREFIX() + "Der Spieler §a" + killer.getName() + "§7 hat den Spieler §c" +
                    player.getName() + "getötet!");

            event.setDeathMessage("");

            //elo geben/entfernen
            eloManager.addElo(killer);
            killer.sendMessage(Main.PREFIX() + "Du erhälst §a+10 Elo!");
            killer.performCommand("/elo");
            eloManager.removeElo(player);
            player.sendMessage(Main.PREFIX() + "Du erhälst §c-10 Elo!");
            player.performCommand("/elo");


            //neuen Rang gebena
            switch (killerElo) {
                case 100:
                    Bukkit.broadcastMessage(Main.PREFIX() + "Der Spieler §6" + killer.getName() + "§7 ist nun in §cBronze§7!");
            }
        }
    }
}