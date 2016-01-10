package com.github.nzyuzin.modelling;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineSegment;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

public class BoundedArea {
    private final Coordinate leftmostPoint = new Coordinate(0.0, 0.5);
    private final Coordinate topPoint = new Coordinate(0.5, 0.75);
    private final Coordinate rightmostPoint = new Coordinate(0.75, 0.5);
    private final Coordinate bottomPoint = new Coordinate(0.25, 0.25);

    private final LineSegment bottomLeftBound = new LineSegment(leftmostPoint, bottomPoint);
    private final LineSegment bottomRightBound = new LineSegment(bottomPoint, rightmostPoint);
    private final LineSegment topLeftBound = new LineSegment(leftmostPoint, topPoint);
    private final LineSegment topRightBound = new LineSegment(topPoint, rightmostPoint);
    private final LineSegment[] allBounds = new LineSegment[]
            {bottomLeftBound, bottomRightBound, topLeftBound, topRightBound};

    private final Polygon polygon;
    private final GeometryFactory geometryFactory = new GeometryFactory();

    public BoundedArea() {
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

    public boolean insideArea(final Particle particle) {
        return polygon.contains(geometryFactory.createPoint(new Coordinate(particle.x(), particle.y())));
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
