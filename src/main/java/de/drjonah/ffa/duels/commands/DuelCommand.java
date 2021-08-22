package de.drjonah.ffa.duels.commands;

import de.drjonah.ffa.Main;
import de.drjonah.ffa.utils.ItemBuilder;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DuelCommand implements CommandExecutor {

    public static Inventory inv = Bukkit.createInventory(null, 9*5, "§6Duell");
    private TextComponent text = new TextComponent(Main.DUELPREFIX() + "§cHIER §7klicken, um zu akzeptieren oder gib /accept ein");
    public static List<Player> invited = new ArrayList<>();
    public static List<Player> duel = new ArrayList<>();
    private int time = 60;
    private ItemStack platzhalter;
    private ItemStack item;
    private boolean running = false;
    public static Player requester;
    public static Player empfänger;

    public DuelCommand() {

        //inv setzen
        platzhalter = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName(" ").build();

        for (int i = 0; i < 45; i++) {
            inv.setItem(i, platzhalter);
        }

        platzhalter = new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setDisplayName(" ").build();

        for (int i = 0; i < 9; i++) {
            inv.setItem(i, platzhalter);
        }

        for (int i = 36; i < 45; i++) {
            inv.setItem(i, platzhalter);
        }

        inv.setItem(9, platzhalter);
        inv.setItem(18, platzhalter);
        inv.setItem(27, platzhalter);
        inv.setItem(17, platzhalter);
        inv.setItem(26, platzhalter);
        inv.setItem(35, platzhalter);

        text.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/accept"));
        text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§aKlicke, um zu akzeptieren")));
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public static ItemStack getPlayerHead(Player player) {
        ItemStack stack = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) stack.getItemMeta();

        meta.setDisplayName("§9" + player.getName());
        meta.setLore(Arrays.asList("§7Kills: §c" + player.getStatistic(Statistic.PLAYER_KILLS)));
        meta.setOwningPlayer(player);

        stack.setItemMeta(meta);
        return stack;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return false;
        }

        if (args.length == 1) {
            try {
                requester = (Player) sender;
                empfänger = Bukkit.getPlayer(args[0]);

                if (!empfänger.isOnline()) {
                    requester.sendMessage(Main.DUELPREFIX() + "§c");
                    return false;
                }

                if (invited.contains(empfänger)) {
                    requester.sendMessage(Main.DUELPREFIX() + "§cDu hast diesen Spieler bereits eingeladen!");
                    return false;
                }

                /*if (empfänger == requester) {
                    requester.sendMessage(Main.DUELPREFIX() + "§cDu kannst dich nicht selber heraufordern!");
                    return false;
                }*/

                empfänger.sendMessage(Main.PREFIX() + "Der Spieler §9" + requester.getName() + "§7 hat dich zu einem §cDuell §7herausgeordert!");
                empfänger.spigot().sendMessage(text);
                invited.add(empfänger);
                setRunning(true);

                // checken, ob angenommen
                new BukkitRunnable() {
                    @Override
                    public void run() {

                        if (!isRunning()) {
                            return;
                        }

                        if (duel.contains(empfänger)) {
                            time = 0;
                        }

                        if (time == 0) {

                            return;
                        }

                        time--;
                    }
                }.runTaskTimer(Main.getInstance(), 20, 20);

                time = 30;

            }catch (Exception x) {
                sender.sendMessage(Main.DUELPREFIX() + "Der Spieler war noch nie online oder existiert nicht!");
            }

        }else {
            sender.sendMessage(Main.DUELPREFIX() + "§cUse: /duel <Player>");
        }

        return false;
    }
}
