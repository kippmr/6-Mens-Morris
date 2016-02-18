package model;
import javafx.scene.control.Button;


public class Node extends Button{

	// Node values
	final private int layer;
	final private int position;
	private String colour;
	 
	public Node(int l, int p, String col){
		layer = l;
		position = p;
		colour = col;
	}
	
	public int getLayer(){
		return layer;
	}
	
	public int getPosition(){
		return position;
	}
	
	public void setColour(String col){
		colour = col;
	}
	
	public String getColour(){
		return colour;
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
