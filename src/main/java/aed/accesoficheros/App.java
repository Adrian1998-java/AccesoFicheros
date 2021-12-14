package aed.accesoficheros;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

	MainController controller;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		controller = new MainController();
		
		Scene stage = new Scene(controller.getView(), 600, 500);
		
		primaryStage.setScene(stage);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);

	}

}
