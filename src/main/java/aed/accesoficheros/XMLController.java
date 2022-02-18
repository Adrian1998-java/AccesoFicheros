package aed.accesoficheros;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class XMLController implements Initializable {

	@FXML
	private Button ModificarButton;

	@FXML
	private Button aniadirButton;

	@FXML
	private TextField aniadirClienteText;

	@FXML
	private TextField aniadirFinText;

	@FXML
	private TextField aniadirInicioText;

	@FXML
	private Button copiarRutaText;

	@FXML
	private Button eliminarButton;

	@FXML
	private TextField modificarDiaText;

	@FXML
	private TextField modificarHabitacionText;

	@FXML
	private TextField numEliminarText;

	@FXML
	private TextField numHabitacionAniadirText;

	@FXML
	private TextField rutaText;

	@FXML
	private BorderPane view;

	@FXML
	private TextArea vistaText;

	@FXML
	private Button VerContenidoButton;

	@FXML
	private TextField rutaXMLText;

	public XMLController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/XMLView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		rutaXMLText.setText("C:\\Users\\scrag\\OneDrive\\Escritorio\\Habitaciones.xml");
		

	}

	public BorderPane getView() {
		return view;
	}

	//Visualizamos el fichero que hemos puesto en la ruta
    @FXML
    void onVerContenido(ActionEvent event) throws FileNotFoundException, JDOMException, IOException {
    	vistaText.clear();
    	
    	SAXBuilder builder = new SAXBuilder();
		Document documentJDOM = builder.build(new FileInputStream(rutaXMLText.getText()));

		Element raiz = documentJDOM.getRootElement();

		List<Element> hijosraiz = raiz.getChildren();

		for (Element hijo : hijosraiz) {
			String nombre = hijo.getName();

			vistaText.appendText("->" + nombre + "\n");

			String id = hijo.getAttributeValue("numHabitacion");
			if (id != null)
				vistaText.appendText("   |--NumHabitacion: " + id + "\n");

			id = hijo.getAttributeValue("preciodia");
			if (id != null)
				vistaText.appendText("   |--precioDia: " + id + "\n");

			List<Element> hijohijoRaiz = hijo.getChildren();
			for (Element hijo1 : hijohijoRaiz) {
				String nombre1 = hijo1.getName();
				String texto1 = hijo1.getValue();

				if (nombre1 == "codHotel") {
					vistaText.appendText("   |--codHotel: " + texto1 + "\n");
				}
				if (nombre1 == "Estancias") {
					vistaText.appendText("   |--Estancias: " + "\n");

					List<Element> hijoshijoshijosraiz = hijo1.getChildren();

					for (Element hijo2 : hijoshijoshijosraiz) {
						String nombre2 = hijo2.getName();
						String value2 = hijo2.getValue();
						vistaText.appendText("      /-------\n");

						vistaText.appendText("      |--Cliente: " + value2 + "\n");

						id = hijo2.getAttributeValue("fechaInicio");
						if (id != null)
							vistaText.appendText("      |--fechaInicio: " + id + "\n");

						id = hijo2.getAttributeValue("fechaFin");
						if (id != null)
							vistaText.appendText("      |--fechaFin: " + id + "\n");

						vistaText.appendText("      \\-------\n");
					}
				}
			}
		}
    }
    
    //Añadimos una estancia para un cliente
	@FXML
	void onAniadir(ActionEvent event) throws FileNotFoundException, JDOMException, IOException {

		SAXBuilder builder = new SAXBuilder();
		Document documentJDOM = builder.build(new FileInputStream(rutaXMLText.getText()));

		Element raiz = documentJDOM.getRootElement();

		List<Element> hijosRaiz = raiz.getChildren();

		for (Element hijo : hijosRaiz) {
			String id = hijo.getAttributeValue("numHabitacion");
			if (id != null) {
				if (id.equals(numHabitacionAniadirText.getText())) {
					Element etiquetaNueva = new Element("Cliente");

					etiquetaNueva.setAttribute("fechaInicio", aniadirInicioText.getText());
					etiquetaNueva.setAttribute("fechaFin", aniadirFinText.getText());

					etiquetaNueva.setText(aniadirClienteText.getText());

					Element etiquetaHija = (Element) hijo.getChild("Estancias");

					etiquetaHija.addContent(etiquetaNueva);
				}
			}
		}

		XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
		FileOutputStream file = new FileOutputStream(rutaXMLText.getText());
		out.output(documentJDOM, file);
		onVerContenido(event);

	}
	//Copiamos el fichero actual en otro fichero
	@FXML
	void onCopiar(ActionEvent event) throws FileNotFoundException, JDOMException, IOException {

		SAXBuilder builder = new SAXBuilder();
		Document documentJDOM = builder.build(new FileInputStream(rutaXMLText.getText()));

		XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
		FileOutputStream file = new FileOutputStream(copiarRutaText.getText() + ".xml");
		out.output(documentJDOM, file);

	}
	//Eliminamos una habitacion segun su num habitacion
	@FXML
	void onEliminar(ActionEvent event) throws FileNotFoundException, JDOMException, IOException {

		SAXBuilder builder = new SAXBuilder();
		Document documentJDOM = builder.build(new FileInputStream(rutaXMLText.getText()));

		Element raiz = documentJDOM.getRootElement();

		List<Element> hijosRaiz = raiz.getChildren();

		Element HijoEliminar = null;

		for (Element hijo : hijosRaiz) {
			String id = hijo.getAttributeValue("numHabitacion");
			if (id != null) {
				if (id.equals(numEliminarText.getText())) {
					HijoEliminar = hijo;
				}
			}
		}

		hijosRaiz.remove(HijoEliminar);

		XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
		FileOutputStream file = new FileOutputStream(rutaXMLText.getText());
		out.output(documentJDOM, file);


	}
	//Modificamos el precidai segun su num habitacion
	@FXML
	void onModificar(ActionEvent event) throws FileNotFoundException, JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder();
		Document documentJDOM = builder.build(new FileInputStream(rutaXMLText.getText()));

		Element raiz = documentJDOM.getRootElement();

		List<Element> hijosRaiz = raiz.getChildren();

		for (Element hijo : hijosRaiz) {
			String id = hijo.getAttributeValue("numHabitacion");
			if (id != null) {
				if (id.equals(modificarHabitacionText.getText())) {
					hijo.setAttribute("preciodia", modificarDiaText.getText());

				}
			}
		}

		XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
		FileOutputStream file = new FileOutputStream(rutaXMLText.getText());
		out.output(documentJDOM, file);

	}

}
