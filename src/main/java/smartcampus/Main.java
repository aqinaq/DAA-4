package smartcampus;

import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Metrics metrics = new Metrics();

        Graph g = Graph.loadFromResource("tasks.json");
        System.out.println("Loaded graph: n=" + g.n + ", directed=" + g.directed + ", source=" + g.source);

        // tarjan scc
        metrics.reset();
        TarjanSCC tarjan = new TarjanSCC(g, metrics);
        List<List<Integer>> sccs = tarjan.run();

        System.out.println("\nSCCs (" + sccs.size() + " components):");
        for (List<Integer> comp : sccs)
            System.out.println("  " + comp);
        System.out.println("Metrics (SCC): " + metrics);

        // condensation DAG
        CondensationGraph cg = new CondensationGraph(g, sccs);
        Graph dag = cg.toGraph();
        System.out.println("\nCondensation DAG has " + dag.n + " nodes.");

        // topological sort of condensation DAG
        metrics.reset();
        List<Integer> compTopo = KahnTopologicalSort.sort(dag, metrics);
        System.out.println("\nTopological order of SCC components:");
        System.out.println(compTopo);
        System.out.println("Metrics (Topological Sort): " + metrics);

        // component topo order to original nodes
        List<Integer> nodeTopo = new ArrayList<>();
        for (int compId : compTopo)
            nodeTopo.addAll(cg.sccs.get(compId));
        System.out.println("\nDerived node order after SCC compression:");
        System.out.println(nodeTopo);

        // DAG shortest and longest paths
        metrics.reset();
        double[] shortest = DAGShortestPaths.shortest(dag, g.source, compTopo, metrics);
        double[] longest = DAGShortestPaths.longest(dag, g.source, compTopo, metrics);

        System.out.println("\nShortest path distances from source " + g.source + ":");
        for (int i = 0; i < dag.n; i++)
            System.out.printf("  %d -> %.1f\n", i, shortest[i]);

        System.out.println("\nLongest path distances from source " + g.source + ":");
        for (int i = 0; i < dag.n; i++)
            System.out.printf("  %d -> %.1f\n", i, longest[i]);

        System.out.println("Metrics (DAG-SP): " + metrics);
    }
}
