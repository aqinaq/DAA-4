package smartcampus;

import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Graph g = Graph.loadFromResource("tasks.json");
        System.out.println("Loaded graph: n=" + g.n + " directed=" + g.directed);

        // scc
        TarjanSCC tarjan = new TarjanSCC(g);
        List<List<Integer>> comps = tarjan.run();
        System.out.println("\nSCC:");
        for (List<Integer> c : comps)
            System.out.println("  " + c);

        // if dag topological logic
        System.out.println("\nTopological order (approx, may fail if cyclic):");
        List<Integer> topo = KahnTopologicalSort.sort(g);
        System.out.println(topo);

        //  dag short and long paths
        double[] shortest = DAGShortestPaths.shortest(g, g.source, topo);
        double[] longest = DAGShortestPaths.longest(g, g.source, topo);

        System.out.println("\nShortest path distances from " + g.source + ":");
        for (int i = 0; i < g.n; i++)
            System.out.printf("  %d -> %.1f\n", i, shortest[i]);

        System.out.println("\nLongest path distances from " + g.source + ":");
        for (int i = 0; i < g.n; i++)
            System.out.printf("  %d -> %.1f\n", i, longest[i]);
    }
}
