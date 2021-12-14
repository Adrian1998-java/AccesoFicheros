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

	//Models
	private FicheroController ficheroController = new FicheroController();
	
	
	
	//View
    @FXML
    private BorderPane view;

    @FXML
    private Tab tareaUnoTab;

    @FXML
    private Tab tareaDosTab;

    public MainController() throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/MainView.fxml"));
		loader.setController(this);
		loader.load();
	}
    
	public void initialize(URL location, ResourceBundle resources) {
		tareaUnoTab.setContent(ficheroController.getView());

	}
	
	public BorderPane getView() {
		return view;
	}

}
