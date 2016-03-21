package controller;
import javafx.event.Event;
import javafx.event.EventHandler;
import model.Data;
import model.GameState;
import model.Node;
import view.View;


public class NodeHandler implements EventHandler {

	private int layer;
	private int index;
	
	public NodeHandler(int layer, int index) {
		this.layer = layer;
		this.index = index;
	}
	
	@Override
	public void handle(Event event) {
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

	private void sandboxPhase(){
		if (Data.getColour(layer, index).equals("black"))
			Data.setColour(this.layer, this.index);
		View.update();
	}
	
	private void placementPhase(){
		if (Data.getColour(layer, index).equals("black")){
			Data.setColour(this.layer, this.index);
			Data.changeTurn();
		}
		if (Data.getNumPieces() == 12)
			Data.setState(GameState.MOVEMENT);
		if (Data.singleTriple(layer, index)){
			Data.setState(GameState.MILL);
			Data.changeTurn();
		}
		View.update();
	}
	
	private void movementPhase(){
		// set node to move
		String col = (Data.getTurn())? "blue" : "red";
		if (col.equals(Data.getColour(layer, index))){
			Data.setMove(layer, index, col);
			System.out.println("MOVE FOUND");
		}
		else{
			System.out.println("SELECT A PIECE TO MOVE");
		}
		
		// move the node
		if (Data.getColour(layer, index).equals("black") && Data.hasMove()){
			if (Data.canMove(layer, index)){
				Data.swapNode(layer, index);
				if (Data.singleTriple(layer, index))
					Data.setState(GameState.MILL);
				else Data.changeTurn();
				if (Data.checkWin()){
					Data.setState(GameState.ENDGAME);
					if (Data.getTurn())
						System.out.println("RED WON");
					else
						System.out.println("BLUE WON");
				}
			}
		}
		else System.out.println("Not a valid place to move" + Data.hasMove());
		View.update();
	}
	
	private void millPhase(){
		String oppCol = (Data.getTurn())? "red" : "blue";
		int oppCount = (Data.getTurn())? Data.getRedCount() : Data.getBlueCount();
		if (!Data.singleTriple(layer, index) || oppCount == 3){
			if (oppCol.equals(Data.getColour(layer, index))){
				Data.mill(layer, index);
				if (Data.getNumPieces() == 12) Data.setState(GameState.MOVEMENT);
				else Data.setState(GameState.PLACEMENT);
				Data.changeTurn();
				if (Data.checkWin()){
					Data.setState(GameState.ENDGAME);
					if (Data.getTurn())
						System.out.println("RED WON");
					else
						System.out.println("BLUE WON");
				}
				View.update();
			}
		}
	}
}
