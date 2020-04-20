package com.cs435.part2;

import java.util.*;

public class Main {
    static int dijkstraFinalize = 0, astarFinalize = 0;
    public static void main(String[] args) {
        Main run = new Main();
        // Test for Top sort
        DirectedGraph dg = run.createRandomDAGIter(20);
        for(Node node : dg.graph){
            for(Node adj : node.adj.keySet()){
                System.out.println("Node " + node.val + " has adj: " + adj.val);
            }
        }
        ArrayList<Node> ret = run.mDFS(dg);
        for(Node node : ret){
            System.out.println(node.val);
        }

        // Test for dijkstra
        WeightedGraph wg = run.createRandomCompleteWeightedGraph(10);

        HashMap<Node, Integer> distance = run.dijkstras(wg.graph.get(3));
        for(Node node : distance.keySet())
            System.out.println("Node " + node.val + " : " + distance.get(node));
        System.out.println("For dijkstra, the total number of finalize nodes is " + dijkstraFinalize);
        // test for A star
        GridGraph gg = run.createRandomGridGraph(10);
        GridNode source = gg.graph.get(0), dest = gg.graph.get(gg.graph.size() - 1);

        List<GridNode> retAstar = run.astar(source, dest);

        for(GridNode adjNode : retAstar){
            System.out.println(adjNode.val + " " + adjNode.x + " : " + adjNode.y);
        }
        System.out.println("For A star, the total number of finalize nodes is " + astarFinalize);
    }

    public DirectedGraph createRandomDAGIter(final int n){
        DirectedGraph dg = new DirectedGraph();
        Random rand = new Random();
        for(int i = 0; i < n;i++){
            dg.addNode(i);
        }

        for(int i = 0; i < n; i++){
            for(int j = i+1; j < n; j++){
                if(rand.nextBoolean()){
                    dg.addDirectedEdge(dg.graph.get(i), dg.graph.get(j));
                }
            }
        }

        return dg;
    }

    public ArrayList<Node> Kahns(final DirectedGraph graph){
        int n = graph.graph.size();
        ArrayList<Node> ret = new ArrayList<>();

        int[] indegree = new int[n];

        for(Node node : graph.graph){
            for(Node adj : node.adj.keySet()){
                indegree[adj.val]++;
            }
        }

        Queue<Node> q = new LinkedList<>();
        for(int i = 0; i < n; i++){
            Node curr = graph.graph.get(i);
            if(indegree[curr.val] == 0){
                ret.add(curr);
                q.offer(curr);
            }
        }
        while(!q.isEmpty()){
            Node curr = q.poll();
            for(Node adjNode : curr.adj.keySet()){
                if(--indegree[adjNode.val] == 0){
                    q.offer(adjNode);
                    ret.add(adjNode);
                }
            }
        }
        return ret;
    }

    public ArrayList<Node> mDFS(final DirectedGraph graph){
        Stack<Node> stk = new Stack<>();
        Set<Node> visited = new HashSet<>();
        for(Node node : graph.graph){
            if(!visited.contains(node)){
                mDFSHelper(node, visited, stk);
            }
        }
        ArrayList<Node> ret = new ArrayList<>(stk);
        Collections.reverse(ret);
        return ret;
    }

    public void mDFSHelper(Node curr, Set<Node> visited, Stack<Node> stk){
        visited.add(curr);
        for(Node adjNode : curr.adj.keySet()){
            if(!visited.contains(adjNode)){
                mDFSHelper(adjNode, visited, stk);
            }
        }
        stk.push(curr);
    }

    public WeightedGraph createRandomCompleteWeightedGraph(final int n){
        WeightedGraph wg = new WeightedGraph();
        Random rand = new Random();
        for(int i = 0; i < n;i++){
            wg.addNode(i);
        }

        for(int i = 0; i < n; i++){
            for(int j = i+1; j < n; j++){
                int randWeight = rand.nextInt(n);
                if(rand.nextBoolean()){
                    wg.addWeightedEdge(wg.graph.get(i), wg.graph.get(j), randWeight);
                }else{
                    wg.addWeightedEdge(wg.graph.get(j), wg.graph.get(i), randWeight);
                }
            }
        }

        return wg;
    }

