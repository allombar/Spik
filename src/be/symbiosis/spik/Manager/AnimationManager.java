package be.symbiosis.spik.Manager;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class AnimationManager {

    Location locPlayer;
    String AnimationName;
    boolean isStarded;
    Player player;

    public AnimationManager(Location locPlayer, String AnimationName) {
        this.AnimationName = AnimationName;
        this.locPlayer = locPlayer;
        this.isStarded = false;
        this.player = null;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isStarded() {
        return isStarded;
    }

    public void setAnimationName(String animationName) {
        AnimationName = animationName;
    }

    public void setStarded(boolean starded) {
        isStarded = starded;
    }

    public Location getLocPlayer() {
        return locPlayer;
    }

    public String getAnimationName() {
        return AnimationName;
    }
}
