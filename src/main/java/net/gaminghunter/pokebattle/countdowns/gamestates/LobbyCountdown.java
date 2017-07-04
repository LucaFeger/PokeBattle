package net.gaminghunter.pokebattle.countdowns.gamestates;

import lombok.Getter;
import lombok.Setter;
import net.gaminghunter.pokebattle.Pokebattle;
import net.gaminghunter.pokebattle.countdowns.PokeSpawnCountdown;
import net.gaminghunter.pokebattle.enums.GameState;
import net.gaminghunter.pokebattle.utils.LocationUtils;
import net.gaminghunter.pokebattle.utils.MessageUtils;
import net.gaminghunter.pokebattle.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitTask;

/**
 * Created by Luca on 15.06.2017.
 */
public class LobbyCountdown {

    private int remoteTime = 60;

    @Getter @Setter
    private int time;
    @Getter
    private boolean started;
    private BukkitTask task;

    private long delay = 0;
    private long period = 20;
    private int counter = 1;

    public void start() {
        time = remoteTime;
        if(!started) {
            started = true;
            task = Bukkit.getScheduler().runTaskTimer(Pokebattle.getInstance(), () -> {

                if(time <= 60 && time >= 5) {
                    MessageUtils.sendActionBar("§eDas Spiel startet in §3" + time + " §eSekunden");
                } else {
                    switch (time) {
                        case 5:
                            MessageUtils.sendActionBar("§eDas Spiel startet in §3" + time + " §eSekunden");
                            playPling();
                            break;
                        case 4:
                            MessageUtils.sendActionBar("§eDas Spiel startet in §3" + time + " §eSekunden");
                            playPling();
                            break;
                        case 3:
                            MessageUtils.sendActionBar("§eDas Spiel startet in §3" + time + " §eSekunden");
                            playPling();
                            break;
                        case 2:
                            MessageUtils.sendActionBar("§eDas Spiel startet in §3" + time + " §eSekunden");
                            playPling();
                            break;
                        case 1:
                            MessageUtils.sendActionBar("§eDas Spiel startet in §3einer §eSekunde");
                            playPling();
                            break;
                        case 0:
                            MessageUtils.sendActionBar("§eDas Spiel startet §3jetzt");
                            playPling();
                            startGame();
                            break;
                    }
                }
                Pokebattle.getInstance().getLobbyBoard().updateTitle();
                time--;

            }, delay, period);
        } else {
            task.cancel();
            started = false;
            start();
        }
    }

    public void stop() {
        started = false;
        time = remoteTime;
        if(task != null) {
            task.cancel();
        }
    }

    private void playPling() {
        Bukkit.getOnlinePlayers().forEach(p -> {
            p.playSound(p.getLocation(), Sound.NOTE_PLING, (float )0.3, 2);
        });
    }

    public void startGame() {
        stop();
        counter = 1;
        Pokebattle.getInstance().setGameState(GameState.INGAME);
        Bukkit.getOnlinePlayers().forEach(player -> {
            if(Pokebattle.getInstance().getTeamManager().getTeamByPlayer(player) == null) {
                Pokebattle.getInstance().getTeamManager().getTeams().forEach(team -> {
                    if(team.getPlayersInTeam().size() <= 0) {
                        if(Pokebattle.getInstance().getTeamManager().getTeamByPlayer(player) == null) {
                            Pokebattle.getInstance().getTeamManager().joinTeam(player, team);
                        }
                    }
                });
            }
            Pokebattle.getInstance().getLobbyBoard().getBoards().keySet().forEach(scoreboard -> {
                scoreboard.getTeam(Pokebattle.getInstance().getTeamManager().getTeamByPlayer(player).getTeamName())
                        .addEntry(player.getName());
            });
            Pokebattle.getInstance().getIngameBoard().createScoreboard(player);
            player.teleport(LocationUtils.deserializeFully(String.valueOf(Pokebattle.getInstance().getLocationsConfig().get(
                    "playerspawn." + counter))));
            PokeSpawnCountdown spawnCountdown = new PokeSpawnCountdown(Utils.random(4, 20) * 20, player);
            Pokebattle.getInstance().getSpawnCountdowns().put(player, spawnCountdown);
            spawnCountdown.start();
            counter++;
            Pokebattle.getInstance().getInventoryPresets().loadIngamePreset(player);
        });
        Pokebattle.getInstance().getIngameCountdown().start();
    }

}
