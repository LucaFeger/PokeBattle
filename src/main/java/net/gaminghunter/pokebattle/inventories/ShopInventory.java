package net.gaminghunter.pokebattle.inventories;

import net.gaminghunter.pokebattle.inventories.shop.ShopCategory;
import net.gaminghunter.pokebattle.inventories.shop.ShopItem;
import net.gaminghunter.pokebattle.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Luca on 27.06.2017.
 */
public class ShopInventory {

    private List<ShopCategory> categories = new ArrayList<>();
    private HashMap<Player, Inventory> inventories = new HashMap<>();

    private ShopCategory returnCategory = null;
    private ShopItem returnItem = null;

    public void initShop() {
        ShopCategory weapons = new ShopCategory()
                .setMaterial(Material.IRON_SWORD)
                .setDisplayName("§3Waffen")
                .setLores(Arrays.asList("§eGehst du in den Nahkampf?", "§eOder spielst du passiv?"))
                .setSlot(1);

        weapons.addItem(new ShopItem().setMaterial(Material.WOOD_SWORD).setSlot(1).setCoins(10).setDisplayName("§3Holzschwert"));
        weapons.addItem(new ShopItem().setMaterial(Material.STONE_SWORD).setSlot(2).setCoins(40).setDisplayName("§3Steinschwert"));
        weapons.addItem(new ShopItem().setMaterial(Material.IRON_SWORD).setSlot(3).setCoins(70).setDisplayName("§3Eiseinschwert"));
        weapons.addItem(new ShopItem().setMaterial(Material.DIAMOND_SWORD).setSlot(1).setCoins(100).setDisplayName("§3Diamantschwert"));
        weapons.addItem(new ShopItem().setMaterial(Material.BOW).setSlot(8).setCoins(50).setDisplayName("§3Bogen"));
        weapons.addItem(new ShopItem().setMaterial(Material.ARROW).setSlot(9).setCoins(5).setDisplayName("§3Pfeil"));

        ShopCategory armor = new ShopCategory()
                .setMaterial(Material.IRON_CHESTPLATE)
                .setDisplayName("§3Rüstung")
                .setLores(Arrays.asList("§eViel Geld für Rüstung investieren?", "§eOder lieber Risiko?"))
                .setSlot(2);
        armor.addItem(new ShopItem().setMaterial(Material.CHAINMAIL_BOOTS).setSlot(1).setCoins(20).setDisplayName("§3Kettenschuhe"));
        armor.addItem(new ShopItem().setMaterial(Material.CHAINMAIL_LEGGINGS).setSlot(2).setCoins(20).setDisplayName("§3Kettenschuhe"));
        armor.addItem(new ShopItem().setMaterial(Material.CHAINMAIL_CHESTPLATE).setSlot(3).setCoins(20).setDisplayName("§3Kettenharnisch"));
        armor.addItem(new ShopItem().setMaterial(Material.CHAINMAIL_HELMET).setSlot(4).setCoins(20).setDisplayName("§3Kettenhelm"));
        armor.addItem(new ShopItem().setMaterial(Material.IRON_BOOTS).setSlot(6).setCoins(20).setDisplayName("§3Eisenschuhe"));
        armor.addItem(new ShopItem().setMaterial(Material.IRON_LEGGINGS).setSlot(7).setCoins(20).setDisplayName("§3Eisenhose"));
        armor.addItem(new ShopItem().setMaterial(Material.IRON_CHESTPLATE).setSlot(8).setCoins(20).setDisplayName("§3Eisenharnisch"));
        armor.addItem(new ShopItem().setMaterial(Material.IRON_HELMET).setSlot(9).setCoins(20).setDisplayName("§3Eisenhelm"));

        ShopCategory food = new ShopCategory()
                .setMaterial(Material.COOKED_BEEF)
                .setDisplayName("§3Essen")
                .setLores(Arrays.asList("§eGestärkt durch gutes Essen?", "§eOder Blautrausch durch arme Bauernkost"))
                .setSlot(3);
        food.addItem(new ShopItem().setMaterial(Material.APPLE).setDisplayName("§3Apfel").setCoins(2).setSlot(1));
        food.addItem(new ShopItem().setMaterial(Material.CARROT_ITEM).setDisplayName("§3Möhre").setCoins(2).setSlot(2));
        food.addItem(new ShopItem().setMaterial(Material.RAW_BEEF).setDisplayName("§3Rohes Fleisch").setCoins(5).setSlot(3));
        food.addItem(new ShopItem().setMaterial(Material.COOKED_BEEF).setDisplayName("§3Steak").setCoins(10).setSlot(4));
        food.addItem(new ShopItem().setMaterial(Material.GOLDEN_APPLE).setDisplayName("§3Goldener Apfel").setCoins(10).setSlot(9));

        ShopCategory extras = new ShopCategory()
                .setMaterial(Material.TNT)
                .setDisplayName("§3Extras")
                .setLores(Arrays.asList("§eInvestiere in ein taktisches Spiel?", "§eOder werde Opfer eine Falle?"))
                .setSlot(9);
        extras.addItem(new ShopItem().setMaterial(Material.TNT).setDisplayName("§3TNT").setCoins(30).setSlot(1));


        categories.add(weapons);
        categories.add(armor);
        categories.add(food);
        categories.add(extras);
    }

    public void buildInventory(Inventory inventory) {
        for (int i = 9; i<9*2; i++) {
            inventory.setItem(i, ItemBuilder.material(Material.STAINED_GLASS_PANE, 1, (byte) 7)
                    .setDisplayName(" ")
            .build());
        }

        categories.forEach(shopCategory -> {
            inventory.setItem(shopCategory.getSlot(), shopCategory.build());
            shopCategory.getItems().forEach(item -> {

            });
        });
    }

    public void openInventory(Player player) {
        if(!inventories.containsKey(player)) {
            inventories.put(player, Bukkit.createInventory(null, 9*3, "§ePoke§3Shop"));
        }
        Inventory inventory = inventories.get(player);
        buildInventory(inventory);
        player.openInventory(inventory);
    }

    public ShopCategory getCategoryByItem(ItemStack itemStack) {
        categories.forEach(shopCategory -> {
            if(shopCategory.getItem().getType() == itemStack.getType()) {
                returnCategory = shopCategory;
            }
        });
        return returnCategory;
    }

    public ShopItem getShopItemByItem(ItemStack stack) {
        categories.forEach(category -> {
            category.getItems().forEach(item -> {
                if(item.getItem().getType() == stack.getType()) {
                    returnItem = item;
                }
            });
        });
        return returnItem;
    }

    public void loadCategoryForPlayer(ShopCategory category, Player player) {
        Inventory playerInventory = inventories.get(player);
        for (int i = 18; i<9*3; i++) {
            playerInventory.setItem(i, ItemBuilder.material(Material.AIR).build());
        }
        category.getItems().forEach(item -> {
            playerInventory.setItem(item.getSlot(), item.getItem());
        });
        player.updateInventory();
    }

}
