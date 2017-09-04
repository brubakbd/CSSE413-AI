import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Sudoku {

	private static int boardSize = 0;
	private static int partitionSize = 0;
	private static int[] domain = null;
	private static int[][] vals = null;
	
	public static void main(String[] args){
		String filename = args[0];
		File inputFile = new File(filename);
		Scanner input = null;

		int temp = 0;
    	int count = 0;
    	
	    try {
			input = new Scanner(inputFile);
			temp = input.nextInt();
			boardSize = temp;
			partitionSize = (int) Math.sqrt(boardSize);
			System.out.println("Boardsize: " + temp + "x" + temp);
			vals = new int[boardSize][boardSize];
			domain = new int[boardSize];
			for(int i = 0; i < boardSize; i++)
				domain[i] = i+1;
			
			System.out.println("Input:");
	    	int i = 0;
	    	int j = 0;
	    	while (input.hasNext()){
	    		temp = input.nextInt();
	    		count++;
	    		System.out.printf("%3d", temp);
	    		vals[i][j] = temp;
				if (temp == 0) {
					// TODO
				} 
				j++;
				if (j == boardSize) {
					j = 0;
					i++;
					System.out.println();
				}
				if (j == boardSize) {
					break;
				}
	    	}
	    	input.close();
	    } catch (FileNotFoundException exception) {
	    	System.out.println("Input file not found: " + filename);
	    }
	    if (count != boardSize*boardSize) throw new RuntimeException("Incorrect number of inputs.");
	    
	    
		boolean solved = solve(vals);
		
		// Output
		if (!solved) {
			System.out.println("No solution found.");
			return;
		}
		System.out.println("\nOutput\n");
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				System.out.printf("%3d", vals[i][j]);
			}
			System.out.println();
		}		
		
	}
	
	public static boolean solve(int[][] sud){
		if (complete(sud)){
			vals = sud;
			return true;
		}
		int[] loc = selectNext(sud);
		
		for(int n: domain){
			if(consistent(sud, loc, n)){
				sud[loc[0]][loc[1]] = n;
				if(solve(sud))
					return true;
			}
			sud[loc[0]][loc[1]] = 0;
		}
		return false;
	}
	
	public static boolean consistent(int[][] sud, int[] loc, int val){
		for(int j = 0; j < boardSize; j++){
			if(sud[loc[0]][j] == val)
				return false;
		}
		for(int i = 0; i < boardSize; i++){
			if(sud[i][loc[1]] == val)
				return false;
		}
		int size = (int) Math.sqrt(boardSize);
		int n = loc[0]/size;
		int m = loc[1]/size;
		for(int i = n*3; i < (n+1)*3; i ++){
			for(int j = m*3; j < (m+1)*3; j++){
				if(sud[i][j] == val)
					return false;
			}
		}
		return true;
	}
	
	public static int[] selectNext(int[][] sud){
		int[] loc = {-1, -1};
		for(int i = 0; i < boardSize; i++){
			for(int j = 0; j < boardSize; j++){
				if (sud[i][j] == 0){
					loc[0] = i;
					loc[1] = j;
					return loc;
				}
			}
		}
		return loc;
	}
		
	public static boolean complete(int[][] sud){
		for(int i = 0; i < boardSize; i++){
			for(int j = 0; j < boardSize; j++){
				if (sud[i][j] == 0)
					return false;
			}
		}
		return true;
	}
}