package be.symbiosis.spik.listeners;

import be.symbiosis.spik.Manager.CuboidManager;
import be.symbiosis.spik.Manager.GamePlayer;
import be.symbiosis.spik.Spik;
import org.bukkit.event.Listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class CuboidListeners implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();

        if(e.getAction() == Action.LEFT_CLICK_BLOCK) {
            if(player.getItemInHand().getType() == Material.PAPER) {
                GamePlayer gp;
                e.setCancelled(true);

                if(GamePlayer.gamePlayers.get(player.getName()) == null) {
                    gp = new GamePlayer(player.getName());
                }else {
                    gp = GamePlayer.gamePlayers.get(player.getName());
                }

                if(gp.getPos1() == null) {
                    gp.setPos1(e.getClickedBlock().getLocation());
                    player.sendMessage("§aPosition 1 de votre cuboid définie");

                    Bukkit.getScheduler().runTaskLater(Spik.getINSTANCE(), new Runnable() {
                        @Override
                        public void run() {
                            gp.setPos1(null);
                            gp.setPos2(null);
                        }
                    }, 20*60*5);
                    return;
                }

                if(gp.getPos1() != null && gp.getPos2() == null) {

                    gp.setPos2(e.getClickedBlock().getLocation());

                    if(gp.getPos1().distance(gp.getPos2()) < 15 || !gp.getPos1().getWorld().getName().equals(gp.getPos2().getWorld().getName())) {
                        player.sendMessage("§4Erreur: §cVous en pouvez pas faire cela !");
                        gp.setPos1(null);
                        gp.setPos2(null);
                        return;
                    }
                }
                Bukkit.getScheduler().runTaskLater(Spik.getINSTANCE(), new Runnable() {
                    @Override
                    public void run() {
                        gp.setPos1(null);
                        gp.setPos2(null);
                    }
                }, 20*60*1);
                player.sendMessage("§aPosition 2 de votre cuboid définie");
                player.sendMessage("§cFaite maintenant §b/cuboid set [PlayerLoc] [name]");
            }
        }
    }
}
