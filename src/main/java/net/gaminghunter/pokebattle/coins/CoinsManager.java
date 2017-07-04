package net.gaminghunter.pokebattle.coins;

import net.gaminghunter.pokebattle.Pokebattle;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Luca on 15.06.2017.
 */
public class CoinsManager {

    private Map<Player, Integer> coins = new HashMap<>();

    public int getCoins(Player player) {
        return coins.containsKey(player) ? coins.get(player) : 0;
    }

    public void setCoins(Player player, int coins) {
        this.coins.put(player, coins);
    }

    public void addCoins(Player player, int coins) {
        setCoins(player, getCoins(player) + coins);
    }

    public void removeCoins(Player player, int coins) {
        if(hasCoins(player, coins)) {
            setCoins(player, getCoins(player) - coins);
            Pokebattle.getInstance().getIngameBoard().update(player);
        }
    }

    public boolean hasCoins(Player player, int amount) {
        return coins.containsKey(player) ? coins.get(player) >= amount : false;
    }

}
