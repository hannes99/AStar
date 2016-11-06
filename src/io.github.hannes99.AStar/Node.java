package io.github.hannes99.AStar;

import java.util.ArrayList;

/**
 * Created by hannes on 04.11.16.
 */
public class Node {
    private Position pos; //TODO vll a klasse Point odr Position?
    private ArrayList<Node> connected = new ArrayList<Node>();
    private Node prev;
    private boolean start = false;
    private double g;
    private Node target;

    public Node(Position p){
        pos = p;
    }

    public void setStart(){
        start = true;
        g = 0;
    }

    public void setTarget(Node t){
        target = t;
    }

    public void setPrevious(Node p){
        prev = p;
        if(start)
            g = 0;
        else
            g = prev.getG()+prev.getPosition().getDistTo(pos);
    }

    public Position getPosition(){
        return pos;
    }

    public ArrayList<Node> getConnections(){
        return connected;
    }

    public void addConnectioTo(Node to){
        connected.add(to);
    }

    public double getF(){
        return getG()+getH();
    }

    public double getG(){
        if(!start)
            return g + prev.getPosition().getDistTo(pos);
        return g;
    }
    private double getH(Node to){
        return pos.getDistTo(to.getPosition());
    }

    public double getH(){
        return pos.getDistTo(target.getPosition());
    }
}
