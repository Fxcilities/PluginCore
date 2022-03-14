package xyz.fxcilities.core.particle;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BoundingBox;

/**
 * @author Fxcilities
 * Visualize a BoundingBox with particles
 */
public class Visualizer extends BukkitRunnable {
    private final BoundingBox bb;
    private final ParticleBuilder particleBuilder;
    private final World world;


    public Visualizer(BoundingBox bb, ParticleBuilder particleBuilder, World world) {
        this.bb = bb;
        this.particleBuilder = particleBuilder;
        this.world = world;
    }


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
     * Simulates a wireframe with particles.
     *
     * @author Fxcilities
     * @param start The starting location
     * @param end The destination point
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
