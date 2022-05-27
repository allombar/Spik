package be.symbiosis.spik.commands;

import be.symbiosis.spik.Spik;
import be.symbiosis.spik.Utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import sun.security.provider.ConfigFile;

import java.util.Locale;

public class TargetCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(commandSender instanceof Player) {

            Player player = (Player) commandSender;

            if(strings.length == 0 || strings.length > 3) {
                player.sendMessage("Erreur: La commande est: ");
                player.sendMessage("/target [Player]");
                return false;
            }

            if(Bukkit.getPlayer(strings[0]) == null) {
                player.sendMessage("Erreur: ce joueur n'est pas en ligne.");
               return false;
            }
            Player target = Bukkit.getPlayer(strings[0]);

            if(strings[1] != null) {
                switch (strings[1].toLowerCase()) {
                    case "canon":
                        Utils.playAnimationCanon(target);
                        break;
                    case "canonhuman":
                        Utils.playHumanCanonAnimation(target);
                        break;
                    case "arrow":
                        Utils.playAnimationArrow(target);
                        break;
                    case "falling":
                        Utils.playAnimationFallingDown(target);
                        break;
                    case "random":
                        Utils.playRandomAnimation(target);
                    default:
                        break;
                }
            }
        }
        return false;
    }
}
