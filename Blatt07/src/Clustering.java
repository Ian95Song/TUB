import java.util.*;
import java.awt.Color;

/**
 * This class solves a clustering problem with the Prim algorithm.
 */
public class Clustering {
    EdgeWeightedGraph G;
    List<List<Integer>> clusters;
    List<List<Integer>> labeled;

    /**
     * Constructor for the Clustering class, for a given EdgeWeightedGraph and no labels.
     *
     * @param G a given graph representing a clustering problem
     */
    public Clustering(EdgeWeightedGraph G) {
        this.G = G;
        clusters = new LinkedList<List<Integer>>();
    }

    /**
     * Constructor for the Clustering class, for a given data set with labels
     *
     * @param in input file for a clustering data set with labels
     */
    public Clustering(In in) {
        int V = in.readInt();
        int dim = in.readInt();
        G = new EdgeWeightedGraph(V);
        labeled = new LinkedList<List<Integer>>();
        LinkedList labels = new LinkedList();
        double[][] coord = new double[V][dim];
        for (int v = 0; v < V; v++) {
            for (int j = 0; j < dim; j++) {
                coord[v][j] = in.readDouble();
            }
            String label = in.readString();
            if (labels.contains(label)) {
                labeled.get(labels.indexOf(label)).add(v);
            } else {
                labels.add(label);
                List<Integer> l = new LinkedList<Integer>();
                labeled.add(l);
                labeled.get(labels.indexOf(label)).add(v);
                System.out.println(label);
            }
        }

        G.setCoordinates(coord);
        for (int w = 0; w < V; w++) {
            for (int v = 0; v < V; v++) {
                if (v != w) {
                    double weight = 0;
                    for (int j = 0; j < dim; j++) {
                        weight = weight + Math.pow(G.getCoordinates()[v][j] - G.getCoordinates()[w][j], 2);
                    }
                    weight = Math.sqrt(weight);
                    Edge e = new Edge(v, w, weight);
                    G.addEdge(e);
                }
            }
        }
        clusters = new LinkedList<List<Integer>>();
    }

    /**
     * This method finds a specified number of clusters based on a MST.
     * <p>
     * It is based on the idea that removing edges from a MST will create a
     * partition into several connected components, which are the clusters.
     *
     * @param numberOfClusters number of expected clusters
     */
    public void findClusters(int numberOfClusters) {
        // TODO
        PrimMST mst = new PrimMST(G);
        double thres;
        List<Double> weigs = new ArrayList();
        for (Edge i: mst.edges()){
            weigs.add(i.weight());
        }
        Double[] weights = weigs.toArray(new Double[weigs.size()]);
        Arrays.sort(weights);
        thres = weights[weights.length-numberOfClusters+1];

        EdgeWeightedGraph delmst = new EdgeWeightedGraph(G.V());
        for (Edge i:mst.edges()){
            if (i.weight()<thres){
                delmst.addEdge(i);
            }
        }
        connectedComponents(delmst);
    }

    public void connectedComponents(EdgeWeightedGraph delmst){
        UF union = new UF(delmst.V());
        for (Edge e:delmst.edges()){
            union.union(e.either(),e.other(e.either()));
        }

        int[] parentsTo = new int[delmst.V()];
        boolean[] checked = new boolean[delmst.V()];
        for (int i=0;i<delmst.V();i++){
            parentsTo[i]=union.find(i);
        }

        for (int i=0; i<delmst.V(); i++){
            List<Integer> oneCluster = new ArrayList<>();
            if (!checked[i]){
                oneCluster.add(i);
                checked[i]=true;
                for (int j=i+1; j<delmst.V();j++){
                    if (parentsTo[j]==parentsTo[i]&&!checked[j]){
                        oneCluster.add(j);
                        checked[j]=true;
                    }
                }
                clusters.add(oneCluster);
            }
        }

    }

    /**
     * This method finds clusters based on a MST and a threshold for the coefficient of variation.
     * <p>
     * It is based on the idea that removing edges from a MST will create a
     * partition into several connected components, which are the clusters.
     * The edges are removed based on the threshold given. For further explanation see the exercise sheet.
     *
     * @param threshold for the coefficient of variation
     */
    public void findClusters(double threshold) {
        // TODO
        PrimMST mst = new PrimMST(G);
        List<Edge> edgeList = new ArrayList();
        List<Edge> coeffi = new ArrayList();
        for (Edge i: mst.edges()){
            edgeList.add(i);
        }
        Collections.sort(edgeList);
        EdgeWeightedGraph delmst = new EdgeWeightedGraph(G.V());
        for (Edge i: edgeList){
            coeffi.add(i);
            double out = coefficientOfVariation(coeffi);
            if (out>threshold){
                coeffi.remove(i);
            }
            else{
                delmst.addEdge(i);
            }
        }
        connectedComponents(delmst);
    }

    /**
     * Evaluates the clustering based on a fixed number of clusters.
     *
     * @return array of the number of the correctly classified data points per cluster
     */
    public int[] validation() {
        // TODO
        int[] out = new int[clusters.size()];
        for (int i=0; i<clusters.size();i++){
            int tmpOut=0;
            for (int j:clusters.get(i)){
                if (labeled.get(i).contains(j)){
                    tmpOut++;
                }
            }
            out[i]=tmpOut;
        }
        return out;
    }

    /**
     * Calculates the coefficient of variation.
     * For the formula see the exercise sheet.
     *
     * @param part list of edges
     * @return coefficient of variation
     */
    public double coefficientOfVariation(List<Edge> part) {
        // TODO
        double xSum=0.0;
        double x2Sum=0.0;
        for (Edge i: part){
            xSum += i.weight();
            x2Sum += Math.pow(i.weight(),2);
        }
        double out = Math.sqrt(x2Sum/part.size()-Math.pow(xSum/part.size(),2))/(xSum/part.size());
        return out;
    }

    /**
     * Plots clusters in a two-dimensional space.
     */
    public void plotClusters() {
        int canvas = 800;
        StdDraw.setCanvasSize(canvas, canvas);
        StdDraw.setXscale(0, 15);
        StdDraw.setYscale(0, 15);
        StdDraw.clear(new Color(0, 0, 0));
        Color[] colors = {new Color(255, 255, 255), new Color(128, 0, 0), new Color(128, 128, 128),
                new Color(0, 108, 173), new Color(45, 139, 48), new Color(226, 126, 38), new Color(132, 67, 172)};
        int color = 0;
        for (List<Integer> cluster : clusters) {
            if (color > colors.length - 1) color = 0;
            StdDraw.setPenColor(colors[color]);
            StdDraw.setPenRadius(0.02);
            for (int i : cluster) {
                StdDraw.point(G.getCoordinates()[i][0], G.getCoordinates()[i][1]);
            }
            color++;
        }
        StdDraw.show();
    }


    public static void main(String[] args) {
        // FOR TESTING
//        In a= new In("graph_small.txt");
        In input= new In("iris.txt");
        Clustering x=new Clustering(input);
        x.findClusters(3);
        x.plotClusters();
        int [] vali = new int[3];
        vali = x.validation();
        for (int i = 0; i < 3; i++) {
            System.out.println(vali[i]);
        }
    }
}

