package net.gaminghunter.pokebattle.scoreboards;

import lombok.Getter;
import net.gaminghunter.pokebattle.Pokebattle;
import net.gaminghunter.pokebattle.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Luca on 12.06.2017.
 */
public class IngameBoard {

    private Scoreboard board;
    private Objective objective;

    @Getter
    private Map<Scoreboard, Player> boards = new HashMap<>();

    public void createScoreboard(Player player) {
        board = Bukkit.getScoreboardManager().getNewScoreboard();
        objective = board.registerNewObjective("info", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName("§ePokemon§3Battle §7| " + "§3" + Utils.getDurationString(Pokebattle.getInstance().getIngameCountdown().getTime()));

        Team catchedDesc = board.registerNewTeam("catchedDesc");
        catchedDesc.setPrefix("§eGefangene ");
        catchedDesc.setSuffix("§ePokemons:");
        catchedDesc.addEntry(ChatColor.AQUA.toString());

        Team catched = board.registerNewTeam("catched");
        catched.setPrefix("§30");
        catched.addEntry(ChatColor.BLACK.toString());

        Team spacer = board.registerNewTeam("spacer");
        spacer.setPrefix(" ");
        spacer.addEntry(ChatColor.BLUE.toString());

        Team onlineDesc = board.registerNewTeam("onlineDesc");
        onlineDesc.setPrefix("§eSpieler:");
        onlineDesc.addEntry(ChatColor.BOLD.toString());

        Team online = board.registerNewTeam("online");
        online.setPrefix("§30");
        online.addEntry(ChatColor.DARK_AQUA.toString());

        Team spacer1 = board.registerNewTeam("spacer1");
        spacer1.setPrefix("  ");
        spacer1.addEntry(ChatColor.DARK_BLUE.toString());

        Team coinsDesc = board.registerNewTeam("coinsDesc");
        coinsDesc.setPrefix("§eCoins:");
        coinsDesc.addEntry(ChatColor.DARK_GRAY.toString());

        Team coins = board.registerNewTeam("coins");
        coins.setPrefix("§3" + Pokebattle.getInstance().getCoinsManager().getCoins(player));
        coins.addEntry(ChatColor.DARK_GREEN.toString());


        objective.getScore(ChatColor.AQUA.toString()).setScore(10);
        objective.getScore(ChatColor.BLACK.toString()).setScore(9);
        objective.getScore(ChatColor.BLUE.toString()).setScore(8);
        objective.getScore(ChatColor.DARK_GRAY.toString()).setScore(7);
        objective.getScore(ChatColor.DARK_GREEN.toString()).setScore(6);
        objective.getScore(ChatColor.DARK_BLUE.toString()).setScore(5);
        objective.getScore(ChatColor.BOLD.toString()).setScore(4);
        objective.getScore(ChatColor.DARK_AQUA.toString()).setScore(3);

        Pokebattle.getInstance().getTeamManager().getTeams().forEach(team -> {
            if(board.getTeam(team.getTeamName()) == null) {
                board.registerNewTeam(team.getTeamName());
            }
            board.getTeam(team.getTeamName()).setPrefix(team.getPrefix());
        });

        Bukkit.getOnlinePlayers().forEach(o -> {
            if(Pokebattle.getInstance().getTeamManager().getTeamByPlayer(o) != null) {
                board.getTeam(Pokebattle.getInstance().getTeamManager().getTeamByPlayer(o).getTeamName()).addEntry(o.getName());
            }
        });

        boards.put(board, player);
        player.setScoreboard(board);
    }

    public void update() {
        boards.keySet().forEach(scoreboard -> {
            scoreboard.getTeam("catched").setPrefix("§3" + Pokebattle.getInstance().getPokemonManager()
                .getPokemons(boards.get(scoreboard)));
            scoreboard.getTeam("coins").setPrefix("§3" + Pokebattle.getInstance().getCoinsManager()
                .getCoins(boards.get(scoreboard)));
            scoreboard.getTeam("online").setPrefix("§3" + Bukkit.getOnlinePlayers().size());
        });
    }

    public void update(Player player) {
        for (Scoreboard scoreboard : boards.keySet()) {
            if(boards.get(scoreboard) == player) {
                scoreboard.getTeam("catched").setPrefix("§3" + Pokebattle.getInstance().getPokemonManager().getPokemons(player));
                scoreboard.getTeam("coins").setPrefix("§3" + Pokebattle.getInstance().getCoinsManager().getCoins(player));
                return;
            }
        }
    }

    public void updateTitle() {
        boards.keySet().forEach(scoreboard -> {
            scoreboard.getObjective(DisplaySlot.SIDEBAR).setDisplayName("§ePokemon§3Battle §7| " + "§3" +
                    Utils.getDurationString(Pokebattle.getInstance().getIngameCountdown().getTime())
            );
        });
    }

}
