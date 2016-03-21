package controller;
import view.View;
import model.Data;
import model.GameState;
import javafx.event.Event;
import javafx.event.EventHandler;


public class ColourHandler implements EventHandler {

	private String colour;
	
	public ColourHandler(String colour) {
		this.colour = colour;
	}

	@Override
	public void handle(Event arg0) {
		if (Data.getState().equals(GameState.SANDBOX)){
			Data.changeTurn(this.colour);
			View.update();
		}
	}
}
