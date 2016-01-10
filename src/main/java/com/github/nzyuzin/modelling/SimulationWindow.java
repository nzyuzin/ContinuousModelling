package com.github.nzyuzin.modelling;

import edu.princeton.cs.algs4.StdDraw;

public class SimulationWindow {
    public SimulationWindow() {
        StdDraw.setCanvasSize(800, 800);
        StdDraw.show(0);
    }

    public void draw(BoundedArea area, Particle[] particles) {
        StdDraw.clear();
        for (Particle particle : particles) {
            drawParticle(particle);
        }
        drawArea(area);
        StdDraw.show(20);
    }

    private void drawArea(BoundedArea area) {
        StdDraw.polygon(area.xPoints(), area.yPoints());
    }

    private void drawParticle(final Particle particle) {
        StdDraw.setPenColor(particle.color());
        StdDraw.filledCircle(particle.x(), particle.y(), particle.radius());
    }
}
