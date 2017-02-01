
import java.util.HashMap;
import java.util.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by mvc on 10/23/2015.
 */
public class FloodFunction {
    public Driver driver;
    public List<Coord> flooded_list = new LinkedList<>();
    boolean track [][];
    public FloodFunction(final Driver _driver) {
        this.driver = _driver;
        flooded_list.add(new Coord(0, 0));
        track = new boolean [driver.size][driver.size];
    }

    public boolean inbound(final Coord coord) {
        return coord.x > -1 && coord.x < this.driver.size && coord.y > -1 && coord.y < this.driver.size;
    }

    public Coord up(final Coord coord) {
        return new Coord(coord.x, coord.y-1);
    }

    public Coord down(final Coord coord) {
        return new Coord(coord.x, coord.y+1);
    }

    public Coord left(final Coord coord) {
        return new Coord(coord.x-1, coord.y);
    }

    public Coord right(final Coord coord) {
        return new Coord(coord.x+1, coord.y);
    }
    
    public int getx(final Coord c) {
        return c.x;
    }

    public int gety(final Coord c) {
        return c.y;
    }

    public void flood(final Map color_of_tiles, final Integer color) {
    	//Coord c = flooded_list.get(0);
    	
    	for(int i= 0;i<flooded_list.size();i++){
    		Coord c = flooded_list.get(i);
    		if (inbound(down(c)) && color_of_tiles.get(c) == color_of_tiles.get(down(c)) && track[down(c).x][down(c).y]== false ){
    				flooded_list.add(down(c));
    				track[down(c).x][down(c).y]= true;
    			}
    		
    		if (inbound(up(c)) && color_of_tiles.get(c) == color_of_tiles.get(up(c)) && track[up(c).x][up(c).y]== false) {
    				flooded_list.add(up(c));
    				track[up(c).x][up(c).y]= true;
    			}
    		
    		if (inbound(left(c)) && color_of_tiles.get(c) == color_of_tiles.get(left(c))&& track[left(c).x][left(c).y]== false){
    			    flooded_list.add(left(c));
    				track[left(c).x][left(c).y]= true;
    			}
    		
    		if (inbound(right(c)) && color_of_tiles.get(c) == color_of_tiles.get(right(c)) && track[right(c).x][right(c).y]== false) {
      				flooded_list.add(right(c));
    				track[right(c).x][right(c).y]= true;
    			}
    			
    			
    	}
    	//System.out.println(Arrays.toString(flooded_list.toArray()));
    }
    
    public void flood1(final Map color_of_tiles, final Integer color) {
    	//Coord c = flooded_list.get(0);
    	
    	for(int i= 0;i<flooded_list.size();i++){
    		Coord c = flooded_list.get(i);
    		if (inbound(down(c)) && color_of_tiles.get(c) == color_of_tiles.get(down(c)) &&!flooded_list.contains(down(c))){
    			flooded_list.add(down(c));
    		}
    		if (inbound(up(c)) && color_of_tiles.get(c) == color_of_tiles.get(up(c)) && !flooded_list.contains(up(c))) {
    			flooded_list.add(up(c));
    		}
    		if (inbound(left(c)) && color_of_tiles.get(c)==color_of_tiles.get(left(c)) && !flooded_list.contains(left(c))){
     			flooded_list.add(left(c));
    		}
    		if (inbound(right(c)) && color_of_tiles.get(c) == color_of_tiles.get(right(c)) && !flooded_list.contains(right(c))) {
    			flooded_list.add(right(c));
    		}	
    	}
    	//System.out.println(Arrays.toString(flooded_list.toArray()));
    }
}
