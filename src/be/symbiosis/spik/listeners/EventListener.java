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
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class EventListener implements Listener {

    @EventHandler
    public void OnMove(PlayerMoveEvent event) {
     Player player = event.getPlayer();

    if(SpikCore.GetAnimationManager().GetPlayerInAnimation(player) != null){
        event.setCancelled(true);
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
                player.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, player.getLocation(), 50);
                player.setHealth(0.0);
            }
        }
    }
}
