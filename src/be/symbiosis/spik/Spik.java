package be.symbiosis.spik;

import be.symbiosis.spik.commands.ChooseAreaCMD;
import be.symbiosis.spik.commands.CuboidCMD;
import be.symbiosis.spik.commands.TargetCMD;
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
        getCommand("target").setExecutor(new TargetCMD());
        getCommand("anim").setExecutor(new ChooseAreaCMD());
        getCommand("cuboid").setExecutor(new CuboidCMD());

        //listeners
        getServer().getPluginManager().registerEvents(new EventListener(), this);
        getServer().getPluginManager().registerEvents(new CuboidListeners(), this);

        Initialize();
    }


    public static void Initialize() {
        _game = new SpikCore(INSTANCE);
    }

}
