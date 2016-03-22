package model;
import javafx.scene.control.Button;


/**
 * @author Kipp
 *
 */
public class Node extends Button{

	// Node values
	final private int layer;
	final private int position;
	private String colour;
	 
	/**
	 * setup coordinate and colour of the node
	 * @param l - layer of Node
	 * @param p - position of Node
	 * @param col - colour of Node
	 */
	public Node(int l, int p, String col){
		layer = l;
		position = p;
		colour = col;
	}
	
	/**
	 * @return layer of Node
	 */
	public int getLayer(){
		return layer;
	}
	
	/**
	 * @return position of Node
	 */
	public int getPosition(){
		return position;
	}
	
	/**
	 * @return colour of Node
	 */
	public String getColour(){
		return colour;
	}
	
	/**
	 * @param col - change to this colour
	 */
	public void setColour(String col){
		colour = col;
	}
	
	/**
	 * check if two Nodes are adjacent
	 * @param other - Node being compared to this Node
	 * @return true if two Nodes are adjacent
	 */
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
