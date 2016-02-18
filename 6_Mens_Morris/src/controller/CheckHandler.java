package controller;
import model.Data;
import javafx.event.Event;
import javafx.event.EventHandler;


public class CheckHandler implements EventHandler {
	private boolean boardFilled;
	private int redCount;
	private int blueCount;
	
	@Override
	public void handle(Event arg0) {
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
		
		if (redCount <= 6 && blueCount <= 6)
			System.out.println("This set-up is valid");
		else {
			System.out.println("This set-up is invalid");
		}
	}
}
