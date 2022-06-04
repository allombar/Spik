package be.symbiosis.spik;

import be.symbiosis.spik.Manager.Animation.AnimationManager;
import be.symbiosis.spik.Manager.CuboidManager;
public class SpikCore {
    private static AnimationManager _animationManager;
    private static Spik _instance;
    public SpikCore(Spik pluginInstance) {
        _instance = pluginInstance;

        _animationManager = new AnimationManager();
        _animationManager.Init();
        CuboidManager.Init();
    }

    public static Spik GetInstance() {
        return _instance;
    }

    public static AnimationManager GetAnimationManager() {
        return _animationManager;
    }
}
