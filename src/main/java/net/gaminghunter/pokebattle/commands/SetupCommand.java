package net.gaminghunter.pokebattle.commands;

import net.gaminghunter.pokebattle.Pokebattle;
import net.gaminghunter.pokebattle.config.LocationsConfig;
import net.gaminghunter.pokebattle.utils.MessageUtils;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.map.MinecraftFont;

/**
 * Created by Luca on 12.06.2017.
 */
public class SetupCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        if(player.hasPermission(Pokebattle.getInstance().getSetupPermission())) {
            if(args.length == 1 && args[0].equalsIgnoreCase("spawn")) {
                Pokebattle.getInstance().getLocationsConfig().set("spawn", player.getLocation());
                Pokebattle.getInstance().setSpawnLocation(player.getLocation());
                player.sendMessage(Pokebattle.getInstance().getPrefix() + "§aDu hast den §3Spawn §aerfolgreich gesetzt");
            } else if(args.length == 1 && args[0].equalsIgnoreCase("shop")) {
                Pokebattle.getInstance().getLocationsConfig().set("shop", player.getLocation());
                Pokebattle.getInstance().setShopLocation(player.getLocation());
                player.sendMessage(Pokebattle.getInstance().getPrefix() + "§aDu hast den §3Shop §aerfolgreich gesetzt");
            } else if(args.length == 1 && args[0].equalsIgnoreCase("shopVillager")) {
                Pokebattle.getInstance().getLocationsConfig().set("shopVillager", player.getLocation());
                Pokebattle.getInstance().setShopVillagerLocation(player.getLocation());
                player.sendMessage(Pokebattle.getInstance().getPrefix() + "§aDu hast den §3Shop Villager §aerfolgreich gesetzt");
            } else if(args.length == 2 && args[0].equalsIgnoreCase("spawn")) {
                try {
                    int id = Integer.valueOf(args[1]);
                    Pokebattle.getInstance().getLocationsConfig().set("playerspawn." + id, player.getLocation());
                    player.sendMessage(Pokebattle.getInstance().getPrefix() + "§aDu hast den §3Spielerspawn " + id + " §aerfolgreich gesetzt");
                } catch (NumberFormatException ex) {
                    MessageUtils.sendErrorActionbar(player, "Bitte gib eine echte Zahl ein");
                }
            } else {
                player.sendMessage("§7Spawn: " + (Pokebattle.getInstance().getSpawnLocation() != null ? "§a✔" : "§c✘"));
                player.sendMessage("§7Shop: " + (Pokebattle.getInstance().getShopLocation() != null ? "§a✔" : "§c✘"));
                player.sendMessage("§7ShopVillager: " + (Pokebattle.getInstance().getShopVillagerLocation() != null ? "§a✔" : "§c✘"));
            }

        } else {
            MessageUtils.sendErrorActionbar(player, "Keine Rechte für diese Aktion");
        }
        return false;
    }

}
