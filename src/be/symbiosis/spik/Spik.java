package be.symbiosis.spik;

import be.symbiosis.spik.Manager.Localisation.LocalisationManager;
import be.symbiosis.spik.commands.AnimationCommand;
import be.symbiosis.spik.commands.CuboidCommand;
import be.symbiosis.spik.commands.TargetCommand;
import be.symbiosis.spik.listeners.CuboidListeners;
import be.symbiosis.spik.listeners.EventListener;

import org.bukkit.plugin.java.JavaPlugin;
public class Spik extends JavaPlugin {
    private static SpikCore _game;
    private static Spik INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;

        //commandes
        getCommand("target").setExecutor(new TargetCommand());
        getCommand("anim").setExecutor(new AnimationCommand());
        getCommand("cuboid").setExecutor(new CuboidCommand());

        //listeners
        getServer().getPluginManager().registerEvents(new EventListener(), this);
        getServer().getPluginManager().registerEvents(new CuboidListeners(), this);

        Initialize();
    }


    public static void Initialize() {
        _game = new SpikCore(INSTANCE);
    }

}
