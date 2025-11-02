package smartcampus;

import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Metrics metrics = new Metrics();

        Graph g = Graph.loadFromResource("tasks.json");
        System.out.println("Loaded graph: n=" + g.n + " directed=" + g.directed);

        // scc
        metrics.startTimer();
        TarjanSCC tarjan = new TarjanSCC(g);
        List<List<Integer>> sccs = tarjan.run();
        metrics.stopTimer();

        System.out.println("\nSCCs (" + sccs.size() + "):");
        for (List<Integer> comp : sccs)
            System.out.println("  " + comp);
        System.out.println(metrics);

        // condensation DAG
        CondensationGraph cg = new CondensationGraph(g, sccs);
        Graph dag = cg.toGraph();

        // topological sort on condensation DAG
        metrics.reset();
        metrics.startTimer();
        List<Integer> compTopo = KahnTopologicalSort.sort(dag);
        metrics.stopTimer();
        System.out.println("\nTopological order of SCC components:");
        System.out.println(compTopo);
        System.out.println(metrics);

        // topological order to original nodes
        List<Integer> nodeTopo = new ArrayList<>();
        for (int compId : compTopo)
            nodeTopo.addAll(cg.sccs.get(compId));
        System.out.println("\nDerived node order after SCC compression:");
        System.out.println(nodeTopo);

        // DAG shortest and longest paths
        metrics.reset();
        metrics.startTimer();
        double[] shortest = DAGShortestPaths.shortest(dag, g.source, compTopo);
        double[] longest = DAGShortestPaths.longest(dag, g.source, compTopo);
        metrics.stopTimer();

        System.out.println("\nShortest path distances from " + g.source + ":");
        for (int i = 0; i < dag.n; i++)
            System.out.printf("  %d -> %.1f\n", i, shortest[i]);

        System.out.println("\nLongest path distances from " + g.source + ":");
        for (int i = 0; i < dag.n; i++)
            System.out.printf("  %d -> %.1f\n", i, longest[i]);

        System.out.println(metrics);
    }
}
