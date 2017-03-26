package cz.schutzpetr.stock.server.events.events.server;

import cz.schutzpetr.stock.server.events.events.Event;
import cz.schutzpetr.stock.server.events.listeners.Listener;
import cz.schutzpetr.stock.server.events.listeners.server.ServerStoppedListener;

import java.util.List;

/**
 * Created by Petr Schutz on 10.03.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class ServerStoppedEvent extends Event {

    /**
     * This method calls this event
     *
     * @param listeners list of listeners
     */
    @Override
    public void call(List<? extends Listener> listeners) {
        listeners.forEach(x -> ((ServerStoppedListener) x).onServerStopped(this));
    }

    /**
     * @return listener iface for this event
     */
    @Override
    public Class<ServerStoppedListener> getListener() {
        return ServerStoppedListener.class;
    }
}
