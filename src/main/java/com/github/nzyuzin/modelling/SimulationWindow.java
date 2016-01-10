package com.github.nzyuzin.modelling;

import edu.princeton.cs.algs4.StdDraw;

import java.awt.Color;
import java.util.List;

public class SimulationWindow {
    private final BoundedArea area;

    public SimulationWindow(BoundedArea area) {
        this.area = area;
        StdDraw.setCanvasSize(800, 800);
        StdDraw.setScale(-area.height() / 2, area.height() / 2);
        StdDraw.show(0);
    }

    public void draw(List<Particle> particles) {
        StdDraw.clear();
        drawAxis();
        for (Particle particle : particles) {
            drawParticle(particle);
        }
        drawArea();
        StdDraw.show(20);
    }

    private void drawArea() {
        StdDraw.polygon(area.xPoints(), area.yPoints());
    }

    private void drawAxis() {
        final Color previousColor = StdDraw.getPenColor();
        StdDraw.setPenColor(Color.LIGHT_GRAY);
        StdDraw.line(-area.width() / 2, 0, area.width() / 2, 0);
        StdDraw.line(0, -area.height() / 2, 0, area.height() / 2);
        StdDraw.setPenColor(previousColor);
    }

    private void drawParticle(final Particle particle) {
        final Color previousColor = StdDraw.getPenColor();
        StdDraw.setPenColor(particle.color());
        StdDraw.filledCircle(particle.x(), particle.y(), particle.radius());
        StdDraw.setPenColor(previousColor);
    }
}
