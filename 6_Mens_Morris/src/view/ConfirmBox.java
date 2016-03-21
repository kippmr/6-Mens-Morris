package view;
// import statements
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.geometry.*;

public class ConfirmBox {
	
	// Boolean value whether the user does want to exit the program or not
	static boolean confirm;
	
	/**
	 * Creates a window that displays whether the user would like to quit or not with Y/N buttons for choosing
	 * @param title - string value of the title of the display box
	 * @param message - message being sent to the user in the display box
	 * @return - returns the users response as a boolean: Yes or No to closing the program completely
	 */
	public static boolean display(String title, String message){
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(250);
		Label label = new Label();
		label.setText(message);
		
		// Create yes and no buttons
		Button yesButton = new Button("Yes");
		Button noButton = new Button("No");
		
		// If the user does want to exit, close the program completely
		yesButton.setOnAction(e ->{
			confirm = true;
			window.close();
		});
		
		// If the user decides they do not want to exit, exit the confirm box class and return False
		noButton.setOnAction(e ->{
			confirm = false;
			window.close();
		});
		
		// The layout of the confirm exit box
		VBox layout = new VBox(10);
		layout.getChildren().addAll(label,yesButton,noButton);
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
		
		// returns the users response Y/N
		return confirm;
	}
}
