package cz.schutzpetr.stock.server.gui;

import cz.schutzpetr.stock.server.client.Clients;
import cz.schutzpetr.stock.server.commands.CommandManager;
import cz.schutzpetr.stock.server.events.EventManager;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by Petr Schutz on 06.03.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class ServerApplication extends Application {
    private static ServerApplication instance;

    /**
     * Creates instance of this class
     */
    public ServerApplication() {
        instance = this;
    }

    /**
     * Main app method
     *
     * @param args args
     */
    public static void main(String[] args) {
        EventManager.registerListener(Clients.getInstance());
        CommandManager.registerCommands();
        launch(args);
    }

    public static ServerApplication getInstance() {
        return instance;
    }

    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     * <p>
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set. The primary stage will be embedded in
     *                     the browser if the application was launched as an applet.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages and will not be embedded in the browser.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        ApplicationManager.init();
    }
}
