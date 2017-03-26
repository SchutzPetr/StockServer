package cz.schutzpetr.stock.server.events.events.server;

import cz.schutzpetr.stock.server.events.events.Event;
import cz.schutzpetr.stock.server.events.listeners.Listener;
import cz.schutzpetr.stock.server.events.listeners.server.ServerStoppingListener;

import java.util.List;

/**
 * Created by Petr Schutz on 10.03.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class ServerStoppingEvent extends Event {

    /**
     * This method calls this event
     *
     * @param listeners list of listeners
     */
    @Override
    public void call(List<? extends Listener> listeners) {
        listeners.forEach(x -> ((ServerStoppingListener) x).onServerStopping(this));
    }

    /**
     * @return listener iface for this event
     */
    @Override
    public Class<ServerStoppingListener> getListener() {
        return ServerStoppingListener.class;
    }
}
