package net.gaminghunter.pokebattle.teams;

import net.gaminghunter.pokebattle.Pokebattle;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * Created by Luca on 15.06.2017.
 */
public class TeamInventory {

    private Inventory inventory = Bukkit.createInventory(null, 9, "§aTeamwahl");
    private int i = 0;

    public void updateInventory() {
        i = 0;
        inventory.clear();
        Pokebattle.getInstance().getTeamManager().getTeams().forEach(team -> {
            System.out.println(i);
            inventory.setItem(i, team.getItem());
            i++;
        });
        inventory.getViewers().forEach(humanEntity -> ((Player)humanEntity).updateInventory());
    }

    public void openInventory(Player player) {
        player.openInventory(inventory);
    }

}
