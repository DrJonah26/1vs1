package de.drjonah.ffa.kiteditor.listener;

import de.drjonah.ffa.Main;
import de.drjonah.ffa.utils.Base64;
import de.drjonah.ffa.utils.Config;
import de.drjonah.ffa.utils.Timer;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class KitListener implements Listener {

    Timer timer = new Timer(false, 1);

    public static Map<Player, ItemStack> kit = new HashMap<>();
    public static boolean isAllowed = true;


    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) throws IOException {
        Player player = event.getPlayer();


        // Abfrage, ob der Spieler den "Kiteditraum" verlässt/betritt
        if (event.getTo().clone().add(0,-1, 0).getBlock().getType() == Material.GOLD_BLOCK ||
                event.getTo().clone().add(0,-2, 0).getBlock().getType() == Material.GOLD_BLOCK) {

            //Timer starten

            if (!isAllowed) {
                return;
            }

            if (isAllowed) {
                timer.setRunning(true);
                isAllowed = false;
            }


            if (player.getScoreboardTags().contains("Kit-edit")) {

                // Austritt

                // Kit speichern
                Config.getConfig().set("kit." + player.getUniqueId(), Base64.itemStackArrayToBase64(player.getInventory().getContents()));
                Config.getConfig().set("kit.armor." + player.getUniqueId(), Base64.itemStackArrayToBase64(player.getInventory().getArmorContents()));
                Config.save();

                player.sendMessage(Main.PREFIX() + "Du hast den §9Kitedit-Raum §cverlassen");
                player.sendMessage(Main.PREFIX() + "Dein Kit wurde gespeichert!");
                player.removeScoreboardTag("Kit-edit");
                player.setGameMode(GameMode.SURVIVAL);
                player.getInventory().clear();

            } else {

                // Eintritt
                player.sendMessage(Main.PREFIX() + "Du hast den §9Kitedit-Raum §abetreten");
                player.addScoreboardTag("Kit-edit");
                player.setGameMode(GameMode.CREATIVE);

                // Spieler das Kit geben, um es zu bearbeiten
                if (Config.getConfig().contains("kit." + player.getUniqueId()) || Config.getConfig().contains("kit.armor." + player.getUniqueId())) {
                    player.getInventory().setContents(Base64.itemStackArrayFromBase64(Config.getConfig().getString("kit." + player.getUniqueId())));
                    player.getInventory().setArmorContents(Base64.itemStackArrayFromBase64(Config.getConfig().getString("kit.armor." + player.getUniqueId())));
                }
            }
        }
    }

}
