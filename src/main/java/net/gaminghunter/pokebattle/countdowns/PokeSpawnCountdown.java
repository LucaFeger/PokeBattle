package net.gaminghunter.pokebattle.countdowns;

import lombok.Getter;
import lombok.Setter;
import net.gaminghunter.pokebattle.Pokebattle;
import net.gaminghunter.pokebattle.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.Random;

/**
 * Created by Luca on 16.06.2017.
 */
public class PokeSpawnCountdown {

    @Getter @Setter
    private int time;
    private boolean started;
    private BukkitTask task;

    private Random random;
    private Player player;
    private Entity entity;

    public PokeSpawnCountdown(int time, Player player) {
        this.time = time;
        this.player = player;
        this.random = new Random();
    }

    public void start() {
        if(!started) {
            started = true;
            task = Bukkit.getScheduler().runTaskLater(Pokebattle.getInstance(), () -> {
                switch (Pokebattle.getInstance().getPokemons().get(random.nextInt(Pokebattle.getInstance().getPokemons().size()))) {
                    case BAT:
                        entity = player.getWorld().spawnEntity(player.getLocation().clone().add(Utils.random(2, 5), 0,
                                Utils.random(2, 5)), EntityType.BAT);
                        break;
                    case COW:
                        entity = player.getWorld().spawnEntity(player.getLocation().clone().add(Utils.random(2, 5), 0,
                                Utils.random(2, 5)), EntityType.COW);
                        break;
                    case PIG:
                        entity = player.getWorld().spawnEntity(player.getLocation().clone().add(Utils.random(2, 5), 0,
                                Utils.random(2, 5)), EntityType.PIG);
                        break;
                    case SHEEP:
                        entity = player.getWorld().spawnEntity(player.getLocation().clone().add(Utils.random(2, 5), 0,
                                Utils.random(2, 5)), EntityType.SHEEP);
                        break;
                    case RABBIT:
                        entity = player.getWorld().spawnEntity(player.getLocation().clone().add(Utils.random(2, 5), 0,
                                Utils.random(2, 5)), EntityType.RABBIT);
                        break;
                    case CHICKEN:
                        entity = player.getWorld().spawnEntity(player.getLocation().clone().add(Utils.random(2, 5), 0,
                                Utils.random(2, 5)), EntityType.CHICKEN);
                        break;
                }

                setTime(Utils.random(4, 20) * 20);
                started = false;
                start();
                Pokebattle.getInstance().getPokemonManager().getLivingEntities(player).forEach(entity -> {
                    entity.getLocation().getWorld().playEffect(entity.getLocation().clone().add(0, 1, 0),
                            Effect.SMOKE, 52);
                    entity.remove();
                });
                Pokebattle.getInstance().getPokemonManager().addLivingEntity(player, entity);
            }, time);
        }
    }

    public void stop() {
        if(task != null) task.cancel();
        if(started) started = false;
    }

}
