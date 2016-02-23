package model;
import javafx.scene.control.Button;


public class Node extends Button{

	// Node values
	final private int layer;
	final private int position;
	private String colour;
	 
	// setup coordinate and colour of the node
	public Node(int l, int p, String col){
		layer = l;
		position = p;
		colour = col;
	}
	
	// getters
	public int getLayer(){
		return layer;
	}
	
	public int getPosition(){
		return position;
	}
	
	public String getColour(){
		return colour;
	}
	
	// set colour
	public void setColour(String col){
		colour = col;
	}
	
	/* Has no use at the moment
	public boolean isConnected(Node other){
		if (this.layer == other.layer){
			if (this.position+1 == other.position || this.position-1 == other.position)
				return true;
			else if (this.position == 0 && other.position == 7)
				return true;
		}
		else if (this.layer+1 == other.layer || this.layer-1 == other.layer){
			if (this.position == other.position)
				return true;
		}
		
		return false;
	}
	*/
}
