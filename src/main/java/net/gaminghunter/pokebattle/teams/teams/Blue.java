package net.gaminghunter.pokebattle.teams.teams;

import net.gaminghunter.pokebattle.teams.Team;
import net.gaminghunter.pokebattle.utils.ItemBuilder;
import org.bukkit.Material;

/**
 * Created by Luca on 15.06.2017.
 */
public class Blue extends Team {

    public Blue() {
        super("§3Blau", "blue" ,"§3", ItemBuilder.material(Material.WOOL, 1, (byte) 9)
                .setLore("").build(), 1);
        setItem(ItemBuilder.material(Material.WOOL, 1, (byte) 9).setDisplayName(getName()).build());
    }

}
