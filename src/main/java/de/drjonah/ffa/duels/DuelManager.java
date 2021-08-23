package de.drjonah.ffa.duels;

import de.drjonah.ffa.Main;
import de.drjonah.ffa.utils.Base64;
import de.drjonah.ffa.utils.Config;
import org.bukkit.entity.Player;

import java.io.IOException;

public class DuelManager {

    public static void start(Player requester, Player empfänger) throws IOException {

        requester.closeInventory();
        empfänger.closeInventory();
        requester.addScoreboardTag("inMatch");
        empfänger.addScoreboardTag("inMatch");

        if (Config.getConfig().contains("kit." + requester.getUniqueId()) ||
                Config.getConfig().contains("kit.armor" + requester.getUniqueId())) {

            // Kit des Requesters
            requester.getInventory().setContents(Base64.itemStackArrayFromBase64(Config.getConfig().getString("kit." + requester.getUniqueId())));
            empfänger.getInventory().setContents(Base64.itemStackArrayFromBase64(Config.getConfig().getString("kit." + requester.getUniqueId())));

            requester.getInventory().setArmorContents(Base64.itemStackArrayFromBase64(Config.getConfig().getString("kit.armor" + requester.getUniqueId())));
            empfänger.getInventory().setArmorContents(Base64.itemStackArrayFromBase64(Config.getConfig().getString("kit.armor" + requester.getUniqueId())));

            //Hier können die Spieler nun teleportiert werden..

        }else if (Config.getConfig().contains("kit." + empfänger.getUniqueId()) ||
                Config.getConfig().contains("kit.armor" + empfänger.getUniqueId())){
            // Kit des Empfängers
            requester.getInventory().setContents(Base64.itemStackArrayFromBase64(Config.getConfig().getString("kit." + empfänger.getUniqueId())));
            empfänger.getInventory().setContents(Base64.itemStackArrayFromBase64(Config.getConfig().getString("kit." + empfänger.getUniqueId())));

            requester.getInventory().setArmorContents(Base64.itemStackArrayFromBase64(Config.getConfig().getString("kit.armor" + empfänger.getUniqueId())));
            empfänger.getInventory().setArmorContents(Base64.itemStackArrayFromBase64(Config.getConfig().getString("kit.armor" + empfänger.getUniqueId())));


            //Hier können die Spieler nun teleportiert werden..


        } else {
            requester.sendMessage(Main.PREFIX() + "Ihr habt beide kein Kit erstellt, deshalb wird das Match abgebrochen!");
            empfänger.sendMessage(Main.PREFIX() + "Ihr habt beide kein Kit erstellt, deshalb wird das Match abgebrochen!");

            return;
        }
    }
}
