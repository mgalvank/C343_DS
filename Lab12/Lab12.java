package lab12;
import java.util.ArrayList;
import java.util.LinkedList;
import java.lang.Iterable;
import java.util.Iterator;
import java.util.Arrays;
import java.util.function.Function;
import java.util.function.BiPredicate;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.IntStream;

interface Graph<V> {
    int num_vertices();
    void add_edge(V source, V target);
    Iterable<V> adjacent(V source);
    Iterable<V> vertices();
}

class Coord {
    public int i, j;
    Coord(int ii, int jj) { i = ii; j = jj; }
};

class DirectedAdjacencyList implements Graph<Integer> {
    ArrayList<LinkedList<Integer>> adjacent;

    DirectedAdjacencyList(int num_vertices) {
        adjacent = new ArrayList<LinkedList<Integer>>(num_vertices);
        for (int i = 0; i != num_vertices; ++i)
            adjacent.add(new LinkedList<Integer>());
    }
    public int num_vertices() {
        return adjacent.size();
    }
    public void add_edge(Integer u, Integer v) {
        adjacent.get(u).add(v);
    }
    public Iterable<Integer> adjacent(Integer u) {
        return adjacent.get(u);
    }
    class RangeIter implements Iterator<Integer> {
        int curr, end;
        RangeIter(int b, int e) { curr = b; end = e; }
        public boolean hasNext() { return curr != end; }
        public Integer next() { int tmp = curr; ++curr; return tmp; }
    }
    class RangeIterable implements Iterable<Integer> {
        int begin, end;
        RangeIterable(int b, int e) { begin = b; end = e; }
        public RangeIter iterator() { return new RangeIter(begin, end); }
    }
    public Iterable<Integer> vertices() {
        return new RangeIterable(0, num_vertices());
    }
}

class UndirectedAdjacencyList extends DirectedAdjacencyList {

    UndirectedAdjacencyList(int num_vertices) {
        super(num_vertices);
    }
    public void add_edge(Integer u, Integer v) {
        adjacent.get(u).add(v);
        adjacent.get(v).add(u);
    }
}


public class Lab12 {

    static <V> void bfs(Graph<V> G, V start, Map<V,Boolean> visited,Map<V,V> parent) {
    	//System.out.println("vertices" + G.num_vertices());
        for (V v : G.vertices()) {
        	
            visited.put(v, false);
	    parent.put(v, v);
	}
        LinkedList<V> Q = new LinkedList<V>();
        Q.add(start);
        visited.put(start, true);
        while (Q.size() != 0) {
        	
            V u = Q.remove();
            //System.out.println("u " + u);
            for (V v : G.adjacent(u)){
            	//System.out.println("v " + v);
            	
                if (! visited.get(v)) {
                	
                    parent.put(v, u);
                    Q.add(v);
                    visited.put(v, true);
                }
            }
        }
    }
    
    static Boolean in_bounds(Coord c, int height, int width) {
	return 0 <= c.i && c.i < height && 0 <= c.j && c.j < width; 
    }

    static int coord2vertex(Coord c, int width) {
	return c.i * width + c.j;
    }

    static Coord vertex2coord(int u, int width) {
	int i = u / width;
	int j = u % width;
	return new Coord(i, j);
    }
    
    static UndirectedAdjacencyList maze2graph(int maze[][]) {
	int height = maze.length;
	int width = maze[0].length;
	//System.out.println(height +  " " + width);
	UndirectedAdjacencyList G = new UndirectedAdjacencyList(height * width);
	for (int i = 0; i != height; ++i)
	    for (int j = 0; j != width; ++j) {
		if (maze[i][j] == 0) {
		    // north, east, south, west
		    Coord adj[] = { new Coord(i+1, j),
				    new Coord(i, j+1),
				    new Coord(i-1, j),
				    new Coord(i, j-1)};
		    for (Coord c : adj)
			if (in_bounds(c, height, width)
			    && maze[c.i][c.j] == 0) {
			    int u = coord2vertex(new Coord(i,j),width);
			    int v = coord2vertex(c,width);
			    //System.out.println(u+ " " + v);
			    G.add_edge(u, v);
			}
		}
	    }
	return G;
    }

