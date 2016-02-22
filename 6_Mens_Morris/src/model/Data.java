package model;
import java.util.Random;


public class Data {

	// Node variables
	final private static int TOTAL_LAYERS = 2;
	final private static int TOTAL_POSITIONS = 8;
	static private Node[][] nodes = new Node[TOTAL_LAYERS][TOTAL_POSITIONS];
	
	// Turn Order
	static private boolean isBlueTurn;
	
	// Return colour of selected node
	public static String getColour(int layer, int index){
		return nodes[layer][index].getColour();
	}
	
	// Set colour of selected node
	public static void setColour(int layer, int index){
		String col = (isBlueTurn)? "blue" : "red";
		nodes[layer][index].setColour(col);
	}
	
	// return all nodes to black
	public static void reset(){
		for (int i = 0; i < nodes.length; i++){
			for (int j = 0; j < nodes[i].length; j++){
				nodes[i][j] = new Node(i,j, "black");
			}
		}
		chooseTurn();
	}
	
	// Get turn
	public static boolean getTurn(){
		return isBlueTurn;
	}
	
	// Next turn
	public void changeTurn(){
		isBlueTurn = !isBlueTurn;
	}
	
	// Force different turn
	public static void changeTurn(String col){
		if (col.equals("blue"))
			isBlueTurn = true;
		else
			isBlueTurn = false;
	}
	
	// Randomize turn order
	private static void chooseTurn(){
		Random random = new Random();
		isBlueTurn = random.nextInt(2) % 2 == 0;
	}
}
