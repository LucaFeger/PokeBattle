package net.gaminghunter.pokebattle.listeners;

import net.gaminghunter.pokebattle.Pokebattle;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by Luca on 15.06.2017.
 */
public class ItemListeners implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        if(event.getItem() != null && event.getItem().getType() != Material.AIR) {
            if(event.getItem().getType() == Material.WOOL) {
                Pokebattle.getInstance().getTeamInventory().openInventory(event.getPlayer());
            }
        }
    }

}
