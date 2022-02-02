package aed.accesoficheros;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class FicheroController implements Initializable {

	// Model
	private StringProperty rutaActual = new SimpleStringProperty();
	private StringProperty multiFuncion = new SimpleStringProperty();
	private StringProperty contenidoFichero = new SimpleStringProperty();

	private BooleanProperty esCarpeta = new SimpleBooleanProperty();
	private BooleanProperty esFichero = new SimpleBooleanProperty();

	private ListProperty<File> ficherosCarpetas = new SimpleListProperty<File>();

	// View
	@FXML
	private BorderPane view;

	@FXML
	private TextField rutaActualText;

	@FXML
	private Button crearButton;

	@FXML
	private Button eliminarButton;

	@FXML
	private Button moverButton;

	@FXML
	private CheckBox carpetaCheck;

	@FXML
	private CheckBox ficheroCheck;

	@FXML
	private TextField destinoField;

	@FXML
	private Button verFicherosCarpetasButton;

	@FXML
	private ListView<File> ficheroCarpetasList;

	@FXML
	private Button verContenidoButton;

	@FXML
	private Button modificarButton;

	@FXML
	private TextArea contenidoTextArea;

	public FicheroController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/FicheroView.fxml"));
		loader.setController(this);
		loader.load();
	}

	public void initialize(URL location, ResourceBundle resources) {

		// Bindings
		rutaActualText.textProperty().bindBidirectional(rutaActual);
		destinoField.textProperty().bindBidirectional(multiFuncion);

		contenidoTextArea.textProperty().bindBidirectional(contenidoFichero);

		ficheroCarpetasList.itemsProperty().bind(ficherosCarpetas);

		carpetaCheck.selectedProperty().bindBidirectional(esCarpeta);
		ficheroCheck.selectedProperty().bindBidirectional(esFichero);

		// listeners
		esCarpeta.addListener((o, ov, nv) -> onCarpetaSelected(o, ov, nv));
		esFichero.addListener((o, ov, nv) -> onFicheroSelected(o, ov, nv));

		// Init
		rutaActualText.setText("C:\\Users\\scrag\\OneDrive\\Escritorio\\Carpeta de prueba");
	}

	private void onFicheroSelected(ObservableValue<? extends Boolean> o, Boolean ov, Boolean nv) {

		if (nv) {
			carpetaCheck.setDisable(true);
		} else {
			carpetaCheck.setDisable(false);
		}

	}

	private void onCarpetaSelected(ObservableValue<? extends Boolean> o, Boolean ov, Boolean nv) {

		if (nv) {
			ficheroCheck.setDisable(true);
		} else {
			ficheroCheck.setDisable(false);
		}

	}

	public BorderPane getView() {
		return view;
	}

	@FXML
	void onCrear(ActionEvent event) {

		try {
			if (esCarpeta.get()) {
				// Coge la ruta y el nombre del fichero
				File f1 = new File(rutaActual.get() + "\\" + multiFuncion.get());

				if (f1.mkdir()) {
					System.out.println("Carpeta creada en la ubicaci�n: " + rutaActual.get());
					System.out.println("Carpeta creado: " + multiFuncion.get());
				} else {
					System.out.println("Esta carpeta ya existe");
				}
			}
			if (esFichero.get()) {
				File f1 = new File(rutaActual.get() + "\\" + multiFuncion.get());

				if (f1.createNewFile()) {
					System.out.println("Fichero creado en la ubicaci�n: " + rutaActual.get());
					System.out.println("Fichero creado: " + multiFuncion.get());
				} else {
					System.out.println("Este fichero ya existe");
				}

			}

		} catch (Exception e) {
			System.err.println("Ocurrio el siguiente error: " + e);
		}
	}

	@FXML
	void onEliminar(ActionEvent event) {

		try {

			// Creamos File para listar
			File listar = new File(rutaActual.get() + "\\" + multiFuncion.get());

			if(listar.isDirectory()) {
				BorrarDirectorio(listar);
				listar.delete();
			}
			else
				listar.delete();

		} catch (Exception e) {
			System.err.println("Ocurrio el siguiente error: " + e);
		}
	}

	/**
	 * Borra todo el contenido de una carpeta y tambien la misma
	 * 
	 * @param listar La lista de ficheros/carpetas a eliminar
	 */
	public void BorrarDirectorio(File listar) {
		File[] ficheros = listar.listFiles();

		for (int x = 0; x < ficheros.length; x++) {
			if (ficheros[x].isDirectory()) {
				BorrarDirectorio(ficheros[x]);
			}
			ficheros[x].delete();
		}
	}

	// ModificarFichero
	@FXML
	void onModificar(ActionEvent event) {

		try {
			FileWriter fw = null;
			PrintWriter pw = null;

			fw = new FileWriter(rutaActual.get() + "\\" + multiFuncion.get());
			pw = new PrintWriter(fw);
			pw.println(contenidoFichero.get());

			fw.close();
		} catch (Exception e) {
			System.err.println("Ocurrio el siguiente error: " + e);
		}
	}

	@FXML
	void onMover(ActionEvent event) {
		try {
			File f1 = new File(rutaActual.get());
			File f2 = new File(multiFuncion.get());

			if (f1.renameTo(f2)) {
				System.out.println("Renombrado de" + rutaActual + " a " + multiFuncion);
			} else {
				System.out.println("No renombrado de" + rutaActual + " a " + multiFuncion);
			}
		} catch (Exception e) {
			System.err.println("Ocurrio el siguiente error: " + e);
		}
	}

	@FXML
	void onVerContenido(ActionEvent event) {
		try {
			File f1 = new File(rutaActual.get() + "\\" + multiFuncion.get());

			if (f1.canRead()) {
				String cadena;

				FileReader fr = new FileReader(rutaActual.get());
				BufferedReader bf = new BufferedReader(fr);

				while ((cadena = bf.readLine()) != null) {
					contenidoTextArea.setText(contenidoTextArea.getText() + cadena);
				}
			}
		} catch (Exception e) {
			System.err.println("Ocurrio el siguiente error: " + e);
		}
	}

	@FXML
	void onVerFicherosCarpetas(ActionEvent event) {
		try {
			File listar = new File(rutaActual.get());
			File[] ficheros = listar.listFiles();
			for (int x = 0; x < ficheros.length; x++) {
				ficherosCarpetas.add(ficheros[x]);
			}

		} catch (Exception e) {
			System.err.println("Ocurrio el siguiente error: " + e);
		}

	}

}
