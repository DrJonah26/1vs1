package de.drjonah.ffa.elo.ranks;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

public class RankManager {

    private Team bronze;
    private Team silver;
    private Team gold;
    private Team diamond;

    //Bronze
    public void setBronze(Player player) {
        bronze = player.getScoreboard().getTeam("bronze");

        if (bronze == null) {
            bronze = player.getScoreboard().registerNewTeam("bronze");
        }

        bronze.setPrefix("§7[§cB§7] ");
        bronze.addEntry(player.getName());
    }

    //Silber
    public void setSilver(Player player) {
        silver = player.getScoreboard().getTeam("silver");

        if (silver == null) {
            silver = player.getScoreboard().registerNewTeam("silver");
        }

        silver.setPrefix("§7[§9S§7] ");
        silver.addEntry(player.getName());
    }

    //Gold
    public void setGold(Player player) {
        gold = player.getScoreboard().getTeam("gold");

        if (gold == null) {
            gold = player.getScoreboard().registerNewTeam("gold");
        }

        gold.setPrefix("§7[§6G§7] ");
        gold.addEntry(player.getName());
    }

    //Diamond
    public void setDiamond(Player player) {
        diamond = player.getScoreboard().getTeam("diamond");

        if (diamond == null) {
            diamond = player.getScoreboard().registerNewTeam("diamond");
        }

        diamond.setPrefix("§7[§bD§7] ");
        diamond.addEntry(player.getName());
    }
}
