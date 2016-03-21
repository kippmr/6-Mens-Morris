package controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import model.Data;
import model.GameState;
import model.Node;
import view.View;

public class Mill {
	
	private String col;
	private int layer;
	private int index;
	
	public Mill(int layer, int index, String col){
		this.col = col;
		this.layer = layer;
		this.index = index;
	}
	
	public void mill() {
		String selectedCol = Data.boardTriple();
		
	}
	
}
