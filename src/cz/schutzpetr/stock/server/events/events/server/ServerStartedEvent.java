package cz.schutzpetr.stock.server.events.events.server;

import cz.schutzpetr.stock.server.events.events.Event;
import cz.schutzpetr.stock.server.events.listeners.Listener;
import cz.schutzpetr.stock.server.events.listeners.server.ServerStartedListener;

import java.util.List;

/**
 * Created by Petr Schutz on 10.03.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class ServerStartedEvent extends Event {

    /**
     * This method calls this event
     *
     * @param listeners list of listeners
     */
    @Override
    public void call(List<? extends Listener> listeners) {
        listeners.forEach(x -> ((ServerStartedListener) x).onServerStarted(this));
    }

    /**
     * @return listener iface for this event
     */
    @Override
    public Class<ServerStartedListener> getListener() {
        return ServerStartedListener.class;
    }
}
