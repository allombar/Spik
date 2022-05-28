package be.symbiosis.spik.Manager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CuboidManager {
        public Location minLoc, maxLoc;
        public Player player;

        public CuboidManager(Location firstPoint, Location secondPoint) {
            minLoc = new Location(firstPoint.getWorld(), min(firstPoint.getX(), secondPoint.getX()), min(firstPoint.getY(), secondPoint.getY()), min(firstPoint.getZ(), secondPoint.getZ()));
            maxLoc = new Location(firstPoint.getWorld(), max(firstPoint.getX(), secondPoint.getX()), max(firstPoint.getY(), secondPoint.getY()), max(firstPoint.getZ(), secondPoint.getZ()));
            player = null;
        }
        public double min(double a, double b) {
            return a < b ? a : b;
        }

        public double max(double a, double b) {
            return a > b ? a : b;
        }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
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
    public void setBlockCompleteCuboid(Material mat) {
            List<Location> blocks = new ArrayList<>();
            int nb = 0;
        for (int x = minLoc.getBlockX(); x <= maxLoc.getBlockX(); x++) {
            for (int z = minLoc.getBlockZ(); z <= maxLoc.getBlockZ(); z++) {
                for (int y = minLoc.getBlockY(); y <= maxLoc.getBlockY(); y++)
                    blocks.add(new Location(minLoc.getWorld(), x, y, z));
                    blocks.get(nb).getBlock().setType(mat);
                    nb++;
            }
        }
    }
        public void SetBlockOnCuboid(Material mat, int AxeY) {
            for(int x = minLoc.getBlockX(); x <= maxLoc.getBlockX(); x++) {
                for(int z = minLoc.getBlockZ(); z <= maxLoc.getBlockZ(); z++) {
                    Location locBlocks = new Location(minLoc.getWorld(), x, AxeY, z);
                    locBlocks.getBlock().setType(mat);
                }
            }
        }
    }