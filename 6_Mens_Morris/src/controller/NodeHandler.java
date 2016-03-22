package controller;
import javafx.event.Event;
import javafx.event.EventHandler;
import model.Data;
import model.GameState;
import model.Node;
import view.View;


/**
 * @author Kipp
 *
 */
public class NodeHandler implements EventHandler {

	//position of Node on board
	private int layer;
	private int index;
	
	/**
	 * @param layer - 0 for outer ring, 1 for inner ring
	 * @param index - 0 to 7 around ring , clockwise starting from top left
	 */
	public NodeHandler(int layer, int index) {
		this.layer = layer;
		this.index = index;
	}
	
	/* (non-Javadoc)
	 * @see javafx.event.EventHandler#handle(javafx.event.Event)
	 */
	@Override
	public void handle(Event event) {
		//node behaviour depending on state of the game
		GameState currentState = Data.getState();
		switch (currentState){
		
		case SANDBOX:
			sandboxPhase();
			break;
			
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

	/**
	 * game is in sandbox mode
	 */
	private void sandboxPhase(){
		//swap 'black' colour with colour of whose turn it is
		if (Data.getColour(layer, index).equals("black"))
			Data.setColour(this.layer, this.index);
		View.update();
	}
	
	/**
	 * placement mill phase of game
	 */
	private void placementPhase(){
		//swap 'black' with current turn's colour
		if (Data.getColour(layer, index).equals("black")){
			Data.setColour(this.layer, this.index);
			Data.changeTurn();
		}
		//filled board, go to movement phase
		if (Data.getNumPieces() == 12)
			Data.setState(GameState.MOVEMENT);
		//current user makes triple, go to mill state
		if (Data.singleTriple(layer, index)){
			Data.setState(GameState.MILL);
			Data.changeTurn();
		}
		View.update();
	}
	
	/**
	 * perform movement phase of game
	 */
	private void movementPhase(){
		// set node to move
		String col = (Data.getTurn())? "blue" : "red";
		if (col.equals(Data.getColour(layer, index))){
			Data.setMove(layer, index, col);
		}
		
		// move the node
		if (Data.getColour(layer, index).equals("black") && Data.hasMove()){
			if (Data.canMove(layer, index)){
				Data.swapNode(layer, index);
				if (Data.singleTriple(layer, index))
					Data.setState(GameState.MILL);
				else Data.changeTurn();
				//check for winning
				if (Data.checkWin()){
					Data.setState(GameState.ENDGAME);
				}
			}
		}
		View.update();
	}
	
	/**
	 * perform mill phase of game
	 */
	private void millPhase(){
		//set opponent colour and Node count
		String oppCol = (Data.getTurn())? "red" : "blue";
		int oppCount = (Data.getTurn())? Data.getRedCount() : Data.getBlueCount();
		//check if opponent Node is in mill
		if (!Data.singleTriple(layer, index) || oppCount == 3){
			if (oppCol.equals(Data.getColour(layer, index))){
				Data.mill(layer, index);
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
		}
	}
}
