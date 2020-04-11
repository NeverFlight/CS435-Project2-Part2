package com.cs435.part2;

import java.util.HashSet;
import java.util.LinkedList;

public class DirectedGraph {
    LinkedList<Node> graph = new LinkedList<>();
    public void addNode(final int nodeVal){
        graph.add(new Node(nodeVal));
    }
    public void addDirectedEdge(final Node first, final Node second){
        first.adj.put(second, 0);
    }
    public void removeDirectedEdge(final Node first, final Node second){
        first.adj.remove(second);
    }
    public HashSet<Node> getAllNodes(){
        return new HashSet<>(graph);
    }
}
