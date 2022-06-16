package be.symbiosis.spik.commands;

import be.symbiosis.spik.Manager.Animation.Animation;
import be.symbiosis.spik.Manager.Animation.AnimationManager;
import be.symbiosis.spik.Manager.CuboidManager;
import be.symbiosis.spik.SpikCore;
import be.symbiosis.spik.Utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.*;

public class TargetCommand implements CommandExecutor {
    List<String> animationList = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        //string = NameAnim && Integer Le type d'animation

        if (commandSender instanceof Player) {

            Player player = (Player) commandSender;

            if (strings.length == 0 || strings.length > 2) {
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

            if (Bukkit.getPlayer(strings[0]) == null) {
                player.sendMessage("Erreur: ce joueur n'est pas en ligne.");
                return false;
            }
            Player target = Bukkit.getPlayer(strings[0]);

            if (strings.length == 2) {
                VerifName(strings[1].toLowerCase(), target);
            } else {
                aleatoireAnimation(target);
            }
        }
        return false;
    }

    public void aleatoireAnimation(Player player) {
        ConfigurationSection animationsSection = AnimationManager.getConfig().getConfigurationSection("animations");
        if (animationsSection == null) {
            player.sendMessage("§4Erreur: §cIl n'y a pas d'animation enregistré");
            return;
        }
        for (String NameAnimation : animationsSection.getKeys(false)) {
            animationList.add(NameAnimation);
        }

        int max = animationList.size();

        Random rand = new Random();
        int random = rand.nextInt(max);

        String nameRandom = animationList.get(random).toLowerCase();

        if (SpikCore.GetAnimationManager().GetAnimation(nameRandom) == null) {
            player.sendMessage("§4Erreur: §cAttentez cette animation est déjà en cour");
            animationList.removeAll(animationList);
            return;
        }

        Animation animation = SpikCore.GetAnimationManager().GetAnimation(nameRandom);
        Location loc = animation.getLocPlayer();
        animation.setStarted(true);
        if (nameRandom.equalsIgnoreCase("Arrow")) {
            Utils.playAnimationArrow(player, animation);
        } else if (nameRandom.equalsIgnoreCase("Canon")) {
            Utils.playAnimationCanon(player, loc, animation);
        } else if (nameRandom.equalsIgnoreCase("HumanCanon")) {
            Utils.playHumanCanonAnimation(player, loc, animation);
        } else if (nameRandom.equalsIgnoreCase("Cauldron")) {
            Utils.playCauldronAnimation(player, animation);
        } else if (nameRandom.equalsIgnoreCase("Drowned")) {
        } else if (nameRandom.equalsIgnoreCase("Piranha")) {
        } else if (nameRandom.equalsIgnoreCase("Burned")) {
            Utils.playBurnedAnimation(player, animation);
        }

        animationList.removeAll(animationList);
    }

    public void VerifName(String nameRandom, Player player) {
        if (SpikCore.GetAnimationManager().GetAnimation(nameRandom) == null) {
            player.sendMessage("Erreur: Cette animation " + nameRandom + " est déjà utilisé ou n'éxiste pas");
            return;
        }
        Animation anim = SpikCore.GetAnimationManager().GetAnimation(nameRandom);
        Location loc = anim.getLocPlayer();
        anim.setStarted(true);
        if (nameRandom.equalsIgnoreCase("Arrow")) {
            Utils.playAnimationArrow(player, anim);
        } else if (nameRandom.equalsIgnoreCase("Canon")) {
            Utils.playAnimationCanon(player, loc, anim);
        } else if (nameRandom.equalsIgnoreCase("HumanCanon")) {
            Utils.playHumanCanonAnimation(player, loc, anim);
        } else if (nameRandom.equalsIgnoreCase("Cauldron")) {
            Utils.playCauldronAnimation(player, anim);
        } else if (nameRandom.equalsIgnoreCase("Drowned")) {
            if (CuboidManager.GetCuboidByIndex(0) == null) {
                player.sendMessage("§4Erreur: §caucun cuboid n'est disponible pour le moment.");
                return;
            }
            CuboidManager cuboidManager = CuboidManager.GetCuboidByIndex(0);
            Utils.playCuboidAnimation(player, cuboidManager, anim);
        } else if (nameRandom.equalsIgnoreCase("Piranha")) {
            if (CuboidManager.GetCuboidByIndex(0) == null) {
                player.sendMessage("§4Erreur: §caucun cuboid n'est disponible pour le moment.");
                return;
            }
            CuboidManager cuboidManager = CuboidManager.GetCuboidByIndex(0);
            Utils.playerCuboidPiranaAnimation(player, cuboidManager, anim);
        } else if (nameRandom.equalsIgnoreCase("Burned")) {
            Utils.playBurnedAnimation(player, anim);
        }
    }
}
