package com.cs435.part2;

import java.util.HashSet;
import java.util.LinkedList;

public class WeightedGraph {
    LinkedList<Node> graph = new LinkedList<>();

    public void addNode(final int nodeVal){
        graph.add(new Node(nodeVal));
    }

    public void addWeightedEdge(final Node first, final Node second, final int edgeWeight){
        first.adj.put(second, edgeWeight);
    }

    public void removeDirectdEdge(final Node first, final Node second){
        first.adj.remove(second);
    }

    public HashSet<Node> getAllNodes(){
        return new HashSet<>(graph);
    }

}
