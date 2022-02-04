package aed.accesoficheros;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class FicheroController implements Initializable {

	// Model
	private StringProperty rutaActual = new SimpleStringProperty();
	private StringProperty multiFuncion = new SimpleStringProperty();
	private StringProperty contenidoFichero = new SimpleStringProperty();

	private BooleanProperty esCarpeta = new SimpleBooleanProperty();
	private BooleanProperty esFichero = new SimpleBooleanProperty();

	private ListProperty<String> ficherosCarpetas = new SimpleListProperty<>();

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
	private ListView<String> ficheroCarpetasList;

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
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("AVISO");
					alert.setHeaderText("Procedimiento realizado");
					alert.setContentText("Carpeta creada en la ubicaci�n: " + rutaActual.get() + "\nCarpeta creada: "
							+ multiFuncion.get());
					alert.showAndWait();
				} else {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("AVISO");
					alert.setHeaderText("Esta carpeta ya existe");
					alert.setContentText("");
					alert.showAndWait();
				}
			}
			if (esFichero.get()) {
				File f1 = new File(rutaActual.get() + "\\" + multiFuncion.get());

				if (f1.createNewFile()) {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("AVISO");
					alert.setHeaderText("Procedimiento realizado");
					alert.setContentText("Fichero creado en la ubicaci�n: " + rutaActual.get() + "\nFichero creado: "
							+ multiFuncion.get());
					alert.showAndWait();
				} else {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("AVISO");
					alert.setHeaderText("Este fichero ya existe");
					alert.setContentText("");
					alert.showAndWait();

				}

			}

		} catch (Exception e) {

			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("AVISO");
			alert.setHeaderText("Ocurrio el siguiente error: ");
			alert.setContentText(e.getStackTrace()+"");
			alert.showAndWait();
		}
	}

	@FXML
	void onEliminar(ActionEvent event) {

		try {

			// Creamos File para listar
			File listar = new File(rutaActual.get() + "\\" + multiFuncion.get());

			if (listar.isDirectory()) {
				BorrarDirectorio(listar);
				listar.delete();
			} else
				listar.delete();

		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("AVISO");
			alert.setHeaderText("Ocurrio el siguiente error: ");
			alert.setContentText(e.getStackTrace()+"");
			alert.showAndWait();
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
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("AVISO");
			alert.setHeaderText("Ocurrio el siguiente error: ");
			alert.setContentText(e.getStackTrace()+"");
			alert.showAndWait();
		}
	}

	/**
	 * Mover / Renombrar el fichero o carpeta
	 * 
	 * @param event
	 */
	@FXML
	void onMover(ActionEvent event) {
		try {
			File f1 = new File(rutaActual.get());
			File f2 = new File(multiFuncion.get());

			if (f2.exists()) {
				if (f1.renameTo(f2)) {
					System.out.println("Renombrado de" + rutaActual + " a " + multiFuncion);
				} else {
					System.out.println("No renombrado de" + rutaActual + " a " + multiFuncion);
				}
			} else {
				System.out.println("La ruta u objeto no existe");
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("AVISO");
			alert.setHeaderText("Ocurrio el siguiente error: ");
			alert.setContentText(e.getStackTrace()+"");
			alert.showAndWait();
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
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("AVISO");
			alert.setHeaderText("Ocurrio el siguiente error: ");
			alert.setContentText(e.getStackTrace()+"");
			alert.showAndWait();
		}
	}

	@FXML
	void onVerFicherosCarpetas(ActionEvent event) {
		try {
			File listar = new File(rutaActual.get());
			File[] ficheros = listar.listFiles();
			for (int x = 0; x < ficheros.length; x++) {
				ficherosCarpetas.add(ficheros[x].getPath());
			}

		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("ERROR");
			alert.setHeaderText("Ocurrio el siguiente error");

			Exception ex = new FileNotFoundException("No se pudo realizar la accion");

			// Create expandable Exception.
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String exceptionText = sw.toString();

			TextArea textArea = new TextArea(exceptionText);
			textArea.setEditable(false);
			textArea.setWrapText(true);

			textArea.setMaxWidth(Double.MAX_VALUE);
			textArea.setMaxHeight(Double.MAX_VALUE);
			GridPane.setVgrow(textArea, Priority.ALWAYS);
			GridPane.setHgrow(textArea, Priority.ALWAYS);

			GridPane expContent = new GridPane();
			expContent.setMaxWidth(Double.MAX_VALUE);
			expContent.add(textArea, 0, 1);

			alert.getDialogPane().setExpandableContent(expContent);

			alert.showAndWait();
		}

	}

}
