package net.gaminghunter.pokebattle.listeners;

import net.gaminghunter.pokebattle.Pokebattle;
import net.gaminghunter.pokebattle.enums.GameState;
import org.bukkit.Material;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * Created by Luca on 19.06.2017.
 */
public class WorldListeners implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if(Pokebattle.getInstance().getGameState() == GameState.DEATHMATCH) {
            if(event.getBlock().getType() == Material.TNT) {
                event.setCancelled(true);
                event.getBlock().getLocation().getWorld().spawn(event.getBlock().getLocation(), TNTPrimed.class);
            } else {
                event.setCancelled(true);
            }
        } else {
            event.setCancelled(true);
        }
    }

}
