import java.util.*;
import java.lang.*;

class Algo {
    public static void main(String[] args) {
    	// Fake beer data and puts them all into an arrray of beers
        int[] beer1 = {1, 5, 7, 8, 9, 10, 1, 5, 9, 7};
        int[] beer2 = {2, 9, 9, 1, 5, 8, 9, 10, 9, 2};
        int[] beer3 = {5, 5, 5, 5, 5, 5, 5, 5, 5, 5};
        int[] beer4 = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        int[] beer5 = {10, 10, 10, 10, 10, 10, 10, 10, 10, 10};
        int[][] beerArray = {beer1, beer2, beer3, beer4, beer5};

        // Fake user pref
        int[] userPref = {10, 9, 8, 10, 8, 10, 10, 10, 10, 8};

        // Calls the recommendation algorithm
        // Params(int[][], int[])
        // Returns int[]
        int[] rec = recommend(beerArray, userPref);

        // Prints out the recommended array
        System.out.println(Arrays.toString(rec));
    }

    public static int[] recommend(int[][] beers, int[] user) {
    	// Creates an array for storing the deviation of each array vs user pref
    	int[] beerDeviation = new int[beers.length];
    	for(int i = 0; i < beers.length; ++i) { beerDeviation[i] = 0; }

    	// Goes through each array and generates the beer vs user pref deviation
    	int beerNum = 0;
    	for (int[] array : beers) {
        	for(int i = 0; i < array.length; ++i) {
        		int diff = array[i] - user[i];
        		if(Math.abs(diff) > beerDeviation[beerNum]) beerDeviation[beerNum] = Math.abs(diff); 
        	}
        	++beerNum;
        }

        // Finds the beer with the lowest deviation
        int closest = beerDeviation[0];
        int closestPos = 0;

        for(int i = 1; i < beers.length; ++i) {
        	if(beerDeviation[i] < closest) {
        		closest = beerDeviation[i];
        		closestPos = i;
        	}
        }

        // Checks if there are more than one beer with the same deviation
        int numOfClosest = 0;
        for(int i = 0; i < beers.length; ++i) {
        	if(beerDeviation[i] == closest) ++numOfClosest;
        }

        // In the case that more than one beer has same smallest deviation number
        // Find the beer with the lowest deviation out of the 2 smallest
        // If there are multiple beers with the same lowest deviation, give the user a random beer from that group
        if(numOfClosest > 1) {
        	beerNum = 0;
	    	for (int[] array : beers) {
	    		int sum = 0;
	        	for(int i = 0; i < array.length; ++i) {
	        		int diff = array[i] - user[i];
	        		sum += Math.abs(diff);
	        	}
	        	beerDeviation[beerNum++] = sum;
	        }

	        closest = beerDeviation[0];
	        closestPos = 0;

	        for(int i = 1; i < beers.length; ++i) {
	        	if(beerDeviation[i] < closest) {
	        		closest = beerDeviation[i];
	        		closestPos = i;
	        	}
	        }
        }

        // Returns the best recommended beer array
        return beers[closestPos];
    }
}