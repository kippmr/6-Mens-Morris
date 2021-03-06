package model;
// import statements
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Random;
import java.util.Scanner;

import controller.AIHandler;


/**
 * contains data of board and nodes
 */
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
	
	// AI variables
	private static boolean isAIOn = false;
	private static AIHandler aiBot = null;
	
	/**
	 * @return number of pieces during placement phase
	 */
	public static int getNumPieces(){
		return numPieces;
	}

	/**
	 * @return number of blue Nodes
	 */
	public static int getBlueCount(){
		return blueCount;
	}
	
	/**
	 * @return numbe of red Nodes
	 */
	public static int getRedCount(){
		return redCount;
	}
	
	// Set state
	/**
	 * @param nextState - set game phase to this
	 */
	public static void setState(GameState nextState){
		curState = nextState;
	}
	
	// Get current state
	/**
	 * @return current game phase
	 */
	public static GameState getState(){
		return curState;
	}
	
	/**
	 * @param layer - layer of Node
	 * @param index - position of Node
	 * @return colour of selected node
	 */
	public static String getColour(int layer, int index){
		return nodes[layer][index].getColour();
	}
	
	/**
	 * Set colour of selected node
	 * @param layer - layer of Node
	 * @param index - position of Node
	 */
	public static void setColour(int layer, int index){
		//set turn colour
		String col = (isBlueTurn)? "blue" : "red";
		nodes[layer][index].setColour(col);
		//increase counters
		if (curState.equals(GameState.PLACEMENT)){
			if (col.equals("blue")) blueCount++;
			else redCount++;
			numPieces++;
		}
	}
	
	/**
	 * make new moveNode
	 * @param layer - layer of Node
	 * @param index - position of Node
	 * @param col - colour of turn
	 */
	public static void setMove(int layer, int index, String col){
		moveNode = new Node(layer, index, col);
	}
	
	/**
	 * check if moveNode is adjacent to next position
	 * @param layer - layer of Node
	 * @param index - position of Node
	 * @return true if Nodes are adjacent
	 */
	public static boolean canMove(int layer, int index){
		return moveNode.isConnected(nodes[layer][index]);
	}

	/**
	 * swaps coloured Node with 'black' Node
	 * @param layer - layer of Node
	 * @param index - position of Node
	 */
	public static void swapNode(int layer, int index) {
		nodes[moveNode.getLayer()][moveNode.getPosition()].setColour("black");
		nodes[layer][index].setColour(moveNode.getColour());
		moveNode = null;
	}
	
	/**
	 * return all nodes to black and move counters to zero
	 */
	public static void reset(){
		//change colour of all Nodes to 'black'
		for (int i = 0; i < nodes.length; i++){
			for (int j = 0; j < nodes[i].length; j++){
				nodes[i][j] = new Node(i,j, "black");
			}
		}
		curState = GameState.PLACEMENT;
		numPieces = 0;
		redCount = 0;
		blueCount = 0;
		moveNode = null;
		isAIOn = false;
		aiBot = null;
		chooseTurn();
	}
	
	/**
	 * Get turn
	 * @return true if it is 'blue's turn
	 */
	public static boolean getTurn(){
		return isBlueTurn;
	}
	
	/**
	 * Next turn
	 */
	public static void changeTurn(){
		isBlueTurn = !isBlueTurn;
	}
	
	/**
	 * Force different turn
	 * @param col - colour of next turn
	 */
	public static void changeTurn(String col){
		if (col.equals("blue"))
			isBlueTurn = true;
		else
			isBlueTurn = false;
	}
	
	/**
	 * Randomize turn order
	 */
	private static void chooseTurn(){
		Random random = new Random();
		isBlueTurn = random.nextInt(2) == 0;
	}
	
	/**
	 * determine if a player has won
	 * @return true if a player has met win conditions
	 */
	public static boolean checkWin(){
		//two pieces remaining after placement phase
		if ((redCount == 2 || blueCount == 2) && numPieces == 12) return true;
		String col = (isBlueTurn)? "blue" : "red";
		//check if all cooloured pieces can move
		for (int i = 0; i < nodes.length; i++) {
			for (int j = 0; j < nodes[i].length; j++) {
				if (nodes[i][j].getColour().equals(col)){
					if (ableToMove(i, j)) return false;
				}
			}
		}
		
		return true;
	}
	
	/**
	 * check if a specific node is part of a triple
	 * @param layer - layer of Node
	 * @param index - position of Node
	 * @return true if Node is part of a triple
	 */
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
	
	/**
	 * perform a mill
	 * @param layer - layer of Node
	 * @param index - position of Node
	 */
	public static void mill(int layer, int index){
		//change colour to black
		String col = nodes[layer][index].getColour();
		nodes[layer][index].setColour("black");
		//reduce colour count
		if (col.equals("red")) redCount--;
		else blueCount--;
	}

	/**
	 * @return true if moveNode has a value
	 */
	public static boolean hasMove() {
		return moveNode != null;
	}
	
	/**
	 * @param layer - layer of Node
	 * @param index - position of Node
	 * @return true if a Node can move
	 */
	private static boolean ableToMove(int layer, int index){
		//odd positioned Node check left and right
		if (index % 2 == 1){
			if (layer == 1){
				if (nodes[0][index].getColour().equals("black")) return true;
			}
			else if (layer == 0){
				if (nodes[1][index].getColour().equals("black")) return true;
			}
		}
		// position 0 case
		if (index == 0){
			if (nodes[layer][7].getColour().equals("black")) return true;
		}
		else if (nodes[layer][index-1].getColour().equals("black")) return true;
		//position 7 case
		if (index == 7){
			if (nodes[layer][0].getColour().equals("black")) return true;
		}
		else if (nodes[layer][index+1].getColour().equals("black")) return true;
		
		return false;
	}

	/**
	 * save game to savefile.txt in root
	 */
	public static void save() {
		//move data into csv style string
		String out = "";
		out = out.concat(curState.toString() + ",");
		out = out.concat(Boolean.toString(isBlueTurn) + ",");
		out = out.concat(Integer.toString(numPieces) + ",");
		out = out.concat(Integer.toString(blueCount) + ",");
		out = out.concat(Integer.toString(redCount) + ",");
		out = out.concat(Boolean.toString(isAIOn) + "\n");
		
		for (int i = 0; i < nodes[0].length-1; i++) {
			out = out.concat(nodes[0][i].getColour() + ",");
		}
		out = out.concat(nodes[0][7].getColour() + "\n");
		
		for (int i = 0; i < nodes[1].length-1; i++) {
			out = out.concat(nodes[1][i].getColour() + ",");
		}
		out = out.concat(nodes[1][7].getColour() + "\n");
		
		try{
			//writing results to output.txt
			FileWriter f = new FileWriter("savefile.txt");
			f.write(out);
			f.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * load game from savefile.txt in root
	 * @throws FileNotFoundException
	 */
	public static void load() throws FileNotFoundException{
		//load data from file into memory
		File f = new File("savefile.txt");
		Scanner s = new Scanner(f);
		String[] data = s.nextLine().split(",");
		String[] outerCol = s.nextLine().split(",");
		String[] innerCol = s.nextLine().split(",");
		s.close();
		
		//restore game state
		if (data[0].equals("PLACEMENT"))
			setState(GameState.PLACEMENT);
		else if (data[0].equals("MOVEMENT"))
			setState(GameState.MOVEMENT);
		else if (data[0].equals("MILL"))
			setState(GameState.MILL);
		else if (data[0].equals("ENDGAME"))
			setState(GameState.ENDGAME);
		
		//restore turn
		if (data[1].equals("true"))
			isBlueTurn = true;
		else isBlueTurn = false;

		//restore counts
		numPieces = Integer.parseInt(data[2]);
		blueCount = Integer.parseInt(data[3]);
		redCount = Integer.parseInt(data[4]);
		setAI(Boolean.parseBoolean(data[5]));

		//restore board
		for (int i = 0; i < 8; i++){
			nodes[0][i].setColour(outerCol[i]);
			nodes[1][i].setColour(innerCol[i]);
		}
		
		moveNode = null;
	}
	
	/**
	 * AI setup on new game and load
	 * @param b - true if AI is playing
	 */
	public static void setAI(boolean b){
		isAIOn = b;
		if (b) aiBot = new AIHandler();
	}
	
	/**
	 * tell AI to make a move
	 */
	public static void moveAI(){
		if (isAIOn) aiBot.makeMove(isBlueTurn);
	}
}
