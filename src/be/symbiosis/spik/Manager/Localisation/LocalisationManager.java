package be.symbiosis.spik.Manager.Localisation;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public class LocalisationManager {
    public static Map<Player, Localisation> specLocMap = new HashMap<>();

    public LocalisationManager() {
        this.specLocMap = new HashMap();
    }

    public static Localisation getLocalisationManager(Player player) {
        if (specLocMap.containsKey(player)) {
            return specLocMap.get(player);
        }
        return null;
    }

    public static void addPlayerManager(Player player) {
        if (!specLocMap.containsKey(player)) {
            specLocMap.put(player, new Localisation(player.getLocation()));
        }
    }

    public static void SetSpecInAnimation(Location locSpec, Player player) {
        for(Player p : Bukkit.getOnlinePlayers()) {
            if (!Objects.equals(p, player)) {
                addPlayerManager(p);
                p.setGameMode(GameMode.ADVENTURE);
                p.teleport(locSpec);
            }
        }
    }

    public static void resetSpecToLocation() {
        for (Map.Entry<Player, Localisation> pair : specLocMap.entrySet()) {
            Player p = pair.getKey().getPlayer();
            Localisation localisationmanager = getLocalisationManager(p);
            Location loc = localisationmanager.getLocation();
            p.teleport(loc);
            p.setGameMode(GameMode.SURVIVAL);
        }
    }
}