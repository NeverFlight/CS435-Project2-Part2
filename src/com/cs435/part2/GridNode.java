package com.cs435.part2;

import java.util.HashSet;
import java.util.Set;

public class GridNode {
    int val, x, y;
    Set<GridNode> adj;
    public GridNode(int val, int x, int y){
        this.val = val;
        this.x = x;
        this.y = y;
        this.adj = new HashSet<>();
    }
}
