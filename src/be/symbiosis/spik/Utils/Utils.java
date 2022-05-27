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
                if(timer == 9) {
                    createBlockAtPos(player, Material.DARK_OAK_FENCE, 1,0, 0);
                    createBlockAtPos(player, Material.DARK_OAK_FENCE, -1,0, 0);

                    createBlockAtPos(player, Material.DARK_OAK_FENCE, 1,1, 0);
                    createBlockAtPos(player, Material.DARK_OAK_FENCE, -1,1, 0);

                    createBlockAtPos(player, Material.DARK_OAK_FENCE, 1,2, 0);
                    createBlockAtPos(player, Material.DARK_OAK_FENCE, -1,2, 0);

                    createBlockAtPos(player, Material.DARK_OAK_FENCE, 1,3, 0);
                    createBlockAtPos(player, Material.DARK_OAK_FENCE, -1,3, 0);
                    createBlockAtPos(player, Material.DARK_OAK_FENCE, 0,3, 0);

                    createBlockAtPos(player, Material.DARK_OAK_SLAB, 0,4, 0);
                    createBlockAtPos(player, Material.DARK_OAK_SLAB, 1,4, 0);
                    createBlockAtPos(player, Material.DARK_OAK_SLAB, -1,4, 0);

                    player.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, player.getLocation(), 1000);
                }else if(timer == 8) {
                    player.teleport(new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY()+1.3, player.getLocation().getZ(), 180, 0));
                    player.setAllowFlight(true);
                    player.setFlying(true);
                }else if(timer == 7) {
                    Location cannon = new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY()+1, player.getLocation().getZ()-12.5);
                    cannon.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, cannon, 1000);

                    createBlockAtPos(player, Material.POLISHED_BLACKSTONE_BUTTON, 0, 1, 9);

                    createBlockAtPos(player, Material.COAL_BLOCK, 0, 0, -10);
                    createBlockAtPos(player, Material.COAL_BLOCK, 0, 0, -11);
                    createBlockAtPos(player, Material.COAL_BLOCK, 0, 0, -12);
                    createBlockAtPos(player, Material.COAL_BLOCK, 0, 0, -13);
                    createBlockAtPos(player, Material.COAL_BLOCK, 0, 0, -14);
                    createBlockAtPos(player, Material.COAL_BLOCK, 0, 0, -15);

                    createBlockAtPos(player, Material.COAL_BLOCK, 1, 0, -13);
                    createBlockAtPos(player, Material.COAL_BLOCK, -1, 0, -13);

                    createBlockAtPos(player, Material.COAL_BLOCK, 1, 0, -14);
                    createBlockAtPos(player, Material.COAL_BLOCK, -1, 0, -14);

                    createBlockAtPos(player, Material.SPRUCE_FENCE, 0, 1, -12);
                    createBlockAtPos(player, Material.CHISELED_NETHER_BRICKS, 0, 1, -13);
                    createBlockAtPos(player, Material.NETHER_BRICK_SLAB, 0, 1, -14);

                    createBlockAtPos(player, Material.NETHER_BRICK_SLAB, 1, 0, -12);
                    createBlockAtPos(player, Material.NETHER_BRICK_SLAB, -1, 0, -12);
                    createBlockAtPos(player, Material.NETHER_BRICK_SLAB, 1, 0, -15);
                    createBlockAtPos(player, Material.NETHER_BRICK_SLAB, -1, 0, -15);

                    createBlockAtPos(player, Material.DARK_OAK_LOG, 1, -1, -12);
                    createBlockAtPos(player, Material.DARK_OAK_LOG, -1, -1, -12);
                    createBlockAtPos(player, Material.DARK_OAK_LOG, 1, -1, -15);
                    createBlockAtPos(player, Material.DARK_OAK_LOG, -1, -1, -15);
                }else if(timer == 6 || timer == 5 || timer == 4 || timer == 3) {
                    Location cannon = new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY()+1, player.getLocation().getZ()-9);
                        if(timer == 6) {
                            Particle.DustOptions dustOptions = new Particle.DustOptions(Color.GREEN, 1.0F);
                            cannon.getWorld().spawnParticle(Particle.REDSTONE, cannon, 50, dustOptions);
                        }else if(timer == 5) {
                            Particle.DustOptions dustOptions = new Particle.DustOptions(Color.YELLOW, 2.0F);
                            cannon.getWorld().spawnParticle(Particle.REDSTONE, cannon, 50, dustOptions);
                        }else if(timer == 4) {
                            Particle.DustOptions dustOptions = new Particle.DustOptions(Color.ORANGE, 3.0F);
                            cannon.getWorld().spawnParticle(Particle.REDSTONE, cannon, 50, dustOptions);
                        }else if(timer == 3) {
                            Particle.DustOptions dustOptions = new Particle.DustOptions(Color.RED, 5.0F);
                            cannon.getWorld().spawnParticle(Particle.REDSTONE, cannon, 50, dustOptions);
                        }
                }else if(timer == 2) {
                    Location cannon = new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY()+1, player.getLocation().getZ()-9);
                    Entity entity = createNPC(cannon);
                    entity.setVelocity(VectorArrowToTarget(cannon, player));
                }else if(timer == 1) {
                    player.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, player.getLocation(), 50);
                    player.setHealth(0.0);
                }else if(timer == 0) {
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
                    player.setVelocity(new Location(player.getWorld(), player.getLocation().getX() - player.getLocation().getX() + getRandomNumber(-50, 50), world.getHighestBlockYAt(0, 0) - world.getHighestBlockYAt(0, 0) + getRandomNumber(-25, 25), player.getLocation().getZ() - player.getLocation().getZ() + getRandomNumber(-50, 50)).toVector());
                    player.getWorld().spawnParticle(Particle.FLAME, player.getLocation(), 500);
                }
                if (timer == 10) {
                    this.cancel();
                }
                timer++;
            }
        }.runTaskTimer(Spik.INSTANCE, 0, 10);
    }

    public static void playHumanCanonAnimation(Player player) {
        new BukkitRunnable() {
            int timer = 0;
            @Override
            public void run() {
                if (timer == 0) {
                    player.hidePlayer(Spik.INSTANCE, player);
                    createBlockAtPos(player, Material.COAL_BLOCK, 0, 1, 1);
                    createBlockAtPos(player, Material.COAL_BLOCK, 0, 1, 2);
                    createBlockAtPos(player, Material.COAL_BLOCK, 0, 1, 3);
                    createBlockAtPos(player, Material.COAL_BLOCK, 0, 1, 4);
                    createBlockAtPos(player, Material.COAL_BLOCK, 0, 1, 5);
                    createBlockAtPos(player, Material.COAL_BLOCK, 0, 1, 6);

                    createBlockAtPos(player, Material.SPRUCE_FENCE, 0, 2, 3);
                    createBlockAtPos(player, Material.CHISELED_NETHER_BRICKS, 0, 2, 4);
                    createBlockAtPos(player, Material.NETHER_BRICK_SLAB, 0, 2, 5);

                    createBlockAtPos(player, Material.NETHER_BRICK_SLAB, 1, 1, 3);
                    createBlockAtPos(player, Material.NETHER_BRICK_SLAB, -1, 1, 3);
                    createBlockAtPos(player, Material.NETHER_BRICK_SLAB, 1, 1, 6);
                    createBlockAtPos(player, Material.NETHER_BRICK_SLAB, -1, 1, 6);

                    createBlockAtPos(player, Material.DARK_OAK_LOG, 1, 0, 3);
                    createBlockAtPos(player, Material.DARK_OAK_LOG, -1, 0, 3);
                    createBlockAtPos(player, Material.DARK_OAK_LOG, 1, 0, 6);
                    createBlockAtPos(player, Material.DARK_OAK_LOG, -1, 0, 6);
                } else if (timer == 1) {
                    createBlockAtPos(player, Material.QUARTZ_BLOCK, -1, 12, -22);
                    createBlockAtPos(player, Material.QUARTZ_BLOCK, 1, 12, -22);
                    createBlockAtPos(player, Material.QUARTZ_BLOCK, 0, 12, -22);

                    createBlockAtPos(player, Material.RED_CONCRETE, -1, 11, -22);
                    createBlockAtPos(player, Material.RED_CONCRETE, 1, 11, -22);
                    createBlockAtPos(player, Material.RED_CONCRETE, 0, 11, -22);
                    createBlockAtPos(player, Material.QUARTZ_BLOCK, -2, 11, -22);
                    createBlockAtPos(player, Material.QUARTZ_BLOCK, 2, 11, -22);

                    createBlockAtPos(player, Material.RED_CONCRETE, -1, 10, -22);
                    createBlockAtPos(player, Material.RED_CONCRETE, 1, 10, -22);
                    createBlockAtPos(player, Material.QUARTZ_BLOCK, 0, 10, -22);
                    createBlockAtPos(player, Material.RED_CONCRETE, -2, 10, -22);
                    createBlockAtPos(player, Material.RED_CONCRETE, 2, 10, -22);
                    createBlockAtPos(player, Material.QUARTZ_BLOCK, -3, 10, -22);
                    createBlockAtPos(player, Material.QUARTZ_BLOCK, 3, 10, -22);

                    createBlockAtPos(player, Material.QUARTZ_BLOCK, -1, 9, -22);
                    createBlockAtPos(player, Material.QUARTZ_BLOCK, 1, 9, -22);
                    createBlockAtPos(player, Material.QUARTZ_BLOCK, 0, 9, -22);
                    createBlockAtPos(player, Material.RED_CONCRETE, -2, 9, -22);
                    createBlockAtPos(player, Material.RED_CONCRETE, 2, 9, -22);
                    createBlockAtPos(player, Material.RED_CONCRETE, -3, 9, -22);
                    createBlockAtPos(player, Material.RED_CONCRETE, 3, 9, -22);
                    createBlockAtPos(player, Material.QUARTZ_BLOCK, -4, 9, -22);
                    createBlockAtPos(player, Material.QUARTZ_BLOCK, 4, 9, -22);

                    createBlockAtPos(player, Material.QUARTZ_BLOCK, -1, 8, -22);
                    createBlockAtPos(player, Material.QUARTZ_BLOCK, 1, 8, -22);
                    createBlockAtPos(player, Material.REDSTONE_BLOCK, 0, 8, -22);
                    createBlockAtPos(player, Material.QUARTZ_BLOCK, -2, 8, -22);
                    createBlockAtPos(player, Material.QUARTZ_BLOCK, 2, 8, -22);
                    createBlockAtPos(player, Material.RED_CONCRETE, -3, 8, -22);
                    createBlockAtPos(player, Material.RED_CONCRETE, 3, 8, -22);
                    createBlockAtPos(player, Material.QUARTZ_BLOCK, -4, 8, -22);
                    createBlockAtPos(player, Material.QUARTZ_BLOCK, 4, 8, -22);

                    createBlockAtPos(player, Material.QUARTZ_BLOCK, -1, 7, -22);
                    createBlockAtPos(player, Material.QUARTZ_BLOCK, 1, 7, -22);
                    createBlockAtPos(player, Material.QUARTZ_BLOCK, 0, 7, -22);
                    createBlockAtPos(player, Material.RED_CONCRETE, -2, 7, -22);
                    createBlockAtPos(player, Material.RED_CONCRETE, 2, 7, -22);
                    createBlockAtPos(player, Material.RED_CONCRETE, -3, 7, -22);
                    createBlockAtPos(player, Material.RED_CONCRETE, 3, 7, -22);
                    createBlockAtPos(player, Material.QUARTZ_BLOCK, -4, 7, -22);
                    createBlockAtPos(player, Material.QUARTZ_BLOCK, 4, 7, -22);

                    createBlockAtPos(player, Material.RED_CONCRETE, -1, 6, -22);
                    createBlockAtPos(player, Material.RED_CONCRETE, 1, 6, -22);
                    createBlockAtPos(player, Material.QUARTZ_BLOCK, 0, 6, -22);
                    createBlockAtPos(player, Material.RED_CONCRETE, -2, 6, -22);
                    createBlockAtPos(player, Material.RED_CONCRETE, 2, 6, -22);
                    createBlockAtPos(player, Material.QUARTZ_BLOCK, -3, 6, -22);
                    createBlockAtPos(player, Material.QUARTZ_BLOCK, 3, 6, -22);

                    createBlockAtPos(player, Material.RED_CONCRETE, -1, 5, -22);
                    createBlockAtPos(player, Material.RED_CONCRETE, 1, 5, -22);
                    createBlockAtPos(player, Material.RED_CONCRETE, 0, 5, -22);
                    createBlockAtPos(player, Material.QUARTZ_BLOCK, -2, 5, -22);
                    createBlockAtPos(player, Material.QUARTZ_BLOCK, 2, 5, -22);

                    createBlockAtPos(player, Material.QUARTZ_BLOCK, -1, 4, -22);
                    createBlockAtPos(player, Material.QUARTZ_BLOCK, 1, 4, -22);
                    createBlockAtPos(player, Material.QUARTZ_BLOCK, 0, 4, -22);
                    player.setVelocity(new Vector(0, 1, -200).multiply(4));
                }
                if (timer == 10) {
                    player.showPlayer(Spik.INSTANCE, player);
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
