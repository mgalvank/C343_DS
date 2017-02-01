package routing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;


public class Routing {

    public static ArrayList<ArrayList<Coord>>
	findPaths(Board board, ArrayList<Coord[]> points)
    {
    	/*-- Students Code --*/
    	board.pretty_print_grid();
    	ArrayList<ArrayList<Coord>> result = new ArrayList<ArrayList<Coord>>();
    	
    	
    	 
    	 /////////////////////////////bfs
    	int w = board.getWidth();
    	int h = board.getHeight();
    	 
    	for(Coord[] x : points){
    	 Map<Coord, Boolean> visited = new HashMap<Coord, Boolean>();
    	 Map<Coord,Coord> parent = new HashMap<Coord, Coord>();
    	 ArrayList<Coord> adjs = new ArrayList<Coord>();
    	 ArrayList<Coord> single_path = new ArrayList<Coord>();
    	 
    	
    	 Coord start = x[0];
    	 Coord end = x[1];
    	 int value = board.getValue(x[0]);
    	 for(int i=0;i<w;i++){
    		 for(int j=0;j<h;j++){
    			 visited.put(new Coord(i,j), false);
    			 parent.put(new Coord(i,j), new Coord(i,j));
    		 }
    	 }
    	 LinkedList<Coord> Q = new LinkedList<Coord>();
    	 Q.add(end);
    	 visited.put(end, true);
    	 while(Q.size()!=0){
    		 Coord u = Q.remove();
    		 int xyz = board.getValue(end);
    		 adjs = board.adj(u);
    		 for(Coord c : adjs){
    			 
    			 if((! board.isObstacle(c)) && ((board.getValue(c) == xyz) || board.getValue(c)==0) ){ 
    				 if(! visited.get(c)){
    					 //System.out.println(c + " " +  u + " " + xyz);
    					 parent.put(c, u);
    					 Q.add(c);
    					 visited.put(c, true);
    				 }
    			 }
    		 }
    	 }
    	 
    	 Coord c = parent.get(new Coord(2,2));
    	 

    	 
    	 
    	 board.putValue(start, value);
    	 
    	 

    	 if(!(parent.get(start).equals(start))){
    		 single_path.add(start);
    	 if(! end.equals(start)){
    	 do{
    		 Coord cord = parent.get(start);
    		 board.putValue(cord, value);
    		 single_path.add(cord);
    		 start = cord;
    	
    	 }while(! end.equals(start));
    	 }
    	 }else{
    		 single_path.add(start);
    		 single_path.add(end);
    	 }

    	 result.add(single_path);
    	board.pretty_print_grid(); 
    	 
    	 
    	 }

    	 
    	 
    	 
    	
	return result;
    	
    	

    }

}

