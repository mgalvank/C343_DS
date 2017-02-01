
import java.util.*;

public class Carve{



static int carve_seam(int[][] D, ArrayList<Coord> seam) { 
	int least_cost = 1000; //i set this high bc the cost has to be less than to replace it as the best cost
	int test_cost = 0;
	ArrayList<Coord> test_seam;
	for(int i = 0; i != D[0].length; ++i){
		Coord pos = new Coord(0,i);
		test_seam = carve_seam_helper(pos, D ); //for each column in the first row, call the helper
		for(Coord coord:test_seam){ //finding the total cost of the path
			test_cost += D[coord.x][coord.y];
		}if(test_cost < least_cost){ //if test cost is lower than current cost, replace the test cost and the seam. Else reset test cost to zero for next iteration
			least_cost = test_cost;
			seam = test_seam;
			test_cost = 0;
		}
	}return least_cost;
	}
	  

static ArrayList<Coord> carve_seam_helper(Coord i, int[][]D){
	ArrayList<Coord> test_seam = new ArrayList<Coord>();
	test_seam.add(i);
	if(i.y < 0 || i.y > D.length){ //if the new coordinate is out of vertical bounds
		return test_seam;
	}else{
		if(i.x > 0 && i.x < D[0].length){ //if the new coordinate is within horizontal bounds
			test_seam.add(i);
			Coord j = new Coord(i.x-1,i.y-1); //calling helper for row down, and to the left
			carve_seam_helper(j, D); 
			Coord k = new Coord(i.x, i.y-1);//calling helper for directly below
			carve_seam_helper(k, D);
			Coord l = new Coord(i.x+1, i.y-1);//calling helper for row down, to the right
			carve_seam_helper(l,D);
		}
	}
	return test_seam;
}


public static void main(String[] args){
	int[][] D = new int[3][3];
    D[0][0] = 0; D[0][1] = 1; D[0][2] = 1;
    D[1][0] = 1; D[1][1] = 0; D[1][2] = 1;
    D[2][0] = 1; D[2][1] = 1; D[2][2] = 0;

    ArrayList<Coord> seam = new ArrayList<Coord>();
    int disruption = carve_seam(D, seam);
    assert disruption == 0;
    for (int i = 0; i != seam.size(); ++i) {
    Coord c = seam.get(i);
    //System.out.println(disruption);
    assert c.x == i;
    assert c.y == 1;
    }
    
    int[][] D2 = new int[3][3];
    D2[0][0] = 1; D2[0][1] = 1; D2[0][2] = 2;
    D2[1][0] = 1; D2[1][1] = 0; D2[1][2] = 1;
    D2[2][0] = 1; D2[2][1] = 0; D2[2][2] = 1;

    ArrayList<Coord> seam2 = new ArrayList<Coord>();
    int disruption2 = carve_seam(D2, seam2);
    System.out.println(disruption2);
    assert disruption2 == 0;
    for (int i = 0; i != seam2.size(); ++i) {
    Coord c2 = seam2.get(i);
    assert c2.x == i;
    assert c2.y == i;
    }
}

}
