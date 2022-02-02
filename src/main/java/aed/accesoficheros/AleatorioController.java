package aed.accesoficheros;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class AleatorioController implements Initializable {

	//Model
	private StringProperty ruta = new SimpleStringProperty();
	
	

	//View
    @FXML
    private BorderPane view;
	
    @FXML
    private CheckBox activaCheck;

    @FXML
    private TextField capacidadText;

    @FXML
    private TextField codHabitacionText;

    @FXML
    private TextField codHotelText;

    @FXML
    private Button crearButton;

    @FXML
    private Button modificarButton;

    @FXML
    private TextField numHabitacionText;

    @FXML
    private TextField precioDiaText;

    @FXML
    private TextArea registrosAreaBox;

    @FXML
    private TextField rutaText;

    @FXML
    private Button visualizarButton;


    public AleatorioController() throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/AleatorioView.fxml"));
		loader.setController(this);
		loader.load();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//Init
		rutaText.setText("C:\\Users\\scrag\\OneDrive\\Escritorio\\Carpeta de prueba");
		
	}

	public BorderPane getView() {
		return view;
	}
	
    @FXML
    void onCrear(ActionEvent event) {

    }

    @FXML
    void onModificar(ActionEvent event) {

    }

    @FXML
    void onVisualizar(ActionEvent event) {

    }
}
