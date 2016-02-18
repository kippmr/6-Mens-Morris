package controller;
import model.Data;
import javafx.event.Event;
import javafx.event.EventHandler;


public class ColourHandler implements EventHandler {

	private String colour;
	
	public ColourHandler(String colour) {
		this.colour = colour;
	}

	@Override
	public void handle(Event arg0) {
		Data.changeTurn(this.colour);
	}
}
