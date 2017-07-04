package net.gaminghunter.pokebattle.config;

import lombok.Getter;
import net.gaminghunter.pokebattle.utils.LocationUtils;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * Created by Luca on 11.06.2017.
 */
public class PokeConfig {

    @Getter
    private File configFile = new File("plugins/PokeBattle", "config.yml");
    @Getter
    private FileConfiguration configConfiguration = YamlConfiguration.loadConfiguration(configFile);

    public void set(String path, Object value) {
        if(value instanceof Location) {
            configConfiguration.set(path, LocationUtils.serializeFully((Location) value));
        } else {
            configConfiguration.set(path, value);
        }
        save();
    }

    public Object get(String path) {
        return configConfiguration.get(path);
    }

    public void save() {
        try {
            configConfiguration.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadDefaultConfig() {
        configConfiguration.addDefault("prefix", "§aPokeBattle §7| ");
        configConfiguration.addDefault("prefixBeforeEveryMessage", true);
        configConfiguration.addDefault("minPlayers", 2);
        configConfiguration.addDefault("setupPermission", "pokebattle.setup");
        configConfiguration.addDefault("startPermission", "pokebattle.start");
        configConfiguration.options().copyDefaults(true);
        save();
    }

}
