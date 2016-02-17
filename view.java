// import statements
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class viewer extends Application{
	Stage window;
	Scene mainScene,boardScene,creditScene;
	// Menu Buttons
	Button onePlayer,twoPlayers,credits, exitButton, quitButton, returnButton;
	// Buttons for controller logic
	Button reset,check,red,blue;
	
	static Boolean confirm;
	
	public static void main(String[] args){
		// Launches the application
		launch(args);
	}
	
	// Start Java fx stage
	@Override 
	public void start(Stage primaryStage) throws Exception{
		
		window = primaryStage;
		// Set stage title
		primaryStage.setTitle("Six Man's Morris");
		
		// if user manually clicks close button then run set close method
		window.setOnCloseRequest(e ->{
			e.consume();
			closeProgram();
		});
		
		// set labels
		Label label1 = new Label("Six Man's Morris");
		Label label2 = new Label("Make your move");
		Label label3 = new Label("Model: William Tran\nView: Ben Miller\nController: Matt Kipp");
		
		// Creating button
		onePlayer= new Button();
		onePlayer.setText("1 Player");
		onePlayer.setOnAction(e -> window.setScene(boardScene));
		
		// 2 Players option
		twoPlayers = new Button();
		twoPlayers.setText("2 Players");
		twoPlayers.setOnAction(e -> window.setScene(boardScene));
		
		// Credits Option
		credits = new Button();
		credits.setText("Credits");
		credits.setOnAction(e -> window.setScene(creditScene));
		
		// Exit program button / handler
		exitButton = new Button();
		exitButton.setText("Exit");
		exitButton.setOnAction(e -> closeProgram());
		
		// Return to main menu button
		quitButton = new Button();
		quitButton.setText("Quit");
		quitButton.setOnAction(e -> window.setScene(mainScene));
		
		// Returns from credit screen to main screen
		returnButton = new Button();
		returnButton.setText("Return");
		returnButton.setOnAction(e -> window.setScene(mainScene));
		
		red = new Button();
		red.setText("R");
		//red.setOnAction(e -> Logic for setting a red piece, handled by controller)
		
		blue = new Button();
		blue.setText("B");
		//blue.setOnAction(e -> Logic for setting a blue piece, handled by controller)
		
		check = new Button();
		check.setText("Check");
		//check.setOnAction(e -> Logic for checking if the pieces are legal, handled by controller)
		
		reset = new Button();
		reset.setText("Reset");
		//reset.setOnAction(e -> Resets the game pieces, handled by controller)
		
		// Main Screen layout
		VBox layout1 = new VBox(20);
		layout1.getChildren().addAll(label1,onePlayer,twoPlayers,credits,exitButton);
		mainScene = new Scene(layout1,550,400);
		
		// Board game layout
		VBox layout2 = new VBox(20);
		layout2.getChildren().addAll(label2,red,blue,reset,check,quitButton);
		boardScene = new Scene(layout2,550,400);
		
		// Credit Screen layout
		VBox layout3 = new VBox(20);
		layout3.getChildren().addAll(label3,returnButton);
		creditScene = new Scene(layout3,550,400);
		
		// Center the buttons
		layout1.setAlignment(Pos.CENTER);
		layout2.setAlignment(Pos.CENTER);
		layout3.setAlignment(Pos.CENTER);
		
		// Sets initial scene and displays Window
		window.setScene(mainScene);
		window.show();
	}

	private void closeProgram(){
		boolean exitProgram = ConfirmBox.display("Exit","Are you sure you want to exit?");
		if(exitProgram){
		System.exit(0);}
	}
}

