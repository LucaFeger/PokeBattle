package net.gaminghunter.pokebattle.teams.teams;

import net.gaminghunter.pokebattle.teams.Team;
import net.gaminghunter.pokebattle.utils.ItemBuilder;
import org.bukkit.Material;

/**
 * Created by Luca on 15.06.2017.
 */
public class Red extends Team {

    public Red() {
        super("§cRot", "red", "§c", ItemBuilder.material(Material.WOOL, 1, (byte) 14)
                .setLore("").build(), 1);
        setItem(ItemBuilder.material(Material.WOOL, 1, (byte) 14).setDisplayName(getName()).build());
    }
}
