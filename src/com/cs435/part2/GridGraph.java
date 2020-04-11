package com.cs435.part2;

import javax.print.attribute.HashAttributeSet;
import java.util.HashSet;
import java.util.LinkedList;

public class GridGraph {
    LinkedList<GridNode> graph = new LinkedList<>();
    public void addGridNode(final int x, final int y, final int nodeVal){
        graph.add(new GridNode(nodeVal, x, y));
    }
    public void addUndirectedEdge(final GridNode first, final GridNode second){
        first.adj.add(second);
        second.adj.add(first);
    }
    public void removeUndirectedEdge(final GridNode first, final GridNode second){
        first.adj.remove(second);
        second.adj.remove(first);
    }
    public HashSet<GridNode> getAllNodes(){
        return new HashSet<>(graph);
    }
}
