package com.fj_ramirez.pointSetUtility;

import java.awt.geom.Point2D;
import java.util.Comparator;

public class PointComparator implements Comparator<Point2D> {

    private Point2D vertex;

    public PointComparator() {
        vertex = new Point2D.Double(0.0, 0.0);
    }

    public PointComparator(Point2D vertex){
        this.vertex = vertex;
    }

    @Override
    public int compare(Point2D o1, Point2D o2) {
        int result = 0;
        double polarAngle = getPolarAngle(vertex, o1, o2);

        if(polarAngle > 0) result = 1;
        else if(polarAngle < 0) result = -1;
        else if(polarAngle == 0){
            double point1Distance = vertex.distance(o1);
            double point2Distance = vertex.distance(o2);
            if(point1Distance > point2Distance) result = 1;
            else if(point2Distance > point1Distance) result = -1;
        }
        return result;
    }

    public static double getPolarAngle(Point2D p0, Point2D p1, Point2D p2) {
        return (p2.getX() - p0.getX()) * (p1.getY() - p0.getY()) - (p1.getX() - p0.getX()) * (p2.getY() - p0.getY());
    }
}
