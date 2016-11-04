package io.github.hannes99.AStar;

import java.util.ArrayList;

/**
 * Created by hannes on 04.11.16.
 */
public class Node {
    private double x,y; //TODO vll a klasse Point odr Position?
    private ArrayList<Node> connected = new ArrayList<Node>();
    public static ArrayList<Node> nodeList = new ArrayList<Node>();

    public Node(double x, double y){
        this.x = x;
        this.y = y;
        nodeList.add(this);
    }

    public void addConnectioTo(Node to){
        connected.add(to);
    }

    public double getF(Node to){
        if(connected.contains(to)){
           //TODO F-Wert zu to ausrechnen
            return 0;
        }else
            return Double.NaN;
    }
    public double getH(Node to){
        if(connected.contains(to)){
            //TODO H-Wert zu to ausrechnen
            return 0;
        }else
            return Double.NaN;
    }
}
