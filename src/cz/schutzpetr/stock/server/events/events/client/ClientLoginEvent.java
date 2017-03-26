package cz.schutzpetr.stock.server.events.events.client;

import cz.schutzpetr.stock.server.client.Client;
import cz.schutzpetr.stock.server.client.UnknownClient;
import cz.schutzpetr.stock.server.events.events.Event;
import cz.schutzpetr.stock.server.events.listeners.Listener;
import cz.schutzpetr.stock.server.events.listeners.client.ClientLoginListener;

import java.util.List;

/**
 * Created by Petr Schutz on 16.03.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class ClientLoginEvent extends Event {
    /**
     * Instance of {@code Client}
     */
    private final Client client;

    /**
     * Instance of {@code UnknownClient}
     */
    private final UnknownClient unknownClient;

    /**
     * @param client        the logged-on client
     * @param unknownClient unknownClient
     */
    public ClientLoginEvent(Client client, UnknownClient unknownClient) {
        this.client = client;
        this.unknownClient = unknownClient;
    }

    /**
     * This method calls this event
     *
     * @param listeners list of listeners
     */
    @Override
    public void call(List<? extends Listener> listeners) {
        listeners.forEach(x -> ((ClientLoginListener) x).onClientLoginEvent(this));
    }

    /**
     * @return listener iface for this event
     */
    @Override
    public Class<ClientLoginListener> getListener() {
        return ClientLoginListener.class;
    }

    /**
     * @return the logged-on client
     */
    public Client getClient() {
        return client;
    }

    /**
     * @return unknownClient
     */
    public UnknownClient getUnknownClient() {
        return unknownClient;
    }
}
