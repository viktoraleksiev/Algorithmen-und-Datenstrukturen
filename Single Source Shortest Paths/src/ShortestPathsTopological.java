import java.util.PriorityQueue;
import java.util.Stack;

public class ShortestPathsTopological {
    private int[] parent;
    private int s;
    private double[] dist;
    private PriorityQueue<Integer> Q;

    public ShortestPathsTopological(WeightedDigraph G, int s) {
        parent = new int[G.V()];
        dist = new double[G.V()];
        Q = new PriorityQueue<>();

        this.s = s;

        for (int i = 0; i < G.V(); i++) {
            dist[i] = Double.MAX_VALUE;
        }

        dist[s] =0;

        Q.add(s);
        while (!Q.isEmpty()) {
            Integer v = Q.poll();
            for (DirectedEdge e : G.incident(v)){
                    relax(e);
            }

        }
    }

    public void relax(DirectedEdge e) {
        int v = e.from();
        int w = e.to();
        if (dist[w] > dist[v] + e.weight()) {
            parent[w] = v;
            dist[w] = dist[v] + e.weight();
            if(!Q.contains(w)) Q.add(w);
        }
    }

    public boolean hasPathTo(int v) {
        return parent[v] >= 0;
    }

    public Stack<Integer> pathTo(int v) {
        if (!hasPathTo(v)) {
            return null;
        }
        Stack<Integer> path = new Stack<>();
        for (int w = v; w != s; w = parent[w]) {
            path.push(w);
        }
        path.push(s);
        return path;
    }
}

