package be.symbiosis.spik.Utils;

import be.symbiosis.spik.Spik;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

public class Utils {
    public static void playAnimationArrow(Player player) {

        new BukkitRunnable() {
            int timer1 = 20;
            List<Entity> arrows = new ArrayList<>();
            @Override
            public void run() {
                if(timer1 == 20) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 1 * 20, 10));
                }else if(timer1 == 19) {
                    player.setAllowFlight(true);
                    player.setFlying(true);
                } else if(timer1 == 18) {
                    Entity arrow = createNPC(player.getLocation().add(5, 1, -5));
                    arrows.add(arrow);
                }else if(timer1 == 17) {
                    arrows.add(createNPC(player.getLocation().add(5, 1, 0)));
                }else if(timer1 == 16) {
                    arrows.add(createNPC(player.getLocation().add(5, 1, 5)));
                } else if (timer1 == 15) {
                    arrows.add(createNPC(player.getLocation().add(0, 1, 5)));
                }else if(timer1 == 14) {
                    arrows.add(createNPC(player.getLocation().add(-5, 1, 5)));
                } else if (timer1 == 13) {
                    arrows.add(createNPC(player.getLocation().add(-5, 1, 0)));
                }else if(timer1 == 12) {
                    arrows.add(createNPC(player.getLocation().add(-5, 1, -5)));
                } else if (timer1 == 11) {
                    arrows.add(createNPC(player.getLocation().add( 0, 1, -5)));
                }
                else if(timer1 == 9) {
                    for(int x = 0; x < arrows.size();) {
                        Entity arrow = arrows.get(x);
                        arrow.setVelocity(VectorArrowToTarget(arrow.getLocation(), player));
                        arrow.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, arrow.getLocation(), 10);
                        x++;
                    }

                } else if(timer1 == 8) {
                    for(int xx = 0; xx < arrows.size();) {
                        Entity arroww = arrows.get(xx);
                        arroww.remove();
                        player.getWorld().strikeLightningEffect(player.getLocation());
                        player.getWorld().spawnParticle(Particle.SOUL, player.getLocation().add(0, -1, 0), 30);
                        xx++;
                    }
                    player.setHealth(0.0D);
                    arrows.clear();
                    this.cancel();
                }
                timer1--;
            }
        }.runTaskTimer(Spik.INSTANCE, 0 , 20);
    }

    public static void playAnimationCanon(Player player) {
        Location loc = player.getLocation();
        new BukkitRunnable(){
            int timer = 10;
            @Override
            public void run() {
                //player.getWorld().spawnFallingBlock(player.getLocation(), Material.BONE_BLOCK);
                if(timer == 10) {
                    //cage
                    createBlockSpawn(loc.add(1, 0, 0), Material.ACACIA_FENCE);
                    createBlockSpawn(loc.add(-2, 0, 0), Material.ACACIA_FENCE);

                    createBlockSpawn(loc.add(1, 1, 0), Material.ACACIA_FENCE);
                    createBlockSpawn(loc.add(-2, 1, 0), Material.ACACIA_FENCE);

                    createBlockSpawn(loc.add(1, 2, 0), Material.ACACIA_FENCE);
                    createBlockSpawn(loc.add(-2, 2, 0), Material.ACACIA_FENCE);

                    createBlockSpawn(loc.add(1, 3, 0), Material.ACACIA_FENCE);
                    createBlockSpawn(loc.add(-2, 3, 0), Material.ACACIA_FENCE);

                    createBlockSpawn(loc.add(1, 4, 0), Material.ACACIA_FENCE);
                    createBlockSpawn(loc.add(-2, 4, 0), Material.ACACIA_FENCE);
                    loc.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, loc, 500);
                }else if(timer == 9) {
                    player.teleport(loc.add(1, 1.5, 0));
                }

                if(timer == 0) {
                    this.cancel();
                }
                timer--;
            }
        }.runTaskTimer(Spik.INSTANCE, 0, 20);
    }

    public static void playAnimationFallingDown(Player player) {
        new BukkitRunnable() {
            int timer = 0;
            World world = player.getWorld();
            @Override
            public void run () {
                if (timer <= 9) {
                    Vector vec = player.getVelocity();
                    player.teleport(new Location(player.getWorld(), player.getLocation().getX() + getRandomNumber(-5, 5), world.getHighestBlockYAt(0, 0) + getRandomNumber(10, 25), player.getLocation().getZ()  + getRandomNumber(-5, 5)));
                    player.setVelocity(vec);
                    player.getWorld().spawnParticle(Particle.FLAME, player.getLocation(), 500);
                }
                if (timer == 10) {
                    player.setVelocity(player.getLocation().multiply(2).toVector().setY(-2));
                    this.cancel();
                }
                timer++;
            }
        }.runTaskTimer(Spik.INSTANCE, 0, 10);
    }

    public static void playRandomAnimation(Player player) {
        Random rand = new Random();
        int number = rand.nextInt(3);

        switch (number) {
            case 0:
                playAnimationCanon(player);
                break;
            case 1:
                playAnimationArrow(player);
                break;
            case 2:
                playAnimationFallingDown(player);
                break;
            default:
                return;
        }
    }

    public static Vector VectorArrowToTarget(Location loc, Player player) {
        Vector a = loc.toVector();//arrow loc
        Vector b = player.getLocation().toVector();//target
        return b.subtract(a).normalize();//create vector
    }

    public static Entity createNPC(Location loc) {
        NPC entity = CitizensAPI.getNPCRegistry().createNPC(EntityType.ARROW, "");
        entity.teleport(loc, PlayerTeleportEvent.TeleportCause.COMMAND);
        entity.spawn(loc);
        entity.getEntity().setGravity(false);
        entity.getEntity().setGlowing(true);
        entity.getEntity().getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, loc, 5);
        return entity.getEntity();
    }


    public static void createBlockSpawn(Location loc, Material mat) {
        loc.getWorld().spawnFallingBlock(loc, mat, (byte) 0);
    }

    public static int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max + 1 - min) + min;
    }
}
