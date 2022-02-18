package aed.accesoficheros;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
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

	// Model
	private StringProperty ruta = new SimpleStringProperty();

	// View
	@FXML
	private BorderPane view;

	@FXML
	private CheckBox activaCheck;

	@FXML
	private TextField capacidadText;

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

	@FXML
	private Button VerDatosButton;
	
	@FXML
    private TextField codHabitacionText;

	public AleatorioController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/AleatorioView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Init
		rutaText.setText("C:\\Users\\scrag\\OneDrive\\Escritorio\\Fichero.data");

	}

	public BorderPane getView() {
		return view;
	}

	/*
	 * Crea un registro nuevo. SI no hay un .data, lo crea en la direccion puesta
	 */
	@FXML
	void onCrear(ActionEvent event) throws IOException {
		String[] registro = new String[5];
		registro[0] = (codHotelText.getText() + "      ").substring(0, 6);
		registro[1] = (numHabitacionText.getText() + "    ").substring(0, 4);
		registro[2] = capacidadText.getText();
		registro[3] = precioDiaText.getText();
		registro[4] = activaCheck.selectedProperty().getValue().toString();

		RandomAccessFile file = new RandomAccessFile(rutaText.getText(), "rw");
		int id = 0;
		char separator = ',';

		if (file.length() == 0) {
			id = 1;
		} else {
			file.seek(file.length() - 45);
			id = file.readInt() + 1;
			file.seek(file.length());
		}
		
		file.writeInt(id);
		file.writeChar(separator);
		file.writeChars(registro[0]);
		file.writeChar(separator);
		file.writeChars(registro[1]);
		file.writeChar(separator);
		file.writeInt(Integer.parseInt(registro[2]));
		file.writeChar(separator);
		file.writeInt(Integer.parseInt(registro[3]));
		file.writeChar(separator);
		file.writeBoolean(Boolean.parseBoolean(registro[4]));
		file.writeChar(separator);

		onVisualizar(event);
	}

	/*
	 * Modifica el registro PRECIODIA según el CodHabitacion
	 */
	@FXML
	void onModificar(ActionEvent event) throws IOException {

		RandomAccessFile file = new RandomAccessFile(rutaText.getText(), "rw");
		int id = Integer.parseInt(codHabitacionText.getText());
		int preciodia = Integer.parseInt(precioDiaText.getText());
		
		if (file.length() == 0) {
			registrosAreaBox.setText("Nada que visualizar");
		} else {
			id = (id -1) * 45;
			file.seek(id + 36);
			file.writeInt(preciodia);
		}
		onVisualizar(event);
	}

	/* Visualiza el fichero */
	@FXML
	void onVisualizar(ActionEvent event) throws IOException {
		RandomAccessFile file = new RandomAccessFile(rutaText.getText(), "r");
		Charset charset = StandardCharsets.UTF_16;

		String string = "";
		String content = "";

		if (file.length() == 0) {
			registrosAreaBox.setText("Nada que visualizar");
		} else {
			while (file.getFilePointer() < file.length()) {
				// CodHabitacion
				content += ("Cod Habitacion: " + file.readInt());
				// Coma
				content += file.readChar();
				// CodHotel
				byte[] arr1 = new byte[12];
				file.readFully(arr1);
				string = new String(arr1, charset);
				content += ("CodHotel: " + string);
				// Coma
				content += file.readChar();
				// numHabitacion
				byte[] arr2 = new byte[8];
				file.readFully(arr2);
				string = new String(arr2, charset);
				content += ("numHabitacion: " + string);
				// Coma
				content += file.readChar();
				// Capadidad
				content += ("Capacidad: " + file.readInt());
				// Coma
				content += file.readChar();
				// Capadidad
				content += ("PrecioDia: " + file.readInt());
				// Coma
				content += file.readChar();
				// Capadidad
				content += ("Activa: " + file.readBoolean());
				content += file.readChar() + "\n";
			}
			registrosAreaBox.setText(content);
		}
	}

	/* Visualiza solo las habiationes según el codVisualizar */
	@FXML
	void onVerDatosButton(ActionEvent event) throws IOException {

		int id = Integer.parseInt(codHabitacionText.getText());
		RandomAccessFile file = new RandomAccessFile(rutaText.getText(), "r");
		Charset charset = StandardCharsets.UTF_16;
		
		String string = "";
		String content = "";
		
		if (file.length() == 0) {
			registrosAreaBox.setText("Nada que visualizar");
		}
		else
		{
			id = (id - 1) * 45;
			file.seek(id);
			// CodHabitacion
			content += ("Cod Habitacion: " + file.readInt());
			// Coma
			content += file.readChar();
			// CodHotel
			byte[] arr1 = new byte[12];
			file.readFully(arr1);
			string = new String(arr1, charset);
			content += ("CodHotel: " + string);
			// Coma
			content += file.readChar();
			// numHabitacion
			byte[] arr2 = new byte[8];
			file.readFully(arr2);
			string = new String(arr2, charset);
			content += ("numHabitacion: " + string);
			// Coma
			content += file.readChar();
			// Capadidad
			content += ("Capacidad: " + file.readInt());
			// Coma
			content += file.readChar();
			// Capadidad
			content += ("PrecioDia: " + file.readInt());
			// Coma
			content += file.readChar();
			// Capadidad
			content += ("Activa: " + file.readBoolean());
			content += file.readChar() + "\n";
		}
		registrosAreaBox.setText(content);
	}
}
