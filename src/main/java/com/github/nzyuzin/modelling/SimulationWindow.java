package com.github.nzyuzin.modelling;

import edu.princeton.cs.algs4.StdDraw;

public class SimulationWindow {
    private final BoundedArea area;

    public SimulationWindow(BoundedArea area) {
        this.area = area;
        StdDraw.setCanvasSize(800, 800);
        StdDraw.setScale(-area.height() / 2, area.height() / 2);
        StdDraw.show(0);
    }

    public void draw(Particle[] particles) {
        StdDraw.clear();
        for (Particle particle : particles) {
            drawParticle(particle);
        }
        drawArea();
        drawAxis();
        StdDraw.show(20);
    }

    private void drawArea() {
        StdDraw.polygon(area.xPoints(), area.yPoints());
    }

    private void drawAxis() {
        StdDraw.line(-area.width() / 2, 0, area.width() / 2, 0);
        StdDraw.line(0, -area.height() / 2, 0, area.height() / 2);
    }

    private void drawParticle(final Particle particle) {
        StdDraw.setPenColor(particle.color());
        StdDraw.filledCircle(particle.x(), particle.y(), particle.radius());
    }
}
