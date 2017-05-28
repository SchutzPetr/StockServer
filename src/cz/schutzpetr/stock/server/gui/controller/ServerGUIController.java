package cz.schutzpetr.stock.server.gui.controller;


import com.jfoenix.controls.JFXTextField;
import cz.schutzpetr.stock.core.gui.ControllerImpl;
import cz.schutzpetr.stock.core.utils.TextAreaHandler;
import cz.schutzpetr.stock.server.Server;
import cz.schutzpetr.stock.server.gui.server.CPULoadChart;
import cz.schutzpetr.stock.server.gui.server.ServerControls;
import cz.schutzpetr.stock.server.utils.Logger;
import cz.schutzpetr.stock.server.utils.LoggerFormatter;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;

/**
 * Created by schut on 26.02.2017.
 * <p>
 * Sample Skeleton for 'ServerGUI.fxml' Controller Class
 */

public class ServerGUIController implements ControllerImpl {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="tabOnlineUsers1"
    private Tab tabOnlineUsers1; // Value injected by FXMLLoader

    @FXML // fx:id="serverStartButton"
    private Button serverStartButton; // Value injected by FXMLLoader

    @FXML // fx:id="serverStopButton"
    private Button serverStopButton; // Value injected by FXMLLoader

    @FXML // fx:id="serverRestartButton"
    private Button serverRestartButton; // Value injected by FXMLLoader

    @FXML // fx:id="statusLabel"
    private Label statusLabel; // Value injected by FXMLLoader

    @FXML // fx:id="cpu_Chart"
    private LineChart<String, Number> cpu_Chart; // Value injected by FXMLLoader

    @FXML // fx:id="serverConsole"
    private TextArea serverConsole; // Value injected by FXMLLoader

    @FXML // fx:id="commandLine"
    private TextField commandLine; // Value injected by FXMLLoader

    @FXML // fx:id="sendCommandButton"
    private Button sendCommandButton; // Value injected by FXMLLoader

    @FXML // fx:id="tabConsole"
    private Tab tabConsole; // Value injected by FXMLLoader

    @FXML // fx:id="tabOnlineUsers"
    private Tab tabOnlineUsers; // Value injected by FXMLLoader

    @FXML // fx:id="vBox_TabOnlineUsers"
    private VBox vBox_TabOnlineUsers; // Value injected by FXMLLoader

    @FXML // fx:id="filterTextField"
    private JFXTextField filterTextField; // Value injected by FXMLLoader

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert tabOnlineUsers1 != null : "fx:id=\"tabOnlineUsers1\" was not injected: check your FXML file 'ServerGUI.fxml'.";
        assert serverStartButton != null : "fx:id=\"serverStartButton\" was not injected: check your FXML file 'ServerGUI.fxml'.";
        assert serverStopButton != null : "fx:id=\"serverStopButton\" was not injected: check your FXML file 'ServerGUI.fxml'.";
        assert serverRestartButton != null : "fx:id=\"serverRestartButton\" was not injected: check your FXML file 'ServerGUI.fxml'.";
        assert statusLabel != null : "fx:id=\"statusLabel\" was not injected: check your FXML file 'ServerGUI.fxml'.";
        assert cpu_Chart != null : "fx:id=\"cpu_Chart\" was not injected: check your FXML file 'ServerGUI.fxml'.";
        assert serverConsole != null : "fx:id=\"serverConsole\" was not injected: check your FXML file 'ServerGUI.fxml'.";
        assert commandLine != null : "fx:id=\"commandLine\" was not injected: check your FXML file 'ServerGUI.fxml'.";
        assert sendCommandButton != null : "fx:id=\"sendCommandButton\" was not injected: check your FXML file 'ServerGUI.fxml'.";
        assert tabConsole != null : "fx:id=\"tabConsole\" was not injected: check your FXML file 'ServerGUI.fxml'.";
        assert tabOnlineUsers != null : "fx:id=\"tabOnlineUsers\" was not injected: check your FXML file 'ServerGUI.fxml'.";
        assert vBox_TabOnlineUsers != null : "fx:id=\"vBox_TabOnlineUsers\" was not injected: check your FXML file 'ServerGUI.fxml'.";
        assert filterTextField != null : "fx:id=\"filterTextField\" was not injected: check your FXML file 'ServerGUI.fxml'.";

        CPULoadChart cpuLoadChart = new CPULoadChart(cpu_Chart);//todo:
        ServerControls serverControls = new ServerControls(serverStartButton, serverStopButton, serverRestartButton, statusLabel);

        TextAreaHandler console = new TextAreaHandler(serverConsole);

        console.setFormatter(new LoggerFormatter(false));
        console.setLevel(Level.ALL);

        Logger.getLogger().addHandler(console);


        /*PrintStream ps = new PrintStream(console, true);
        System.setOut(ps);
        System.setErr(ps);*/
        serverStartButton.setOnAction(event -> Server.getInstance().start());
        serverStopButton.setOnAction(event -> Server.getInstance().stop());
        serverRestartButton.setOnAction(event -> Server.getInstance().restart());

        //DatabaseManager.getInstance().getDatabase().getLocationTable().insertLocation(Location.getLocation("H-100", LocationType.PILE, "101"));
        //DatabaseManager.getInstance().getDatabase().getLocationTable().insertLocation(Location.getLocation("H-100", LocationType.PILE, "101"));
    }
}

