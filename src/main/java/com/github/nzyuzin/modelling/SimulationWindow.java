package com.github.nzyuzin.modelling;

import com.google.common.collect.Maps;
import com.vividsolutions.jts.geom.Coordinate;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Map;

public class SimulationWindow {
    private final BoundedArea area;
    private final Map<Particle, Deque<Coordinate>> previousCoordinates;

    public SimulationWindow(BoundedArea area) {
        this.area = area;
        this.previousCoordinates = Maps.newHashMap();
        StdDraw.setCanvasSize(800, 800);
        StdDraw.setScale(-area.height() / 2, area.height() / 2);
        StdDraw.show(0);
    }

    public void draw(List<Particle> particles) {
        StdDraw.clear();
        drawAxis();
        for (Particle particle : particles) {
            drawParticle(particle);
            if (previousCoordinates.containsKey(particle)) {
                Deque<Coordinate> deque = previousCoordinates.get(particle);
                if (deque.size() == 2500) {
                    deque.pollLast();
                }
                deque.addFirst(new Coordinate(particle.coordinate()));
                drawTrail(particle);
            } else {
                Deque<Coordinate> newStack = new ArrayDeque<>(particles.size());
                newStack.push(new Coordinate(particle.coordinate()));
                previousCoordinates.put(particle, newStack);
            }
        }
        drawArea();
        StdDraw.show(20);
    }

    private void drawTrail(final Particle particle) {
        final Color previousColor = StdDraw.getPenColor();
        StdDraw.setPenColor(particle.color());
        for (final Coordinate position : previousCoordinates.get(particle)) {
            StdDraw.filledCircle(position.x, position.y, 0.001);
        }
        StdDraw.setPenColor(previousColor);
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
