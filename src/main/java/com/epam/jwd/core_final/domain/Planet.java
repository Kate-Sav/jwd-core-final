package com.epam.jwd.core_final.domain;

import java.util.Map;

/**
 * Expected fields:
 * <p>
 * location could be a simple class Point with 2 coordinates
 */
public class Planet extends AbstractBaseEntity {
    Point location;

    public Planet(String name, double x, double y) {
        super(name);
        location = new Point(x, y);
    }

    public class Point {
        private double x;
        private double y;
         Point(double x, double y){
            this.x = x;
            this.y = y;
        }

         public double getY() {
            return y;
        }
         public double getX() {
            return x;
        }
    }

    public Point getCoordinate() {
        return location;
    }

    @Override
    public Long getId() {
        return super.getId();
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public String toString() {
        return "Planet{" +
                "name=" + getName() +
                "," +
                "x=" + location.getX() +
                "," +
                "y=" + location.getY() +
                '}';
    }
}
