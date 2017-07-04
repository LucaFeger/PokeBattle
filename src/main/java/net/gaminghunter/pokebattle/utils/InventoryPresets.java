package net.gaminghunter.pokebattle.utils;

import net.gaminghunter.pokebattle.Pokebattle;
import net.gaminghunter.pokebattle.teams.teams.Red;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * Created by Luca on 15.06.2017.
 */
public class InventoryPresets {

    public void loadLobbyPreset(Player player) {
        player.getInventory().clear();
        player.getActivePotionEffects().clear();
        player.setGameMode(GameMode.ADVENTURE);
        player.setHealth(20);
        player.setSaturation(20);
        player.getInventory().setArmorContents(null);

        player.getInventory().setItem(0, ItemBuilder.material(Material.WOOL).setDisplayName("Team wählen").build());
    }

    public void loadIngamePreset(Player player) {
        player.getInventory().clear();
        player.getActivePotionEffects().clear();
        player.setGameMode(GameMode.ADVENTURE);
        player.setHealth(20);
        player.setSaturation(20);

        player.getInventory().setItem(0, ItemBuilder.material(Material.SNOW_BALL, 10)
                .setDisplayName("§ePoke§3Ball").build());
        if(Pokebattle.getInstance().getTeamManager().playerIsInTeam(player, Pokebattle.getInstance().getTeamManager()
            .getTeamByName("red"))) {
            player.getInventory().setHelmet(ItemBuilder.material(Material.LEATHER_HELMET).setColor(186, 9, 9).build());
            player.getInventory().setChestplate(ItemBuilder.material(Material.LEATHER_CHESTPLATE).setColor(186, 9, 9).build());
            player.getInventory().setLeggings(ItemBuilder.material(Material.LEATHER_LEGGINGS).setColor(186, 9, 9).build());
            player.getInventory().setBoots(ItemBuilder.material(Material.LEATHER_BOOTS).setColor(186, 9, 9).build());
        } else if(Pokebattle.getInstance().getTeamManager().playerIsInTeam(player, Pokebattle.getInstance().getTeamManager()
                .getTeamByName("green"))) {
            player.getInventory().setHelmet(ItemBuilder.material(Material.LEATHER_HELMET).setColor(1, 150, 2).build());
            player.getInventory().setChestplate(ItemBuilder.material(Material.LEATHER_CHESTPLATE).setColor(1, 150, 2).build());
            player.getInventory().setLeggings(ItemBuilder.material(Material.LEATHER_LEGGINGS).setColor(1, 150, 2).build());
            player.getInventory().setBoots(ItemBuilder.material(Material.LEATHER_BOOTS).setColor(1, 150, 2).build());
        } else if(Pokebattle.getInstance().getTeamManager().playerIsInTeam(player, Pokebattle.getInstance().getTeamManager()
                .getTeamByName("blue"))) {
            player.getInventory().setHelmet(ItemBuilder.material(Material.LEATHER_HELMET).setColor(7, 20, 119).build());
            player.getInventory().setChestplate(ItemBuilder.material(Material.LEATHER_HELMET).setColor(7, 20, 119).build());
            player.getInventory().setLeggings(ItemBuilder.material(Material.LEATHER_LEGGINGS).setColor(7, 20, 119).build());
            player.getInventory().setBoots(ItemBuilder.material(Material.LEATHER_BOOTS).setColor(7, 20, 119).build());
        } else {
            player.getInventory().setHelmet(ItemBuilder.material(Material.LEATHER_HELMET).setColor(201, 204, 59).build());
            player.getInventory().setChestplate(ItemBuilder.material(Material.LEATHER_CHESTPLATE).setColor(201, 204, 59).build());
            player.getInventory().setLeggings(ItemBuilder.material(Material.LEATHER_LEGGINGS).setColor(201, 204, 59).build());
            player.getInventory().setBoots(ItemBuilder.material(Material.LEATHER_BOOTS).setColor(201, 204, 59).build());
        }
    }

}
