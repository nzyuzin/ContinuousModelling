package com.github.nzyuzin.modelling;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineSegment;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import edu.princeton.cs.algs4.StdRandom;

public class BoundedArea {
    private final double lx;
    private final double rx;
    private final double hy;
    private final double ly;

    private final Coordinate leftmostPoint = new Coordinate(-1.0, 0.0);
    private final Coordinate topPoint = new Coordinate(0.0, 1.0);
    private final Coordinate rightmostPoint = new Coordinate(1.0, -1.0);
    private final Coordinate bottomPoint = new Coordinate(0.0, -2.0);

    private final LineSegment bottomLeftBound = new LineSegment(leftmostPoint, bottomPoint);
    private final LineSegment bottomRightBound = new LineSegment(bottomPoint, rightmostPoint);
    private final LineSegment topLeftBound = new LineSegment(leftmostPoint, topPoint);
    private final LineSegment topRightBound = new LineSegment(topPoint, rightmostPoint);
    private final LineSegment[] allBounds = new LineSegment[]
            {bottomLeftBound, bottomRightBound, topLeftBound, topRightBound};

    private final Polygon polygon;
    private final GeometryFactory geometryFactory = new GeometryFactory();

    public BoundedArea(double lx, double rx, double ly, double hy) {
        this.lx = lx;
        this.rx = rx;
        this.hy = hy;
        this.ly = ly;
        this.polygon = geometryFactory.createPolygon(geometryFactory.createLinearRing(new Coordinate[]{
                leftmostPoint, topPoint, rightmostPoint, bottomPoint, leftmostPoint
        }), null);
    }

    public double[] xPoints() {
        return new double[]{leftmostPoint.x, topPoint.x, rightmostPoint.x, bottomPoint.x};
    }

    public double[] yPoints() {
        return new double[]{leftmostPoint.y, topPoint.y, rightmostPoint.y, bottomPoint.y};
    }

    public double width() {
        return rx - lx;
    }

    public double height() {
        return hy - ly;
    }

    public Coordinate randomCoordinateInside() {
        Coordinate coordinate = new Coordinate();
        do {
            coordinate.x = StdRandom.uniform(lx, rx);
            coordinate.y = StdRandom.uniform(ly, hy);
        } while (!insideArea(coordinate));
        return coordinate;
    }

    public boolean insideArea(final Particle particle) {
        return insideArea(new Coordinate(particle.x(), particle.y()));
    }

    private boolean insideArea(final Coordinate coordinate) {
        return polygon.contains(geometryFactory.createPoint(coordinate));
    }

    public boolean nearBound(final Particle particle) {
        final Point point = geometryFactory.createPoint(new Coordinate(particle.x(), particle.y()));
        return polygon.getBoundary().isWithinDistance(point, particle.radius());
    }

    public double distanceToBounds(Particle particle) {
        final Point point = geometryFactory.createPoint(new Coordinate(particle.x(), particle.y()));
        return polygon.getBoundary().distance(point);
    }

    public Coordinate coordinateOnOppositeBound(Particle particle) {
        final Coordinate particleCoordinate = new Coordinate(particle.x(), particle.y());
        final LineSegment closestBound = closestBound(particleCoordinate);
        final LineSegment oppositeBound = oppositeLine(closestBound);
        final double segmentFraction = closestBound.segmentFraction(particleCoordinate);
        return oppositeBound.pointAlong(segmentFraction);
    }

    private LineSegment closestBound(final Coordinate coordinate) {
        double minDistance = Integer.MAX_VALUE;
        LineSegment result = null;
        for (final LineSegment line : allBounds) {
            final double currentDistance = line.distance(coordinate);
            if (currentDistance < minDistance) {
                minDistance = currentDistance;
                result = line;
            }
        }
        return result;
    }

    private LineSegment oppositeLine(final LineSegment line) {
        if (line == bottomLeftBound) {
            return topRightBound;
        } else if (line == bottomRightBound) {
            return topLeftBound;
        } else if (line == topRightBound) {
            return bottomLeftBound;
        } else if (line == topLeftBound) {
            return bottomRightBound;
        } else {
            throw new IllegalArgumentException("Unexpected line -- " + line);
        }
    }
}
