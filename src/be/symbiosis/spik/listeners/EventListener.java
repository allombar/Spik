package be.symbiosis.spik.listeners;

import be.symbiosis.spik.Manager.AnimationManager;
import be.symbiosis.spik.Spik;
import be.symbiosis.spik.Utils.Utils;
import net.minecraft.server.v1_16_R3.BlockCauldron;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class EventListener implements Listener {

    @EventHandler
    public void OnMove(PlayerMoveEvent event) {
     Player player = event.getPlayer();

    if(Spik.getINSTANCE().getPlayerInAnimation(player) != null){
        event.setCancelled(true);
        }
    }

    @EventHandler
    public void OnBreakBlock(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if(Spik.getINSTANCE().getPlayerInAnimation(player) != null){
            event.setCancelled(true);
        }
    }
}
