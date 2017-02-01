public class AlignmentFunction {
	static int mscr;
	static int dscr;
	static int iscr;
	static int scr;

    static Result[][] align(final String a1, final String a2, StringBuilder a1_alignd, StringBuilder a2_alignd) {

			Result[][] result = new Result[a1.length()+1][a2.length()+1];
			
			result[0][0] = new Result(0,Mutation.M);
			

			for(int i = 1;i<a1.length()+1;i++){
				result[0][i] = new Result((result[0][i-1].score)-1,Mutation.I);
			}
			
			for(int i = 1;i<a2.length()+1;i++){
				result[i][0] = new Result((result[i-1][0].score)-1,Mutation.D);
			}
			
			

			for(int i =1;i<a1.length()+1;i++){
				for(int j=1;j<a2.length()+1;j++){
					mscr = Driver.match(a1.charAt(i-1),a2.charAt(j-1)) + result[i-1][j-1].score;
					dscr = -1 + result[i][j-1].score;
					iscr = -1  + result[i-1][j].score;
					scr = Math.max(mscr,Math.max(dscr, iscr));
					if(scr == mscr){
						result[i][j] = new Result(scr,Mutation.M);
					}
					else if(scr == iscr){
						result[i][j] = new Result(scr,Mutation.I);
					}
					else {
						result[i][j] = new Result(scr,Mutation.D);
					}				
				}
			}
			
			
			
			int j = result.length -2;
	        int i = j;
	        int b1 = a1.length()-1;
	        int b2 = a2.length()-1;
	        
	         
		 
	        while(j > -1 || i > -1) {
	        	
		    if (j>-1 && i>-1 && (result[i+1 ][j+1 ].dir == Mutation.M)) {
		    	
		    		j -= 1;
	                i -= 1;  
	                a1_alignd.append(a1.charAt(b1));
	                a2_alignd.append(a2.charAt(b2));
	                b1  = b1 - 1;
	                b2 = b2 -1;
	            } else if (j > -1 && (result[i+1 ][j+1 ].dir == Mutation.D)) {
	            	
	                
	                
	                a1_alignd.append(a1.charAt(b1));
	                b1=b1-1;
	                a2_alignd.append("_");
	            
	                j -= 1;
	            } else if (i > -1 && (result[i +1][j+1 ].dir == Mutation.I)) {
	            	a1_alignd.append("_");
	                b2=b2-1;
	            
	                i -= 1;
	            } else {
	                System.out.println(j);
	                System.out.println(i);
	                break;
	            }
	        }
			
    	
    	
    	return result;
	

    }
}
