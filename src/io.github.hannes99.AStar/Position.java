package io.github.hannes99.AStar;



public class Position {
    private double x,y;


    public Position(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double getDistTo(Position to){
        double dX = Math.abs(x - to.getX());
        double dY = Math.abs(y - to.getY());
        return Math.sqrt(dX*dX+dY*dY);
    }

    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
}
