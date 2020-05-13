package com.fjramirez.pointSetUtility;

import java.util.*;
import java.awt.geom.Point2D;

public class ConvexHull {

    List<Point2D> hull;

    public List<Point2D> getHull() {
        return hull;
    }

    public ConvexHull(List<Point2D> pointSet){
        this.hull = getConvexHull(pointSet);
    }

    public Double getMaxDistance(){
        return getMaxDistance(1);
    }

    public Double getMaxDistance(double factor) {
        if(hull.size() <= 1) return null;
        if(hull.size() == 2) return hull.get(0).distance(hull.get(1));
        Double maxDistance = 0.0;
        List<Point2D> points = getConvexHull(hull);
        for(int i = 0; i < points.size() - 1; i++){
            for(int j = i+1; j < points.size(); j++){
                Double distance = points.get(i).distance(points.get(j));
                maxDistance = (distance > maxDistance) ? distance : maxDistance;
            }
        }
        return maxDistance * factor;
    }

    private List<Point2D> getConvexHull(List<Point2D> pointSet){
        LinkedList<Point2D> points = new LinkedList<>(pointSet);
        Point2D vertex = new Point2D.Double(Double.MAX_VALUE, Double.MAX_VALUE);

        int vertexIndex = -1;

        for(int i = 0; i < points.size(); i++){
            if(points.get(i).getY() < vertex.getY()){
                vertex = points.get(i);
                vertexIndex = i;
            }
            else if(points.get(i).getY() == vertex.getY()){
                if(points.get(i).getX() < vertex.getX()){
                    vertex = points.get(i);
                    vertexIndex = i;
                }
            }
        }

        points.remove(vertexIndex);
        points.addFirst(vertex);
        points.sort(new PointComparator(vertex));


        return grahamScan(points);
    }

    private List<Point2D> grahamScan(List<Point2D> pointSet){
        if(pointSet.size() < 3) return null;

        Stack<Point2D> convexHull = new Stack<>();

        convexHull.push(pointSet.get(0));
        convexHull.push(pointSet.get(1));

        for(int i = 2; i < pointSet.size(); i++){
            convexHull.push(pointSet.get(i));
            double polarAngle = PointComparator.getPolarAngle(convexHull.get(convexHull.size()-3), convexHull.get(convexHull.size()-2), convexHull.get(convexHull.size()-1));
            while(polarAngle >= 0){
                Point2D top = convexHull.pop();
                convexHull.pop();
                convexHull.push(top);
                polarAngle = PointComparator.getPolarAngle(convexHull.get(convexHull.size()-3), convexHull.get(convexHull.size()-2), convexHull.get(convexHull.size()-1));
            }
        }

        return convexHull;
    }
}
