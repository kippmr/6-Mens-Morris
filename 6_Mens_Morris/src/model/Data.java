package model;
import java.util.Random;


public class Data {

	// Node variables
	final private static int TOTAL_LAYERS = 2;
	final private static int TOTAL_POSITIONS = 8;
	static private Node[][] nodes = new Node[TOTAL_LAYERS][TOTAL_POSITIONS];
	static private Node moveNode;
	
	// Piece Counts
	private static int blueCount;
	private static int redCount;
	private static int numPieces;
	
	// Turn Order
	static private boolean isBlueTurn;
	
	// Current state
	private static GameState curState;
	
	public static int getBlueCount(){
		return blueCount;
	}
	
	public static int getRedCount(){
		return redCount;
	}
	
	public static int getNumPieces(){
		return numPieces;
	}
	
	public static void increaseCount(String col){
		if (col.equals("blue")) blueCount++;
		else redCount++;
	}
	
	public static void decreaseCount(String col){
		if (col.equals("blue")) blueCount--;
		else redCount--;
	}
	
	// Set state
	public static void setState(GameState nextState){
		curState = nextState;
	}
	
	// Get current state
	public static GameState getState(){
		return curState;
	}
	
	// Return colour of selected node
	public static String getColour(int layer, int index){
		return nodes[layer][index].getColour();
	}
	
	// Set colour of selected node
	public static void setColour(int layer, int index){
		String col = (isBlueTurn)? "blue" : "red";
		nodes[layer][index].setColour(col);
		if (curState.equals(GameState.PLACEMENT)){
			increaseCount(col);
			numPieces++;
		}
	}
	
	public static void setMove(int layer, int index, String col){
		moveNode = new Node(layer, index, col);
	}
	
	public static boolean canMove(int layer, int index){
		return moveNode.isConnected(nodes[layer][index]);
	}

	public static void swapNode(int layer, int index) {
		nodes[moveNode.getLayer()][moveNode.getPosition()].setColour("black");
		nodes[layer][index] = moveNode;
		moveNode = null;
	}
	
