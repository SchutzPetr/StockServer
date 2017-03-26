package cz.schutzpetr.stock.server.events.events.client;

import cz.schutzpetr.stock.server.client.Client;
import cz.schutzpetr.stock.server.events.events.Event;
import cz.schutzpetr.stock.server.events.listeners.Listener;
import cz.schutzpetr.stock.server.events.listeners.client.ClientDisconnectListener;

import java.util.List;

/**
 * Created by Petr Schutz on 13.03.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class ClientDisconnectEvent extends Event {
    /**
     * Instance of {@code Client}
     */
    private final Client client;

    public ClientDisconnectEvent(Client client) {
        this.client = client;
    }

    /**
     * This method calls this event
     *
     * @param listeners list of listeners
     */
    @Override
    public void call(List<? extends Listener> listeners) {
        listeners.forEach(x -> ((ClientDisconnectListener) x).onClientDisconnect(this));
    }

    /**
     * @return listener iface for this event
     */
    @Override
    public Class<ClientDisconnectListener> getListener() {
        return ClientDisconnectListener.class;
    }

    /**
     * @return the logged-on client
     */
    public Client getClient() {
        return client;
    }
}
