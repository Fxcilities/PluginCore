package xyz.fxcilities.core.particle;

import org.bukkit.Particle;

public class ParticleBuilder {
    public Particle particle;
    public Object data;

    public ParticleBuilder(Particle particle, Object data) {
        this.particle = particle;
        this.data = data;
    }

    public boolean shouldUseData() {
        return particle.getDataType() != Void.class;
    }
}
