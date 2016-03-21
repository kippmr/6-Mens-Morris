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
	
	public boolean isConnected(Node other){
		if (this.layer == other.layer){
			if (Math.abs(this.position - other.position) == 1 || Math.abs(this.position - other.position) == 7)
				return true;
		}
		else if (this.layer != other.layer){
			if (this.position == other.position)
				return true;
		}
		
		return false;
	}
}
