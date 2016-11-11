package io.github.hannes99.world;

import io.github.hannes99.a_star.Connection;
import io.github.hannes99.a_star.Node;

import javax.vecmath.Point3d;

/**
 * A node in 3d space
 */
public class Node3d extends Node {
    // Position
    final Point3d position = new Point3d();
    // Extra
    Object extra; // Can be used to store data

    /**
     * Creates a new node
     *
     * @param x The X coordinate
     * @param y The Y coordinate
     * @param z The Z coordinate
     */
    public Node3d(double x, double y, double z) {
        position.set(x, y, z);

    }

    /**
     * @return The position
     */
    public Point3d getPosition() {
        return position;
    }

    /**
     * Sets the distance to the target node as H
     *
     * @param target The target node
     */
    public void setH(Node3d target) {
        setH(target.getPosition().distance(position));
    }

    /**
     * @return User data
     */
    public Object getExtra() {
        return extra;
    }

    /**
     * Can be used to store data related to this node.
     *
     * @param extra User data
     */
    public void setExtra(Object extra) {
        this.extra = extra;
    }

    /**
     * Connects to a node and sets the distance as value
     *
     * @param target The target node
     */
    public Connection connectTo(Node3d target) {
        return connectTo(target, target.getPosition().distance(position));
    }

}