package cz.schutzpetr.stock.server.events.listeners;

import cz.schutzpetr.stock.server.events.events.LogMessageEvent;

/**
 * Created by Petr Schutz on 15.03.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public interface LogMessageListener extends Listener {

    /**
     * Invoked when a specific event of the type for which this handler is registered happens.
     *
     * @param event {@code LogMessageEvent}
     */
    void onLogMessage(LogMessageEvent event);
}
