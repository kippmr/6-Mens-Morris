import javafx.event.Event;
import javafx.event.EventHandler;


public class NodeHandler implements EventHandler {

	private int layer;
	private int index;
	
	public NodeHandler(int layer, int index) {
		this.layer = layer;
		this.index = index;
	}
	
	@Override
	public void handle(Event event) {
		Data.setColour(this.layer, this.index);
		View.update();
	}

}
