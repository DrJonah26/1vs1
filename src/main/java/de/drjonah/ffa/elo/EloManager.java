package de.drjonah.ffa.elo;

import de.drjonah.ffa.utils.Config;
import org.bukkit.entity.Player;

public class EloManager {

    public int getElo(Player player) {
        if (!Config.getConfig().contains("elo." + player.getUniqueId())) {
            Config.getConfig().set("elo." + player.getUniqueId(), 0);
        }
        int elo = Config.getConfig().getInt("elo." + player.getUniqueId());
        return elo;
    }

    public void addElo(Player player) {
        if (Config.getConfig().contains("elo." + player.getUniqueId())) {
            int elo = Config.getConfig().getInt("elo." + player.getUniqueId());
            Config.getConfig().set("elo." + player.getUniqueId(), elo + 10);
        }
    }

    public void removeElo(Player player) {

        if (Config.getConfig().getInt("elo." + player.getUniqueId()) == 0) {
            return;
        }

        if (Config.getConfig().contains("elo." + player.getUniqueId())) {
            int elo = Config.getConfig().getInt("elo." + player.getUniqueId());
            Config.getConfig().set("elo." + player.getUniqueId(), elo - 10);
        }
    }
}
