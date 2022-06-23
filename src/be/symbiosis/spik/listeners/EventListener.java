package be.symbiosis.spik.listeners;

import be.symbiosis.spik.Manager.Animation.Animation;
import be.symbiosis.spik.Manager.Animation.AnimationManager;
import be.symbiosis.spik.Spik;
import be.symbiosis.spik.SpikCore;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import sun.security.provider.ConfigFile;

import java.util.List;

public class EventListener implements Listener {

    @EventHandler
    public void OnMove(PlayerMoveEvent event) {
     Player player = event.getPlayer();

    if(SpikCore.GetAnimationManager().GetPlayerInAnimation(player) != null){
        event.setCancelled(true);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if(SpikCore.GetAnimationManager().GetPlayerInAnimation(player) != null) {
            SpikCore.GetAnimationManager().destroy();
        }
    }

    @EventHandler
    public void OnJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if(!player.hasPermission("spik.animation.fly") || !player.isOp()) {
            player.setAllowFlight(false);
        }
    }

    @EventHandler
    public void OnBreakBlock(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if(SpikCore.GetAnimationManager().GetPlayerInAnimation(player) != null){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onHitPlayer(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (SpikCore.GetAnimationManager().GetPlayerInAnimation(player) != null
                    && SpikCore.GetAnimationManager()
                    .GetPlayerInAnimation(player)
                    .getAnimationName()
                    .equalsIgnoreCase("canon")) {
                Animation animation = SpikCore.GetAnimationManager().GetPlayerInAnimation(player);
                animation.setStarted(false);
                animation.setPlayer(null);
                player.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, player.getLocation(), 50);
                player.setHealth(0.0);
            }
        }
    }
}
