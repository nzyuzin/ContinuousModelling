package com.github.nzyuzin.modelling;

import java.util.List;

import static java.lang.Math.pow;

public class LennardJonesPotentialSimulation {
    private final List<Particle> particles;
    private final SimulationWindow view;
    private final BoundedArea area;
    private final Double affectionDistance;
    private final Double sigma;
    private final Double epsilon;

    public LennardJonesPotentialSimulation(List<Particle> particles,
                                           SimulationWindow view,
                                           BoundedArea area,
                                           Double affectionDistance,
                                           Double sigma,
                                           Double epsilon) {
        this.particles = particles;
        this.view = view;
        this.area = area;
        this.affectionDistance = affectionDistance;
        this.sigma = sigma;
        this.epsilon = epsilon;
    }

    public void start() {
        view.draw(particles);
        while (true) {
            nextTick();
            view.draw(particles);
        }
    }

    public void nextTick() {
        final double dt = 0.001;
        for (int i = 0; i < 300; i++) {
            for (Particle particle : particles) {
                particle.move(area, dt);
                for (Particle closeParticle : particles) {
                    if (closeParticle == particle
                            || (affectionDistance != null && particle.distanceTo(closeParticle) > affectionDistance)) {
                        // either the same particle or far particle
                        continue;
                    }
                    final double dx = particle.x() - closeParticle.x();
                    final double dy = particle.y() - closeParticle.y();
                    particle.updateVelocity(-lennardJonesPotential(dx, dy), -lennardJonesPotential(dy, dx), dt);
                }
            }
        }
    }

    private double lennardJonesPotential(double dx, double dy) {
        double distance = dx * dx + dy * dy;
        if (distance < 0.001) {
            distance = 0.001;
        }
        final double sixSigma = 6 * pow(sigma, 6) / pow(distance, 4);
        final double twelveSigma = -12 * pow(sigma, 12) / pow(distance, 7);
        return 4 * epsilon * dx * (twelveSigma + sixSigma);
    }
}
