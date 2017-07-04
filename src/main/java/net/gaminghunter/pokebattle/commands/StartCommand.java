package net.gaminghunter.pokebattle.commands;

import net.gaminghunter.pokebattle.Pokebattle;
import net.gaminghunter.pokebattle.utils.MessageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Luca on 12.06.2017.
 */
public class StartCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        if(player.hasPermission(Pokebattle.getInstance().getStartPermission())) {
            if(Pokebattle.getInstance().getLobbyCountdown().isStarted()) {
                if (Pokebattle.getInstance().getLobbyCountdown().getTime() > 10) {
                    Pokebattle.getInstance().getLobbyCountdown().setTime(10);
                } else {
                    MessageUtils.sendErrorActionbar(player, "Du kannst nicht mehr starten");
                }
            } else {
                MessageUtils.sendErrorActionbar(player, "Es läuft kein Countdown");
            }
        } else {
            MessageUtils.sendErrorActionbar(player, "Keine Rechte für diese Aktion");
        }
        return false;
    }

}
