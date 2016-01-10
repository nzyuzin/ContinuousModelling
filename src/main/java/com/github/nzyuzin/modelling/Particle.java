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

    public Particle(BoundedArea area) {
        this.coordinate = new Coordinate(StdRandom.uniform(0.0, 1.0), StdRandom.uniform(0.0, 1.0));
        while (!area.insideArea(this)) {
            this.coordinate.x = StdRandom.uniform(0.0, 1.0);
            this.coordinate.y = StdRandom.uniform(0.0, 1.0);
        }
        vx = StdRandom.uniform(-.005, 0.005);
        vy = StdRandom.uniform(-.005, 0.005);
        radius = 0.01;
        mass = 0.5;
        color = Color.BLACK;
    }

    public void move(BoundedArea area, double dt) {
        this.coordinate.x += vx / mass * dt;
        this.coordinate.y += vy / mass * dt;
        if (!area.insideArea(this)) {
            this.coordinate = area.coordinateOnOppositeBound(this);
        }
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

    public double mass() {
        return mass;
    }

    public Color color() {
        return color;
    }
}
