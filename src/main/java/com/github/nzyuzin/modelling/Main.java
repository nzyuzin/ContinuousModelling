package com.github.nzyuzin.modelling;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import edu.princeton.cs.algs4.StdIn;

import java.awt.Color;
import java.util.List;

public class Main {
    @Parameter(names = "--random-particles", description = "Number of random particles in simulation. " +
            "Doesn't work with --stdin")
    private int randomParticles = 7;

    @Parameter(names = "--stdin", description = "Read particles from stdin. Particles should be in the following " +
            "format: \"%f %f %f %f %f %f %d %d %d\" corresponding to (rx, ry, vx, vy, radius, mass, r, g, b)")
    private boolean readFromStdIn;

    @Parameter(names = "--help", help = true, description = "Show usage and exit")
    private boolean help;

    @Parameter(names = "--affecting-distance",
            description = "Maximum distance in which one particle can affect another")
    private Double affectionDistance;

    @Parameter(names = "--sigma", description = "Value of sigma in Lennard-Jones Potential computation")
    private double sigma = 0.07;

    @Parameter(names = "--epsilon", description = "Value of epsilon in Lennard-Jones Potential computation")
    private double epsilon = 0.000001;

    @Parameter(names = "--particle-radius", description = "Radius of one particle")
    private double particleRadius = 0.03;

    public static void main(String[] args) {
        Main main = new Main();
        JCommander jCommander = new JCommander(main, args);
        if (main.help) {
            jCommander.usage();
        } else {
            main.run();
        }
    }

    public void run() {
        Preconditions.checkArgument(affectionDistance == null || affectionDistance >= 0.0,
                "Affection distance can't be negative");
        Preconditions.checkArgument(randomParticles >= 0.0,
                "Number of particles can't be negative");
        final BoundedArea area = new BoundedArea(-2.0, 2.0, -2.0, 2.0);
        final List<Particle> particles;
        if (readFromStdIn) {
            particles = particlesFromStdIn();
        } else {
            // Use random particles
            particles = Lists.newArrayListWithCapacity(randomParticles);
            for (int i = 0; i < randomParticles; i++) {
                particles.add(new Particle(area, particleRadius));
            }
        }
        final LennardJonesPotentialSimulation simulation =
                new LennardJonesPotentialSimulation(particles, new SimulationWindow(area), area,
                        affectionDistance, sigma, epsilon);
        simulation.start();
    }

    private static List<Particle> particlesFromStdIn() {
        final List<Particle> result = Lists.newArrayList();
        while (StdIn.hasNextChar()) {
            double rx = StdIn.readDouble();
            double ry = StdIn.readDouble();
            double vx = StdIn.readDouble();
            double vy = StdIn.readDouble();
            double radius = StdIn.readDouble();
            double mass = StdIn.readDouble();
            int r = StdIn.readInt();
            int g = StdIn.readInt();
            int b = StdIn.readInt();
            char c = StdIn.readChar();
            while (c == ' ' || c == '\t') {
                c = StdIn.readChar();
            }
            if (c != '\n') {
                throw new IllegalArgumentException("Wrong input format: particles should be on separate lines " +
                        "with last symbol terminated by '\n'");
            }
            Color color   = new Color(r, g, b);
            result.add(new Particle(rx, ry, vx, vy, radius, mass, color));
        }
        return result;
    }
}
