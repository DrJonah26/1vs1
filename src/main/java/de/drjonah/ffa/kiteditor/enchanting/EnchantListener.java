package de.drjonah.ffa.kiteditor.enchanting;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.ItemStack;

public class EnchantListener implements Listener {

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {

        if (event.getInventory() instanceof EnchantingInventory) {
            EnchantingInventory inv = (EnchantingInventory) event.getInventory();

            ItemStack stack = new ItemStack(Material.LAPIS_LAZULI);
            stack.setAmount(64);

            inv.setItem(1, stack);
        }
    }
}
