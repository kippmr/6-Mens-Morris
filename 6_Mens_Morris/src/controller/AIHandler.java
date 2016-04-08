package controller;

import java.util.Random;

import model.Data;
import model.GameState;
import model.Node;
import view.View;

/**
 * calculates AI logic for piece movement in one player mode
 */
public class AIHandler {

	private boolean hasBlueTurn = true; // AI is always blue
	
	/**
	 * constructor, makes a move if AI starts
	 */
	public AIHandler(){
		makeMove(Data.getTurn());
	}
	
	/**
	 * makes move if it is AI's turn based on GameState
	 * @param currentTurn - true if blue turn
	 */
	public void makeMove(boolean currentTurn){
		if (currentTurn == hasBlueTurn){
//			System.out.printf("%d, %d, %d\n", Data.getBlueCount(), Data.getRedCount(), Data.getNumPieces());
			switch (Data.getState()){
			case PLACEMENT:
				placementPhase();
				break;
				
			case MOVEMENT:
				movementPhase();
				break;
				
			case MILL:
				millPhase();
				break;
			}
		}
	}
	
	/**
	 * calculates logic for placement phase, update view
	 */
	private void placementPhase(){
		Node temp = findPlacement();
		
		Data.setColour(temp.getLayer(), temp.getPosition());
		Data.changeTurn();
		//filled board, go to movement phase
		if (Data.getNumPieces() == 12)
			Data.setState(GameState.MOVEMENT);
		//current user makes triple, go to mill state
		if (Data.singleTriple(temp.getLayer(), temp.getPosition())){
			Data.setState(GameState.MILL);
			Data.changeTurn();
		}
		View.update();
	}
	
	/**
	 * calculates logic for movement phase, update view
	 */
	private void movementPhase(){
		Node[] moveNodes = new Node[2];
		moveNodes = findMove();
		Data.setMove(moveNodes[1].getLayer(), moveNodes[1].getPosition(), "blue");
		Data.swapNode(moveNodes[0].getLayer(), moveNodes[0].getPosition());
		if (Data.singleTriple(moveNodes[0].getLayer(), moveNodes[0].getPosition()))
			Data.setState(GameState.MILL);
		else Data.changeTurn();
		//check for winning
		if (Data.checkWin()){
			Data.setState(GameState.ENDGAME);
		}
		View.update();
		
	}
	
	/**
	 * calculates logic for mill phase, update view
	 */
	private void millPhase(){
		Node toMill = findMill();
		Data.mill(toMill.getLayer(), toMill.getPosition());
		//go to movement or placement phase
		if (Data.getNumPieces() == 12) Data.setState(GameState.MOVEMENT);
		else Data.setState(GameState.PLACEMENT);
		Data.changeTurn();
		//check for a win
		if (Data.checkWin()){
			Data.setState(GameState.ENDGAME);
		}
		View.update();
	}
	
	// find a good spot to place a piece
	/**
	 * finds spot to place piece
	 * prioritizes finishing a mill or blocking a mill
	 * @return Node to be placed
	 */
	private Node findPlacement(){
		
		// find blue triple
		Node ret = findOpenTriple("blue");
		
		if (ret != null) return ret;
		
		// find red triple
		ret = findOpenTriple("red");
		
		if (ret != null) return ret;
		
		// place "randomly"
		Random r = new Random();
		while (true){
			int l = r.nextInt(2);
			int i = r.nextInt(8);
			if (Data.getColour(l, i).equals("black"))
				return new Node(l, i, "black");
		}
	}
	
