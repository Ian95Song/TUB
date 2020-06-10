import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Class that represents a maze with N*N junctions.
 *
 * @author Vera Röhr
 */
public class Maze {
    private final int N;
    private final Graph M;    //Maze
    public int startnode;
    RandomDepthFirstPaths rdfp;
    int [] rdfpEdge;

    public Maze(int N, int startnode) {

        if (N < 0) throw new IllegalArgumentException("Number of vertices in a row must be nonnegative");
        this.N = N;
        this.M = new Graph(N * N);
        this.startnode = startnode;
        buildMaze();
    }

    public Maze(In in) {
        this.M = new Graph(in);
        this.N = (int) Math.sqrt(M.V());
        this.startnode = 0;
    }


    /**
     * Adds the undirected edge v-w to the graph M.
     *
     * @param v one vertex in the edge
     * @param w the other vertex in the edge
     * @throws IllegalArgumentException unless both {@code 0 <= v < V} and {@code 0 <= w < V}
     */
    public void addEdge(int v, int w) {
        // TODO
        M.addEdge(v,w);

    }

    /**
     * Returns true if there is an edge between 'v' and 'w'
     *
     * @param v one vertex
     * @param w another vertex
     * @return true or false
     */
    public boolean hasEdge(int v, int w) {
        // TODO
        LinkedList<Integer> adjv = M.adj(v);
        if (v==w) return true;
        if (adjv.contains(w)){
            return true;
        }
        else{
            return false;
        }

    }

    /**
     * Builds a grid as a graph.
     *
     * @return Graph G -- Basic grid on which the Maze is built
     */
    public Graph mazegrid() {
        // TODO
        Graph g = new Graph(N*N);
        for (int i =0; i<N; ++i){
            for (int j=0; j<N-1; ++j){
                g.addEdge(i+j*N,i+(j+1)*N);
                g.addEdge(j+i*N,j+i*N+1);
            }
        }
        return g;

    }

    /**
     * Builds a random maze as a graph.
     * The maze is build with a randomized DFS as the Graph M.
     */
    private void buildMaze() {
        // TODO
        Graph G = mazegrid();
        rdfp = new RandomDepthFirstPaths(G,startnode);
        rdfp.randomDFS(G);
//        rdfp.randomNonrecursiveDFS(G);
        rdfpEdge = rdfp.edge();
        for (int i=1; i<rdfpEdge.length;++i){
            if (rdfp.hasPathTo(i)&&!hasEdge(rdfpEdge[i],i)){
                addEdge(rdfpEdge[i],i);
            }
        }
    }

    /**
     * Find a path from node v to w
     *
     * @param v start node
     * @param w end node
     * @return List<Integer> -- a list of nodes on the path from v to w (both included) in the right order.
     */
    public List<Integer> findWay(int v, int w) {
        // TODO
        if (!rdfp.hasPathTo(w)){
            return null;
        }
        LinkedList<Integer> p = new LinkedList<>();
        for (int i=w; i!=v; i=rdfpEdge[i]){
            p.addFirst(i);
        }
        p.addFirst(v);
        return p;

    }

    /**
     * @return Graph M
     */
    public Graph M() {
        return M;
    }

    public static void main(String[] args) {
        // FOR TESTING
        Maze game = new Maze(50,0);
        List<Integer> p = game.findWay(0, 2499);
        GridGraph vis1 = new GridGraph(game.M);
        GridGraph vis2 = new GridGraph(game.M,p);
    }


}

