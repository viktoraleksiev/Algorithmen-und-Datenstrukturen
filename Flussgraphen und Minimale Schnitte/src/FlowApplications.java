import java.util.*;


public class FlowApplications {

    /**
     * cloneFlowNetwork() makes a deep copy of a FlowNetwork
     * (FlowNetwork has unfortunately no copy constructor)
     *
     * @param flowNetwork the flow network that should be cloned
     * @return cloned flow network (deep copy) with same order of edges
     */
    private static FlowNetwork cloneFlowNetwork(FlowNetwork flowNetwork) {
        int V = flowNetwork.V();
        FlowNetwork clone = new FlowNetwork(V);

//        Simple version (but reverses order of edges)
//        for (FlowEdge e : flowNetwork.edges()) {
//            FlowEdge eclone = new FlowEdge(e.from(), e.to(), e.capacity());
//            clone.addEdge(eclone);
//        }

        for (int v = 0; v < flowNetwork.V(); v++) {
            // reverse so that adjacency list is in same order as original
            Stack<FlowEdge> reverse = new Stack<>();
            for (FlowEdge e : flowNetwork.adj(v)) {
                if (e.to() != v) {
                    FlowEdge eclone = new FlowEdge(e.from(), e.to(), e.capacity());
                    reverse.push(eclone);
                }
            }
            while (!reverse.isEmpty()) {
                clone.addEdge(reverse.pop());
            }
        }
        return clone;
    }




    /**
     * numberOfEdgeDisjointPaths() returns the (maximum) number of edge-disjoint paths that exist in
     * an undirected graph between two nodes s and t using Edmonds-Karp.
     *
     * @param graph the graph that is to be investigated
     * @param s     node on one end of the path
     * @param t     node on the other end of the path
     * @return number of edge-disjoint paths in graph between s and t
     */

    public static int numberOfEdgeDisjointPaths(Graph graph, int s, int t) {
        return edgeDisjointPaths(graph,s,t).size();
    }

    /**
     * edgeDisjointPaths() returns a maximal set of edge-disjoint paths that exist in
     * an undirected graph between two nodes s and t using Edmonds-Karp.
     *
     * @param graph the graph that is to be investigated
     * @param s     node on one end of the path
     * @param t     node on the other end of the path
     * @return a {@code Bag} of edge-disjoint paths in graph between s and t
     * Each path is stored in a {@code LinkedList<Integer>}.
     */
	public static void  search(FlowNetwork p1, int s, int t, LinkedList disPath){
        for (FlowEdge w : p1.adj(s)){
            if (w.flow() == 1 && w.to() != t && w.to() != s) {
                int to = w.to();
                w.addResidualFlowTo(w.from(), 1);
                disPath.add(w.to());
                search(p1,w.to(),t,disPath);
                break;
            }
        }

    }
	
    public static Bag<LinkedList<Integer>> edgeDisjointPaths(Graph graph, int s, int t) {
        Bag<LinkedList<Integer>> bag = new Bag();
        FlowNetwork p1 = new FlowNetwork(graph.V());

        for (int j = 0; j < graph.V(); j++) {
            for (int w : graph.adj(j)) {
                    FlowEdge e = new FlowEdge(j, w, 1);
                    p1.addEdge(e);
            }
        }
        FordFulkerson ford = new FordFulkerson(p1, s, t);

        for(int i = 0; i < ford.value(); i++){
            LinkedList<Integer> disPath = new LinkedList();

            disPath.add(s);
            search(p1,s,t,disPath);
            disPath.add(t);

            bag.add(disPath);
        }

        return bag;
    }


    /**
     * isUnique determines for a given Flow Network that has a guaranteed minCut,
     * if that one is unique, meaning it's the only minCut in that network
     *
     * @param flowNetworkIn the graph that is to be investigated
     * @param s             source node s
     * @param t             sink node t
     * @return true if the minCut is unique, otherwise false
     */

