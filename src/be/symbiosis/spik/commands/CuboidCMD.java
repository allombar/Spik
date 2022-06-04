package be.symbiosis.spik.commands;

import be.symbiosis.spik.Manager.CuboidManager;
import be.symbiosis.spik.Manager.GamePlayerManager;
import be.symbiosis.spik.Spik;
import be.symbiosis.spik.SpikCore;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class CuboidCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (s.equalsIgnoreCase("cuboid")) {
                String message = "§3Les commandes sont:\n" +
                        "§b/cuboid set\n" +
                        "§b/cuboid del\n";
                if(strings.length == 0) {
                    player.sendMessage(message);
                    return false;
                }

                if (strings[0].equalsIgnoreCase("set")) {
                    World world = player.getWorld();

                    GamePlayerManager gp = GamePlayerManager.gamePlayers.get(player.getName());

                    if(gp.getPos1() == null || gp.getPos2() == null) {
                       return true;
                    }

                    CuboidManager.AddCuboidYML(gp, world);
                    CuboidManager.AddCuboid(gp.getPos1(), gp.getPos2());

                    player.sendMessage("Le cuboid est maintenant opérationnel");
                }else if (strings[0].equalsIgnoreCase("del")) {
                    if (strings.length < 2) {
                        player.sendMessage(message);
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
