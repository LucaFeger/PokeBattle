package net.gaminghunter.pokebattle.teams.teams;

import net.gaminghunter.pokebattle.teams.Team;
import net.gaminghunter.pokebattle.utils.ItemBuilder;
import org.bukkit.Material;

/**
 * Created by Luca on 15.06.2017.
 */
public class Green extends Team {

    public Green() {
        super("§aGrün", "green" ,"§a", ItemBuilder.material(Material.WOOL, 1, (byte) 5)
                .setLore("").build(), 1);
        setItem(ItemBuilder.material(Material.WOOL, 1, (byte) 5).setDisplayName(getName()).build());
    }

}