    public static boolean isUnique(FlowNetwork flowNetworkIn, int s, int t) {
        Bag<FlowEdge> schnitt1 = new Bag<>();
        Bag<FlowEdge> schnitt2 = new Bag<>();
        boolean[] marked1 = new boolean[flowNetworkIn.V()];
        boolean[] marked2 = new boolean[flowNetworkIn.V()];
        PriorityQueue<Integer> Q1 = new PriorityQueue<>();
        PriorityQueue<Integer> Q2 = new PriorityQueue<>();
        LinkedList<Integer> list1 = new LinkedList<>();
        LinkedList<Integer> list2 = new LinkedList<>();

        FordFulkerson p1 = new FordFulkerson(flowNetworkIn,s,t);

        // Group from side s
        list1.add(s);
        marked1[s] = true;
        Q1.add(s);
        while (!Q1.isEmpty()){
            int k = Q1.poll();
            for (FlowEdge p : flowNetworkIn.adj(k)){
                if(p.flow() < p.capacity() && !marked1[p.to()]){
                    list1.add(p.to());
                    marked1[p.to()] = true;
                    Q1.add(p.to());
                }
            }
        }
        // cut from side s
        for(int i =0; i < list1.size(); i++){
            for (FlowEdge p : flowNetworkIn.adj(list1.get(i))){
                if (!(list1.contains(p.to()))) schnitt1.add(p);
            }
        }
        // Creating oposite flowgraph
            LinkedList<FlowEdge> bag = new LinkedList<>();
            for (FlowEdge w : flowNetworkIn.edges()) {
                    FlowEdge e = new FlowEdge(w.to(), w.from(), w.capacity());
                    e.addResidualFlowTo(w.from(), w.capacity() - w.flow());
                    bag.add(e);

            }


        FlowNetwork work = new FlowNetwork(flowNetworkIn.V());
        for(FlowEdge e : bag){
            work.addEdge(e);
        }

        // Group from side t
        list2.add(t);
        marked2[t] = true;
        Q2.add(t);
        while (!Q2.isEmpty()){
            int k = Q2.poll();
            for (FlowEdge p : work.adj(k)){
                if( p.flow() != 0.0 && !marked2[p.to()]){
                    list2.add(p.to());
                    marked2[p.to()] = true;
                    Q2.add(p.to());
                }
            }
        }
        // Cut from side t
        for(int i =0; i < list2.size(); i++){
            for (FlowEdge p : work.adj(list2.get(i))){
                if (!(list2.contains(p.to()))) schnitt2.add(p);
            }
        }

        // Comparing the two cuts
        if (schnitt1.size() != schnitt2.size()) return false;
        else {
            boolean unique=false;
            for( FlowEdge e : schnitt1) {
                unique = false;
                for (FlowEdge e1 : schnitt2) {
                    if(e.from() == e1.to() && e.to() == e1.from()) unique = true;
                }
                if (!unique) return false;
            }
            for( FlowEdge e : schnitt2) {
                unique = false;
                for (FlowEdge e1 : schnitt1) {
                    if(e.from() == e1.to() && e.to() == e1.from()) unique = true;
                }
                if (!unique) return false;
            }
            return unique;
        }
    }


    /**
     * findBottlenecks finds all bottleneck nodes in the given flow network
     * and returns the indices in a Linked List
     *
     * @param flowNetwork the graph that is to be investigated
     * @param s           index of the source node of the flow
     * @param t           index of the target node of the flow
     * @return {@code LinkedList<Integer>} containing all bottleneck vertices
     * @throws IllegalArgumentException is flowNetwork does not have a unique cut
     */

    public static LinkedList<Integer> findBottlenecks(FlowNetwork flowNetwork, int s, int t) {
        LinkedList<Integer> neck = new LinkedList<>();

        Bag<FlowEdge> schnitt1 = new Bag<>();
        boolean[] marked1 = new boolean[flowNetwork.V()];
        PriorityQueue<Integer> Q1 = new PriorityQueue<>();
        LinkedList<Integer> list1 = new LinkedList<>();

        FordFulkerson p1 = new FordFulkerson(flowNetwork,s,t);

        // Creating group with s
        list1.add(s);
        marked1[s] = true;
        Q1.add(s);
        while (!Q1.isEmpty()){
            int k = Q1.poll();
            for (FlowEdge p : flowNetwork.adj(k)){
                if( p.flow() < p.capacity() && !marked1[p.to()]){
                    list1.add(p.to());
                    marked1[p.to()] = true;
                    Q1.add(p.to());
                }
            }
        }
        // Cut from side s
        for(int i =0; i < list1.size(); i++){
            for (FlowEdge p : flowNetwork.adj(list1.get(i))){
                if (!(list1.contains(p.to()))) schnitt1.add(p);
            }
        }
        // bottleneck edges 
        for (FlowEdge e : schnitt1) {
            if (!(neck.contains(e.from()))) neck.add(e.from());
        }


        return neck;
    }

    public static void main(String[] args) {
/*
        // Test for Task 2.1 and 2.2 (useful for debugging!)
        Graph graph = new Graph(new In("Graph1.txt"));
        int s = 0;
        int t = graph.V() - 1;
        int n = numberOfEdgeDisjointPaths(graph, s, t);
        System.out.println("#numberOfEdgeDisjointPaths: " + n);
        Bag<LinkedList<Integer>> paths = edgeDisjointPaths(graph, s, t);
        for (LinkedList<Integer> path : paths) {
            System.out.println(path);
        }
*/

/*
        // Example for Task 3.1 and 3.2 (useful for debugging!)
        FlowNetwork flowNetwork = new FlowNetwork(new In("Flussgraph1.txt"));
        int s = 0;
        int t = flowNetwork.V() - 1;
        boolean unique = isUnique(flowNetwork, s, t);
        System.out.println("Is mincut unique? " + unique);
        // Flussgraph1 is non-unique, so findBottlenecks() should be tested with Flussgraph2
        flowNetwork = new FlowNetwork(new In("Flussgraph2.txt"));
        LinkedList<Integer> bottlenecks = findBottlenecks(flowNetwork, s, t);
        System.out.println("Bottlenecks: " + bottlenecks);
*/
    }

}

