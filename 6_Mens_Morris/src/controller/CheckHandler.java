package controller;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Data;


/**
 * checks if a given board has a valid set-up
 */
public class CheckHandler implements EventHandler {
	private int redCount;
	private int blueCount;
	
	/* (non-Javadoc)
	 * @see javafx.event.EventHandler#handle(javafx.event.Event)
	 */
	@Override
	public void handle(Event arg0) {
		//counting Node colours
		redCount = 0;
		blueCount = 0;
		
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 8; j++) {
				if (Data.getColour(i,j).equals("blue"))
					blueCount++;
				if (Data.getColour(i,j).equals("red"))
					redCount++;
			}
		}
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Check");
		alert.setHeaderText("Validation");

		//check if board has legal or illegal set up
		if (redCount <= 6 && blueCount <= 6)
			alert.setContentText("This setup is valid.");
		else
			alert.setContentText("This setup is invalid. Cannot have more than 6 pieces of the same colour.");
		
		if ((redCount == 0 || blueCount == 0) && (redCount > 1 || blueCount >1))
			alert.setContentText("This setup is invalid. Cannot have one colour having more than 1 "
					+ "piece on the board while the other colour has none.");
		else 
			alert.setContentText("This setup is valid.");
		
		alert.showAndWait();
	}
}
