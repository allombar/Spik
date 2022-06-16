package be.symbiosis.spik.Manager;

import be.symbiosis.spik.Manager.Animation.Animation;
import be.symbiosis.spik.SpikCore;
import be.symbiosis.spik.Utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CuboidManager {
    public static List<CuboidManager> _cuboids = new ArrayList<>();
    public static File file;
    public static YamlConfiguration conf;

    public Location minLoc, maxLoc;

    public CuboidManager(Location firstPoint, Location secondPoint) {
        minLoc = new Location(firstPoint.getWorld(), min(firstPoint.getX(), secondPoint.getX()), min(firstPoint.getY(), secondPoint.getY()), min(firstPoint.getZ(), secondPoint.getZ()));
        maxLoc = new Location(firstPoint.getWorld(), max(firstPoint.getX(), secondPoint.getX()), max(firstPoint.getY(), secondPoint.getY()), max(firstPoint.getZ(), secondPoint.getZ()));
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

    public static CuboidManager GetCuboidByIndex(int index) {
        if (_cuboids.get(index) == null) {
            return null;
        }
        CuboidManager cuboid = _cuboids.get(index);
        return cuboid;
    }

    public List<Location> SetBlockOnCuboid(int AxeY) {
        List<Location> blocksLocation = new ArrayList<>();

        for (int x = minLoc.getBlockX(); x <= maxLoc.getBlockX(); x++) {
            for (int z = minLoc.getBlockZ(); z <= maxLoc.getBlockZ(); z++) {
                blocksLocation.add(new Location(minLoc.getWorld(), x, minLoc.getY() + AxeY, z));
            }
        }
        return blocksLocation;
    }

    public void DelBlockOnCuboid(int AxeY) {
        for (int x = minLoc.getBlockX(); maxLoc.getBlockX() >= x; x--) {
            for (int z = minLoc.getBlockZ(); maxLoc.getBlockZ() >= z; z--) {
                Location loc = new Location(minLoc.getWorld(), x, minLoc.getY() + AxeY, z);
                loc.getBlock().setType(Material.AIR);
            }
        }
    }

    public static void loadConfig() {
        file = new File(SpikCore.GetInstance().getDataFolder() + File.separator + "cuboid.yml");

        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        conf = YamlConfiguration.loadConfiguration(file);
    }
    private static void save() {
        try {
            conf.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void AddCuboid(Location one, Location two) {
        CuboidManager cuboidManager = new CuboidManager(one, two);
        _cuboids.add(cuboidManager);
    }

    public static void AddCuboidYML(GamePlayerManager gp,  World world) {
        String key = "cuboids.";
        getConfig().set(key + "pos1", Utils.unparseLocToString(gp.getPos1()));
        getConfig().set(key + "pos2", Utils.unparseLocToString(gp.getPos2()));
        getConfig().set(key + "world", world.getName());
        save();
    }
    public static YamlConfiguration getConfig() {
        return conf;
    }

    public static void Init() {
        loadConfig();
        String loc1 =  getConfig().getString("cuboids.pos1");
        String loc2 =  getConfig().getString("cuboids.pos2");
        String worldName = getConfig().getString("cuboids.world");
        World world = Bukkit.getWorld(worldName);

        AddCuboid(Utils.parseStringToLoc(loc1, world), Utils.parseStringToLoc(loc2, world));
    }
}