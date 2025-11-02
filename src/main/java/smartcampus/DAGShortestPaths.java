package smartcampus;

import java.util.*;

public class DAGShortestPaths {

    public static double[] shortest(Graph g, int source, List<Integer> topo, Metrics metrics) {
        double[] dist = new double[g.n];
        Arrays.fill(dist, Double.POSITIVE_INFINITY);
        dist[source] = 0;

        metrics.startTimer();
        for (int u : topo)
            if (dist[u] < Double.POSITIVE_INFINITY)
                for (int[] e : g.adj.get(u)) {
                    metrics.relaxations++;
                    dist[e[0]] = Math.min(dist[e[0]], dist[u] + e[1]);
                }
        metrics.stopTimer();
        return dist;
    }

    public static double[] longest(Graph g, int source, List<Integer> topo, Metrics metrics) {
        double[] dist = new double[g.n];
        Arrays.fill(dist, Double.NEGATIVE_INFINITY);
        dist[source] = 0;

        metrics.startTimer();
        for (int u : topo)
            if (dist[u] > Double.NEGATIVE_INFINITY)
                for (int[] e : g.adj.get(u)) {
                    metrics.relaxations++;
                    dist[e[0]] = Math.max(dist[e[0]], dist[u] + e[1]);
                }
        metrics.stopTimer();
        return dist;
    }
}
