import javafx.event.Event;
import javafx.event.EventHandler;


public class CheckHandler implements EventHandler {
	private boolean boardFilled;
	private int redCount = 0;
	private int blueCount = 0;
	
	public CheckHandler() {
		for (int i = 0; i < 2; i++) {
			for (int j = 0; i < 8; i++) {
				if (Data.getColour(i,j).equals("blue"))
					blueCount++;
				if (Data.getColour(i,j).equals("red"))
					redCount++;
			}
		}
	}
	
	@Override
	public void handle(Event arg0) {
		if (redCount == 8 && blueCount == 8)
			System.out.println("This set-up is valid");
		else {
			System.out.println("This set-up is invalid. Resetting");
			Data.reset();
		}
		View.update();
	}
}
