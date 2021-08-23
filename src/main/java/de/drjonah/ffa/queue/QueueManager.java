package de.drjonah.ffa.queue;

import de.drjonah.ffa.Main;
import de.drjonah.ffa.duels.DuelManager;
import de.drjonah.ffa.utils.ItemBuilder;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class QueueManager {

    public ItemStack queueItem = new ItemBuilder(Material.CLOCK).setDisplayName("§7Queue").build();
    public ItemStack leaveQueueItem = new ItemBuilder(Material.BARRIER).setDisplayName("§cLeave Queue").build();
    public List<Player> inQueue = new ArrayList<>();

    public void joinQueue(Player player) {
        player.sendMessage(Main.QUEUEPREFIX() + "Du bist der Warteschlange §abeigetreten§7!");
        inQueue.add(player);
        player.getInventory().clear();

        player.getInventory().setItem(1, leaveQueueItem);
    }

    public void leaveQueue(Player p) {
        inQueue.remove(p);
        p.getInventory().removeItem(leaveQueueItem);
        p.getInventory().setItem(0, queueItem);
        p.sendMessage(Main.QUEUEPREFIX() + "Du hast die Warteschlange §cverlassen§7!");
    }

    public void findMatch(Player p) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (inQueue.contains(p)) {
                    sendActionBar(p);
                    if (inQueue.size() >= 2) {
                        if (!(inQueue.get(0) == p)) {
                            Player p2 = inQueue.get(0);
                            p.sendMessage(Main.QUEUEPREFIX() + "Es wurde ein Match gefunden!");
                            p2.sendMessage(Main.QUEUEPREFIX() + "Es wurde ein Match gefunden!");

                            //starten
                            try {
                                DuelManager.start(p, p2);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }else {
                            Player p2 = inQueue.get(1);
                            p.sendMessage(Main.QUEUEPREFIX() + "Es wurde ein Match gefunden!");
                            p2.sendMessage(Main.QUEUEPREFIX() + "Es wurde ein Match gefunden!");

                            //starten
                            try {
                                DuelManager.start(p, p2);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(Main.getInstance(), 20, 20);
    }

    public boolean isInQueue(Player p) {
        if (inQueue.contains(p)) {
            return true;
        }

        return false;
    }

    public void sendActionBar(Player p) {
        if (isInQueue(p)) {
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§7Es wird ein §cMatch §7für dich gesucht.."));
        }
    }

}
