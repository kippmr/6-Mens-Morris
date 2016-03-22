package controller;
import view.View;
import model.Data;
import javafx.event.Event;
import javafx.event.EventHandler;


/**
 * reset board and update view
 *
 */
public class ResetHandler implements EventHandler {
	
	
	/* (non-Javadoc)
	 * @see javafx.event.EventHandler#handle(javafx.event.Event)
	 */
	@Override
	public void handle(Event arg0) {
		Data.reset();
		View.update();
	}
}
