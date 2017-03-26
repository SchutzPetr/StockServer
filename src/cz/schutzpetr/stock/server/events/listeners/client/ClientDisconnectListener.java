package cz.schutzpetr.stock.server.events.listeners.client;

import cz.schutzpetr.stock.server.events.events.client.ClientDisconnectEvent;
import cz.schutzpetr.stock.server.events.listeners.Listener;

/**
 * Created by Petr Schutz on 13.03.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public interface ClientDisconnectListener extends Listener {

    /**
     * Invoked when a specific event of the type for which this handler is registered happens.
     *
     * @param event {@code ClientDisconnectEvent}
     */
    void onClientDisconnect(ClientDisconnectEvent event);
}
