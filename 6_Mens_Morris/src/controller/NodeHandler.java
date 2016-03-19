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
			if (Data.getColour(layer, index).equals("black"))
				Data.setColour(this.layer, this.index);
			View.update();
			break;
			
		case PLACEMENT:
			if (Data.getColour(layer, index).equals("black"))
				Data.setColour(this.layer, this.index);
			if (Data.getNumPieces() == 12)
				Data.setState(GameState.MOVEMENT);
			View.update();
			break;
			
		case MOVEMENT:
			// set node to move
			String col = (Data.getTurn())? "blue" : "red";
			if (col.equals(Data.getColour(layer, index)))
				Data.setMove(layer, index, col);
			else{
				System.out.println("SELECT A PIECE TO MOVE");
			}
			
			// move the node
			if (col.equals("black")){
				if (Data.canMove(layer, index)){
					Data.swapNode(layer, index);
				}
			}
			else System.out.println("Not a valid place to move");
			checkMill();
			View.update();
			break;
		}
	}
	
	private void checkMill(){
		
	}

}
