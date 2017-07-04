package net.gaminghunter.pokebattle.config;

import lombok.Getter;
import net.gaminghunter.pokebattle.utils.LocationUtils;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * Created by Luca on 12.06.2017.
 */
public class LocationsConfig {

    @Getter
    private File file = new File("plugins/PokeBattle", "locations.yml");
    @Getter
    private FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);

    public void set(String path, Object value) {
        if(value instanceof Location) {
            configuration.set(path, LocationUtils.serializeFully((Location) value));
        } else {
            configuration.set(path, value);
        }
        save();
    }

    public Object get(String path) {
        return configuration.get(path);
    }

    public void save() {
        try {
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadDefaultConfig() {
        save();
    }

}
