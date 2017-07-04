package net.gaminghunter.pokebattle.listeners;

import net.gaminghunter.pokebattle.Pokebattle;
import net.gaminghunter.pokebattle.enums.GameState;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;


/**
 * Created by Luca on 12.06.2017.
 */
public class PlayerListeners implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Pokebattle.getInstance().getInventoryPresets().loadLobbyPreset(event.getPlayer());
        //<editor-fold desc="GameState = LOBBY">
        if(Pokebattle.getInstance().getGameState() == GameState.LOBBY) {
            event.setJoinMessage("§a+ §7" + event.getPlayer().getName());
            Pokebattle.getInstance().getLobbyBoard().createScoreboard(event.getPlayer());
            if (Bukkit.getOnlinePlayers().size() >= Pokebattle.getInstance().getMinPlayers()) {
                Pokebattle.getInstance().getWaitingCountdown().stop();
                if(!Pokebattle.getInstance().getLobbyCountdown().isStarted()) {
                    Pokebattle.getInstance().getLobbyCountdown().start();
                }
            } else {
                Pokebattle.getInstance().getLobbyCountdown().stop();
                if(!Pokebattle.getInstance().getWaitingCountdown().isStarted()) {
                    Pokebattle.getInstance().getWaitingCountdown().start();
                }
            }
            Pokebattle.getInstance().getLobbyBoard().update();

            Bukkit.getScheduler().runTaskLater(Pokebattle.getInstance(), () -> {
                if (Pokebattle.getInstance().getSpawnLocation() != null) {
                    if (!event.getPlayer().hasPlayedBefore()) {
                        event.getPlayer().teleport(Pokebattle.getInstance().getSpawnLocation());
                    } else {
                        event.getPlayer().teleport(Pokebattle.getInstance().getSpawnLocation());
                    }
                }
            }, 5);
        }
        //</editor-fold>
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if(Pokebattle.getInstance().getGameState() == GameState.LOBBY) {
            //<editor-fold desc="GameState = LOBBY">
            event.setQuitMessage("§c- §7" + event.getPlayer().getName());
            Bukkit.getScheduler().runTaskLater(Pokebattle.getInstance(), () -> {
                Pokebattle.getInstance().getLobbyBoard().update();
                if (Bukkit.getOnlinePlayers().size() < Pokebattle.getInstance().getMinPlayers()) {
                    Pokebattle.getInstance().getLobbyCountdown().stop();
                    if(!Pokebattle.getInstance().getWaitingCountdown().isStarted()) {
                        Pokebattle.getInstance().getWaitingCountdown().start();
                    }
                }
            }, 5);
            if(Pokebattle.getInstance().getTeamManager().getTeamByPlayer(event.getPlayer()) != null) {
                Pokebattle.getInstance().getTeamManager().leaveTeam(event.getPlayer(),
                        Pokebattle.getInstance().getTeamManager().getTeamByPlayer(event.getPlayer()));
            }
            //</editor-fold>
        } else {
            event.setQuitMessage(Pokebattle.getInstance().getPrefix() + "§eDer Spieler §3" + event.getPlayer()
                    .getName() + " §aist ausgeschieden");
            Bukkit.getScheduler().runTaskLater(Pokebattle.getInstance(), () -> {
                Pokebattle.getInstance().getIngameBoard().update();
                if(Bukkit.getOnlinePlayers().size() == 1) {

                }
            }, 5);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if(Pokebattle.getInstance().getGameState() == GameState.DEATHMATCH) {
            event.setCancelled(false);
        } else {
            if(event.getEntity() instanceof Player) {
                event.setCancelled(true);
            }
        }
    }

}
