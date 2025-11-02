package smartcampus.graph;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class DataGenerator {

    static class Edge {
        int u, v, w;
        Edge(int u, int v, int w) { this.u = u; this.v = v; this.w = w; }
    }

    static class Graph {
        int n;
        boolean directed = true;
        List<Edge> edges = new ArrayList<>();
        int source;
        String weightModel = "edge";

        Graph(int n, int source) {
            this.n = n;
            this.source = source;
        }

        void addEdge(int u, int v, int w) {
            edges.add(new Edge(u, v, w));
        }

        JSONObject toJson() {
            JSONObject obj = new JSONObject();
            obj.put("directed", directed);
            obj.put("n", n);

            JSONArray arr = new JSONArray();
            for (Edge e : edges) {
                JSONObject edgeObj = new JSONObject();
                edgeObj.put("u", e.u);
                edgeObj.put("v", e.v);
                edgeObj.put("w", e.w);
                arr.put(edgeObj);
            }
            obj.put("edges", arr);
            obj.put("source", source);
            obj.put("weight_model", weightModel);
            return obj;
        }
    }

    public static void main(String[] args) throws IOException {
        Random rand = new Random();

        String base = "src/main/resources";
        new File(base).mkdirs();

        generateSet("small", 6, 10, 3, rand, base);
        generateSet("medium", 10, 20, 3, rand, base);
        generateSet("large", 20, 50, 3, rand, base);

        System.out.println("datasets generated under src/main/resources/");
    }
    static void generateSet(String prefix, int minN, int maxN, int count, Random rand, String base) throws IOException {
        for (int i = 1; i <= count; i++) {
            int n = rand.nextInt(maxN - minN + 1) + minN;
            int source = rand.nextInt(n);
            Graph g = new Graph(n, source);

            boolean cyclic = rand.nextBoolean();
            double density = 0.3 + rand.nextDouble() * 0.4;
            int maxEdges = (int)(density * n * (n - 1));

            for (int e = 0; e < maxEdges; e++) {
                int u = rand.nextInt(n);
                int v = rand.nextInt(n);
                if (u == v) continue;
                if (!cyclic && v <= u) continue;
                int w = 1 + rand.nextInt(10);
                g.addEdge(u, v, w);
            }

            JSONObject obj = g.toJson();
            File file = new File(base + "/" + prefix + "-" + i + ".json");
            try (FileWriter fw = new FileWriter(file)) {
                fw.write(obj.toString(2));
            }
        }
    }
}
