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

    public AStar(ArrayList<Position> points, ArrayList<ArrayList<Position>> connections){
        for(Position p:points){
            nodeList.add(new Node(p));
        }
        for(Node n:nodeList){
            for(Node c:nodeList){
                if(c!=n);

            }
        }
        target = nodeList.get(5);
        nodeList.get(0).setStart();
        now=nodeList.get(0);
        start = now;
    }

    public Node getNext(){
        Node bestF = null;
        for(Connection n:now.getConnections()){

        }
        return bestF;
    }
}
