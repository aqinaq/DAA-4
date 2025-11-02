package smartcampus;

import java.util.*;

public class TarjanSCC {
    private final Graph graph;
    private final Metrics metrics;
    private int time = 0;
    private int[] ids, low;
    private boolean[] onStack;
    private Deque<Integer> stack = new ArrayDeque<>();
    public List<List<Integer>> sccs = new ArrayList<>();

    public TarjanSCC(Graph g, Metrics metrics) {
        this.graph = g;
        this.metrics = metrics;
        ids = new int[g.n];
        low = new int[g.n];
        onStack = new boolean[g.n];
        Arrays.fill(ids, -1);
    }

    public List<List<Integer>> run() {
        metrics.startTimer();
        for (int i = 0; i < graph.n; i++)
            if (ids[i] == -1) {
                metrics.dfsCalls++;
                dfs(i);
            }
        metrics.stopTimer();
        return sccs;
    }

    private void dfs(int at) {
        stack.push(at);
        onStack[at] = true;
        ids[at] = low[at] = time++;

        for (int[] edge : graph.adj.get(at)) {
            metrics.edgeVisits++;
            int to = edge[0];
            if (ids[to] == -1) {
                metrics.dfsCalls++;
                dfs(to);
            }
            if (onStack[to]) low[at] = Math.min(low[at], low[to]);
        }

        if (ids[at] == low[at]) {
            List<Integer> component = new ArrayList<>();
            while (true) {
                int node = stack.pop();
                onStack[node] = false;
                component.add(node);
                if (node == at) break;
            }
            sccs.add(component);
        }
    }
}
