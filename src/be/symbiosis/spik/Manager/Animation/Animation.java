package be.symbiosis.spik.Manager.Animation;

import be.symbiosis.spik.Spik;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Animation {
    private String AnimationName;
    private final Location locPlayer;
    private Boolean isStarted;
    private Player player;
    public Animation(Location locPlayer, String AnimationName) {
        this.AnimationName = AnimationName;
        this.locPlayer = locPlayer;
        this.isStarted = false;
        this.player = null;
    }


    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setStarted(boolean started) {
        isStarted = started;
    }

    public Location getLocPlayer() {
        return locPlayer;
    }

    public String getAnimationName() {
        return AnimationName;
    }
}