package com.github.nzyuzin.modelling;

import com.vividsolutions.jts.geom.Coordinate;
import edu.princeton.cs.algs4.StdRandom;

import java.awt.Color;

public class Particle {
    // position
    private Coordinate coordinate;

    // velocity
    private double vx, vy;

    private final double radius;
    private final double mass;
    private final Color color;

    public Particle(double x, double y, double vx, double vy, double radius, double mass, Color color) {
        this.coordinate = new Coordinate(x, y);
        this.vx = vx;
        this.vy = vy;
        this.radius = radius;
        this.mass = mass;
        this.color = color;
    }

    public Particle(BoundedArea area, double radius) {
        this.coordinate = area.randomCoordinateInside();
        final double scale = area.height();
        this.vx = StdRandom.uniform(-.005 * scale, 0.005 * scale);
        this.vy = StdRandom.uniform(-.005 * scale, 0.005 * scale);
        this.radius = radius;
        this.mass = 1;
        this.color = Color.getHSBColor((float) StdRandom.uniform(0.0, 1.0),
                (float) StdRandom.uniform(0.5, 1.0),
                (float) StdRandom.uniform(0.5, 1.0));
    }

    public void updateVelocity(double dvx, double dvy, double dt) {
        this.vx += dvx * dt;
        this.vy += dvy * dt;
    }

    public void move(BoundedArea area, double dt) {
        this.coordinate.x += vx / mass * dt;
        this.coordinate.y += vy / mass * dt;
        if (!area.insideArea(this)) {
            this.coordinate = area.coordinateOnOppositeBound(this);
        }
    }

    public double distanceTo(Particle another) {
        return this.coordinate.distance(another.coordinate);
    }

    public double x() {
        return this.coordinate.x;
    }

    public double y() {
        return this.coordinate.y;
    }

    public double radius() {
        return radius;
    }

    public Color color() {
        return color;
    }

    Coordinate coordinate() {
        return coordinate;
    }

    @Override
    public String toString() {
        return String.format("[coord: (%.2f, %.2f), velocity: (%.2f,%.2f)]",
                this.coordinate.x, this.coordinate.y, this.vx, this. vy);
    }
}
