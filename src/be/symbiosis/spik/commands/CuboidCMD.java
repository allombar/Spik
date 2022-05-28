package be.symbiosis.spik.commands;

import be.symbiosis.spik.Manager.AnimationManager;
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
                        "§b/cuboid set [PlayerLoc]\n" +
                        "§b/cuboid del\n";
                if(strings.length == 0) {
                    player.sendMessage(message);
                    return false;
                }

                if (strings[0].equalsIgnoreCase("set")) {
                    World world = player.getWorld();

                    GamePlayer gp = GamePlayer.gamePlayers.get(player.getName());

                    if(gp.getPos1() == null || gp.getPos2() == null) {
                       return true;
                    }

                    String key = "cuboids.";
                    String key2 = "animations."+"Cuboid";

                    Spik.getINSTANCE().getConfig().set(key2+".world", player.getWorld());
                    Spik.getINSTANCE().getConfig().set(key2+".pos", strings[1]);
                    Spik.getINSTANCE().save();
                    
                    Spik.getINSTANCE().getConfig().set(key + "pos1", Spik.getINSTANCE().unparseLocToString(gp.getPos1()));
                    Spik.getINSTANCE().getConfig().set(key + "pos2", Spik.getINSTANCE().unparseLocToString(gp.getPos2()));
                    Spik.getINSTANCE().save();

                    CuboidManager cuboidManager = new CuboidManager(gp.getPos1(), gp.getPos2());
                    Spik.cuboids.add(cuboidManager);

                    AnimationManager animationManager = new AnimationManager(Spik.getINSTANCE().parseStringToLoc(strings[1], world),"Cuboid");
                    Spik.animations.add(animationManager);

                    player.sendMessage("Le cuboid " + strings[1] + " est maintenant opérationnel");
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
