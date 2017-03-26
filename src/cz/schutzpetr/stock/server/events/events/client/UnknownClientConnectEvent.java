package cz.schutzpetr.stock.server.events.events.client;

import cz.schutzpetr.stock.server.client.UnknownClient;
import cz.schutzpetr.stock.server.events.events.Event;
import cz.schutzpetr.stock.server.events.listeners.Listener;
import cz.schutzpetr.stock.server.events.listeners.client.UnknownClientConnectListener;

import java.util.List;

/**
 * Created by Petr Schutz on 13.03.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class UnknownClientConnectEvent extends Event {
    /**
     * Instance of {@code UnknownClient}
     */
    private final UnknownClient unknownClient;

    /**
     * @param unknownClient instance of {@code UnknownClient}
     */
    public UnknownClientConnectEvent(UnknownClient unknownClient) {
        this.unknownClient = unknownClient;
    }

    /**
     * This method calls this event
     *
     * @param listeners list of listeners
     */
    @Override
    public void call(List<? extends Listener> listeners) {
        listeners.forEach(x -> ((UnknownClientConnectListener) x).onUnknownClientConnect(this));
    }

    /**
     * @return listener iface for this event
     */
    @Override
    public Class<UnknownClientConnectListener> getListener() {
        return UnknownClientConnectListener.class;
    }

    /**
     * @return unknownClient
     */
    public UnknownClient getUnknownClient() {
        return unknownClient;
    }
}
