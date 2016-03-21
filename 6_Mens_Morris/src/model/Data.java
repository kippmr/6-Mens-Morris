package model;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Random;
import java.util.Scanner;


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
			if (col.equals("blue")) blueCount++;
			else redCount++;
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
		nodes[layer][index].setColour(moveNode.getColour());
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
	public static void changeTurn(){
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
		isBlueTurn = random.nextInt(2) == 0;
	}
	
	//determine if a player has won
	public static boolean checkWin(){
		if ((redCount == 2 || blueCount == 2) && numPieces == 12) return true;
		String col = (isBlueTurn)? "blue" : "red";
		
		for (int i = 0; i < nodes.length; i++) {
			for (int j = 0; j < nodes[i].length; j++) {
				if (nodes[i][j].getColour().equals(col)){
					if (ableToMove(i, j)) return false;
				}
			}
		}
		
		return true;
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
	public static void mill(int layer, int index){
		String col = nodes[layer][index].getColour();
		nodes[layer][index].setColour("black");
		if (col.equals("red")) redCount--;
		else blueCount--;
	}

	public static boolean hasMove() {
		return moveNode != null;
	}
	
	private static boolean ableToMove(int layer, int index){
		if (index % 2 == 1){
			if (layer == 1){
				if (nodes[0][index].getColour().equals("black")) return true;
			}
			else if (layer == 0){
				if (nodes[1][index].getColour().equals("black")) return true;
			}
		}
		if (index == 0){
			if (nodes[layer][7].getColour().equals("black")) return true;
		}
		else if (nodes[layer][index-1].getColour().equals("black")) return true;
		
		if (index == 7){
			if (nodes[layer][0].getColour().equals("black")) return true;
		}
		else if (nodes[layer][index+1].getColour().equals("black")) return true;
		
		return false;
	}

	public static void save() {
		String out = "";
		out = out.concat(curState.toString() + ",");
		out = out.concat(Boolean.toString(isBlueTurn) + ",");
		out = out.concat(Integer.toString(numPieces) + ",");
		out = out.concat(Integer.toString(blueCount) + ",");
		out = out.concat(Integer.toString(redCount) + "\n");
		
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
	
	public static void load() throws FileNotFoundException{
		File f = new File("savefile.txt");
		Scanner s = new Scanner(f);
		String[] data = s.nextLine().split(",");
		String[] outerCol = s.nextLine().split(",");
		String[] innerCol = s.nextLine().split(",");
		s.close();
		
		if (data[0].equals("SANDBOX"))
			setState(GameState.SANDBOX);
		else if (data[0].equals("PLACEMENT"))
			setState(GameState.PLACEMENT);
		else if (data[0].equals("MOVEMENT"))
			setState(GameState.MOVEMENT);
		else if (data[0].equals("MILL"))
			setState(GameState.MILL);
		else if (data[0].equals("ENDGAME"))
			setState(GameState.ENDGAME);
		
		if (data[1].equals("blue"))
			isBlueTurn = true;
		else isBlueTurn = false;

		numPieces = Integer.parseInt(data[2]);
		blueCount = Integer.parseInt(data[3]);
		redCount = Integer.parseInt(data[4]);

		for (int i = 0; i < 8; i++){
			nodes[0][i].setColour(outerCol[i]);
			nodes[1][i].setColour(outerCol[i]);
		}
	}
}
