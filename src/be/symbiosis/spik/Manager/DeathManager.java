package be.symbiosis.spik.Manager;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class DeathManager {
        public Location minLoc, maxLoc;
        public String name;

        public DeathManager(Location firstPoint, Location secondPoint, String name, String chef) {
            minLoc = new Location(firstPoint.getWorld(), min(firstPoint.getX(), secondPoint.getX()), min(firstPoint.getY(), secondPoint.getY()), min(firstPoint.getZ(), secondPoint.getZ()));
            maxLoc = new Location(firstPoint.getWorld(), max(firstPoint.getX(), secondPoint.getX()), max(firstPoint.getY(), secondPoint.getY()), max(firstPoint.getZ(), secondPoint.getZ()));
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public double min(double a, double b) {
            return a < b ? a : b;
        }

        public double max(double a, double b) {
            return a > b ? a : b;
        }

        public boolean isInArea(Location loc) {
            return (minLoc.getX() <= loc.getX() && minLoc.getY() <= loc.getY() && minLoc.getZ() <= loc.getZ() && maxLoc.getX() >= loc.getX() && maxLoc.getY() >= loc.getY() && maxLoc.getZ() >= loc.getZ());
        }

        public Location getMiddle() {
            double a, b;
            a = (maxLoc.getX() - minLoc.getX()) / 2D + minLoc.getX();
            b = (maxLoc.getZ() - minLoc.getZ()) / 2D + minLoc.getZ();

            return new Location(Bukkit.getWorld("world"), a, minLoc.getY(), b);
        }

        public List<Location> getArea() {
            List<Location> blocksLocation = new ArrayList<>();

            for (int x = minLoc.getBlockX(); x <= maxLoc.getBlockX(); x++) {
                for (int z = minLoc.getBlockZ(); z <= maxLoc.getBlockZ(); z++) {
                    for (int y = minLoc.getBlockY(); y <= maxLoc.getBlockY(); y++)
                        blocksLocation.add(new Location(minLoc.getWorld(), x, y, z));
                }
            }
            return blocksLocation;
        }
    }