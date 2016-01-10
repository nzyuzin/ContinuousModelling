package com.github.nzyuzin.modelling;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import edu.princeton.cs.algs4.StdIn;

import java.awt.Color;

public class Main {
    @Parameter(names = "-n", description = "Number of particles in simulation")
    private Integer numberOfParticles;

    @Parameter(names = "-r", description = "Read particles from stdin")
    private boolean readFromStdIn;

    @Parameter(names = "--help", help = true, description = "Show usage and exit")
    private boolean help;

    private static final Integer DEFAULT_NUMBER_OF_PARTICLES = 15;

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
        final Particle[] particles;
        if (readFromStdIn) {
            particles = readFromStdIn();
        } else {
            particles = new Particle[numberOfParticles == null ? DEFAULT_NUMBER_OF_PARTICLES : numberOfParticles];
        }
        BoundedArea area = new BoundedArea(-2.0, 2.0, -2.0, 2.0);
        for (int i = 0; i < particles.length; i++) {
            particles[i] = new Particle(area);
        }
        LennardJonesPotentialSimulation simulation =
                new LennardJonesPotentialSimulation(particles, new SimulationWindow(area), area);
        simulation.start();
    }

    private static Particle[] readFromStdIn() {
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