    public WeightedGraph createLinkedList(final int n){
        WeightedGraph wg = new WeightedGraph();
        Random rand = new Random();
        for(int i = 0; i < n; i++) {
            wg.addNode(i);
        }
        Collections.shuffle(wg.graph);
        for(int i = 0; i < n - 1;i++){
            int randWeight = rand.nextInt(n);
            wg.addWeightedEdge(wg.graph.get(i), wg.graph.get(i+1), randWeight);
        }
        return wg;
    }

    public HashMap<Node, Integer> dijkstras(final Node start){
        HashMap<Node, Integer> ret = new HashMap<>();
        Set<Node> visited = new HashSet<>();
        Queue<Node> q = new PriorityQueue<>((a, b) -> ((ret.containsKey(a) ? ret.get(a) : 0) - (ret.containsKey(b) ? ret.get(b) : 0)));
        ret.put(start, 0);
        q.add(start);
        while(!q.isEmpty()) {
            Node curr = q.poll();
            for (Node adjNode : curr.adj.keySet()) {
                if(visited.contains(adjNode)) continue;
                q.offer(adjNode);
                int weight = curr.adj.get(adjNode);
                int prevWeight = ret.get(curr);
                if (!ret.containsKey(adjNode) || (weight + prevWeight < ret.get(adjNode))) {
                    ret.put(adjNode, weight + prevWeight);
                }
            }
            dijkstraFinalize++;
            visited.add(curr);
        }
        return ret;
    }

    public GridGraph createRandomGridGraph(int n){
        int[][] directions = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        GridGraph gg = new GridGraph();
        Random rand = new Random();
        int k = 0;
        for(int i = 0; i < n; i++){
            for(int j = 0 ; j < n; j++){
                gg.addGridNode(i, j, k++);
            }
        }
        for(int i = 0; i < gg.graph.size();i++){
            GridNode curr = gg.graph.get(i);
            for(int[] direction : directions){
                int newX = curr.x + direction[0];
                int newY = curr.y + direction[1];
                if(newX >= 0 && newX < n && newY >= 0 && newY < n){
                    if(rand.nextBoolean()){
                        GridNode newNode = gg.graph.get(newX * n  + newY);
                        gg.addUndirectedEdge(curr, newNode);
                    }
                }
            }
        }
        return gg;
    }

    // Heuristic Function will be Manhatton Distance:

    public ArrayList<GridNode> astar(final GridNode sourceNode, final GridNode destNode){
        Map<GridNode, Integer> distance = new HashMap<>();
        Set<GridNode> visited = new HashSet<>();
        ArrayList<GridNode> ret = new ArrayList<>();
        distance.put(sourceNode, H(sourceNode, destNode));
        int G = 0;
        GridNode curr = sourceNode;
        while(curr.val != destNode.val){
            ret.add(curr);
            GridNode next = curr;
            G++;
            int minF = Integer.MAX_VALUE;
            for(GridNode adjNode : curr.adj){
                if(visited.contains(adjNode)) continue;
                int F = G + H(adjNode,destNode);
                if(!distance.containsKey(adjNode) || (G < distance.get(adjNode))){
                    distance.put(adjNode, G);
                }
                if(F < minF){
                    minF = F;
                    next = adjNode;
                }
            }
            astarFinalize++;
            visited.add(curr);
            if(next.val == curr.val){
                System.out.println("Fail due to unconnected edge!");
                break;
            }
            curr = next;
        }
        return ret;
    }

    public int H(final GridNode sourceNode, final GridNode destNode){
        return Math.abs(sourceNode.x - destNode.x) + Math.abs(sourceNode.y - destNode.y);
    }
}
