package aed.accesoficheros;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;

public class MainController implements Initializable {

	// Models
	private FicheroController ficheroController = new FicheroController();
	private AleatorioController aleatorioController = new AleatorioController();

	// View
	@FXML
	private BorderPane view;

	@FXML
	private Tab tareaUnoTab;

	@FXML
	private Tab tareaDosTab;

	@FXML
	private Tab tareaTresTab;

	public MainController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/MainView.fxml"));
		loader.setController(this);
		loader.load();
	}

	public void initialize(URL location, ResourceBundle resources) {
		tareaUnoTab.setContent(ficheroController.getView());
		tareaDosTab.setContent(aleatorioController.getView());
		tareaTresTab.setContent(null);

	}

	public BorderPane getView() {
		return view;
	}

}
