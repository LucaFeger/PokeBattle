package net.gaminghunter.pokebattle.inventories.shop;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.gaminghunter.pokebattle.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luca on 29.06.2017.
 */
@Getter
public class ShopCategory {

    private int row = 0;
    private ItemStack item;

    private Material material;
    private String displayName;
    private List<String> lores;
    private int slot;
    private List<ShopItem> items = new ArrayList<>();

    public ShopCategory setMaterial(Material material) {
        this.material = material;
        return this;
    }

    public ShopCategory setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public ShopCategory setLores(List<String> lores) {
        this.lores = lores;
        return this;
    }

    public ShopCategory setSlot(int slot) {
        this.slot = (slot + (row * 9)) - 1;
        return this;
    }

    public void addItem(ShopItem item) {
        if(!items.contains(item)) items.add(item);
    }

    public ItemStack getItem() {
        if(item == null) build();
        return item;
    }

    public ItemStack build() {
        this.item = ItemBuilder.material(material).setDisplayName(displayName).setLore(lores).build();
        return item;
    }

}
