package cz.schutzpetr.stock.server.events.listeners.server;

import cz.schutzpetr.stock.server.events.events.server.ServerStartingEvent;
import cz.schutzpetr.stock.server.events.listeners.Listener;

/**
 * Created by Petr Schutz on 10.03.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public interface ServerStartingListener extends Listener {

    /**
     * Invoked when a specific event of the type for which this handler is registered happens.
     *
     * @param event {@code ServerStartingEvent}ServerStartingEvent
     */
    void onServerStarting(ServerStartingEvent event);

}
