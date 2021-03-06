package xyz.fxcilities.core.particle;

import org.bukkit.Particle;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** Represents a particle with particle data includes */
public class ParticleBuilder {
    public Particle particle;
    public Object data;

    /**
     * @param particle The particle
     * @param data The particle data
     */
    public ParticleBuilder(@Nonnull Particle particle, @Nullable Object data) {
        this.particle = particle;
        this.data = data;
    }

    /**
     * @param particle The particle
     */
    public ParticleBuilder(@Nonnull Particle particle) {
        this(particle, null);
    }

    /**
     * @return if the particle needs to use its custom data
     */
    public boolean shouldUseData() {
        return data == null || particle.getDataType() != Void.class;
    }
}
