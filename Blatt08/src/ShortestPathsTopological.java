import java.util.Stack;

public class ShortestPathsTopological {
    private int[] parent;
    private int s;
    private double[] dist;

    /**
     * Constructor
     * Finds shortest paths in G starting in s. Saves result in the instance attribute parent.
     *
     * @param G Graph G
     * @param s start node s
     */
    public ShortestPathsTopological(WeightedDigraph G, int s) {
        // TODO
        parent = new int[G.V()];
        this.s= s;
        dist = new double[G.V()];
        TopologicalWD g = new TopologicalWD(G);
        g.dfs(s);

        for (int i =0; i<G.V(); i++){
            dist[i]=Double.POSITIVE_INFINITY;
        }
        dist[s]=0;
//        parent[s]=s;
        while (!g.order().isEmpty()){
            int v = g.order().pop();
            for (DirectedEdge e:G.incident(v)){
                relax(e);
            }
        }
    }

    /**
     * Implements the relaxation of en edge.
     * For further information see subsection on relaxation in the script.
     *
     * @param e edge that will be relaxed
     */
    public void relax(DirectedEdge e) {
        // TODO
        int v = e.from();
        int w = e.to();
        if (dist[w]>dist[v]+e.weight()){
            dist[w]=dist[v]+e.weight();
            parent[w]=v;
        }
    }

    /**
     * @param v node
     * @return whether the node already has a path to it
     */
    public boolean hasPathTo(int v) {
        return parent[v] >= 0;
    }

    /**
     * @param v node
     * @return path to node from start node s
     */
    public Stack<Integer> pathTo(int v) {
        if (!hasPathTo(v)) {
            return null;
        }
        Stack<Integer> path = new Stack<>();
        for (int w = v; w != s; w = parent[w]) {
            path.push(w);
        }
        path.push(s);
        return path;
    }
}

