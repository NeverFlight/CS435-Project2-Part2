package com.cs435.part2;

import java.util.HashMap;
import java.util.Map;

public class Node {
    Map<Node, Integer> adj;
    int val;
    public Node(int val){
        this.val = val;
        adj = new HashMap<>();
    }
}
