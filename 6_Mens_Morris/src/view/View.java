package view;

import controller.CheckHandler;
import controller.ColourHandler;
import controller.NodeHandler;
import controller.ResetHandler;
import model.Data;
import model.GameState;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.*;
import javafx.stage.Stage;

/**
 * gui for user
 */
public class View extends Application{
	//window size
	final private int HEIGHT = 700;
	final private int WIDTH = 900;
	//number of nodes
	final static private int LAYERS = 2;
	final static private int POSITIONS = 8;
	
	private Stage window;
	private Scene mainScene,sandboxScene, gameScene;
	// Menu Buttons
	private Button onePlayerBtn, twoPlayerBtn, loadBtn, sandBox, exitButton, quitButton;
	// State label
	private static Label curState;
	// Buttons for controller logic
	private Button reset, check, save;
	private static Button red;
	private static Button blue;
	static //Node buttons
	private Button[][] nodes = new Button[LAYERS][POSITIONS];
	// Node Images
	static private String blackNode = "-fx-background-image: url('/black.png')";
	static private String redNode = "-fx-background-image: url('/red.png')";
	static private String blueNode = "-fx-background-image: url('/blue.png')";
	static private String redHighlight = "-fx-background-image: url('/redHighlight.png')";
	static private String blueHighlight = "-fx-background-image: url('/blueHighlight.png')";
	// Board Image
	static private String boardImage = "-fx-background-image: url('/board.png')";
	
	/**
	 * Begins the game by launching the GUI and resetting values
	 * @param args - customised user input, not needed 
	 */
	public static void main(String[] args){
		// Sets up the board
		Data.reset();
		// Launches the application
		launch(args);
		update();
	}
	
