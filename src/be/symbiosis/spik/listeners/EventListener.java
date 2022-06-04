package be.symbiosis.spik.listeners;

import be.symbiosis.spik.Spik;
import be.symbiosis.spik.SpikCore;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
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
}
