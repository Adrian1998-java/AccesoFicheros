package aed.accesoficheros;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

public class App extends Application {

	private static Stage primaryStage;
	
	MainController controller;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		App.primaryStage = primaryStage;
		
		controller = new MainController();
		
		Scene stage = new Scene(controller.getView(), 600, 500);
		
		primaryStage.setScene(stage);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);

	}

	public static Stage getPrimaryStage() {
		return primaryStage;
	}

}
