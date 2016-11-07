package io.github.hannes99.AStar;


/**
 * Created by hannes on 07.11.16.
 */
public class Connection {
    private double cost;
    private Node a, b;

    public Node getA(){
        return a;
    }

    public Node getConnectedTo(Node n){
        Node
        if(n==a)
            return b;
        if(n==b)
            return a;
    }

    public Node getB(){
        return b;
    }

    public double getCost(){
        return cost;
    }

    public Connection(Node a, Node b){
        this.a = a;
        this.b = b;
        cost = a.getPosition().getDistTo(a.getPosition());
        if(!b.isConnectedTo(a))
            b.addConnection(this);
    }




}

