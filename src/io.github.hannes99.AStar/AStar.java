package io.github.hannes99.AStar;

import java.util.ArrayList;

/**
 * Created by hannes on 06.11.16.
 */
public class AStar {
    private ArrayList<Node> nodeList = new ArrayList<Node>();
    private ArrayList<Node> way = new ArrayList<Node>();
    private Node now;
    private Node target, start;

    public AStar(ArrayList<Node> nodes, Node start, Node target){
        nodeList = nodes;
        setStart(start);
        setTarget(target);
    }

    public void setTarget(Node end){
        target = end;
    }

    public void setStart(Node start){
        this.start = start;
    }

    public Node getNext(){
        Connection best = null;
        for(Connection n:now.getConnections()){
            if(best==null)
                best=n;
            else if(n.getCost()<best.getCost())
                best=n;
        }
        return best.getConnectedTo(now);
    }
}
