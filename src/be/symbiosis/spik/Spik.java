package be.symbiosis.spik;

import be.symbiosis.spik.Manager.AnimationManager;
import be.symbiosis.spik.Manager.CuboidManager;
import be.symbiosis.spik.commands.ChooseAreaCMD;
import be.symbiosis.spik.commands.CuboidCMD;
import be.symbiosis.spik.commands.TargetCMD;
import be.symbiosis.spik.listeners.CuboidListeners;
import be.symbiosis.spik.listeners.EventListener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Spik extends JavaPlugin {
    public static List<AnimationManager> animations = new ArrayList<>();
    public static List<CuboidManager> cuboids = new ArrayList<>();
    public File file;
    public YamlConfiguration conf;
    public static Spik INSTANCE;

    public AnimationManager animationManager;
    public CuboidManager cuboidManager;

    @Override
    public void onEnable() {
        INSTANCE = this;

        //commandes
        getCommand("target").setExecutor(new TargetCMD());
        getCommand("anim").setExecutor(new ChooseAreaCMD());
        getCommand("cuboid").setExecutor(new CuboidCMD());

        //listeners
        getServer().getPluginManager().registerEvents(new EventListener(), this);
        getServer().getPluginManager().registerEvents(new CuboidListeners(), this);

        //load config
        loadConfig();

        //charges AnimationManager
        ConfigurationSection animationsSection = getConfig().getConfigurationSection("animations");
        if(animationsSection != null) {
            for(String string : animationsSection.getKeys(false)) {

                String loc =  animationsSection.getString(string + ".pos");
                String world = animationsSection.getString(string + ".world");
                AnimationManager animationManager = new AnimationManager(parseStringToLoc(loc, Bukkit.getWorld(world)), string);
                animations.add(animationManager);
            }
        }

        //charges cuboidManager
        ConfigurationSection cuboidSection = getConfig().getConfigurationSection("cuboids");
        if(cuboidSection != null) {
            for(String string : cuboidSection.getKeys(false)) {

                String loc1 =  cuboidSection.getString(string + ".pos1");
                String loc2 =  cuboidSection.getString(string + ".pos2");
                String world = cuboidSection.getString(string + ".world");

                CuboidManager cuboidManager = new CuboidManager(parseStringToLoc(loc1, Bukkit.getWorld(world)), parseStringToLoc(loc2, Bukkit.getWorld(world)));

                cuboids.add(cuboidManager);
            }
        }
    }

    public void loadConfig() {
        if(!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        file = new File(getDataFolder() + File.separator + "animation.yml");

        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        conf = YamlConfiguration.loadConfiguration(file);
    }

    public static Spik getINSTANCE() {
        return INSTANCE;
    }

    public File getFile() {
        return file;
    }


    public YamlConfiguration getConfig() {
        return conf;
    }

    public Location parseStringToLoc(String string, World world) {
        String[] parsedLoc = string.split(",");
        double x = Double.parseDouble(parsedLoc[0]);
        double y = Double.parseDouble(parsedLoc[1]);
        double z = Double.parseDouble(parsedLoc[2]);

        return new Location(world, x, y ,z);
    }

    public String unparseLocToString(Location loc) {
        return loc.getX() + "," + loc.getY() + "," + loc.getZ();
    }

    public AnimationManager getPlayerInAnimation(Player player) {
        for(int x=0;x<animations.size();x++) {
            AnimationManager animationManager = animations.get(x);
            if(animationManager.getPlayer() == player) {
                return animationManager;
            }
        }
        return null;
    }

    public AnimationManager getAnimationToString(String name) {
        for(int x=0;x<animations.size();x++) {
            AnimationManager animationManager = animations.get(x);
                if(!animationManager.isStarded()) {
                    if(animationManager.getAnimationName().equalsIgnoreCase(name)) {
                        return animationManager;
                    }
                }
        }
        return null;
    }

    public void save() {
        try {
            conf.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public CuboidManager getCuboidManagerPlayer(Player player) {
        for(CuboidManager cuboidManager1 : cuboids) {
            if(cuboidManager1.getPlayer() == player) {
                return cuboidManager1;
            }
        }
        return null;
    }

    public CuboidManager getCuboidManagerNoPlayer() {
        for(CuboidManager cuboidManager1 : cuboids) {
            if(cuboidManager1.getPlayer() == null) {
                return cuboidManager1;
            }
        }
        return null;
    }
}
