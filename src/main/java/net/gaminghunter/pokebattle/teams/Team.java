package net.gaminghunter.pokebattle.teams;

import lombok.*;
import net.gaminghunter.pokebattle.Pokebattle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luca on 15.06.2017.
 */
@Getter
@Setter
public class Team {

    private String name;
    private String teamName;
    private String prefix;
    private ItemStack item;
    private int maxPlayers;

    private List<Player> playersInTeam = new ArrayList<>();

    public Team(String name, String teamName, String prefix, ItemStack item, int maxPlayers) {
        this.name = name;
        this.teamName = teamName;
        this.prefix = prefix;
        this.item = item;
        this.maxPlayers = maxPlayers;
    }


}
