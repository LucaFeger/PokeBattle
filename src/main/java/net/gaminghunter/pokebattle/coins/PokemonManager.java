package net.gaminghunter.pokebattle.coins;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Luca on 16.06.2017.
 */
public class PokemonManager {

    private Map<Player, Integer> catchedPokemonsCount = new HashMap<>();
    private Map<Player, ArrayList<Entity>> livingEntities = new HashMap<>();

    public int getPokemons(Player player) {
        return catchedPokemonsCount.containsKey(player) ? catchedPokemonsCount.get(player) : 0;
    }

    public void setPokemons(Player player, int count) {
        this.catchedPokemonsCount.put(player, count);
    }

    public void addPokemons(Player player, int count) {
        setPokemons(player, getPokemons(player) + count);
    }

    public void removePokemons(Player player, int count) {
        if(hasPokemons(player, count)) {
            setPokemons(player, getPokemons(player) - count);
        }
    }

    public boolean hasPokemons(Player player, int amount) {
        return catchedPokemonsCount.containsKey(player) ? catchedPokemonsCount.get(player) >= amount : false;
    }

    public void addLivingEntity(Player player, Entity entity) {
        if(livingEntities.get(player) != null) {
            livingEntities.get(player).add(entity);
        } else {
            livingEntities.put(player, new ArrayList<>(Arrays.asList(entity)));
        }
    }

    public void removeLivingEntity(Player player, Entity entity) {
        if(livingEntities.get(player) != null) {
            livingEntities.get(player).remove(entity);
        }
    }

    public ArrayList<Entity> getLivingEntities(Player player) {
        if (livingEntities.get(player) != null) {
            return livingEntities.get(player);
        }
        return new ArrayList();
    }

}