	// return all nodes to black
	public static void reset(){
		for (int i = 0; i < nodes.length; i++){
			for (int j = 0; j < nodes[i].length; j++){
				nodes[i][j] = new Node(i,j, "black");
			}
		}
		redCount = 0;
		blueCount = 0;
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
	
	//determine if a player has won
	private static boolean checkWin(){
		
		return false;
	}
	
	//check for any triples on board
	public static String boardTriple(){
		String selectedCol;
		
		//check outer layer
		selectedCol = nodes[0][0].getColour();
		if (nodes[0][1].getColour().equals(selectedCol) && nodes[0][2].getColour().equals(selectedCol))
			return selectedCol;
		selectedCol = nodes[0][0].getColour();
		if (nodes[0][7].getColour().equals(selectedCol) && nodes[0][6].getColour().equals(selectedCol))
			return selectedCol;
		selectedCol = nodes[0][4].getColour();
		if (nodes[0][3].getColour().equals(selectedCol) && nodes[0][2].getColour().equals(selectedCol))
			return selectedCol;
		selectedCol = nodes[0][4].getColour();
		if (nodes[0][5].getColour().equals(selectedCol) && nodes[0][6].getColour().equals(selectedCol))
			return selectedCol;	
		
		//check inner layer
		selectedCol = nodes[1][0].getColour();
		if (nodes[1][1].getColour().equals(selectedCol) && nodes[1][2].getColour().equals(selectedCol))
			return selectedCol;
		selectedCol = nodes[1][0].getColour();
		if (nodes[1][7].getColour().equals(selectedCol) && nodes[1][6].getColour().equals(selectedCol))
			return selectedCol;
		selectedCol = nodes[1][4].getColour();
		if (nodes[1][3].getColour().equals(selectedCol) && nodes[1][2].getColour().equals(selectedCol))
			return selectedCol;
		selectedCol = nodes[1][4].getColour();
		if (nodes[1][5].getColour().equals(selectedCol) && nodes[1][6].getColour().equals(selectedCol))
			return selectedCol;
		
		//when nothing is true
		return "none";
	}
	
	//check if a specific node is part of a triple
	public static boolean singleTriple(int layer, int index){
		String selectedCol = nodes[layer][index].getColour();
		
		//check outer layer
		if (layer == 0){
			if (index == 0) {
				if (nodes[0][1].getColour().equals(selectedCol) && nodes[0][2].getColour().equals(selectedCol))
					return true;
				if (nodes[0][7].getColour().equals(selectedCol) && nodes[0][6].getColour().equals(selectedCol))
					return true;
			} if (index == 1) {
				if (nodes[0][0].getColour().equals(selectedCol) && nodes[0][2].getColour().equals(selectedCol))
					return true;
			} if (index == 2) {
				if (nodes[0][0].getColour().equals(selectedCol) && nodes[0][1].getColour().equals(selectedCol))
					return true;
				if (nodes[0][3].getColour().equals(selectedCol) && nodes[0][4].getColour().equals(selectedCol))
					return true;
			} if (index == 3) {
				if (nodes[0][2].getColour().equals(selectedCol) && nodes[0][4].getColour().equals(selectedCol))
					return true;
			} if (index == 4) {
				if (nodes[0][2].getColour().equals(selectedCol) && nodes[0][3].getColour().equals(selectedCol))
					return true;
				if (nodes[0][5].getColour().equals(selectedCol) && nodes[0][6].getColour().equals(selectedCol))
					return true;
			} if (index == 5) {
				if (nodes[0][4].getColour().equals(selectedCol) && nodes[0][6].getColour().equals(selectedCol))
					return true;
			} if (index == 6) {
				if (nodes[0][4].getColour().equals(selectedCol) && nodes[0][5].getColour().equals(selectedCol))
					return true;
				if (nodes[0][7].getColour().equals(selectedCol) && nodes[0][0].getColour().equals(selectedCol))
					return true;
			}if (index == 7) {
				if (nodes[0][6].getColour().equals(selectedCol) && nodes[0][0].getColour().equals(selectedCol))
					return true;
			}
		}
		//check inner layer
		else if (layer == 1){
			if (index == 0) {
				if (nodes[1][1].getColour().equals(selectedCol) && nodes[1][2].getColour().equals(selectedCol))
					return true;
				if (nodes[1][7].getColour().equals(selectedCol) && nodes[1][6].getColour().equals(selectedCol))
					return true;
			} if (index == 1) {
				if (nodes[1][0].getColour().equals(selectedCol) && nodes[1][2].getColour().equals(selectedCol))
					return true;
			} if (index == 2) {
				if (nodes[1][0].getColour().equals(selectedCol) && nodes[1][1].getColour().equals(selectedCol))
					return true;
				if (nodes[1][3].getColour().equals(selectedCol) && nodes[1][4].getColour().equals(selectedCol))
					return true;
			} if (index == 3) {
				if (nodes[1][2].getColour().equals(selectedCol) && nodes[1][4].getColour().equals(selectedCol))
					return true;
			} if (index == 4) {
				if (nodes[1][2].getColour().equals(selectedCol) && nodes[1][3].getColour().equals(selectedCol))
					return true;
				if (nodes[1][5].getColour().equals(selectedCol) && nodes[1][6].getColour().equals(selectedCol))
					return true;
			} if (index == 5) {
				if (nodes[1][4].getColour().equals(selectedCol) && nodes[1][6].getColour().equals(selectedCol))
					return true;
			} if (index == 6) {
				if (nodes[1][4].getColour().equals(selectedCol) && nodes[1][5].getColour().equals(selectedCol))
					return true;
				if (nodes[1][7].getColour().equals(selectedCol) && nodes[1][0].getColour().equals(selectedCol))
					return true;
			}if (index == 7) {
				if (nodes[1][6].getColour().equals(selectedCol) && nodes[1][0].getColour().equals(selectedCol))
					return true;
			}
		}
		
		//when nothing is true
		return false;
	}
	
	//perform a mill
	private static void mill(){
		
	}
}
