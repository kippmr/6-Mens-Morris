import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class viewer extends Application{
	Stage window;
	Scene mainScene,boardScene,creditScene;
	// Creates buttons
	Button button,button2,button3, button4, button5, button6;
	
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
		button = new Button();
		button.setText("1 Player");
		button.setOnAction(e -> window.setScene(boardScene));
		
		// 2 Players option
		button2 = new Button();
		button2.setText("2 Players");
		button2.setOnAction(e -> window.setScene(boardScene));
		
		// Credits Option
		button3 = new Button();
		button3.setText("Credits");
		button3.setOnAction(e -> window.setScene(creditScene));
		
		// Exit program button / handler
		button4 = new Button();
		button4.setText("Exit");
		button4.setOnAction(e -> closeProgram());
		
		// Return to main menu button
		button5 = new Button();
		button5.setText("Quit");
		button5.setOnAction(e -> window.setScene(mainScene));
		
		// Returns from credit screen to main screen
		button6 = new Button();
		button6.setText("Return");
		button6.setOnAction(e -> window.setScene(mainScene));
		
		// Main Screen layout
		VBox layout1 = new VBox(20);
		layout1.getChildren().addAll(label1,button,button2,button3,button4);
		mainScene = new Scene(layout1,450,300);
		
		// Board game layout
		VBox layout2 = new VBox(20);
		layout2.getChildren().addAll(label2,button5);
		boardScene = new Scene(layout2,450,300);
		
		// Credit Screen layout
		VBox layout3 = new VBox(20);
		layout3.getChildren().addAll(label3,button6);
		creditScene = new Scene(layout3,450,300);
		
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