    static void print_maze(int maze[][]) {
	int height = maze.length;
	int width = maze[0].length;
	for (int j = 0; j != width + 2; ++j)
	    System.out.print("-");
	System.out.print("\n");
	for (int i = 0; i != height; ++i) {
	    System.out.print("|");
	    for (int j = 0; j != width; ++j) {
		System.out.print(String.format("%1$d", maze[i][j]));
	    }
	    System.out.print("|");
	    System.out.print("\n");
	}
	for (int j = 0; j != width + 2; ++j)
	    System.out.print("-");
	System.out.print("\n");
    }

    static <V> void print_graph(Graph<V> G, Function<V,String> v2s) {
	for (V u : G.vertices()) {
	    System.out.print(v2s.apply(u));
	    System.out.print("->");
	    for (V v : G.adjacent(u)) {
		System.out.print(v2s.apply(v));
		System.out.print(",");
	    }
	    System.out.print("\n");
	}
    }
    
    static <V> void solve_labyrinth(int labyrinth[][],
				Coord start, Coord end) {
    	print_maze(labyrinth);
    	int width = labyrinth[0].length;
    	Graph<Integer> G =  maze2graph(labyrinth);
    	Map<V,Boolean> visited= new HashMap<V, Boolean>();
        Map<V,V> parent = new HashMap<V, V>();

    	Integer end_vertex = coord2vertex(end,width);
    	Integer start_vertex = coord2vertex(start,width);
    	bfs((Graph<V>) G,(V) start_vertex,visited,parent);
    	labyrinth[end.i][end.j]=2;
    	//System.out.println(width + " " + start_vertex  + " " + end_vertex);
    	do{
    	int par = (int) (parent.get((V) end_vertex));
    	Coord c = vertex2coord(par,width);
    	//System.out.println(par + "  " + c.i + " " + c.j);
    	labyrinth[c.i][c.j]=2;
    	end_vertex = par;
    	}while(end_vertex!=start_vertex);
    	
    	
    	print_maze(labyrinth);
	//System.out.println("error: solve_labyrinth not yet implemented");
	//System.exit(-1);
    }

    
    ////////////////////////////////////////////////////////////////////////////
    // Testing

    public static void main(String[] args) {
	{
	    int labyrinth[][] = {
	    		{0, 0},
	    		{1, 0}};
	    int solution[][] = {
		{2, 2},
		{1, 2}};
	    
	    solve_labyrinth(labyrinth, new Coord(0,0), new Coord(1,1));
	    //System.out.println(labyrinth[0][0]);
	    assert java.util.Arrays.deepEquals(labyrinth, solution);
	}
	{
	    int labyrinth[][] = {
		{0, 0, 0},
		{0, 1, 0},
		{0, 0, 0}};
	    int solution[][] = {
		{2, 0, 0},
		{2, 1, 0},
		{2, 2, 0}};
	    solve_labyrinth(labyrinth, new Coord(0,0), new Coord(2,1));
	    assert java.util.Arrays.deepEquals(labyrinth, solution);
	}
	{
	    int labyrinth[][] = {
		{0, 0, 0},
		{0, 1, 0},
		{0, 0, 0}};
	    int solution[][] = {
		{2, 2, 2},
		{0, 1, 2},
		{0, 0, 0}};
	    solve_labyrinth(labyrinth, new Coord(0,0), new Coord(1,2));
	    assert java.util.Arrays.deepEquals(labyrinth, solution);
	}
	{
	    int labyrinth[][] = {
		{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
		{1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1},
		{1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
		{1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1},
		{1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1},
		{1, 0, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1},
		{1, 0, 1, 0, 0, 0, 0, 1, 0, 1, 1, 1},
		{1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 1},
		{1, 0, 1, 0, 0, 1, 1, 1, 1, 1, 0, 1},
		{1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1},
		{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};
	    int solution[][] = {
		{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		{1, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
		{1, 2, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1},
		{1, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
		{1, 2, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1},
		{1, 2, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1},
		{1, 2, 2, 2, 1, 1, 0, 1, 1, 1, 0, 1},
		{1, 0, 1, 2, 2, 2, 2, 1, 0, 1, 1, 1},
		{1, 0, 1, 1, 0, 1, 2, 2, 2, 2, 2, 1},
		{1, 0, 1, 0, 0, 1, 1, 1, 1, 1, 2, 1},
		{1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 2, 1},
		{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};
	    solve_labyrinth(labyrinth, new Coord(1,1), new Coord(10,10));
	    assert java.util.Arrays.deepEquals(labyrinth, solution);
	}

        System.out.println("passed Labyrinth tests");
    }

}
