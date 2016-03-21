package view;
// import statements
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

public class View extends Application{
	//window size
	final private int HEIGHT = 700;
	final private int WIDTH = 900;
	//number of nodes
	final static private int LAYERS = 2;
	final static private int POSITIONS = 8;
	
	private Stage window;
	private Scene mainScene,boardScene,creditScene;
	// Menu Buttons
	private Button newGameBtn, loadBtn, sandBox, exitButton, quitButton;
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
	// Win state
	static private String result = "Game in progress";
	
	
	public static void main(String[] args){
		// Sets up the board
		Data.reset();
		// Launches the application
		launch(args);
		update();
	}
	
	// Start Java fx stage
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
		Label label1 = new Label("Six Men's Morris\n ");
		label1.setFont(new Font(35));
		//Label label2 = new Label("Change the turn by clicking a piece on the side");
		Label winState = new Label(result);
		// Creating button
		newGameBtn= new Button();
		newGameBtn.setText("NEW GAME");
		newGameBtn.setOnAction(e -> {
		Data.setState(GameState.PLACEMENT);
		Data.reset();
		View.update();
		window.setScene(boardScene);
		});
		// Load Previous Game
		loadBtn = new Button();
		loadBtn.setText("LOAD GAME");
		loadBtn.setOnAction(e ->  {
		Data.setState(GameState.PLACEMENT);
		window.setScene(boardScene);});
		
		// Sand box mode
		sandBox = new Button();
		sandBox.setText("SANDBOX MODE");
		sandBox.setOnAction(e -> {
		Data.setState(GameState.SANDBOX);
		Data.reset();
		View.update();
		window.setScene(boardScene);});
		
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
		layout1.getChildren().addAll(label1,newGameBtn,loadBtn,sandBox,exitButton);
		// Removed credits for simplicity
		mainScene = new Scene(layout1,WIDTH, HEIGHT);
		
		// Board game layout
		
		/* Top: Text
		 * Bottom: Reset, Check, Quit
		 * Left + Right: Blue and Red Selection
		 * Center: Board
		 */
		
		BorderPane layout2 = new BorderPane();
		layout2.setPadding(new Insets(15, 15, 15, 15));
		HBox bottomPane = new HBox(50);
		HBox topPane = new HBox();
		VBox leftPane = new VBox(20);
		VBox rightPane = new VBox(20);
		topPane.getChildren().add(winState);
		bottomPane.getChildren().addAll(reset, check, quitButton);
		leftPane.getChildren().add(blue);
		rightPane.getChildren().add(red);
		layout2.setBottom(bottomPane);
		layout2.setLeft(leftPane);
		layout2.setRight(rightPane);
		layout2.setTop(topPane);
		layout2.setCenter(addGrid());
		layout2.setStyle(boardImage);
		boardScene = new Scene(layout2,WIDTH, HEIGHT);
		
		// Center the buttons
		layout1.setAlignment(Pos.CENTER);
		topPane.setAlignment(Pos.CENTER);
		bottomPane.setAlignment(Pos.CENTER);
		leftPane.setAlignment(Pos.CENTER);
		rightPane.setAlignment(Pos.CENTER);
		
		// Sets initial scene and displays Window
		window.setScene(mainScene);
		window.show();
	}

	private void closeProgram(){
		boolean exitProgram = ConfirmBox.display("Exit","Are you sure you want to exit?");
		if(exitProgram){
		System.exit(0);}
	}
	
	// Place Nodes on the grid
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
	
	// Change node icons according to value
	public static void update(){
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
		
		if (Data.getTurn()){
			blue.setStyle(blueHighlight);
			red.setStyle(redNode);
		}
		else{
			blue.setStyle(blueNode);
			red.setStyle(redHighlight);
		}
	}
}
