package net.gaminghunter.pokebattle.listeners;

import net.gaminghunter.pokebattle.Pokebattle;
import net.gaminghunter.pokebattle.inventories.shop.ShopCategory;
import net.gaminghunter.pokebattle.inventories.shop.ShopItem;
import net.gaminghunter.pokebattle.utils.MessageUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Created by Luca on 15.06.2017.
 */
public class InventoryListeners implements Listener {

    private ShopCategory category;
    private ShopItem item;

    @EventHandler
    public void onInvClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if(event.getClickedInventory() != null) {
            if (event.getClickedInventory().getName().equalsIgnoreCase("§aTeamwahl")) {
                //<editor-fold desc="Teamwahl Content">
                if (event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR) {
                    event.setCancelled(true);
                    if (event.getCurrentItem().getType() == Material.WOOL) {
                        if (event.getCurrentItem().getData().getData() == 14) {
                            if (Pokebattle.getInstance().getTeamManager().getTeamByPlayer(player) != null) {
                                Pokebattle.getInstance().getTeamManager().leaveTeam(player, Pokebattle.getInstance()
                                        .getTeamManager().getTeamByPlayer(player));
                            }
                            Pokebattle.getInstance().getTeamManager().joinTeam(player, Pokebattle.getInstance().getTeamManager()
                                    .getTeamByName("red"));
                        } else if (event.getCurrentItem().getData().getData() == 5) {
                            if (Pokebattle.getInstance().getTeamManager().getTeamByPlayer(player) != null) {
                                Pokebattle.getInstance().getTeamManager().leaveTeam(player, Pokebattle.getInstance()
                                        .getTeamManager().getTeamByPlayer(player));
                            }
                            Pokebattle.getInstance().getTeamManager().joinTeam(player, Pokebattle.getInstance().getTeamManager()
                                    .getTeamByName("green"));
                        } else if (event.getCurrentItem().getData().getData() == 4) {
                            if (Pokebattle.getInstance().getTeamManager().getTeamByPlayer(player) != null) {
                                Pokebattle.getInstance().getTeamManager().leaveTeam(player, Pokebattle.getInstance()
                                        .getTeamManager().getTeamByPlayer(player));
                            }
                            Pokebattle.getInstance().getTeamManager().joinTeam(player, Pokebattle.getInstance().getTeamManager()
                                    .getTeamByName("yellow"));
                        } else if (event.getCurrentItem().getData().getData() == 9) {
                            if (Pokebattle.getInstance().getTeamManager().getTeamByPlayer(player) != null) {
                                Pokebattle.getInstance().getTeamManager().leaveTeam(player, Pokebattle.getInstance()
                                        .getTeamManager().getTeamByPlayer(player));
                            }
                            Pokebattle.getInstance().getTeamManager().joinTeam(player, Pokebattle.getInstance().getTeamManager()
                                    .getTeamByName("blue"));
                        }
                    }
                }
                //</editor-fold>

            } else if (event.getInventory().getName().equalsIgnoreCase("§ePoke§3Shop")) {
                //<editor-fold desc="Shop Content">
                if (event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR) {
                    event.setCancelled(true);
                    if (event.getSlot() <= 8) {
                        category = Pokebattle.getInstance().getShopInventory().getCategoryByItem(event.getCurrentItem());
                        Pokebattle.getInstance().getShopInventory().loadCategoryForPlayer(category, player);
                    }
                    if(event.getSlot() >= 18) {
                        item = Pokebattle.getInstance().getShopInventory().getShopItemByItem(event.getCurrentItem());
                        if(Pokebattle.getInstance().getCoinsManager().hasCoins(player, item.getCoins())) {
                            Pokebattle.getInstance().getCoinsManager().removeCoins(player, item.getCoins());
                            player.getInventory().addItem(item.getItem());
                        } else {
                            MessageUtils.sendErrorActionbar(player, "Du hast nicht genügend Coins");
                        }
                    }
                }
                //</editor-fold>
            }
        }
    }
}
