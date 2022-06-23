package be.symbiosis.spik.Manager.Localisation;

import org.bukkit.GameMode;
import org.bukkit.Location;

public class Localisation {
    private Location location;

    public Localisation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

}
