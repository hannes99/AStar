package io.github.hannes99.AStar;

import java.util.ArrayList;

/**
 * Created by hannes on 04.11.16.
 */
public class Node {
    private Position pos; //TODO vll a klasse Point odr Position?
    private ArrayList<Connection> connections = new ArrayList<Connection>();
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

    public ArrayList<Connection> getConnections(){
        return connections;
    }

    public void addConnectionTo(Node to){
        connections.add(new Connection(this, to));
    }

    public void addConnection(Connection c){
        connections.add(c);
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

    public boolean isConnectedTo(Node n){
        boolean ret = false;
        for (int i = 0; i < connections.size() && !ret; ++i) {
            ret = connections.get(i).contains(n);
        }
        return ret;
    }
}
