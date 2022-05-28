package be.symbiosis.spik.commands;

import be.symbiosis.spik.Manager.AnimationManager;
import be.symbiosis.spik.Manager.CuboidManager;
import be.symbiosis.spik.Spik;
import be.symbiosis.spik.Utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import sun.security.provider.ConfigFile;

import javax.rmi.CORBA.Util;
import java.util.*;

public class TargetCMD implements CommandExecutor {
    List<String> animationList = new ArrayList<>();
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        //string = NameAnim && Integer Le type d'animation

        if(commandSender instanceof Player) {

            Player player = (Player) commandSender;

            if(strings.length == 0 || strings.length > 2) {
                String message = "§3Voici les commandes pour lancer une animation:\n" +
                        "§b/target §c[PlayerName] §7§m| §2Animation aléatoire\n" +
                        "§b/target §c[PlayerName] §3[AnimationName] §7§m| §2Animation ciblé\n" +
                        "§m§l§7----------------------------------------\n" +
                        "§e1- §3Arrow\n" +
                        "§e2- §3Canon\n" +
                        "§e3- §3HumanCanon\n" +
                        "§e4- §3Drowned\n" +
                        "§e5- §3Piranha\n" +
                        "§e6- §3Burned\n" +
                        "§e7- §3FallingDown\n" +
                        "§e8- §3Cauldron\n";
                player.sendMessage(message);
                return false;
            }

            if(Bukkit.getPlayer(strings[0]) == null) {
                player.sendMessage("Erreur: ce joueur n'est pas en ligne.");
               return false;
            }
            Player target = Bukkit.getPlayer(strings[0]);

            if(strings.length == 2) {
                VerifName(strings[1], target);
            }else {
                aleatoireAnimation(target);
            }
        }
        return false;
    }

    public void aleatoireAnimation(Player player) {
        ConfigurationSection animationsSection = Spik.getINSTANCE().getConfig().getConfigurationSection("animations");
        if(animationsSection == null) {
            player.sendMessage("§4Erreur: §cIl n'y a pas d'animation enregistré");
            return;
        }
        for(String NameAnimation : animationsSection.getKeys(false)) {
            animationList.add(NameAnimation);
        }

        int max = animationList.size();

        Random rand = new Random();
        int random = rand.nextInt(max);

        String nameRandom = animationList.get(random);

        if(Spik.getINSTANCE().getAnimationToString(nameRandom) == null) {
            player.sendMessage("§4Erreur: §cAttentez cette animation est déjà en cour");
            animationList.removeAll(animationList);
            return;
        }

        AnimationManager animationManager = Spik.getINSTANCE().getAnimationToString(nameRandom);
        Location loc = animationManager.getLocPlayer();

        if(nameRandom.equalsIgnoreCase("Arrow")) {
               Utils.playAnimationArrow(player, animationManager);
            }else if(nameRandom.equalsIgnoreCase("Canon")) {
                Utils.playAnimationCanon(player, loc, animationManager);
            }else if(nameRandom.equalsIgnoreCase("HumanCanon")) {
                Utils.playHumanCanonAnimation(player, loc, animationManager);
            }else if(nameRandom.equalsIgnoreCase("FallingHumanDown")) {
                Utils.playAnimationFallingDown(player, loc, animationManager);
            }else if(nameRandom.equalsIgnoreCase("Cauldron")) {
                Utils.playCauldronAnimation(player, animationManager);
            }else if(nameRandom.equalsIgnoreCase("Drowned")) {
            if(Spik.getINSTANCE().getCuboidManagerNoPlayer() == null) {
                player.sendMessage("§4Erreur: §cIl n'y à pas de cuboid disponible");
                return;
            }
            CuboidManager cuboidManager = Spik.getINSTANCE().getCuboidManagerNoPlayer();
                Utils.playCuboidAnimation(player, cuboidManager, animationManager);
            }else if(nameRandom.equalsIgnoreCase("Piranha")) {
            if(Spik.getINSTANCE().getCuboidManagerNoPlayer() == null) {
                player.sendMessage("§4Erreur: §cIl n'y à pas de cuboid disponible");
                return;
            }
            CuboidManager cuboidManager = Spik.getINSTANCE().getCuboidManagerNoPlayer();
                Utils.playerCuboidPiranaAnimation(player, cuboidManager, animationManager);
            }else if(nameRandom.equalsIgnoreCase("Burned")) {
                Utils.playBurnedAnimation(player, animationManager);
            }

            animationManager.setStarded(true);
            animationList.removeAll(animationList);
    }

    public void VerifName(String nameRandom, Player player) {
        if(Spik.getINSTANCE().getAnimationToString(nameRandom) == null) {
            player.sendMessage("Erreur: Cette animation "+nameRandom+" est déjà utilisé ou n'éxiste pas");
            return;
        }
        AnimationManager animationManager = Spik.getINSTANCE().getAnimationToString(nameRandom);
        Location loc = animationManager.getLocPlayer();

        if(nameRandom.equalsIgnoreCase("Arrow")) {
            Utils.playAnimationArrow(player, animationManager);
        }else if(nameRandom.equalsIgnoreCase("Canon")) {
            Utils.playAnimationCanon(player, loc, animationManager);
        }else if(nameRandom.equalsIgnoreCase("HumanCanon")) {
            Utils.playHumanCanonAnimation(player, loc, animationManager);
        }else if(nameRandom.equalsIgnoreCase("FallingHumanDown")) {
            Utils.playAnimationFallingDown(player, loc, animationManager);
        }else if(nameRandom.equalsIgnoreCase("Cauldron")) {
            Utils.playCauldronAnimation(player, animationManager);
        }else if(nameRandom.equalsIgnoreCase("Cauldron")) {
            Utils.playCauldronAnimation(player, animationManager);
        }else if(nameRandom.equalsIgnoreCase("Drowned")) {
            if(Spik.getINSTANCE().getCuboidManagerNoPlayer() == null) {
                player.sendMessage("§4Erreur: §cIl n'y à pas de cuboid disponible");
                return;
            }
            CuboidManager cuboidManager = Spik.getINSTANCE().getCuboidManagerNoPlayer();
            Utils.playCuboidAnimation(player, cuboidManager, animationManager);
        }else if(nameRandom.equalsIgnoreCase("Piranha")) {
            if(Spik.getINSTANCE().getCuboidManagerNoPlayer() == null) {
            player.sendMessage("§4Erreur: §cIl n'y à pas de cuboid disponible");
            return;
        }
            CuboidManager cuboidManager = Spik.getINSTANCE().getCuboidManagerNoPlayer();

            Utils.playerCuboidPiranaAnimation(player, cuboidManager, animationManager);
        }else if(nameRandom.equalsIgnoreCase("Burned")) {
            Utils.playBurnedAnimation(player, animationManager);
        }


    }
}
