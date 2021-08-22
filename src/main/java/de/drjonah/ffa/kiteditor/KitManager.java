package de.drjonah.ffa.kiteditor;

import de.drjonah.ffa.Main;
import de.drjonah.ffa.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collections;

public class KitManager implements Listener {

    private Inventory inventory = Bukkit.createInventory(null, 9*5, ChatColor.BOLD + "§6Kiteditor");
    private ItemStack platzhalter = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("").build();
    private ItemStack item;
    private ItemStack select = new ItemStack(Material.SLIME_BLOCK);
    private ItemMeta meta = select.getItemMeta();
    private int time = 30;
    private ItemStack firstItem = null;


    public KitManager() {
        // Platzhalter einsetzen
        for (int i = 0; i < 45; i++) {
            inventory.setItem(i, platzhalter);
        }

        for (int i = 0; i < 9; i++) {
            inventory.setItem(i, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setDisplayName("").build());
        }

        for (int i = 36; i < 45; i++) {
            inventory.setItem(i, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setDisplayName("").build());
        }

        inventory.setItem(9, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setDisplayName("").build());
        inventory.setItem(18, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setDisplayName("").build());
        inventory.setItem(27, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setDisplayName("").build());
        inventory.setItem(17, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setDisplayName("").build());
        inventory.setItem(26, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setDisplayName("").build());
        inventory.setItem(35, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setDisplayName("").build());


        // Items setzen
        item = new ItemBuilder(Material.IRON_SWORD).setDisplayName("§9Haltbarkeit bestimmen").
                setLore("§7Du kannst damit die Haltbarkeit deines Items setzen \n" +
                        "§cBitte dies als Letztes benutzen").build();
        inventory.setItem(21, item);

        item = new ItemBuilder(Material.OAK_SIGN).setDisplayName("§9Namen bestimmen").
                setLore("§7Du kannst damit deinem Item einen Namen geben").build();
        inventory.setItem(22, item);

        item = new ItemBuilder(Material.SPRUCE_SIGN).setDisplayName("§9Lore bestimmen").
                setLore("§7Du kannst damit deinem Item eine Lore setzen").build();
        inventory.setItem(23, item);

        item = new ItemBuilder(Material.BARRIER).setDisplayName("§cReset").
                setLore("§cSetzt alle Einstellungen zurück!").build();
        inventory.setItem(34, item);

    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        try {
            Player player = event.getPlayer();

            if (event.getItem() == null) {
                if (event.getClickedBlock().getType().equals(Material.STONECUTTER)) {
                    player.sendMessage(Main.PREFIX() + "§cDu musst ein Item in der Hand halten!");
                }
                return;
            }

            if (event.getItem() == null) {
                return;
            }

            if (event.getClickedBlock() == null) {
                return;
            }

            // Kitedit
            if (event.getClickedBlock().getType().equals(Material.STONECUTTER)) {
                setType(player);
                inventory.setItem(31, select);
                player.openInventory(inventory);
            }
        }catch (Exception x) {
            return;
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        inventory.setItem(31, select);

        if (event.getCurrentItem() == null) {
            return;
        }

        if (event.getView().getTitle().equals(ChatColor.BOLD + "§6Kiteditor")) {
            event.setCancelled(true);
            // Kit bearbeiten
            switch (event.getSlot()) {
                default:break;
                case 21:
                    // Durability
                    sendDuraTitle(player);
                    time = 30;
                    break;
                case 22:
                    // Name
                    sendNameTitle(player);
                    time = 30;
                    break;
                case 23:
                    // Lore
                    sendLoreTitle(player);
                    time = 30;
                    break;
                case 34:
                    // resets the item
                    resetItem(player);
            }
        }
    }

    @EventHandler
    public void onAsyncPlayerChat(PlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        // Itemnamen bestimmen
        if(player.getScoreboardTags().contains("Kit-edit-name")) {
            player.resetTitle();
            time = 0;
            meta.setDisplayName(message);
            select.setItemMeta(meta);
            item = new ItemBuilder(Material.OAK_SIGN).setDisplayName("§9Namen bestimmen").
                    setLore("§7Du kannst damit deinem Item einen Namen geben").addEnchant(Enchantment.MENDING, 1).
                    addItemFlags(ItemFlag.HIDE_ENCHANTS).build();
            inventory.setItem(22, item);
            inventory.setItem(31, select);
            player.getInventory().setItem(player.getInventory().getHeldItemSlot(), select);
            player.removeScoreboardTag("Kit-edit-name");

        }

        // Itemlore bestimmen
        if(player.getScoreboardTags().contains("Kit-edit-lore")) {
            player.resetTitle();
            time = 0;
            event.setCancelled(true);
            meta.setLore(Collections.singletonList(message));
            select.setItemMeta(meta);
            item = new ItemBuilder(Material.SPRUCE_SIGN).setDisplayName("§9Lore bestimmen").
                    setLore("§7Du kannst damit deinem Item eine Lore setzen").addEnchant(Enchantment.MENDING, 1).
                    addItemFlags(ItemFlag.HIDE_ENCHANTS).build();
            inventory.setItem(23, item);
            inventory.setItem(31, select);
            player.getInventory().setItem(player.getInventory().getHeldItemSlot(), select);
            player.removeScoreboardTag("Kit-edit-lore");
        }

        // Itemdurability bestimmen
        if(player.getScoreboardTags().contains("Kit-edit-dura")) {
            try {
                player.resetTitle();
                time = 0;
                event.setCancelled(true);
                short dura = Short.parseShort(message);

                if (dura <= 0) {
                    player.sendMessage(Main.PREFIX() + "§cBitte nur positive Zahlen!");
                }

                select.setDurability(dura);
                item = new ItemBuilder(Material.IRON_SWORD).setDisplayName("§9Haltbarkeit bestimmen").addEnchant(Enchantment.MENDING, 1)
                        .addItemFlags(ItemFlag.HIDE_ENCHANTS).
                        setLore("§7Du kannst damit die Haltbarkeit deines Items setzen").build();
                inventory.setItem(21, item);
                inventory.setItem(31, select);
                player.getInventory().setItem(player.getInventory().getHeldItemSlot(), select);
                player.removeScoreboardTag("Kit-edit-dura");
                player.resetTitle();
            } catch (NumberFormatException x) {
                player.sendMessage(Main.PREFIX() + "§cBitte gib eine Zahl an!");
            }
        }

    }

    // setzt dem Spieler einen Titel
    public void sendNameTitle(Player player) {
        player.closeInventory();
        player.addScoreboardTag("Kit-edit-name");

        new BukkitRunnable() {
            @Override
            public void run() {

                player.sendTitle("§7Gib den Namen des Items in den Chat ein!", "§9Noch " + time + " Sekunden Zeit!");

                if (time == 0) {
                    player.resetTitle();
                    player.removeScoreboardTag("Kit-edit-name");
                    return;
                }

                time--;
            }
        }.runTaskTimer(Main.getInstance(), 20, 20);
    }

    public void sendLoreTitle(Player player) {
        player.closeInventory();
        player.addScoreboardTag("Kit-edit-lore");

        new BukkitRunnable() {
            @Override
            public void run() {

                player.sendTitle("§7Gib die Lore des Items in den Chat ein!", "§9Noch " + time + " Sekunden Zeit!");

                if (time == 0) {
                    player.resetTitle();
                    player.removeScoreboardTag("Kit-edit-lore");
                    return;
                }

                time--;
            }
        }.runTaskTimer(Main.getInstance(), 20, 20);
    }

    public void sendDuraTitle(Player player) {
        player.closeInventory();
        player.addScoreboardTag("Kit-edit-dura");

        new BukkitRunnable() {
            @Override
            public void run() {

                player.sendTitle("§7Gib die Haltbarkeit deines Items als Zahl in den Chat ein!",
                        "§9Noch " + time + " Sekunden Zeit!");

                if (time == 0) {
                    player.removeScoreboardTag("Kit-edit-dura");
                    player.resetTitle();
                    return;
                }

                time--;
            }
        }.runTaskTimer(Main.getInstance(), 20, 20);


    }

    public void resetItem(Player player) {
        player.getInventory().setItem(player.getInventory().getHeldItemSlot(), firstItem);
        inventory.setItem(31, firstItem);
    }

    public void setType(Player player) {
        select.setType(player.getInventory().getItemInMainHand().getType());
    }
}
