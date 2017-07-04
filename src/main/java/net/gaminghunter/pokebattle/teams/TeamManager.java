package net.gaminghunter.pokebattle.teams;

import lombok.Getter;
import net.gaminghunter.pokebattle.Pokebattle;
import net.gaminghunter.pokebattle.teams.teams.Blue;
import net.gaminghunter.pokebattle.teams.teams.Green;
import net.gaminghunter.pokebattle.teams.teams.Red;
import net.gaminghunter.pokebattle.teams.teams.Yellow;
import net.gaminghunter.pokebattle.utils.ItemBuilder;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luca on 15.06.2017.
 */
public class TeamManager {

    @Getter
    private List<Team> teams = new ArrayList<>();

    private Team returnTeam;

    private Team red = new Red();
    private Team green = new Green();
    private Team yellow = new Yellow();
    private Team blue = new Blue();

    public void initTeams() {
        registerTeam(red);
        registerTeam(green);
        registerTeam(yellow);
        registerTeam(blue);
    }

    public void registerTeam(Team team) {
        if(!teams.contains(team)) {
            teams.add(team);
        }
    }

    public void joinTeam(Player player, Team team) {
        if(team.getPlayersInTeam().size() < team.getMaxPlayers()) {
            team.getPlayersInTeam().add(player);
            Pokebattle.getInstance().getLobbyBoard().getBoards().keySet().forEach(scoreboard -> {
                scoreboard.getTeam(team.getTeamName()).addEntry(player.getName());
            });
            team.setItem(ItemBuilder.material(team.getItem()).setLore(player.getName()).build());
            player.getInventory().setItem(0, team.getItem());
            Pokebattle.getInstance().getTeamInventory().updateInventory();
            player.sendMessage(Pokebattle.getInstance().getPrefix() + "Du bist jetzt im Team " + team.getName());
        } else {
            player.sendMessage(Pokebattle.getInstance().getPrefix() + "§cDieses Team ist leider voll");
        }

    }

    public void leaveTeam(Player player, Team team) {
        if(team.getPlayersInTeam().contains(player)) {
            team.getPlayersInTeam().remove(player);
            Pokebattle.getInstance().getLobbyBoard().getBoards().keySet().forEach(scoreboard -> {
                scoreboard.getTeam(team.getTeamName()).removeEntry(player.getName());
            });
            team.setItem(ItemBuilder.material(team.getItem()).setLore("").build());
            Pokebattle.getInstance().getTeamInventory().updateInventory();
        } else {
            player.sendMessage(Pokebattle.getInstance().getPrefix() + "§cDu bist nicht in diesem Team");
        }
    }

    public Team getTeamByName(String name) {
        returnTeam = null;
        teams.forEach(team -> {
            if(team.getTeamName().equalsIgnoreCase(name)) {
                returnTeam = team;
            }
        });
        return returnTeam;
    }

    public Team getTeamByPlayer(Player player) {
        returnTeam = null;
        teams.forEach(team -> {
            if(team.getPlayersInTeam().contains(player)) {
                returnTeam = team;
            }
        });
        return returnTeam;
    }

    public boolean playerIsInTeam(Player player, Team team) {
        return team.getPlayersInTeam().contains(player);
    }

}
