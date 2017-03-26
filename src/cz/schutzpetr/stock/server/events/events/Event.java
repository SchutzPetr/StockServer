package cz.schutzpetr.stock.server.events.events;

import cz.schutzpetr.stock.server.events.listeners.Listener;

import java.util.List;

/**
 * Created by Petr Schutz on 13.03.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public abstract class Event {
    /**
     * This method calls this event
     *
     * @param listeners list of listeners
     */
    public abstract void call(List<? extends Listener> listeners);

    /**
     * @return listener iface for this event
     */
    public abstract Class<? extends Listener> getListener();
}
