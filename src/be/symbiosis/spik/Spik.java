package be.symbiosis.spik;

import be.symbiosis.spik.Utils.Utils;
import be.symbiosis.spik.commands.TargetCMD;
import be.symbiosis.spik.listeners.EventListener;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Spik extends JavaPlugin {

    private File file;
    private YamlConfiguration config;
    public List<Player> playersInAnimation = new ArrayList<Player>();
    public static Spik INSTANCE;


    @Override
    public void onEnable() {

        INSTANCE = this;
        //commandes
        getCommand("target").setExecutor(new TargetCMD());

        //listeners
        getServer().getPluginManager().registerEvents(new EventListener(), this);

        //load config
        loadConfig();
    }

    public void loadConfig() {
        if(!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        file = new File(getDataFolder() + File.separator + "waterDeath.yml");

        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        config = YamlConfiguration.loadConfiguration(file);
    }

    public List<Player> getPlayersInAnimation() {
        return playersInAnimation;
    }

    public Spik getINSTANCE() {
        return INSTANCE;
    }
}
