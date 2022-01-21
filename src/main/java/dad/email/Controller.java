package dad.email;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import javafx.fxml.Initializable;



public class Controller implements Initializable {

	// model
	
	//StringProperty emailFromProperty = new SimpleStringProperty();
	
	// view
	
	public Controller() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EnviarEmail.fxml"));
		loader.setController(this);
		loader.load();
	}
	
	@FXML
    private CheckBox SSLcheckBox;

    @FXML
    private Button closeButton;

    @FXML
    private TextField emailFromTextField;

    @FXML
    private TextField emailToTextField;

    @FXML
    private Button emptyButton;

    @FXML
    private TextField messageTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField passwordFromTextField;

    @FXML
    private TextField portTextField;
    
    @FXML
    private TextField topicTextField;

    @FXML
    private Button sendButton;

    @FXML
    private GridPane view;
	
    @FXML
    void onCloseButton(ActionEvent event) {
    	System.exit(0);
    }

    @FXML
    void onEmptyButton(ActionEvent event) {
    	nameTextField.textProperty().set("");
    	portTextField.textProperty().set("");
    	emailFromTextField.textProperty().set("");
    	passwordFromTextField.textProperty().set("");
    	emailToTextField.textProperty().set("");
    	topicTextField.textProperty().set("");	
    	messageTextField.textProperty().set("");	  	
    }

    @FXML
    void onSendButton(ActionEvent event) throws EmailException {
    	
    	
    	Task<Void> task = new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				Email email = new SimpleEmail();
		    	email.setSslSmtpPort(portTextField.textProperty().get());
		    	email.setHostName(nameTextField.textProperty().get());
		    	email.setAuthenticator(new DefaultAuthenticator(emailFromTextField.textProperty().get(), passwordFromTextField.textProperty().get()));
		    	email.setSSLOnConnect(true);
		    	email.setFrom(emailFromTextField.textProperty().get());
		    	email.setSubject(topicTextField.textProperty().get());
		    	email.setMsg(messageTextField.textProperty().get());
		    	email.addTo(emailToTextField.textProperty().get());
				email.send();
				return null;
			}
    			
    	};
    	
    	task.setOnSucceeded(e->{
    		Alert alertSuccess = new Alert(AlertType.INFORMATION);
    		alertSuccess.setTitle("Mensaje enviado");
    		alertSuccess.setHeaderText("Mensaje enviado con éxito a: '" + emailToTextField.textProperty().get() + "'");
    		alertSuccess.initOwner(view.getScene().getWindow());
    		alertSuccess.showAndWait();

    	});
    	
    	task.setOnFailed(e->{
    		Alert alertFail = new Alert(AlertType.ERROR);
    		alertFail.setTitle("Error");
    		alertFail.setHeaderText("No se pudo enviar el email.");
    		alertFail.setContentText("Invalid message supplied");
    		alertFail.initOwner(view.getScene().getWindow());
    		alertFail.showAndWait();
    	});
    	
    	new Thread(task).start();  	
    }
    
    public GridPane getView() {
		return view;
	}
    
	public void initialize(URL location, ResourceBundle resources) {	
		
	}

}