	/**
	 * determine piece to move
	 * @return two Nodes, [0] = Node empty node, [1] = Node to be moved
	 */
	private Node[] findMove(){
		Node[] moveNode = new Node[2];
		
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 8; j+=2) {
				int sum = 0;
				int index = -1;
				if (Data.getColour(i, j).equals("blue")) sum++;
				else if(Data.getColour(i, j).equals("black")) index = j;
				if (Data.getColour(i, j+1).equals("blue")) sum++;
				else if(Data.getColour(i, j+1).equals("black")) index = j+1;
				if (j == 6){
					if (Data.getColour(i, 0).equals("blue")) sum++;
					else if(Data.getColour(i, 0).equals("black")) index = 0;
				}
				else{
					if (Data.getColour(i, j+2).equals("blue")) sum++;
					else if(Data.getColour(i, j+2).equals("black")) index = j+2;
				}
				if (sum == 2 && index != -1){
					int move = checkAdj(i, j, index);
					if (move != -1){
						int layer1 = i;
						int layer2 = i;
						if (move == index){
							layer2 = (i == 1)? 0:1;
						}
						moveNode[0] = new Node(layer1, index, "black");
						moveNode[1] = new Node(layer2, move, "blue");
						if (moveNode[0].isConnected(moveNode[1]))
							return moveNode;
					}
				}
			}
		}
		
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 8; j+=2) {
				int sum = 0;
				int index = -1;
				if (Data.getColour(i, j).equals("red")) sum++;
				else if(Data.getColour(i, j).equals("black")) index = j;
				if (Data.getColour(i, j+1).equals("red")) sum++;
				else if(Data.getColour(i, j+1).equals("black")) index = j+1;
				if (j == 6){
					if (Data.getColour(i, 0).equals("red")) sum++;
					else if(Data.getColour(i, 0).equals("black")) index = 0;
				}
				else{
					if (Data.getColour(i, j+2).equals("red")) sum++;
					else if(Data.getColour(i, j+2).equals("black")) index = j+2;
				}
				if (sum == 2 && index != -1){
					int move = checkAdj(i, j, index);
					if (move != -1){
						int layer1 = i;
						int layer2 = i;
						if (move == index){
							layer2 = (i == 1)? 0:1;
						}
						moveNode[0] = new Node(layer1, index, "black");
						moveNode[1] = new Node(layer2, move, "blue");
						if (moveNode[0].isConnected(moveNode[1]))
							return moveNode;
					}
				}
			}
		}
		
		Random r = new Random();
		while (true){
			int l = r.nextInt(2);
			int i = r.nextInt(8);
			if (ableToMove(l, i) != null && Data.getColour(l, i).equals("blue")){
				moveNode[1] = new Node(l, i, "blue");
				while (true){
					l = r.nextInt(2);
					i = r.nextInt(8);
					if (Data.getColour(l, i).equals("black") && moveNode[1].isConnected(new Node(l, i, "black"))){
						moveNode[0] = new Node(l, i, "black");
						return moveNode;
					}
				}
			}
		}
		
	}
	
	/**
	 * Tries to find opponent mill to remove, otherwise random opponent Node
	 * @return Node to be removed
	 */
	private Node findMill(){
		Node temp;
		
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 8; j++) {
				temp = findRedPair(i, j);
				if (temp != null) return temp;
			}
		}
		
		Random r = new Random();
		while (true){
			int l = r.nextInt(2);
			int i = r.nextInt(8);
			if (Data.getColour(l, i).equals("red") && (Data.getRedCount() <= 3 || !Data.singleTriple(l, i)))
				return new Node(l, i, "red");
		}
	}

	/**
	 * check for adjacent black node
	 * @param layer
	 * @param index
	 * @return Node if there is a black Node, otherwise null
	 */
	private Node ableToMove(int layer, int index) {
		//odd positioned Node check left and right
		if (index % 2 == 1){
			if (layer == 1){
				if (Data.getColour(0, index).equals("black")) return new Node(0, index, "black");
			}
			else if (layer == 0){
				if (Data.getColour(1, index).equals("black")) return new Node(1, index, "black");
			}
		}
		
		// position 0 case
		if (index == 0){
			if (Data.getColour(layer, 7).equals("black")) return new Node(layer, 7, "black");
		}
		else if (Data.getColour(layer, index-1).equals("black")) return new Node(layer, index-1, "black");
		//position 7 case
		if (index == 7){
			if (Data.getColour(layer, 0).equals("black")) return new Node(layer, 0, "black");
		}
		else if (Data.getColour(layer, index+1).equals("black")) return new Node(layer, index+1, "black");
		return null;
	}

	/**
	 * check for adjacent blue pieces
	 * @param layer
	 * @param triple
	 * @param index
	 * @return index of blue piece, otherwise -1
	 */
	private int checkAdj(int layer, int triple, int index) {
		if (triple == index){ //check left
			if (index == 0){
				if (Data.getColour(layer, 7).equals("blue"))
					return 7;
			}
			else{
				if (Data.getColour(layer,  index - 1).equals("blue"))
					return index-1;
			}
		}
		
		else if (triple + 1 == index){ // check other layer
			if (layer == 0){
				if (Data.getColour(1, index).equals("blue"))
					return index;
			}
			else{
				if (Data.getColour(0, index).equals("blue"))
					return index;
			}
		}
		
		else { // check right
			if (Data.getColour(layer,  index + 1).equals("blue"))
				return index+1;
		}

		return -1;
	}

	/**
	 * Finds pair of a colour
	 * @param col - colour of pieces to check
	 * @return Node if pair can be milled by filling in a black Node
	 */
	private Node findOpenTriple(String col){
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 8; j+=2) {
				int sum = 0;
				int index = -1;
				if (Data.getColour(i, j).equals(col)) sum++;
				else if(Data.getColour(i, j).equals("black")) index = j;
				if (Data.getColour(i, j+1).equals(col)) sum++;
				else if(Data.getColour(i, j+1).equals("black")) index = j+1;
				if (j == 6){
					if (Data.getColour(i, 0).equals(col)) sum++;
					else if(Data.getColour(i, 0).equals("black")) index = 0;
				}
				else{
					if (Data.getColour(i, j+2).equals(col)) sum++;
					else if(Data.getColour(i, j+2).equals("black")) index = j+2;
				}
				if (sum == 2 && index != -1){
					return new Node(i, index, "black");
				}
			}
		}
		
		return null;
	}
	
	/**
	 * finds pair of opponent pieces
	 * @param layer
	 * @param index
	 * @return opponent Node if removable, otherwise null
	 */
	private Node findRedPair(int layer, int index){
		int row = index - index%2;
		int sum = 0;
		int pos = -1;
		
		if (Data.getColour(layer, row).equals("red")) sum++;
		else if(Data.getColour(layer, row).equals("black")) pos = row;
		if (Data.getColour(layer, row+1).equals("red")) sum++;
		else if(Data.getColour(layer, row+1).equals("black")) pos = row+1;
		if (row == 6){
			if (Data.getColour(layer, 0).equals("red")) sum++;
			else if(Data.getColour(layer, 0).equals("black")) pos = 0;
		}
		else{
			if (Data.getColour(layer, row+2).equals("red")) sum++;
			else if(Data.getColour(layer, row+2).equals("black")) pos = row+2;
		}
		if (sum == 2 && pos != -1 && checkAdj(layer, row, pos) != -1){
			for (int i = 0; i < 3; i++) {
				int in = (row+i == 8)? 0:row+i;
				if (!Data.singleTriple(layer, in) || Data.getRedCount() <= 3){
					return new Node(layer, in, "red");
				}
			}
		}
		return null;
	}
}
