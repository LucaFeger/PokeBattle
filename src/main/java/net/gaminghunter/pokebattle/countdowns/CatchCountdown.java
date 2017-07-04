package net.gaminghunter.pokebattle.countdowns;

import lombok.Getter;
import lombok.Setter;
import net.gaminghunter.pokebattle.Pokebattle;
import net.gaminghunter.pokebattle.utils.MessageUtils;
import net.gaminghunter.pokebattle.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.lang.reflect.Method;
import java.util.Random;

/**
 * Created by Luca on 16.06.2017.
 */
public class CatchCountdown {

    @Getter @Setter
    private int time;
    private boolean started;
    private BukkitTask task;

    private Player player;
    private Entity entity;
    private Random random;
    private int coins;

    private PokeSpawnCountdown pokeSpawnCountdown;

    public CatchCountdown(int time, Player player, Entity entity, int coins) {
        this.time = time;
        this.player = player;
        this.entity = entity;
        this.random = new Random();
        this.coins = coins;
    }

    public void start() {
        if(!started) {
            Pokebattle.getInstance().getSpawnCountdowns().get(player).stop();
            Utils.setAiEnabled(entity, false);
            started = true;
            task = Bukkit.getScheduler().runTaskTimer(Pokebattle.getInstance(), () -> {
                if(time >= 1) {
                    MessageUtils.sendActionBar(player, "§eDein Pokemon wird gefangen in §3" + time);
                } else {
                    int rnd = random.nextInt(4);
                    if(rnd < 3) {
                        player.sendMessage(Pokebattle.getInstance().getPrefix() + "§ePokemon wurde gefangen §7| §3+"
                                + coins + " Coins");
                        Pokebattle.getInstance().getCoinsManager().addCoins(player, coins);
                        Pokebattle.getInstance().getPokemonManager().addPokemons(player, 1);
                        Pokebattle.getInstance().getPokemonManager().removeLivingEntity(player, entity);
                        Pokebattle.getInstance().getIngameBoard().update(player);
                        Utils.setAiEnabled(entity, true);
                        entity.setVelocity(new Vector(0, 1.9, 0));
                        Bukkit.getScheduler().runTaskLater(Pokebattle.getInstance(), () -> entity.remove(), 40);
                    } else {
                        MessageUtils.sendErrorActionbar(player, "Dein Pokemon ist wieder entwischt :(");
                        entity.getLocation().getWorld().playEffect(entity.getLocation().clone().add(0, 1, 0),
                                Effect.SMOKE, 52);
                        Bukkit.getScheduler().runTaskLater(Pokebattle.getInstance(), () -> entity.remove(), 40);
                    }
                    setTime(3);


                    if(entity instanceof Chicken) {
                        pokeSpawnCountdown = new PokeSpawnCountdown(Utils.random(7, 25) * 20, player);
                    } else if(entity instanceof Pig) {
                        pokeSpawnCountdown = new PokeSpawnCountdown(Utils.random(3, 10) * 20, player);
                    } else if(entity instanceof Bat) {
                        pokeSpawnCountdown = new PokeSpawnCountdown(Utils.random(9, 30) * 20, player);
                    } else if(entity instanceof Sheep) {
                        pokeSpawnCountdown = new PokeSpawnCountdown(Utils.random(2, 5) * 20, player);
                    } else if(entity instanceof Cow) {
                        pokeSpawnCountdown = new PokeSpawnCountdown(Utils.random(2, 5) * 20, player);
                    } else if(entity instanceof Rabbit) {
                        pokeSpawnCountdown = new PokeSpawnCountdown(Utils.random(6, 18) * 20, player);
                    }
                    pokeSpawnCountdown.start();
                    Pokebattle.getInstance().getSpawnCountdowns().put(player, pokeSpawnCountdown);
                    stop();
                }
                time--;
            }, 0, 20);
        }
    }

    public void stop() {
        if(task != null) task.cancel();
        if(started) started = false;
    }

}
