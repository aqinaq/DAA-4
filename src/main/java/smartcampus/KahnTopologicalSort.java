package smartcampus;
import java.util.*;

public class KahnTopologicalSort {
    public static List<Integer> sort(Graph g) {
        int[] indeg = new int[g.n];
        for (int u = 0; u < g.n; u++)
            for (int[] e : g.adj.get(u))
                indeg[e[0]]++;

        Queue<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < g.n; i++)
            if (indeg[i] == 0) q.add(i);

        List<Integer> order = new ArrayList<>();
        while (!q.isEmpty()) {
            int u = q.poll();
            order.add(u);
            for (int[] e : g.adj.get(u)) {
                if (--indeg[e[0]] == 0)
                    q.add(e[0]);
            }
        }
        return order;
    }
}
