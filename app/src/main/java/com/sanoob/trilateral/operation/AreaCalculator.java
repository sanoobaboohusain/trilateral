package com.sanoob.trilateral.operation;



import java.util.List;

public class AreaCalculator {

    private static double calculate(double sideA, double sideB, double sideC) {

        double s = (sideA + sideB + sideC)/2;
        double a =  (s * (s-sideA) * (s-sideB) * (s-sideC));
        try{
            return Math.sqrt(a);
        }catch (Exception exception){
            return Double.NaN;
        }
    }

    public static double calculate(TriangleSides sides){
        return calculate(sides.getSideA(), sides.getSideB(), sides.getSideC());
    }


    public static double totalArea(List<TrianglesModel> triangles){

        double sum = 0.0;
        for (TrianglesModel triangle:
             triangles) {
            sum += triangle.getArea();
        }
        return sum;
    }

    public static double totalAreaInCents(double area){
        return (area/40.47);
    }
}
