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

    public Node getOtherNode(Node n){
        Node ret = null;
        if(n==a)
            ret = b;
        else if (n==b)
            ret = a;
        return ret;
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

    public boolean contains(Node n) {
        return a == n || b == n;
    }


}

