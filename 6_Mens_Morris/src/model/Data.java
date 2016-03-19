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
}
