package cz.schutzpetr.stock.server.gui;

import cz.schutzpetr.stock.server.gui.controller.LoginGUIController;
import cz.schutzpetr.stock.server.gui.controller.ServerGUIController;
import javafx.stage.Modality;

/**
 * Created by Petr Schutz on 06.03.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class ApplicationManager {
    /**
     * Instance of this class
     */
    private static ApplicationManager instance = null;

    /**
     * @return instance of this class
     */
    public static ApplicationManager getInstance() {
        return instance;
    }

    /**
     * Instance of {@code BasicGUI} with {@code LoginGUIController} as param
     */
    private final BasicGUI<LoginGUIController> loginGUI;

    /**
     * Instance of {@code BasicGUI} with {@code ServerGUIController} as param
     */
    private BasicGUI<ServerGUIController> serverGUI;

    /**
     * A method that initializes the class. This method can be called only once. The next call will be ignored.
     */
    static void init(){
        if(instance != null)return;
        instance = new ApplicationManager();
    }

    /**
     * Creates instance of this class and start Login GUI
     */
    private ApplicationManager() {
        instance = this;
        this.loginGUI = new BasicGUI<>("Stock", 800, 450, true, "/cz/schutzpetr/stock/server/gui/fxml/LoginGUI.fxml", 
                "/res/img/Deployment-50.png", Modality.APPLICATION_MODAL);
    }

    /**
     * This method start server GUI and close login GUI
     */
    public void runServerGUI(){
        this.loginGUI.closeStage();
        this.serverGUI = new BasicGUI<>("Stock", 1280, 720, true, "/cz/schutzpetr/stock/server/gui/fxml/ServerGUI.fxml",
                "/res/img/Deployment-50.png", Modality.APPLICATION_MODAL);
    }

    /**
     * @return instance of {@code BasicGUI} with {@code ServerGUIController} as param
     */
    public BasicGUI<ServerGUIController> getServerGUI() {
        return serverGUI;
    }
}
