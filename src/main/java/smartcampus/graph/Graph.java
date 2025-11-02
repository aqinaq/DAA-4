package smartcampus.graph;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Graph {
    public int n;
    public boolean directed;
    public List<List<int[]>> adj;
    public int source;

    public Graph(int n, boolean directed) {
        this.n = n;
        this.directed = directed;
        adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
    }

    public void addEdge(int u, int v, int w) {
        adj.get(u).add(new int[]{v, w});
        if (!directed) adj.get(v).add(new int[]{u, w});
    }

    public static Graph loadFromResource(String filename) throws IOException {
        InputStream in = Graph.class.getResourceAsStream("/" + filename);
        if (in == null) throw new FileNotFoundException(filename + " not found in resources.");

        String text = new String(in.readAllBytes(), StandardCharsets.UTF_8);
        JSONObject obj = new JSONObject(text);

        boolean directed = obj.getBoolean("directed");
        int n = obj.getInt("n");
        int source = obj.getInt("source");
        Graph g = new Graph(n, directed);
        g.source = source;

        JSONArray edges = obj.getJSONArray("edges");
        for (int i = 0; i < edges.length(); i++) {
            JSONObject e = edges.getJSONObject(i);
            g.addEdge(e.getInt("u"), e.getInt("v"), e.getInt("w"));
        }
        return g;
    }
}
