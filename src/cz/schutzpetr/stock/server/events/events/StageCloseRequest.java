package cz.schutzpetr.stock.server.events.events;


import cz.schutzpetr.stock.server.events.listeners.Listener;
import cz.schutzpetr.stock.server.events.listeners.StageCloseRequestListener;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.List;

/**
 * Created by Petr Schutz on 13.03.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class StageCloseRequest extends Event {

    private final WindowEvent windowEvent;


    public StageCloseRequest(WindowEvent eventHandler) {
        this.windowEvent = eventHandler;
    }

    public WindowEvent getWindowEvent() {
        return windowEvent;
    }

    public Stage getStage() {
        return ((Stage) getWindowEvent().getSource());
    }

    /**
     * This method calls this event
     *
     * @param listeners list of listeners
     */
    @Override
    public void call(List<? extends Listener> listeners) {
        listeners.forEach(x -> ((StageCloseRequestListener) x).onStageCloseRequest(this));
    }

    /**
     * @return listener iface for this event
     */
    @Override
    public Class<StageCloseRequestListener> getListener() {
        return StageCloseRequestListener.class;
    }

}
