package be.symbiosis.spik.commands;

import be.symbiosis.spik.Manager.CuboidManager;
import be.symbiosis.spik.Manager.GamePlayer;
import be.symbiosis.spik.Spik;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CuboidCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (s.equalsIgnoreCase("cuboid")) {
                String message = "§3Les commandes sont:\n" +
                        "§b/cuboid set [PlayerLoc] [Name]\n" +
                        "§b/cuboid del [PlayerLoc]\n";
                if(strings.length == 0) {
                    player.sendMessage(message);
                    return false;
                }

                if (strings[0].equalsIgnoreCase("set")) {
                    if (strings.length < 3) {
                        player.sendMessage(message);
                        return false;
                    }

                    World world = player.getWorld();

                    GamePlayer gp = GamePlayer.gamePlayers.get(player.getName());

                    if(gp.getPos1() == null || gp.getPos2() == null) {
                       return true;
                    }

                    String key = "cuboids." + strings[2];

                    Spik.getINSTANCE().configCuboid.set("cuboids.", strings[2]);
                    Spik.getINSTANCE().configCuboid.set(key + ".pos1", Spik.getINSTANCE().unparseLocToString(gp.getPos1()));
                    Spik.getINSTANCE().configCuboid.set(key + ".pos2", Spik.getINSTANCE().unparseLocToString(gp.getPos2()));
                    Spik.getINSTANCE().configCuboid.set(key + ".posPlayer", Spik.getINSTANCE().unparseLocToString(player.getLocation()));
                    Spik.getINSTANCE().configCuboid.set(key + ".world", world.getName());
                    Spik.getINSTANCE().saveConfigCuboid();

                    CuboidManager cuboidManager = new CuboidManager(gp.getPos1(), gp.getPos2(), strings[2], Spik.getINSTANCE().parseStringToLoc(strings[1], player.getWorld()));
                    Spik.cuboids.add(cuboidManager);

                    player.sendMessage("Le cuboid " + strings[2] + " est maintenant opérationnel");
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
