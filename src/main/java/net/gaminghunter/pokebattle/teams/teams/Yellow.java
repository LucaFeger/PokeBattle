package net.gaminghunter.pokebattle.teams.teams;

import net.gaminghunter.pokebattle.teams.Team;
import net.gaminghunter.pokebattle.utils.ItemBuilder;
import org.bukkit.Material;

/**
 * Created by Luca on 15.06.2017.
 */
public class Yellow extends Team {

    public Yellow() {
        super("§eGelb", "yellow" ,"§e", ItemBuilder.material(Material.WOOL, 1, (byte) 4)
                .setLore("").build(), 1);
        setItem(ItemBuilder.material(Material.WOOL, 1, (byte) 4).setDisplayName(getName()).build());
    }

}
