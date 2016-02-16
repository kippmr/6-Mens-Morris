import java.util.Random;


public class Data {

	
	final private static int TOTAL_LAYERS = 2;
	final private static int TOTAL_POSITIONS = 8;
	static private Node[][] nodes = new Node[TOTAL_LAYERS][TOTAL_POSITIONS];
	
	boolean isBlueTurn;

	public Data(){
		reset();
		chooseTurn();
	}
	
	public void getColour(int layer, int index){
		return nodes[layer][index].getColour();
	}
	
	public void setColour(int layer. int index){
		String col = (isBlueTurn)? "blue" : "red";
		nodes[layer][index].setColour(col);
	}
	
	public void reset(){
		for (int i = 0; i < nodes.length; i++){
			for (int j = 0; j < nodes[i].length; j++){
				nodes[i][j] = new Node(i,j);
			}
		}
	}
	
	public void changeTurn(){
		isBlueTurn = !isBlueTurn;
	}
	
	public void changeTurn(String col){
		if (col.equals("blue"))
			isBlueTurn = true;
		else
			isBlueTurn = false;
	}
	
	public void chooseTurn(){
		Random random = new Random();
		isBlueTurn = random.nextInt(2) % 2 == 0;
	}
	
	public void setPiece(int layer, int position){
		if (isBlueTurn)
			nodes[layer][position].setColour("blue");
		else
			nodes[layer][position].setColour("red");
	}
}
