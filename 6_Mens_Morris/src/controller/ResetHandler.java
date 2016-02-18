package controller;
import view.View;
import model.Data;
import javafx.event.Event;
import javafx.event.EventHandler;


public class ResetHandler implements EventHandler {
	
	
	@Override
	public void handle(Event arg0) {
		Data.reset();
		View.update();
	}
}
