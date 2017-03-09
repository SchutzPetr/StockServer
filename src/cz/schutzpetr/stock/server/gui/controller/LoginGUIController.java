package cz.schutzpetr.stock.server.gui.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import cz.schutzpetr.stock.core.auth.AuthResult;
import cz.schutzpetr.stock.server.database.Database;
import cz.schutzpetr.stock.server.database.DatabaseManager;
import cz.schutzpetr.stock.server.gui.ApplicationManager;
import cz.schutzpetr.stock.server.gui.utils.ButtonUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Petr Schutz on 18.12.2016
 *
 * @author Petr Schutz
 * @version 1.0
 */

public class LoginGUIController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="wrongImg"
    private ImageView wrongImg; // Value injected by FXMLLoader

    @FXML // fx:id="wrongLabel"
    private Label wrongLabel; // Value injected by FXMLLoader

    @FXML // fx:id="userField"
    private JFXTextField userField; // Value injected by FXMLLoader

    @FXML // fx:id="passField"
    private JFXPasswordField passField; // Value injected by FXMLLoader

    @FXML // fx:id="loginButton"
    private Button loginButton; // Value injected by FXMLLoader

    @FXML // fx:id="img_View"
    private ImageView img_View; // Value injected by FXMLLoader

    /**
     * Called to initialize a controller after its root element has been completely processed.
     */
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert wrongImg != null : "fx:id=\"wrongImg\" was not injected: check your FXML file 'LoginGUI.fxml'.";
        assert wrongLabel != null : "fx:id=\"wrongLabel\" was not injected: check your FXML file 'LoginGUI.fxml'.";
        assert userField != null : "fx:id=\"userField\" was not injected: check your FXML file 'LoginGUI.fxml'.";
        assert passField != null : "fx:id=\"passField\" was not injected: check your FXML file 'LoginGUI.fxml'.";
        assert loginButton != null : "fx:id=\"loginButton\" was not injected: check your FXML file 'LoginGUI.fxml'.";
        assert img_View != null : "fx:id=\"img_View\" was not injected: check your FXML file 'LoginGUI.fxml'.";

        ButtonUtils.setupButton(loginButton);

        loginButton.setOnAction(event -> login());

    }

    /**
     * This method will be called when login button will be pressed
     */
    private void login(){
        wrongLabel.setVisible(false);
        wrongImg.setVisible(false);
        PasswordAuthentication passwordAuthentication = new PasswordAuthentication(userField.getText(), passField.getText().toCharArray());
        Database database = DatabaseManager.getInstance().getDatabase();
        if(database == null){
            wrongLabel.setText("Spojení s databází nebylo navázáno!");
            wrongLabel.setVisible(true);
            wrongImg.setVisible(true);
        }else{
            AuthResult authResult = database.getUserTable().check(passwordAuthentication);
            if(authResult.isResult()){
                ApplicationManager.getInstance().runServerGUI();
            }else{
                wrongLabel.setText(authResult.getMessage());
                wrongLabel.setVisible(true);
                wrongImg.setVisible(true);
            }
        }
    }
}
