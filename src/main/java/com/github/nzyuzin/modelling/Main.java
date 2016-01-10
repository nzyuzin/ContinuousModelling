package com.github.nzyuzin.modelling;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.internal.Lists;
import com.google.common.base.Preconditions;
import edu.princeton.cs.algs4.StdIn;

import java.awt.Color;

public class Main {
    @Parameter(names = "--particles", description = "Number of particles in simulation")
    private Integer numberOfParticles = 7;

    @Parameter(names = "--stdin", description = "Read particles from stdin")
    private boolean readFromStdIn;

    @Parameter(names = "--help", help = true, description = "Show usage and exit")
    private boolean help;

    @Parameter(names = "--affecting-distance",
            description = "Maximum distance in which one particle can affect another")
    private Double affectionDistance;

    @Parameter(names = "--sigma", description = "Value of sigma in Lennard-Jones Potential computation")
    private Double sigma = 0.05;

    @Parameter(names = "--epsilon", description = "Value of epsilon in Lennard-Jones Potential computation")
    private Double epsilon = 0.000001;

    @Parameter(names = "--particle-radius", description = "Radius of one particle")
    private Double particleRadius = 0.03;

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
        Preconditions.checkArgument(numberOfParticles == null || numberOfParticles >= 0.0,
                "Number of particles can't be negative");
        final Particle[] particles;
        if (readFromStdIn) {
            particles = particlesFromStdIn();
        } else {
            particles = new Particle[numberOfParticles];
        }
        BoundedArea area = new BoundedArea(-2.0, 2.0, -2.0, 2.0);
        for (int i = 0; i < particles.length; i++) {
            particles[i] = new Particle(area, particleRadius);
        }
        LennardJonesPotentialSimulation simulation =
                new LennardJonesPotentialSimulation(Lists.newArrayList(particles), new SimulationWindow(area), area,
                        affectionDistance, sigma, epsilon);
        simulation.start();
    }

    private static Particle[] particlesFromStdIn() {
        int N = StdIn.readInt();
        Particle[] result = new Particle[N];
        for (int i = 0; i < N; i++) {
            double rx = StdIn.readDouble();
            double ry = StdIn.readDouble();
            double vx = StdIn.readDouble();
            double vy = StdIn.readDouble();
            double radius = StdIn.readDouble();
            double mass = StdIn.readDouble();
            int r = StdIn.readInt();
            int g = StdIn.readInt();
            int b = StdIn.readInt();
            Color color   = new Color(r, g, b);
            result[i] = new Particle(rx, ry, vx, vy, radius, mass, color);
        }
        return result;
    }
}
