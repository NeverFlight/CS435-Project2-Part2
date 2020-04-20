package com.cs435.part2;

import java.util.HashSet;
import java.util.LinkedList;

public class DirectedGraph {
    LinkedList<Node> graph = new LinkedList<>();
    public void addNode(final int nodeVal){
        graph.add(new Node(nodeVal));
    }
    public void addDirectedEdge(final Node first, final Node second){
        if(first != null && second != null)
            first.adj.put(second, 0);
        else System.out.println("Node doesn't exist!");
    }
    public void removeDirectedEdge(final Node first, final Node second){
        if(first != null && second != null)
            first.adj.remove(second);
        else System.out.println("Node doesn't exist!");
    }
    public HashSet<Node> getAllNodes(){
        return new HashSet<>(graph);
    }
}
