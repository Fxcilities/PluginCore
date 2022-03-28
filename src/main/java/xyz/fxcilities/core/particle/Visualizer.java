package xyz.fxcilities.core.particle;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BoundingBox;

/**
 * @author Fxcilities
 * Visualize a BoundingBox with particles
 *
 * Example:
 * <pre>
 * {@code
 * ParticleBuilder particle = new ParticleBuilder(Particle.REDSTONE, new Particle.DustOptions(Color.PURPLE, 1)); // Purple redstone particle
 *
 * World world = Bukkit.getWorld("myWorld");
 * Location corner1 = new Location(world, 10, 10, 10);
 * Location corner2 = new Location(world, 20, 20, 20);
 * BoundingBox box = BoundingBox.of(corner1, corner2);
 *
 * Visualizer visualizer = new Visualizer(boundingBox, particleBuilder, world);
 * visualizer.runTaskTimer(Core.getInstance(), 1, 0); // Run constantly
 * // You can stop it with visualizer.cancel();
 * }
 * </pre>
 */
public class Visualizer extends BukkitRunnable {
    private final BoundingBox bb;
    private final ParticleBuilder particleBuilder;
    private final World world;

    /**
     * Creates a Visualizer object
     * @param bb Bounding box the visualizer will display
     * @param particleBuilder The particle that will outline the bounding box
     * @param world The world that the visualizer will display in
     */
    public Visualizer(BoundingBox bb, ParticleBuilder particleBuilder, World world) {
        this.bb = bb;
        this.particleBuilder = particleBuilder;
        this.world = world;
    }

    /**
     * @see BukkitRunnable#runTaskTimer
     */
    @Override
    public void run() {
        wireframe(new Location(world, bb.getMinX(), bb.getMinY(), bb.getMinZ()), new Location(world, bb.getMaxX(), bb.getMinY(), bb.getMaxZ()));
        wireframe(new Location(world, bb.getMaxX(), bb.getMaxY(), bb.getMaxZ()), new Location(world, bb.getMinX(), bb.getMaxY(), bb.getMinZ()));

        wireframe(new Location(world, bb.getMaxX(), bb.getMaxY(), bb.getMinZ()), new Location(world, bb.getMinX(), bb.getMinY(), bb.getMinZ()));
        wireframe(new Location(world, bb.getMinX(), bb.getMaxY(), bb.getMinZ()), new Location(world, bb.getMinX(), bb.getMinY(), bb.getMaxZ()));

        wireframe(new Location(world, bb.getMinX(), bb.getMaxY(), bb.getMaxZ()), new Location(world, bb.getMaxX(), bb.getMinY(), bb.getMaxZ()));
        wireframe(new Location(world, bb.getMaxX(), bb.getMaxY(), bb.getMinZ()), new Location(world, bb.getMaxX(), bb.getMinY(), bb.getMaxZ()));
    }

    /**
     * Simulates a wireframe between two corners with particles.
     *
     * @param start The starting location
     * @param end The destination location
     */
    private void wireframe(Location start, Location end) {
        BoundingBox box = new BoundingBox(start.getX(), start.getY(),  start.getZ(), end.getX(), end.getY(), end.getZ());
        for (double x = box.getMinX(); x <= box.getMaxX(); x++) for (double y = box.getMinY(); y <= box.getMaxY(); y++) for (double z = box.getMinZ(); z <= box.getMaxZ(); z++) {
            Location loc = new Location(world, x, y, z);
            if (particleBuilder.shouldUseData()) {
                world.spawnParticle(particleBuilder.particle, loc, 0, particleBuilder.data);
            } else {
                world.spawnParticle(particleBuilder.particle, loc, 0);
            }
        }
    }
}
