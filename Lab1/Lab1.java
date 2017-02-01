/*Implement a Java program that consumes a space-separated list of numbers 
 * representing daily rainfall amounts as entered by a user. The list may 
 * contain the number -999 indicating the end of the data of interest.
 * Output the average of the non-negative values in the list up to the first -999 
 * (if it shows up).  There may be negative numbers other than -999 in the list.
 * */

import java.io.*;
import java.util.*;

public class Lab1 {

	public static void main(String args[]) {
		Scanner sc = new Scanner(System.in);
		//main arraylist of all rainfall values
		ArrayList a = new ArrayList();
		int sum = 0;
		int count = 0;
		int temp =0;
		System.out.println("Enter the space separated data for daily rainfall amount  : - ");
		//Take space separated data input in a string 
		String s = sc.nextLine();
		int j =0;
		try{
		     //Use a tokenizer and space as delimiter
		     StringTokenizer t = new StringTokenizer(s, " ");
	         while (t.hasMoreTokens()){
	        	  //Store data in the arraylist by parsing the object as int
	                a.add(Integer.parseInt(t.nextToken()));
	                if((int) a.get(j)== -999){
	            	   break;
	                }
	                j++;
	         }
	            
	         System.out.println("The data entered is  :- " +Arrays.toString(a.toArray()));
	         for(int i=0;i<a.size() && (int)a.get(i)!= -999;i++){   
	    	     if((int)a.get(i)> 0){
	    		 sum = sum + (int) a.get(i); //Calculate the sum of all positive values
	    		 count++; //counter to keep track of number of positive values
	    	     }
	    	 
	         }
		    }catch(Exception e){System.out.println("Please enter real numbers only !");System.exit(0);}

		//Calculate the average
		int  avg = sum/count;
                System.out.println("The average daily rainfall amount is : - " +avg);
	}
}

