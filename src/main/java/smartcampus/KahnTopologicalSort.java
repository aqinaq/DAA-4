package smartcampus;
import java.util.*;

public class KahnTopologicalSort {
    public static List<Integer> sort(Graph g, Metrics metrics) {
        int[] indeg = new int[g.n];
        for (int u = 0; u < g.n; u++)
            for (int[] e : g.adj.get(u)) indeg[e[0]]++;

        Queue<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < g.n; i++)
            if (indeg[i] == 0) {
                q.add(i);
                metrics.queueOps++;
            }

        List<Integer> order = new ArrayList<>();
        metrics.startTimer();
        while (!q.isEmpty()) {
            int u = q.poll();
            metrics.queueOps++;
            order.add(u);
            for (int[] e : g.adj.get(u)) {
                if (--indeg[e[0]] == 0) {
                    q.add(e[0]);
                    metrics.queueOps++;
                }
            }
        }
        metrics.stopTimer();
        return order;
    }
}