	// Start Java fx stage
	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override 
	public void start(Stage primaryStage) throws Exception{
		
		window = primaryStage;
		// Set stage title
		primaryStage.setTitle("Six Men's Morris");
		
		// if user manually clicks close button then run set close method
		window.setOnCloseRequest(e ->{
			e.consume();
			closeProgram();
		});
		
		// set labels
		Label label1 = new Label("Six Men's Morris\n");
		label1.setFont(new Font(35));
		//Label label2 = new Label("Change the turn by clicking a piece on the side");
		curState = new Label();
		
		// Creating button
		
		//one player button
		onePlayerBtn = new Button();
		onePlayerBtn.setText("One Player");
		onePlayerBtn.setOnAction(e -> {
			Data.reset();
			Data.setAI(true);
			View.update();
			window.setScene(gameScene);
		});
		
		//two player button
		twoPlayerBtn = new Button();
		twoPlayerBtn.setText("Two Player");
		twoPlayerBtn.setOnAction(e -> {
		Data.reset();
		Data.setAI(false);
		View.update();
		window.setScene(gameScene);
		});
		
		// Load Previous Game
		loadBtn = new Button();
		loadBtn.setText("LOAD GAME");
		loadBtn.setOnAction(e ->  {
		Data.reset();
		try {
			Data.load();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		update();
		window.setScene(gameScene);});
		
		// Sand box mode
		sandBox = new Button();
		sandBox.setText("SANDBOX MODE");
		sandBox.setOnAction(e -> {
		Data.setState(GameState.SANDBOX);
		Data.reset();
		View.update();
		window.setScene(sandboxScene);});
		
		// Exit program button / handler
		exitButton = new Button();
		exitButton.setText("EXIT");
		exitButton.setOnAction(e -> closeProgram());
		
		// Return to main menu button
		quitButton = new Button();
		quitButton.setText("Quit");
		quitButton.setOnAction(e -> window.setScene(mainScene));
		
		// Select red before placing a red piece
		red = new Button();
		red.setPrefSize(40, 40);
		red.setMinSize(40, 40);
		red.setMaxSize(40, 40);
		red.setStyle(redNode);
		if (!Data.getTurn()) red.setStyle(redHighlight);
		red.setOnAction(new ColourHandler("red"));
		
		// Select blue before placing a blue piece
		blue = new Button();
		blue.setPrefSize(40, 40);
		blue.setMinSize(40, 40);
		blue.setMaxSize(40, 40);
		blue.setStyle(blueNode);
		if (Data.getTurn()) blue.setStyle(blueHighlight);
		blue.setOnAction(new ColourHandler("blue"));
		
		// Checks valid piece placement
		check = new Button();
		check.setText("Check");
		check.setOnAction(new CheckHandler());
		
		// Resets the board
		reset = new Button();
		reset.setText("Reset");
		reset.setOnAction(new ResetHandler());
		
		// Save game state
		save = new Button();
		save.setText("Save");
		save.setOnAction(e -> Data.save());
		
		// Add event handler to nodes, setup size and image
		for (int i = 0; i < nodes.length; i++) {
			for (int j = 0; j < nodes[i].length; j++) {
				nodes[i][j] = new Button();
				nodes[i][j].setOnAction(new NodeHandler(i, j));
				nodes[i][j].setPrefSize(40, 40);
				nodes[i][j].setMinSize(40, 40);
				nodes[i][j].setMaxSize(40, 40);
				nodes[i][j].setStyle(blackNode);
			}
		}
		
		// Main Screen layout
		VBox layout1 = new VBox(20);
		layout1.getChildren().addAll(label1,onePlayerBtn,twoPlayerBtn,loadBtn,sandBox,exitButton);
		// Removed credits for simplicity
		mainScene = new Scene(layout1,WIDTH, HEIGHT);
		
		// Board game layout
		
		/* Top: Text
		 * Bottom: Reset, Check, Quit
		 * Left + Right: Blue and Red Selection
		 * Center: Board
		 */
		
		//Sandbox mode
		BorderPane layout2 = new BorderPane();
		layout2.setPadding(new Insets(15, 15, 15, 15));
		HBox bottomPane = new HBox(50);
		HBox topPane = new HBox();
		VBox leftPane = new VBox(20);
		VBox rightPane = new VBox(20);
		topPane.getChildren().add(curState);
		bottomPane.getChildren().addAll(reset, check, quitButton);
		leftPane.getChildren().add(blue);
		rightPane.getChildren().add(red);
		layout2.setBottom(bottomPane);
		layout2.setLeft(leftPane);
		layout2.setRight(rightPane);
		layout2.setTop(topPane);
		layout2.setCenter(addGrid());
		layout2.setStyle(boardImage);
		sandboxScene = new Scene(layout2,WIDTH, HEIGHT);
		
		//Game mode
		BorderPane layout3 = new BorderPane();
		layout3.setPadding(new Insets(15, 15, 15, 15));
		HBox bottomGamePane = new HBox(50);
		bottomGamePane.getChildren().addAll(save, quitButton);
		layout3.setBottom(bottomGamePane);
		layout3.setLeft(leftPane);
		layout3.setRight(rightPane);
		layout3.setTop(topPane);
		layout3.setCenter(addGrid());
		layout3.setStyle(boardImage);
		gameScene = new Scene(layout3, WIDTH, HEIGHT);
		
		// Center the buttons
		layout1.setAlignment(Pos.CENTER);
		topPane.setAlignment(Pos.CENTER);
		bottomPane.setAlignment(Pos.CENTER);
		leftPane.setAlignment(Pos.CENTER);
		rightPane.setAlignment(Pos.CENTER);
		bottomGamePane.setAlignment(Pos.CENTER);	
		
		// Sets initial scene and displays Window
		window.setScene(mainScene);
		window.show();
	}
	/**
	 * Begins the close program process by calling confirm box exit prompt
	 */ 
	private void closeProgram(){
		boolean exitProgram = ConfirmBox.display("Exit","Are you sure you want to exit?");
		if(exitProgram){
		System.exit(0);}
	}
	
	/**
	 * Place Nodes on the grid
	 * @return GridPane of Nodes
	 */
	private GridPane addGrid(){
		GridPane boardPane = new GridPane();
		boardPane.add(nodes[0][0], 0, 0);
		boardPane.add(nodes[0][1], 0, 2);
		boardPane.add(nodes[0][2], 0, 4);
		boardPane.add(nodes[0][3], 2, 4);
		boardPane.add(nodes[0][4], 4, 4);
		boardPane.add(nodes[0][5], 4, 2);
		boardPane.add(nodes[0][6], 4, 0);
		boardPane.add(nodes[0][7], 2, 0);
		boardPane.add(nodes[1][0], 1, 1);
		boardPane.add(nodes[1][1], 1, 2);
		boardPane.add(nodes[1][2], 1, 3);
		boardPane.add(nodes[1][3], 2, 3);
		boardPane.add(nodes[1][4], 3, 3);
		boardPane.add(nodes[1][5], 3, 2);
		boardPane.add(nodes[1][6], 3, 1);
		boardPane.add(nodes[1][7], 2, 1);
		boardPane.setPadding(new Insets(80,80,80,180));
		boardPane.setHgap(59);
		boardPane.setVgap(59);
		return boardPane;
	}
	
	/**
	 * Update function which updates the game board when called
	 * Change node icons according to value
	 */
	public static void update(){
		//set colours of each node
		for (int i = 0; i < LAYERS; i++) {
			for (int j = 0; j < POSITIONS; j++){
				String col = Data.getColour(i, j);
				if (col.equals("red"))
					nodes[i][j].setStyle(redNode);
				else if (col.equals("blue"))
					nodes[i][j].setStyle(blueNode);
				else
					nodes[i][j].setStyle(blackNode);
			}
		}
		
		//highlight turn's colour
		if (Data.getTurn()){
			blue.setStyle(blueHighlight);
			red.setStyle(redNode);
		}
		else{
			blue.setStyle(blueNode);
			red.setStyle(redHighlight);
		}
		//update game phase label
		switch(Data.getState()){
		case PLACEMENT:
			if (Data.getTurn())
				curState.setText("BLUE'S MOVE: PLACE A PIECE");
			else
				curState.setText("RED'S MOVE: PLACE A PIECE");
			break;
		case MOVEMENT:
		if (Data.getTurn())
			curState.setText("BLUE'S MOVE: MOVE A PIECE");
		else
			curState.setText("RED'S MOVE: MOVE A PIECE");
		break;
		case MILL:
			if (Data.getTurn())
				curState.setText("BLUE'S MOVE: REMOVE A RED PIECE NOT IN A MILL");
			else
				curState.setText("RED'S MOVE: REMOVE A BLUE PIECE NOT IN A MILL");
			break;
		case ENDGAME:
			if (Data.getTurn())
				curState.setText("RED WINS!!!");
			else
				curState.setText("BLUE WINS!!!");
			break;
		}
	}
}
