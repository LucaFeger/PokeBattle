package net.gaminghunter.pokebattle.listeners;

import net.gaminghunter.pokebattle.Pokebattle;
import net.gaminghunter.pokebattle.countdowns.CatchCountdown;
import net.gaminghunter.pokebattle.enums.GameState;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luca on 16.06.2017.
 */
public class EntityListeners implements Listener {

    private List<Entity> inCatch = new ArrayList<>();

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if(inCatch.contains(event.getEntity()))
            return;

        if(event.getDamager() instanceof Snowball) {
            Snowball snowball = (Snowball) event.getDamager();
            if(snowball.getShooter() instanceof Player) {
                Player player = (Player) snowball.getShooter();
                if(event.getEntity() instanceof Chicken) {
                    new CatchCountdown(3, player, event.getEntity(), 30).start();
                } else if(event.getEntity() instanceof Pig) {
                    new CatchCountdown(3, player, event.getEntity(), 20).start();
                } else if(event.getEntity() instanceof Bat) {
                    new CatchCountdown(3, player, event.getEntity(), 50).start();
                } else if(event.getEntity() instanceof Sheep) {
                    new CatchCountdown(3, player, event.getEntity(), 10).start();
                } else if(event.getEntity() instanceof Cow) {
                    new CatchCountdown(3, player, event.getEntity(), 5).start();
                } else if(event.getEntity() instanceof Rabbit) {
                    new CatchCountdown(3, player, event.getEntity(), 30).start();
                }
                Pokebattle.getInstance().getIngameBoard().update(player);
                inCatch.add(event.getEntity());
            }
        }
    }

    @EventHandler
    public void onSpawn(CreatureSpawnEvent event) {
        if(event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.CUSTOM)
            event.setCancelled(true);
    }

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent event) {
        if(event.getRightClicked().getType() == EntityType.VILLAGER && Pokebattle.getInstance().getGameState()
                == GameState.SHOP) {
            event.setCancelled(true);
            Pokebattle.getInstance().getShopInventory().openInventory(event.getPlayer());
        }
    }

}
