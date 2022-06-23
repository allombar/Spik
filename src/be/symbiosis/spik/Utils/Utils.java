package be.symbiosis.spik.Utils;

import be.symbiosis.spik.Manager.Animation.Animation;
import be.symbiosis.spik.Manager.Animation.AnimationManager;
import be.symbiosis.spik.Manager.CuboidManager;
import be.symbiosis.spik.SpikCore;
import net.minecraft.server.v1_16_R3.ItemFireworks;
import org.bukkit.*;
import org.bukkit.Color;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.List;

public class Utils {
    public static BukkitTask playAnimationArrow(Player player, Animation animation) {
        List<Entity> arrows = new ArrayList<>();
        World world = player.getWorld();
        Location loc = parseStringToLoc(AnimationManager.getConfig().getString("animations.arrow.pos"), world);

        new BukkitRunnable() {
            int timer1 = 20;
            @Override
            public void run() {
                if(timer1 == 20) {
                    System.out.println(loc);
                    player.teleport(loc);
                } else if (timer1 == 19) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 20, 10));
                } else if (timer1 == 18) {
                    player.setAllowFlight(true);
                    animation.setPlayer(player);
                    Entity arrow = setArrowToPoc(newLocation(player.getLocation(), 5, 1, -5), -90, 90);
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

                } else if (timer1 == 8) {
                    for (int xx = 0; xx < arrows.size(); ) {
                        Entity arroww = arrows.get(xx);
                        arroww.remove();
                        player.getWorld().spawnParticle(Particle.SOUL, loc.add(0, -1, 0), 10);
                        xx++;
                    }
                    loc.getWorld().strikeLightningEffect(player.getLocation());
                    player.setHealth(0);
                    animation.setStarted(false);
                    animation.setPlayer(null);
                    arrows.clear();
                    player.setAllowFlight(false);
                } else if (timer1 == 6) {
                    LocalisationManager.resetSpecToLocation();
                    this.cancel();
                }
                timer1--;
            }
        }.runTaskTimer(SpikCore.GetInstance(), 0, 20);
        return run;
    }

    public static BukkitTask playAnimationCanon(Player player, Location loc, Animation animation) {
        List<Entity> fireball = new ArrayList<>();
        List<Location> block = new ArrayList<>();
        BukkitTask run = new BukkitRunnable() {
            int timer = -3;

            @Override
            public void run() {
                if(timer == 0) {
                    player.teleport(new Location(player.getWorld(), loc.getX(), loc.getY(), loc.getZ(), 180, 0));

                    block.add(createBlockAtPos(player.getLocation(), Material.DARK_OAK_FENCE, 1, 0, 0));
                    block.add(createBlockAtPos(player.getLocation(), Material.DARK_OAK_FENCE, -1, 0, 0));

                    block.add(createBlockAtPos(player.getLocation(), Material.DARK_OAK_FENCE, 1, 1, 0));
                    block.add(createBlockAtPos(player.getLocation(), Material.DARK_OAK_FENCE, -1, 1, 0));

                    block.add(createBlockAtPos(player.getLocation(), Material.DARK_OAK_FENCE, 1, 2, 0));
                    block.add(createBlockAtPos(player.getLocation(), Material.DARK_OAK_FENCE, -1, 2, 0));

                    block.add(createBlockAtPos(player.getLocation(), Material.DARK_OAK_FENCE, 1, 3, 0));
                    block.add(createBlockAtPos(player.getLocation(), Material.DARK_OAK_FENCE, -1, 3, 0));
                    block.add(createBlockAtPos(player.getLocation(), Material.DARK_OAK_FENCE, 0, 3, 0));

                    block.add(createBlockAtPos(player.getLocation(), Material.DARK_OAK_SLAB, 0, 4, 0));
                    block.add(createBlockAtPos(player.getLocation(), Material.DARK_OAK_SLAB, 1, 4, 0));
                    block.add(createBlockAtPos(player.getLocation(), Material.DARK_OAK_SLAB, -1, 4, 0));

                    player.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, loc, 200);
                } else if (timer == 4) {
                    player.teleport(new Location(player.getWorld(), loc.getX(), loc.getY() + 1, loc.getZ(), 180, 0));
                } else if (timer == 5) {
                    Location cannon = new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ() - 12.5);
                    Objects.requireNonNull(cannon.getWorld()).spawnParticle(Particle.EXPLOSION_NORMAL, cannon, 200);

                    block.add(createBlockAtPos(player.getLocation(), Material.COAL_BLOCK, 0, 0, -10));
                    block.add(createBlockAtPos(player.getLocation(), Material.COAL_BLOCK, 0, 0, -11));
                    block.add(createBlockAtPos(player.getLocation(), Material.COAL_BLOCK, 0, 0, -12));
                    block.add(createBlockAtPos(player.getLocation(), Material.COAL_BLOCK, 0, 0, -13));
                    block.add(createBlockAtPos(player.getLocation(), Material.COAL_BLOCK, 0, 0, -14));
                    block.add(createBlockAtPos(player.getLocation(), Material.COAL_BLOCK, 0, 0, -15));

                    block.add(createBlockAtPos(player.getLocation(), Material.COAL_BLOCK, 1, 0, -13));
                    block.add(createBlockAtPos(player.getLocation(), Material.COAL_BLOCK, -1, 0, -13));

                    block.add(createBlockAtPos(player.getLocation(), Material.COAL_BLOCK, 1, 0, -14));
                    block.add(createBlockAtPos(player.getLocation(), Material.COAL_BLOCK, -1, 0, -14));

                    block.add(createBlockAtPos(player.getLocation(), Material.SPRUCE_FENCE, 0, 1, -12));
                    block.add(createBlockAtPos(player.getLocation(), Material.CHISELED_NETHER_BRICKS, 0, 1, -13));
                    block.add(createBlockAtPos(player.getLocation(), Material.NETHER_BRICK_SLAB, 0, 1, -14));

                    block.add(createBlockAtPos(player.getLocation(), Material.NETHER_BRICK_SLAB, 1, 0, -12));
                    block.add(createBlockAtPos(player.getLocation(), Material.NETHER_BRICK_SLAB, -1, 0, -12));
                    block.add(createBlockAtPos(player.getLocation(), Material.NETHER_BRICK_SLAB, 1, 0, -15));
                    block.add(createBlockAtPos(player.getLocation(), Material.NETHER_BRICK_SLAB, -1, 0, -15));

                    block.add(createBlockAtPos(player.getLocation(), Material.DARK_OAK_LOG, 1, -1, -12));
                    block.add(createBlockAtPos(player.getLocation(), Material.DARK_OAK_LOG, -1, -1, -12));
                    block.add(createBlockAtPos(player.getLocation(), Material.DARK_OAK_LOG, 1, -1, -15));
                    block.add(createBlockAtPos(player.getLocation(), Material.DARK_OAK_LOG, -1, -1, -15));

                }else if(timer == 4 || timer == 5 || timer == 6 || timer == 7) {
                    Location cannon = new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY()+0.5, player.getLocation().getZ()-9);
                    if (timer == 4) {
                        Particle.DustOptions dustOptions = new Particle.DustOptions(Color.GREEN, 1.0F);
                        Objects.requireNonNull(cannon.getWorld()).spawnParticle(Particle.REDSTONE, cannon, 50, dustOptions);
                    } else if (timer == 7) {
                        Particle.DustOptions dustOptions = new Particle.DustOptions(Color.YELLOW, 2.0F);
                        Objects.requireNonNull(cannon.getWorld()).spawnParticle(Particle.REDSTONE, cannon, 50, dustOptions);
                    } else if (timer == 8) {
                        Particle.DustOptions dustOptions = new Particle.DustOptions(Color.ORANGE, 3.0F);
                        Objects.requireNonNull(cannon.getWorld()).spawnParticle(Particle.REDSTONE, cannon, 50, dustOptions);
                    } else if (timer == 9) {
                        Particle.DustOptions dustOptions = new Particle.DustOptions(Color.RED, 5.0F);
                        Objects.requireNonNull(cannon.getWorld()).spawnParticle(Particle.REDSTONE, cannon, 50, dustOptions);
                    }
                } else if(timer == 8) {
                    Location cannon = new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ()-9);
                    Entity entity = createNPCFireball(cannon);
                    fireball.add(entity);
                    System.out.println(fireball.size());
                    entity.setVelocity(VectorArrowToTarget(cannon, player));
                } else if(timer == 9) {
                    Iterator<Entity> iter = fireball.iterator();
                    while (iter.hasNext()) {
                        Entity entity = iter.next();
                        if (fireball.contains(entity)) {
                            entity.remove();
                            iter.remove();
                        }
                    }
                } else if (timer == 13) {
                    removeBlock(block);
                } else if (timer == 15) {
                    LocalisationManager.resetSpecToLocation();
                    this.cancel();
                }
                timer++;
            }
        }.runTaskTimer(SpikCore.GetInstance(), 0, 20);
        return run;
    }

    public static BukkitTask playHumanCanonAnimation(Player player, Location loc, Animation animation) {
        List<Location> block = new ArrayList<>();
        BukkitTask run = new BukkitRunnable() {
            int timer = 0;

            @Override
            public void run() {
                if (timer == 0) {
                    player.teleport(loc);
                    player.hidePlayer(SpikCore.GetInstance(), player);
                    block.add(createBlockAtPos(loc, Material.COAL_BLOCK, 0, 1, 1));
                    block.add(createBlockAtPos(loc, Material.COAL_BLOCK, 0, 1, 2));
                    block.add(createBlockAtPos(loc, Material.COAL_BLOCK, 0, 1, 3));
                    block.add(createBlockAtPos(loc, Material.COAL_BLOCK, 0, 1, 4));
                    block.add(createBlockAtPos(loc, Material.COAL_BLOCK, 0, 1, 5));
                    block.add(createBlockAtPos(loc, Material.COAL_BLOCK, 0, 1, 6));

                    block.add(createBlockAtPos(loc, Material.SPRUCE_FENCE, 0, 2, 3));
                    block.add(createBlockAtPos(loc, Material.CHISELED_NETHER_BRICKS, 0, 2, 4));
                    block.add(createBlockAtPos(loc, Material.NETHER_BRICK_SLAB, 0, 2, 5));

                    block.add(createBlockAtPos(loc, Material.NETHER_BRICK_SLAB, 1, 1, 3));
                    block.add(createBlockAtPos(loc, Material.NETHER_BRICK_SLAB, -1, 1, 3));
                    block.add(createBlockAtPos(loc, Material.NETHER_BRICK_SLAB, 1, 1, 6));
                    block.add(createBlockAtPos(loc, Material.NETHER_BRICK_SLAB, -1, 1, 6));

                    block.add(createBlockAtPos(loc, Material.DARK_OAK_LOG, 1, 0, 3));
                    block.add(createBlockAtPos(loc, Material.DARK_OAK_LOG, -1, 0, 3));
                    block.add(createBlockAtPos(loc, Material.DARK_OAK_LOG, 1, 0, 6));
                    block.add(createBlockAtPos(loc, Material.DARK_OAK_LOG, -1, 0, 6));
                } else if (timer == 4) {
                    Location target = new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY() + 12, player.getLocation().getZ() - 18);
                    Objects.requireNonNull(target.getWorld()).spawnParticle(Particle.SOUL_FIRE_FLAME, newLocation(loc,0, 8, -22), 200);
                    block.add(createBlockAtPos(loc, Material.QUARTZ_BLOCK, -1, 12, -22));
                    block.add(createBlockAtPos(loc, Material.QUARTZ_BLOCK, 1, 12, -22));
                    block.add(createBlockAtPos(loc, Material.QUARTZ_BLOCK, 0, 12, -22));

                    block.add(createBlockAtPos(loc, Material.RED_CONCRETE, -1, 11, -22));
                    block.add(createBlockAtPos(loc, Material.RED_CONCRETE, 1, 11, -22));
                    block.add(createBlockAtPos(loc, Material.RED_CONCRETE, 0, 11, -22));
                    block.add(createBlockAtPos(loc, Material.QUARTZ_BLOCK, -2, 11, -22));
                    block.add(createBlockAtPos(loc, Material.QUARTZ_BLOCK, 2, 11, -22));

                    block.add(createBlockAtPos(loc, Material.RED_CONCRETE, -1, 10, -22));
                    block.add(createBlockAtPos(loc, Material.RED_CONCRETE, 1, 10, -22));
                    block.add(createBlockAtPos(loc, Material.QUARTZ_BLOCK, 0, 10, -22));
                    block.add(createBlockAtPos(loc, Material.RED_CONCRETE, -2, 10, -22));
                    block.add(createBlockAtPos(loc, Material.RED_CONCRETE, 2, 10, -22));
                    block.add(createBlockAtPos(loc, Material.QUARTZ_BLOCK, -3, 10, -22));
                    block.add(createBlockAtPos(loc, Material.QUARTZ_BLOCK, 3, 10, -22));

                    block.add(createBlockAtPos(loc, Material.QUARTZ_BLOCK, -1, 9, -22));
                    block.add(createBlockAtPos(loc, Material.QUARTZ_BLOCK, 1, 9, -22));
                    block.add(createBlockAtPos(loc, Material.QUARTZ_BLOCK, 0, 9, -22));
                    block.add(createBlockAtPos(loc, Material.RED_CONCRETE, -2, 9, -22));
                    block.add(createBlockAtPos(loc, Material.RED_CONCRETE, 2, 9, -22));
                    block.add(createBlockAtPos(loc, Material.RED_CONCRETE, -3, 9, -22));
                    block.add(createBlockAtPos(loc, Material.RED_CONCRETE, 3, 9, -22));
                    block.add(createBlockAtPos(loc, Material.QUARTZ_BLOCK, -4, 9, -22));
                    block.add(createBlockAtPos(loc, Material.QUARTZ_BLOCK, 4, 9, -22));

                    block.add(createBlockAtPos(loc, Material.QUARTZ_BLOCK, -1, 8, -22));
                    block.add(createBlockAtPos(loc, Material.QUARTZ_BLOCK, 1, 8, -22));
                    block.add(createBlockAtPos(loc, Material.REDSTONE_BLOCK, 0, 8, -22));
                    block.add(createBlockAtPos(loc, Material.QUARTZ_BLOCK, -2, 8, -22));
                    block.add(createBlockAtPos(loc, Material.QUARTZ_BLOCK, 2, 8, -22));
                    block.add(createBlockAtPos(loc, Material.RED_CONCRETE, -3, 8, -22));
                    block.add(createBlockAtPos(loc, Material.RED_CONCRETE, 3, 8, -22));
                    block.add(createBlockAtPos(loc, Material.QUARTZ_BLOCK, -4, 8, -22));
                    block.add(createBlockAtPos(loc, Material.QUARTZ_BLOCK, 4, 8, -22));

                    block.add(createBlockAtPos(loc, Material.QUARTZ_BLOCK, -1, 7, -22));
                    block.add(createBlockAtPos(loc, Material.QUARTZ_BLOCK, 1, 7, -22));
                    block.add(createBlockAtPos(loc, Material.QUARTZ_BLOCK, 0, 7, -22));
                    block.add(createBlockAtPos(loc, Material.RED_CONCRETE, -2, 7, -22));
                    block.add(createBlockAtPos(loc, Material.RED_CONCRETE, 2, 7, -22));
                    block.add(createBlockAtPos(loc, Material.RED_CONCRETE, -3, 7, -22));
                    block.add(createBlockAtPos(loc, Material.RED_CONCRETE, 3, 7, -22));
                    block.add(createBlockAtPos(loc, Material.QUARTZ_BLOCK, -4, 7, -22));
                    block.add(createBlockAtPos(loc, Material.QUARTZ_BLOCK, 4, 7, -22));

                    block.add(createBlockAtPos(loc, Material.RED_CONCRETE, -1, 6, -22));
                    block.add(createBlockAtPos(loc, Material.RED_CONCRETE, 1, 6, -22));
                    block.add(createBlockAtPos(loc, Material.QUARTZ_BLOCK, 0, 6, -22));
                    block.add(createBlockAtPos(loc, Material.RED_CONCRETE, -2, 6, -22));
                    block.add(createBlockAtPos(loc, Material.RED_CONCRETE, 2, 6, -22));
                    block.add(createBlockAtPos(loc, Material.QUARTZ_BLOCK, -3, 6, -22));
                    block.add(createBlockAtPos(loc, Material.QUARTZ_BLOCK, 3, 6, -22));

                    block.add(createBlockAtPos(loc, Material.RED_CONCRETE, -1, 5, -22));
                    block.add(createBlockAtPos(loc, Material.RED_CONCRETE, 1, 5, -22));
                    block.add(createBlockAtPos(loc, Material.RED_CONCRETE, 0, 5, -22));
                    block.add(createBlockAtPos(loc, Material.QUARTZ_BLOCK, -2, 5, -22));
                    block.add(createBlockAtPos(loc, Material.QUARTZ_BLOCK, 2, 5, -22));

                    block.add(createBlockAtPos(loc, Material.QUARTZ_BLOCK, -1, 4, -22));
                    block.add(createBlockAtPos(loc, Material.QUARTZ_BLOCK, 1, 4, -22));
                    block.add(createBlockAtPos(loc, Material.QUARTZ_BLOCK, 0, 4, -22));
                } else if(timer == 4 || timer == 5 || timer == 6 || timer == 7) {
                    Location cannon = new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY()+2, player.getLocation().getZ()+1);
                    if(timer == 4) {
                        Particle.DustOptions dustOptions = new Particle.DustOptions(Color.GREEN, 1.0F);
                        Objects.requireNonNull(cannon.getWorld()).spawnParticle(Particle.REDSTONE, cannon, 50, dustOptions);
                    } else if (timer == 7) {
                        Particle.DustOptions dustOptions = new Particle.DustOptions(Color.YELLOW, 2.0F);
                        Objects.requireNonNull(cannon.getWorld()).spawnParticle(Particle.REDSTONE, cannon, 50, dustOptions);
                    } else if (timer == 8) {
                        Particle.DustOptions dustOptions = new Particle.DustOptions(Color.ORANGE, 3.0F);
                        Objects.requireNonNull(cannon.getWorld()).spawnParticle(Particle.REDSTONE, cannon, 50, dustOptions);
                    } else if (timer == 9) {
                        Particle.DustOptions dustOptions = new Particle.DustOptions(Color.RED, 5.0F);
                        Objects.requireNonNull(cannon.getWorld()).spawnParticle(Particle.REDSTONE, cannon, 50, dustOptions);
                    }
                } else if (timer == 13) {
                    Location cannon = new Location(player.getWorld(), player.getLocation().getX() + 0.5, player.getLocation().getY() + 2, player.getLocation().getZ() + 2);
                    Objects.requireNonNull(cannon.getWorld()).spawnParticle(Particle.EXPLOSION_LARGE, cannon, 20);
                    player.removePotionEffect(PotionEffectType.INVISIBILITY);
                    player.setVelocity(vectorPlayerToBlock(player, block.get(43).getBlock()).multiply(4));
                } else if (timer == 9) {
                    player.setHealth(0);
                    animation.setStarted(false);
                    Particle.DustOptions dustOptions = new Particle.DustOptions(Color.RED, 5.0F);
                    player.getWorld().spawnParticle(Particle.REDSTONE, player.getLocation(), 50, dustOptions);
                    player.getWorld().spawnParticle(Particle.REDSTONE, player.getLocation().add(0, 1, 0), 50, dustOptions);
                }
                else if (timer == 12) {
                    removeBlock(block);
                } else if (timer == 19) {
                    LocalisationManager.resetSpecToLocation();
                    this.cancel();
                }
                timer++;
            }
        }.runTaskTimer(SpikCore.GetInstance(), 0, 20);
        return run;
    }

    public static BukkitTask playCuboidAnimation(Player player, CuboidManager cuboidManager, Animation animation) {
        animation.setStarted(true);
        new BukkitRunnable(){
            int timer = 10;
            @Override
            public void run() {
                if(timer == 10) {
                    Location loc = cuboidManager.getMiddle();
                    player.teleport(newLocation(loc, 0.5, 1, 0.5));
                    player.setSwimming(true);
                    player.getWorld().getBlockAt(player.getLocation().add(0, 5, 0)).setType(Material.WATER);
                    player.getWorld().spawnParticle(Particle.FALLING_WATER, player.getLocation().add(0, 4, 0), 500);
                }else if(timer == 9) {
                    addBlock(cuboidManager.SetBlockOnCuboid(0), Material.WATER);
                    animation.setPlayer(player);
                }else if(timer == 8) {
                    addBlock(cuboidManager.SetBlockOnCuboid(1), Material.WATER);
                }else if(timer == 7) {
                    addBlock(cuboidManager.SetBlockOnCuboid(2), Material.WATER);
                }else if(timer == 6) {
                    addBlock(cuboidManager.SetBlockOnCuboid(3), Material.WATER);
                }else if(timer == 5) {
                    addBlock(cuboidManager.SetBlockOnCuboid(4), Material.WATER);
                }else if(timer == 6) {
                    addBlock(cuboidManager.SetBlockOnCuboid(5), Material.WATER);
                }else if(timer == 7) {
                    double health = player.getHealth();
                    player.setHealth(health-2);
                    player.getWorld().spawnParticle(Particle.WATER_BUBBLE, newLocation(player.getLocation(), 0, 1, 0), 10);
                    player.playEffect(EntityEffect.HURT_DROWN);
                }else if(timer == 8) {
                    double health = player.getHealth();
                    player.setHealth(health-2);
                    player.getWorld().spawnParticle(Particle.WATER_BUBBLE, newLocation(player.getLocation(), 0, 2, 0), 10);
                    player.playEffect(EntityEffect.HURT_DROWN);
                }else if(timer == 9) {
                    double health = player.getHealth();
                    player.setHealth(health-2);
                    player.getWorld().spawnParticle(Particle.WATER_BUBBLE, newLocation(player.getLocation(), 0, 3, 0), 10);
                    player.playEffect(EntityEffect.HURT_DROWN);
                }else if(timer == 10) {
                    double health = player.getHealth();
                    player.setHealth(health-2);
                    player.getWorld().spawnParticle(Particle.WATER_BUBBLE, newLocation(player.getLocation(), 0, 4, 0), 10);
                    player.playEffect(EntityEffect.HURT_DROWN);
                }else if(timer == 11){
                    double health = player.getHealth();
                    player.setHealth(health-2);
                    player.getWorld().spawnParticle(Particle.WATER_BUBBLE, newLocation(player.getLocation(), 0, 5, 0), 10);
                    player.playEffect(EntityEffect.HURT_DROWN);
                }else if(timer == 12) {
                    double health = player.getHealth();
                    player.setHealth(health-2);
                    player.getWorld().spawnParticle(Particle.WATER_BUBBLE, newLocation(player.getLocation(), 0, 5, 0), 10);
                    player.playEffect(EntityEffect.HURT_DROWN);
                    player.getWorld().spawnParticle(Particle.WATER_BUBBLE, newLocation(player.getLocation(), 0, 6, 0), 10);
                } else if (timer == 17) {
                    addBlock(cuboidManager.SetBlockOnCuboid(5), Material.AIR);
                } else if (timer == 18) {
                    addBlock(cuboidManager.SetBlockOnCuboid(4), Material.AIR);
                } else if (timer == 19) {
                    addBlock(cuboidManager.SetBlockOnCuboid(3), Material.AIR);
                } else if (timer == 20) {
                    addBlock(cuboidManager.SetBlockOnCuboid(2), Material.AIR);
                } else if (timer == 21) {
                    addBlock(cuboidManager.SetBlockOnCuboid(1), Material.AIR);
                } else if (timer == 23) {
                    addBlock(cuboidManager.SetBlockOnCuboid(0), Material.AIR);
                } else if (timer == 25) {
                    LocalisationManager.resetSpecToLocation();
                    this.cancel();
                }
                timer--;
            }
        }.runTaskTimer(SpikCore.GetInstance(), 0, 20);
        return run;
    }

    public static BukkitTask playerCuboidPiranaAnimation(Player player, CuboidManager cuboidManager, Animation animation) {
        animation.setStarted(true);
        List<Entity> piranas = new ArrayList<>();
        new BukkitRunnable(){
            int timer = 10;
            @Override
            public void run() {
                if(timer == 10) {
                    Location loc = cuboidManager.getMiddle();
                    player.teleport(loc.add(0, 1, 0));
                    animation.setPlayer(player);
                    for (int x = 0; x < 20; x++) {
                        player.getWorld().spawnParticle(Particle.LANDING_LAVA, player.getLocation().add(0, 4, 0), 50);
                        Entity entity = player.getWorld().spawnEntity(player.getLocation().add(0, 4, 0), EntityType.SALMON);
                        entity.setGlowing(true);
                        entity.setCustomNameVisible(true);
                        entity.setCustomName("§4PIRANHA");
                        piranas.add(entity);
                    }
                }else if(timer == 9) {
                    addBlock(cuboidManager.SetBlockOnCuboid(0), Material.WATER);
                }else if(timer == 8) {
                    addBlock(cuboidManager.SetBlockOnCuboid(1), Material.WATER);
                }else if(timer == 7) {
                    addBlock(cuboidManager.SetBlockOnCuboid(2), Material.WATER);
                }else if(timer == 6) {
                    addBlock(cuboidManager.SetBlockOnCuboid(3), Material.WATER);
                }else if(timer == 5) {
                    addBlock(cuboidManager.SetBlockOnCuboid(4), Material.WATER);
                }else if(timer == 4) {
                    addBlock(cuboidManager.SetBlockOnCuboid(5), Material.WATER);
                }else if(timer == 3) {
                    for(Entity entity : piranas) {
                        entity.setVelocity(VectorArrowToTarget(entity.getLocation(), player));
                        Particle.DustOptions dustOptions = new Particle.DustOptions(Color.RED, 5.0F);
                        entity.getWorld().spawnParticle(Particle.REDSTONE, player.getLocation(), 50, dustOptions);
                        entity.getWorld().spawnParticle(Particle.REDSTONE, player.getLocation().add(0, 1, 0), 50, dustOptions);
                        entity.getWorld().spawnParticle(Particle.REDSTONE, player.getLocation().add(0, -1, 0), 50, dustOptions);
                    }
                }else if(timer == 2) {
                    for(Entity entity : piranas) {
                        Random ran = new Random();
                        int x = ran.nextInt(5);
                        int y = ran.nextInt(3);
                        int z = ran.nextInt(5);

                        Location loc = new Location(player.getWorld(), x, y, z);
                        entity.setVelocity(VectorLocToTarget(entity.getLocation(), loc));
                        Particle.DustOptions dustOptions = new Particle.DustOptions(Color.RED, 5.0F);
                        entity.getWorld().spawnParticle(Particle.REDSTONE, player.getLocation(), 50, dustOptions);
                        entity.getWorld().spawnParticle(Particle.REDSTONE, player.getLocation().add(0, 1, 0), 50, dustOptions);
                        entity.getWorld().spawnParticle(Particle.REDSTONE, player.getLocation().add(0, -1, 0), 50, dustOptions);
                    }
                }else if(timer == 1) {
                    for(Entity entity : piranas) {

                        entity.setVelocity(VectorLocToTarget(entity.getLocation(), player.getLocation()));
                    }
                }else if(timer == 0) {
                    player.setSwimming(false);
                    player.setHealth(0);
                    animation.setPlayer(null);
                    animation.setStarted(false);
                    removeBlock(cuboidManager.getArea());
                    this.cancel();
                }
                timer--;
            }
        }.runTaskTimer(SpikCore.GetInstance(), 0, 20);
        return run;
    }

    public static BukkitTask playBurnedAnimation(Player player, Animation animation) {
        LocalisationManager.SetSpecInAnimation(animation.getLocSpec(), player);
        List<Location> bloks = new ArrayList<>();
        Location loc = animation.getLocPlayer();
        new BukkitRunnable(){
            int timer = 10;
            @Override
            public void run() {
                if(timer == 10) {
                    bloks.add(createBlockAtPos(player.getLocation(), Material.SPRUCE_LOG, 0, 0, 1));
                    bloks.add(createBlockAtPos(player.getLocation(), Material.SPRUCE_LOG, -1, 0, 1));
                    bloks.add(createBlockAtPos(player.getLocation(), Material.SPRUCE_LOG, -2, 0, 1));
                    bloks.add(createBlockAtPos(player.getLocation(), Material.SPRUCE_LOG, 1, 0, 1));
                    bloks.add(createBlockAtPos(player.getLocation(), Material.SPRUCE_LOG, 2, 0, 1));

                    bloks.add(createBlockAtPos(player.getLocation(), Material.SPRUCE_LOG, 0, 0, -1));
                    bloks.add(createBlockAtPos(player.getLocation(), Material.SPRUCE_LOG, -1, 0, -1));
                    bloks.add(createBlockAtPos(player.getLocation(), Material.SPRUCE_LOG, -2, 0, -1));
                    bloks.add(createBlockAtPos(player.getLocation(), Material.SPRUCE_LOG, 1, 0, -1));
                    bloks.add(createBlockAtPos(player.getLocation(), Material.SPRUCE_LOG, 2, 0, -1));

                    bloks.add(createBlockAtPos(player.getLocation(), Material.SPRUCE_LOG, 0, 0, -3));
                    bloks.add(createBlockAtPos(player.getLocation(), Material.SPRUCE_LOG, -1, 0, -3));
                    bloks.add(createBlockAtPos(player.getLocation(), Material.SPRUCE_LOG, -2, 0, -3));
                    bloks.add(createBlockAtPos(player.getLocation(), Material.SPRUCE_LOG, 1, 0, -3));
                    bloks.add(createBlockAtPos(player.getLocation(), Material.SPRUCE_LOG, 2, 0, -3));

                    bloks.add(createBlockAtPos(player.getLocation(), Material.SPRUCE_LOG, 0, 0, -3));
                    bloks.add(createBlockAtPos(player.getLocation(), Material.SPRUCE_LOG, -1, 0, -3));
                    bloks.add(createBlockAtPos(player.getLocation(), Material.SPRUCE_LOG, -2, 0, -3));
                    bloks.add(createBlockAtPos(player.getLocation(), Material.SPRUCE_LOG, 1, 0, -3));
                    bloks.add(createBlockAtPos(player.getLocation(), Material.SPRUCE_LOG, 2, 0, -3));
                }else if(timer == 9) {
                    ArmorStand stand = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
                    stand.setArms(true);
                    stand.setChestplate(new ItemStack(Material.GOLDEN_CHESTPLATE));
                    stand.setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                    stand.setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                    stand.setHelmet(new ItemStack(Material.GOLDEN_HELMET));
                    stand.setCustomNameVisible(true);
                    stand.setCustomName("§4BOUREAU");
                    stand.teleport(loc.add(-3, 0, 0));


                }else if(timer == 8) {

                }else if(timer == 7) {

                }else if(timer == 6) {

                }else if(timer == 5) {

                }else if(timer == 4) {

                }else if(timer == 3) {

                }else if(timer == 2) {

                }else if(timer == 1) {

                }else if(timer == 0) {
                removeBlock(bloks);
                this.cancel();
                }
                timer--;
            }
        }.runTaskTimer(SpikCore.GetInstance(), 0, 20);
        return run;
    }

    public static BukkitTask playCauldronAnimation(Player player, Animation animation) {
        LocalisationManager.SetSpecInAnimation(animation.getLocSpec(), player);
        World world = player.getWorld();
        Location loc = parseStringToLoc(Objects.requireNonNull(AnimationManager.getConfig().getString("animations.cauldron.pos")), world);
        List<Location> block = new ArrayList<>();
        final Entity[] entity = {null};
        BukkitTask run = new BukkitRunnable() {
            int timer = -3;

            @Override
            public void run() {
                if (timer == -3) {
                    sendTitleStart(player);
                }
                if (timer == 0) {
                    // couche 1
                    block.add(createBlockAtPos(loc, Material.BEDROCK, 2, 0, 2));
                    block.add(createBlockAtPos(loc, Material.BEDROCK, -2, 0, 2));
                    block.add(createBlockAtPos(loc, Material.BEDROCK, 2, 0, -2));
                    block.add(createBlockAtPos(loc, Material.BEDROCK, -2, 0, -2));

                    // couche 2
                    block.add(createBlockAtPos(loc, Material.BEDROCK, 0, 1, 0));
                    block.add(createBlockAtPos(loc, Material.BEDROCK, -1, 1, 1));
                    block.add(createBlockAtPos(loc, Material.BEDROCK, 0, 1, 1));
                    block.add(createBlockAtPos(loc, Material.BEDROCK, 1, 1, 1));

                    block.add(createBlockAtPos(loc, Material.BEDROCK, 1, 1, -1));
                    block.add(createBlockAtPos(loc, Material.BEDROCK, 1, 1, 0));
                    block.add(createBlockAtPos(loc, Material.BEDROCK, 1, 1, 1));

                    block.add(createBlockAtPos(loc, Material.BEDROCK, -1, 1, -1));
                    block.add(createBlockAtPos(loc, Material.BEDROCK, -1, 1, 0));
                    block.add(createBlockAtPos(loc, Material.BEDROCK, 0, 1, -1));

                    block.add(createBlockAtPos(loc, Material.BEDROCK, -1, 1, 2));
                    block.add(createBlockAtPos(loc, Material.BEDROCK, 1, 1, 2));
                    block.add(createBlockAtPos(loc, Material.BEDROCK, 2, 1, 2));
                    block.add(createBlockAtPos(loc, Material.BEDROCK, 0, 1, 2));
                    block.add(createBlockAtPos(loc, Material.BEDROCK, -2, 1, 2));

                    block.add(createBlockAtPos(loc, Material.BEDROCK, -1, 1, -2));
                    block.add(createBlockAtPos(loc, Material.BEDROCK, 1, 1, -2));
                    block.add(createBlockAtPos(loc, Material.BEDROCK, 2, 1, -2));
                    block.add(createBlockAtPos(loc, Material.BEDROCK, 0, 1, -2));
                    block.add(createBlockAtPos(loc, Material.BEDROCK, -2, 1, -2));

                    block.add(createBlockAtPos(loc, Material.BEDROCK, -2, 1, 1));
                    block.add(createBlockAtPos(loc, Material.BEDROCK, -2, 1, -1));
                    block.add(createBlockAtPos(loc, Material.BEDROCK, -2, 1, 0));

                    block.add(createBlockAtPos(loc, Material.BEDROCK, 2, 1, 1));
                    block.add(createBlockAtPos(loc, Material.BEDROCK, 2, 1, -1));
                    block.add(createBlockAtPos(loc, Material.BEDROCK, 2, 1, 0));
                    //couche 3
                    block.add(createBlockAtPos(loc, Material.LAVA, 0, 2, 0));
                    block.add(createBlockAtPos(loc, Material.LAVA, -1, 2, 1));
                    block.add(createBlockAtPos(loc, Material.LAVA, 0, 2, 1));
                    block.add(createBlockAtPos(loc, Material.LAVA, 1, 2, 1));

                    block.add(createBlockAtPos(loc, Material.LAVA, 1, 2, -1));
                    block.add(createBlockAtPos(loc, Material.LAVA, 1, 2, 0));
                    block.add(createBlockAtPos(loc, Material.LAVA, 1, 2, 1));

                    block.add(createBlockAtPos(loc, Material.LAVA, -1, 2, -1));
                    block.add(createBlockAtPos(loc, Material.LAVA, -1, 2, 0));
                    block.add(createBlockAtPos(loc, Material.LAVA, 0, 2, -1));

                    block.add(createBlockAtPos(loc, Material.BEDROCK, -1, 2, 2));
                    block.add(createBlockAtPos(loc, Material.BEDROCK, 1, 2, 2));
                    block.add(createBlockAtPos(loc, Material.BEDROCK, 2, 2, 2));
                    block.add(createBlockAtPos(loc, Material.BEDROCK, 0, 2, 2));
                    block.add(createBlockAtPos(loc, Material.BEDROCK, -2, 2, 2));

                    block.add(createBlockAtPos(loc, Material.BEDROCK, -1, 2, -2));
                    block.add(createBlockAtPos(loc, Material.BEDROCK, 1, 2, -2));
                    block.add(createBlockAtPos(loc, Material.BEDROCK, 2, 2, -2));
                    block.add(createBlockAtPos(loc, Material.BEDROCK, 0, 2, -2));
                    block.add(createBlockAtPos(loc, Material.BEDROCK, -2, 2, -2));

                    block.add(createBlockAtPos(loc, Material.BEDROCK, -2, 2, 1));
                    block.add(createBlockAtPos(loc, Material.BEDROCK, -2, 2, -1));
                    block.add(createBlockAtPos(loc, Material.BEDROCK, -2, 2, 0));

                    block.add(createBlockAtPos(loc, Material.BEDROCK, 2, 2, 1));
                    block.add(createBlockAtPos(loc, Material.BEDROCK, 2, 2, -1));
                    block.add(createBlockAtPos(loc, Material.BEDROCK, 2, 2, 0));
                    // couche 4
                    block.add(createBlockAtPos(loc, Material.LAVA, 0, 3, 0));
                    block.add(createBlockAtPos(loc, Material.LAVA, -1, 3, 1));
                    block.add(createBlockAtPos(loc, Material.LAVA, 0, 3, 1));
                    block.add(createBlockAtPos(loc, Material.LAVA, 1, 3, 1));

                    block.add(createBlockAtPos(loc, Material.LAVA, 1, 3, -1));
                    block.add(createBlockAtPos(loc, Material.LAVA, 1, 3, 0));
                    block.add(createBlockAtPos(loc, Material.LAVA, 1, 3, 1));

                    block.add(createBlockAtPos(loc, Material.LAVA, -1, 3, -1));
                    block.add(createBlockAtPos(loc, Material.LAVA, -1, 3, 0));
                    block.add(createBlockAtPos(loc, Material.LAVA, 0, 3, -1));

                    block.add(createBlockAtPos(loc, Material.BEDROCK, -1, 3, 2));
                    block.add(createBlockAtPos(loc, Material.BEDROCK, 1, 3, 2));
                    block.add(createBlockAtPos(loc, Material.BEDROCK, 2, 3, 2));
                    block.add(createBlockAtPos(loc, Material.BEDROCK, 0, 3, 2));
                    block.add(createBlockAtPos(loc, Material.BEDROCK, -2, 3, 2));

                    block.add(createBlockAtPos(loc, Material.BEDROCK, -1, 3, -2));
                    block.add(createBlockAtPos(loc, Material.BEDROCK, 1, 3, -2));
                    block.add(createBlockAtPos(loc, Material.BEDROCK, 2, 3, -2));
                    block.add(createBlockAtPos(loc, Material.BEDROCK, 0, 3, -2));
                    block.add(createBlockAtPos(loc, Material.BEDROCK, -2, 3, -2));

                    block.add(createBlockAtPos(loc, Material.BEDROCK, -2, 3, 1));
                    block.add(createBlockAtPos(loc, Material.BEDROCK, -2, 3, -1));
                    block.add(createBlockAtPos(loc, Material.BEDROCK, -2, 3, 0));

                    block.add(createBlockAtPos(loc, Material.BEDROCK, 2, 3, 1));
                    block.add(createBlockAtPos(loc, Material.BEDROCK, 2, 3, -1));
                    block.add(createBlockAtPos(loc, Material.BEDROCK, 2, 3, 0));
                }
                if (timer == 1) {
                    player.teleport(newLocation(loc, 0.5, 10, 0.5));
                    player.setFlying(true);
                }
                if (timer == 3) {
                    player.setFlying(false);
                }
                if(timer == 5) {
                    entity[0] = setSkeletonToPlayerPos(player.getName(),newLocation(loc, 0.5, 2.5, 0.5));
                    Bukkit.broadcastMessage("id of entity: " + entity[0].getEntityId());
                }
                if (timer == 10) {
                    entity[0].remove();
                    removeBlock(block);
                } else if (timer == 15) {
                    LocalisationManager.resetSpecToLocation();
                    this.cancel();
                }
                timer++;
            }
        }.runTaskTimer(SpikCore.GetInstance(), 0, 20);
        return run;
    }

    public static Vector VectorArrowToTarget(Location loc, Player player) {
        Vector a = loc.toVector();//arrow loc
        Vector b = player.getLocation().toVector();//target
        return b.subtract(a).normalize();//create vector
    }

    public static Vector vectorPlayerToBlock(Player player, Block block) {
        Vector a = player.getLocation().toVector();//arrow loc
        Vector b = block.getLocation().toVector();//target
        return b.subtract(a).normalize();//create vector
    }

    public static Vector VectorLocToTarget(Location loc, Location loctarget) {
        Vector a = loc.toVector();//arrow loc
        Vector b = loctarget.toVector();//target
        return b.subtract(a).normalize();//create vector
    }

    public static Location createBlockAtPos(Location player, Material mat, int x, int y, int z) {
        Location locfinal = new Location(player.getWorld(), player.getX() + x, player.getY() + y, player.getZ() + z);
        locfinal.getBlock().setType(mat);
        return locfinal;
    }

    public static void removeBlock(List<Location> block) {
        Iterator<Location> iter = block.iterator();
        while (iter.hasNext()) {
            Location loc = iter.next();
            if (block.contains(loc)) {
                loc.getBlock().setType(Material.AIR);
                iter.remove();
            }
        }
    }

    public static void addBlock(List<Location> block, Material mat) {
        Iterator<Location> iter = block.iterator();
        while (iter.hasNext()) {
            Location loc = iter.next();
            if (block.contains(loc)) {
                loc.getBlock().setType(mat);
                iter.remove();
            }
        }
    }

    public static Location parseStringToLoc(String string, World world) {
        String[] parsedLoc = string.split(",");
        double x = Double.parseDouble(parsedLoc[0]);
        double y = Double.parseDouble(parsedLoc[1]);
        double z = Double.parseDouble(parsedLoc[2]);

        return new Location(world, x, y, z);
    }

    public static String unparseLocToString(Location loc) {
        return loc.getX() + "," + loc.getY() + "," + loc.getZ();
    }
}