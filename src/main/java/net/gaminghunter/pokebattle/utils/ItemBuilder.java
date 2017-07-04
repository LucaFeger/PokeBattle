package net.gaminghunter.pokebattle.utils;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;


public class ItemBuilder {

    private ItemStack item;

    public static ItemBuilder material(Material material) {
        return new ItemBuilder(material);
    }

    public static ItemBuilder material(Material material, int amount) {
        return new ItemBuilder(material, amount);
    }

    public static ItemBuilder material(Material material, int amount, short data) {
        return new ItemBuilder(material, amount, data);
    }

    public static ItemBuilder material(ItemStack itemStack) {
        return new ItemBuilder(itemStack);
    }

    private ItemBuilder(Material material) {
        this.item = new ItemStack(material);
    }

    private ItemBuilder(Material material, int amount) {
        this.item = new ItemStack(material, amount);
    }

    private ItemBuilder(Material material, int amount, short data) {
        this.item = new ItemStack(material, amount, data);
    }

    private ItemBuilder(ItemStack itemStack) {
        this.item = itemStack;
    }

    public ItemBuilder setAmount(int amount) {
        item.setAmount(amount);
        return this;
    }

    public ItemBuilder setDisplayName(String displayName) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setDurability(short durability) {
        item.setDurability(durability);
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        ItemMeta meta = item.getItemMeta();
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        ItemMeta meta = item.getItemMeta();
        meta.setLore(lore);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment ench, int level) {
        item.addUnsafeEnchantment(ench, level);
        return this;
    }

    public ItemBuilder setSkullOwner(String name) {
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwner(name);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setColor(int r, int g, int b) {
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        Color color = Color.fromRGB(r, g, b);
        meta.setColor(color);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setColor(Color color) {
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        meta.setColor(color);
        item.setItemMeta(meta);
        return this;
    }

    public static String getDisplayname(ItemStack is) {
        ItemMeta meta = is.getItemMeta();
        try {
            return meta.getDisplayName();
        } catch (NullPointerException ex) {
            return null;
        }
    }

    public static boolean hasThisName(ItemStack is, String displayname, boolean withColorCodes) {
        ItemMeta meta = is.getItemMeta();
        try {
            String display = meta.getDisplayName();
            if (!withColorCodes) {
                display = ChatColor.stripColor(display);
            }
            return display.equals(displayname);
        } catch (NullPointerException ex) {
            return false;
        }
    }

    public static boolean hasThisName(ItemStack is, String displayname) {
        return hasThisName(is, displayname, true);
    }

    /**
     * ONLY WITH SPIGOT!!
     */

    public ItemBuilder setUnbreakable(boolean unbreakable) {
        ItemMeta meta = item.getItemMeta();
        meta.spigot().setUnbreakable(unbreakable);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setMeta(ItemMeta meta) {
        item.setItemMeta(meta);
        return this;
    }

    /**
     * Build the ItemStack
     *
     * @return {@link ItemStack}
     */

    public ItemStack build() {
        return item;
    }
}
