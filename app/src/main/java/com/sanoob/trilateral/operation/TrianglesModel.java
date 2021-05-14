package com.sanoob.trilateral.operation;

import java.io.Serializable;

public class TrianglesModel implements Serializable {

    private double area;
    TriangleSides sides;

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public TriangleSides getSides() {
        return sides;
    }

    public void setSides(TriangleSides sides) {
        this.sides = sides;
    }

}
