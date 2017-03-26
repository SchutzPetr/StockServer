package cz.schutzpetr.stock.server.gui.server;

import cz.schutzpetr.stock.core.gui.utils.ButtonUtils;
import cz.schutzpetr.stock.server.Server;
import cz.schutzpetr.stock.server.events.EventManager;
import cz.schutzpetr.stock.server.events.events.server.ServerStartedEvent;
import cz.schutzpetr.stock.server.events.events.server.ServerStartingEvent;
import cz.schutzpetr.stock.server.events.events.server.ServerStoppedEvent;
import cz.schutzpetr.stock.server.events.events.server.ServerStoppingEvent;
import cz.schutzpetr.stock.server.events.listeners.server.ServerStartedListener;
import cz.schutzpetr.stock.server.events.listeners.server.ServerStartingListener;
import cz.schutzpetr.stock.server.events.listeners.server.ServerStoppedListener;
import cz.schutzpetr.stock.server.events.listeners.server.ServerStoppingListener;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Created by Petr Schutz on 13.03.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class ServerControls implements ServerStartingListener, ServerStoppingListener, ServerStoppedListener, ServerStartedListener {

    private final Button serverStartButton;
    private final Button serverStopButton;
    private final Button serverRestartButton;
    private final Label statusLabel;


    public ServerControls(Button serverStartButton, Button serverStopButton, Button serverRestartButton, Label statusLabel) {
        this.statusLabel = statusLabel;
        this.serverStartButton = serverStartButton;
        this.serverStopButton = serverStopButton;
        this.serverRestartButton = serverRestartButton;

        EventManager.registerListener(this);

        ButtonUtils.setupImageButton(this.serverStartButton, "/res/img/Start-32.png");
        ButtonUtils.setupImageButton(this.serverStopButton, "/res/img/Stop-32.png");
        ButtonUtils.setupImageButton(this.serverRestartButton, "/res/img/Restart-32.png");
    }

    private void updateStatus() {

        Platform.runLater(() -> {
            if (Server.getInstance().isRunning()) {
                this.statusLabel.setText("Online");
                this.statusLabel.setStyle("-fx-background-color: lime;");
            } else {
                this.statusLabel.setText("Offline");
                this.statusLabel.setStyle("-fx-background-color: red;");
            }
        });
    }

    /**
     * Invoked when a specific event of the type for which this handler is registered happens.
     *
     * @param event {@code ServerStoppingEvent}
     */
    @Override
    public void onServerStopping(ServerStoppingEvent event) {
        serverStartButton.setDisable(false);
        serverStopButton.setDisable(true);
        serverRestartButton.setDisable(true);
    }

    /**
     * Invoked when a specific event of the type for which this handler is registered happens.
     *
     * @param event {@code ServerStartingEvent}ServerStartingEvent
     */
    @Override
    public void onServerStarting(ServerStartingEvent event) {
        serverStartButton.setDisable(true);
        serverStopButton.setDisable(false);
        serverRestartButton.setDisable(false);
    }

    /**
     * Invoked when a specific event of the type for which this handler is registered happens.
     *
     * @param event {@code ServerStartedEvent}
     */
    @Override
    public void onServerStarted(ServerStartedEvent event) {
        updateStatus();
    }

    /**
     * Invoked when a specific event of the type for which this handler is registered happens.
     *
     * @param event {@code ServerStoppedEvent}
     */
    @Override
    public void onServerStopped(ServerStoppedEvent event) {
        updateStatus();
    }
}
