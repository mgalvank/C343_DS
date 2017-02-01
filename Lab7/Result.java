public class Result{
	  int cost; 
	  Coord address;
	  Result leftp;
	  Result rightp;
	  Result parentp;
	  Result before;
	  
	  
	  Result(int c, Result l , Result r, Result p, Result b, Coord a){
	    c = cost;
	    l = leftp;
	    r = rightp;
	    p = parentp;
	    a = address;
	    b = before;
	  }
	  


	public int min( Result b, Result c){
	  if(this.cost < b.cost && this.cost < c.cost){
	   return this.cost;
	  }else if(b.cost < c.cost && b.cost < this.cost){
	    return b.cost;
	  }else{
	    return c.cost;
	  
	}
	}
	}
