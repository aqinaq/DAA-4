package smartcampus;

import java.util.*;

public class CondensationGraph {
    public int n;
    public List<List<int[]>> adj = new ArrayList<>();
    public List<List<Integer>> sccs;
    public Map<Integer, Integer> nodeToComponent = new HashMap<>();

    public CondensationGraph(Graph g, List<List<Integer>> sccs) {
        this.sccs = sccs;
        n = sccs.size();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());

        for (int i = 0; i < sccs.size(); i++)
            for (int node : sccs.get(i))
                nodeToComponent.put(node, i);

        for (int u = 0; u < g.n; u++) {
            int cu = nodeToComponent.get(u);
            for (int[] e : g.adj.get(u)) {
                int cv = nodeToComponent.get(e[0]);
                if (cu != cv)
                    adj.get(cu).add(new int[]{cv, e[1]});
            }
        }
    }

    public Graph toGraph() {
        Graph g = new Graph(n, true);
        for (int u = 0; u < n; u++)
            for (int[] e : adj.get(u))
                g.addEdge(u, e[0], e[1]);
        return g;
    }
}
