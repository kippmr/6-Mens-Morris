package controller;
import view.View;
import model.Data;
import model.GameState;
import javafx.event.Event;
import javafx.event.EventHandler;


/**
 *  changes current turn's colour
 */
public class ColourHandler implements EventHandler {

	private String colour;
	
	/**
	 * @param colour - colour of the piece being placed
	 */
	public ColourHandler(String colour) {
		this.colour = colour;
	}

	/* (non-Javadoc)
	 * @see javafx.event.EventHandler#handle(javafx.event.Event)
	 */
	@Override
	public void handle(Event arg0) {
		if (Data.getState().equals(GameState.SANDBOX)){
			Data.changeTurn(this.colour);
			View.update();
		}
	}
}
