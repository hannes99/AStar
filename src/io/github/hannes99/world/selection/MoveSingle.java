package io.github.hannes99.world.selection;

import io.github.hannes99.a_star.Connection;
import io.github.hannes99.a_star.Node;
import io.github.hannes99.world.Node3d;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Created by hanne on 21.11.2016.
 */
public class MoveSingle extends SingleSelection{

    private Node3d node = null;


    @Override
    public void mouseClicked(MouseEvent e){
        if(node==null) {
            node = getSelectedNodes().get(0);

        }else
            node = null;
    }

    @Override
    public void mouseMoved(MouseEvent e){
        System.out.println("moovee");
        if(node!=null) {
            for(Node n:node.getConnectionsFrom()){
                for(Connection c:n.getConnectionsTo())
                    if(c.getNode()==node) {
                        n.getConnectionsTo().remove(c);
                        break;
                    }
            }
            node.getConnectionsTo().clear();
            ArrayList<Node3d> ret = new ArrayList<Node3d>();
            double radiusSquared = 300;
            for (Node3d n : worldRenderer.getWorld().getAllNodes()) {
                if (n.getPosition().distanceSquared(node.getPosition()) <= radiusSquared) {
                    node.connectTo(n);
                    n.connectTo(node);
                }
            }
            System.out.println("mit node");
            node.getPosition().setX(e.getX());
            node.getPosition().setY(e.getY());
            worldRenderer.repaint();
        }
    }
}
