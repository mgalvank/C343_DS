Implementation.
I have created a boolean 2d array with the size of the choosen grid with default values as false.
I am looping through the flood list till it has elements.
In the loop, I am checking the following conditions :-
1.) If the neighbouring tiles is inbound
2.) If the color of the tile matches with the color of the 
neighbouring tile.
3.) Check the boolean 2d array to see if the (x,y) co-ordinate
 of the tile is false (not visited)
If all the above conditions are satisfied, I am adding the tile to the flood_list
and also making the (x,y) co-ordinate of the tile true in the boolean 2d array.


Analysis
My graph is polynomial. It fits the f(n) = n^2.
According to me, the for loop used to iterate through the flood list is causing the slow
processing. Also due to some bug, the yellow tiles are not getting flooded properly.
Everything except the yellow tiles works properly. Due to this, the program takes
more time to compute the solution

