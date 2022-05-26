package be.symbiosis.spik.commands;

import be.symbiosis.spik.Spik;
import be.symbiosis.spik.Utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import sun.security.provider.ConfigFile;

public class TargetCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(commandSender instanceof Player) {

            Player player = (Player) commandSender;

            if(strings.length == 0 || strings.length > 2) {
                player.sendMessage("Erreur: La commande est: ");
                player.sendMessage("/target [Player]");
                return false;
            }

            if(Bukkit.getPlayer(strings[0]) == null) {
                player.sendMessage("Erreur: ce joueur n'est pas en ligne.");
               return false;
            }

            Player target = Bukkit.getPlayer(strings[0]);
            Utils.playAnimationCanon(target);
            Spik.INSTANCE.getPlayersInAnimation().add(player);
        }
        return false;
    }
}
