import java.util.*;

public class Maze{
    private final int N;
    private Graph M;
    public int startnode;
        
	public Maze(int N, int startnode) {
		
        if (N < 0) throw new IllegalArgumentException("Number of vertices in a row must be nonnegative");
        this.N = N;
        this.M= new Graph(N*N);
        this.startnode= startnode;
        buildMaze();
	}
	
    public Maze (In in) {
    	this.M = new Graph(in);
    	this.N= (int) Math.sqrt(M.V());
    	this.startnode=0;
    }

	
    /**
     * Adds the undirected edge v-w to the graph M.
     *
     * @param  v one vertex in the edge
     * @param  w the other vertex in the edge
     * @throws IllegalArgumentException unless both {@code 0 <= v < V} and {@code 0 <= w < V}
     */
    public void addEdge(int v, int w) {
        if (v < 0 || v >= M.V() || w < 0 || w >= M.V() || v == w) throw new IllegalArgumentException();
        if(v!=w) {
        M.addEdge(v,w);
        }
    }
    
    /**
     * Returns true if there is an edge between 'v' and 'w'
     * @param v
     * @param w
     * @return true or false
     */
    public boolean hasEdge( int v, int w){
        if (v == w ) return true;
        else return M.adj(v).contains(w);
    }	
    
    /**
     * Builds a grid as a graph.
     * @return Graph G -- Basic grid on which the Maze is built
     */
    public Graph mazegrid() {
        Graph G = new Graph(N*N);
        // vertical
        for (int i =0; i < N - 1; i++){
            for (int j =0; j < N ; j++) {
                G.addEdge(i+N*j, i + N*j + 1);
            }
        }
        // horisontal
        for (int i =0; i < N*(N-1); i += N){
            for (int j =0; j < N ; j++) {
                G.addEdge(i + j, i + j + N );
            }
        }
        return G;
    }
    
    private void buildMaze() {
        List<Integer> list1;

        Graph grid = mazegrid();
        RandomDepthFirstPaths random = new RandomDepthFirstPaths(grid,0);
        random.randomDFS(grid);

        for (int i = 1; i < N*N; i++) {

            list1 = random.pathTo(i);
            for (int j = 0; j < list1.size() - 1; j++){
                if (!hasEdge(list1.get(j),list1.get(j+1)) && grid.adj(j).contains(j+1)) addEdge(list1.get(j),list1.get(j+1));
            }
        }
    }

    
    public List<Integer> findWay(int v, int w){
		List<Integer> list = new LinkedList<>();
        boolean[] marked = new boolean[M.V()];
        int[] edgeTo = new int[M.V()];
        Queue<Integer> Q = new LinkedList<Integer>();

        marked[v] = true;
        Q.add(v);
        while (!Q.isEmpty()){
            int k = Q.poll();
            for (int p : M.adj(k)){
                if(!marked[p]){
                    edgeTo[p] = k;
                    marked[p] = true;
                    Q.add(p);
                }
            }

        }

        if (!marked[w]) return null;
        else {
            int temp = edgeTo[w];
            list.add(w);
            while (temp != v){
                list.add(temp);
                temp = edgeTo[temp];
            }
            list.add(v);
        }
        Collections.reverse(list);
        return list;
    }
    
    public Graph M() {
    	return M;
    }

    public static void main(String[] args) {
		// FOR TESTING
    }


}

