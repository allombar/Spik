package be.symbiosis.spik.commands;

import be.symbiosis.spik.SpikCore;
import be.symbiosis.spik.Utils.Utils;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AnimationCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (s.equalsIgnoreCase("anim")) {

                String message = "§3Les commandes sont:\n" +
                        "§b/anim set [AnimationName] [PlayerPos]\n" +
                        "§b/anim del [AnimationName]\n" +
                        "§b/cuboid set [PlayerLoc]\n" +
                        "§b/cuboid del [PlayerLoc]\n" +
                        "§7§m-----------------------------" +
                        "§9Nom des animation:\n" +
                        "§e1- §3Arrow\n" +
                        "§e2- §3Canon\n" +
                        "§e3- §3HumanCanon\n" +
                        "§e4- §3Drowned\n" +
                        "§e5- §3Piranha\n" +
                        "§e6- §3Burned\n" +
                        "§e7- §3FallingHumanDown\n" +
                        "§e8- §3Cauldron\n";
                if (strings.length == 0) {
                    player.sendMessage(message);
                    return false;
                }

                if (strings[0].equalsIgnoreCase("set")) {
                    if (strings[0] == null) {
                        player.sendMessage(message);
                        return false;
                    }

                    if (!VerifAnimationName(strings[1])) {
                        player.sendMessage(message);
                        return false;
                    }

                    if (strings.length != 3) {
                        player.sendMessage(message);
                        return false;
                    }

                    World world = player.getWorld();

                    SpikCore.GetAnimationManager().AddAnimationYML(strings[1], strings[2], world);
                    SpikCore.GetAnimationManager().AddAnimation(Utils.parseStringToLoc(strings[2], world), strings[1]);
                    player.sendMessage("L'animation " + strings[1] + " est maintenant opérationel");


                } else if (strings[0].equalsIgnoreCase("del")) {


                    if (!VerifAnimationName(strings[1])) {
                        player.sendMessage(message);
                        return false;
                    }
                    if (strings.length < 2) {
                        player.sendMessage(message);
                        return true;
                    }
                    if (SpikCore.GetAnimationManager().GetAnimation(strings[1]) == null) {
                        player.sendMessage("§4Erreur: §cCette animation n'existe pas");
                        return true;
                    }

                    SpikCore.GetAnimationManager().RemoveAnimationYML(strings[1]);
                    SpikCore.GetAnimationManager().RemoveAnimation(strings[1]);
                }
            }
        }

        return false;
    }

    public boolean VerifAnimationName(String string) {
        if (string.equalsIgnoreCase("Arrow")) {
            return true;
        } else if (string.equalsIgnoreCase("Canon")) {
            return true;
        } else if (string.equalsIgnoreCase("HumanCanon")) {
            return true;
        } else if (string.equalsIgnoreCase("FallingHumanDown")) {
            return true;
        } else if (string.equalsIgnoreCase("Cauldron")) {
            return true;
        } else if (string.equalsIgnoreCase("Drowned")) {
            return true;
        } else if (string.equalsIgnoreCase("Piranha")) {
            return true;
        } else if (string.equalsIgnoreCase("Burned")) {
            return true;
        }
        return false;
    }
}
