package net.gaminghunter.pokebattle.countdowns;

import lombok.Getter;
import net.gaminghunter.pokebattle.Pokebattle;
import net.gaminghunter.pokebattle.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitTask;

/**
 * Created by Luca on 15.06.2017.
 */
public class WaitingCountdown {

    @Getter
    private boolean started;
    private BukkitTask task;

    private long delay = 0;
    private long period = 20*60;

    public void start() {
        if(!started) {
            started = true;
            task = Bukkit.getScheduler().runTaskTimerAsynchronously(Pokebattle.getInstance(), () -> {
                Bukkit.getOnlinePlayers().forEach(player -> MessageUtils.sendErrorActionbar(player,
                        "Nicht genügend Spieler online"));
            }, delay, period);
        } else {
            task.cancel();
            started = false;
            start();
        }
    }

    public void stop() {
        started = false;
        if(task != null) {
            task.cancel();
        }
    }

}
