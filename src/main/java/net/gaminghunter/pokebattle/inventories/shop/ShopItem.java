package net.gaminghunter.pokebattle.inventories.shop;

import lombok.Getter;
import net.gaminghunter.pokebattle.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luca on 29.06.2017.
 */
@Getter
public class ShopItem {

    private int row = 2;
    private ItemStack item;

    private Material material;
    private String displayName;
    private List<String> lores;
    private int slot;
    private byte data = 0;
    private int count = 1;
    private int coins;

    public ShopItem setMaterial(Material material) {
        this.material = material;
        return this;
    }

    public ShopItem setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public ShopItem setLores(List<String> lores) {
        this.lores = lores;
        return this;
    }

    public ShopItem setSlot(int slot) {
        this.slot = (slot + (row * 9)) - 1;
        return this;
    }

    public ShopItem setCoins(int coins) {
        this.coins = coins;
        return this;
    }

    public ItemStack getItem() {
        if(item == null) build();
        return item;
    }

    public void setData(byte data) {
        this.data = data;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ItemStack build() {
        if(lores == null) lores = new ArrayList<>();
        lores.add("§ePreis: §3" + coins + " §eCoins");
        this.item = ItemBuilder.material(material, count, data).setDisplayName(displayName).setLore(lores).build();
        return item;
    }

}
