package smartcampus;

import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class GraphTest {

    // helps to create simple graph
    private Graph createSimpleDAG() {
        Graph g = new Graph(5, true);
        g.source = 0;
        g.addEdge(0, 1, 2);
        g.addEdge(0, 2, 3);
        g.addEdge(1, 3, 4);
        g.addEdge(2, 3, 1);
        g.addEdge(3, 4, 2);
        return g;
    }

    // tests scc
    @Test
    public void testSCC() {
        Graph g = new Graph(4, true);
        g.addEdge(0, 1, 1);
        g.addEdge(1, 2, 1);
        g.addEdge(2, 0, 1); // cycle 0->1->2->0
        g.addEdge(2, 3, 1);

        Metrics metrics = new Metrics();
        TarjanSCC tarjan = new TarjanSCC(g, metrics);
        List<List<Integer>> sccs = tarjan.run();

        // checks num of components
        assertEquals(2, sccs.size());

        //  one scc should contain 0,1,2
        boolean foundCycle = sccs.stream().anyMatch(c -> c.containsAll(Arrays.asList(0,1,2)));
        assertTrue(foundCycle);

        // other scc should be 3
        boolean foundNode3 = sccs.stream().anyMatch(c -> c.contains(3));
        assertTrue(foundNode3);
    }

    // topo sort test
    @Test
    public void testTopoSort() {
        Graph g = createSimpleDAG();
        Metrics metrics = new Metrics();
        List<Integer> topo = KahnTopologicalSort.sort(g, metrics);

        // each edge u->v, u comes before v in topo
        Map<Integer, Integer> pos = new HashMap<>();
        for (int i = 0; i < topo.size(); i++) pos.put(topo.get(i), i);

        for (int u = 0; u < g.n; u++)
            for (int[] e : g.adj.get(u))
                assertTrue(pos.get(u) < pos.get(e[0]));
    }

    //  DAG shortest and longest paths test
    @Test
    public void testDAGShortestLongest() {
        Graph g = createSimpleDAG();
        Metrics metrics = new Metrics();

        List<Integer> topo = KahnTopologicalSort.sort(g, metrics);

        double[] shortest = DAGShortestPaths.shortest(g, g.source, topo, metrics);
        double[] longest = DAGShortestPaths.longest(g, g.source, topo, metrics);

        // shortest path from 0 to 4 = 0->2->3->4 = 3+1+2=6
        assertEquals(6, shortest[4], 1e-6);

        // longest path from 0 to 4 = 0->1->3->4 = 2+4+2=8
        assertEquals(8, longest[4], 1e-6);
    }
}
