package com.github.nzyuzin.modelling;

public class LennardJonesPotentialSimulation {
    private final Particle[] particles;
    private final SimulationWindow view;
    private final BoundedArea area;

    public LennardJonesPotentialSimulation(Particle[] particles, SimulationWindow view, BoundedArea area) {
        this.particles = particles;
        this.view = view;
        this.area = area;
    }

    public void start() {
        view.draw(area, particles);
        for (int i = 0; i < 10000; i++) {
            nextTick();
            view.draw(area, particles);
        }
    }

    public void nextTick() {
        for (Particle particle : particles) {
            particle.move(area, 1);
        }
    }
}
