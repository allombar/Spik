package be.symbiosis.spik.Manager.Animation;

import be.symbiosis.spik.Spik;
import be.symbiosis.spik.SpikCore;
import be.symbiosis.spik.Utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AnimationManager {
    private final HashMap<String, Animation> _animations;
    private File file;
    private static YamlConfiguration conf;

    public AnimationManager() {
        this._animations = new HashMap<>();
    }

    public void AddAnimation(Location playerLoc, String animationName) {
        _animations.put(animationName, new Animation(playerLoc, animationName));
    }

    public void RemoveAnimation(String animationName) {
        _animations.remove(animationName);
    }

    public Animation GetAnimation(String AnimationName) {
        if (!_animations.containsKey(AnimationName)) {
            return null;
        }
        Animation anim = _animations.get(AnimationName);
        return anim;
    }

    public Animation GetPlayerInAnimation(Player player) {
        for (Map.Entry<String, Animation> pair : _animations.entrySet()) {
            Animation animation = _animations.get(pair.getKey().toLowerCase());
            if(animation.getPlayer() == player) {
                return animation;
            }
        }
        return null;
    }

    public void AddAnimationYML(String animationName, String loc, World world) {
        String key = "animations." + animationName.toLowerCase();
        getConfig().set(key+".pos", loc);
        getConfig().set(key+".world", world.getName());
        save();
    }

    public void RemoveAnimationYML(String animationName) {
        String key = "animations." + animationName;
        getConfig().set(key+".pos", null);
        getConfig().set(key+".world", null);
        getConfig().set("nameAnim.", null);
        save();
    }

    private void save() {
        try {
            conf.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadConfig() {
        if(!SpikCore.GetInstance().getDataFolder().exists()) {
            SpikCore.GetInstance().getDataFolder().mkdir();
        }

        file = new File(SpikCore.GetInstance().getDataFolder() + File.separator + "animation.yml");

        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        conf = YamlConfiguration.loadConfiguration(file);
    }

    public void Init() {
        loadConfig();

        ConfigurationSection animationsSection = getConfig().getConfigurationSection("animations");
        if(animationsSection != null) {
            for(String string : animationsSection.getKeys(false)) {

                String loc =  animationsSection.getString(string.toLowerCase() + ".pos");
                String world = animationsSection.getString(string.toLowerCase() + ".world");
                AddAnimation(Utils.parseStringToLoc(loc, Bukkit.getWorld(world)), string.toLowerCase());
            }
        }
    }
    public static YamlConfiguration getConfig() {
        return conf;
    }
}
