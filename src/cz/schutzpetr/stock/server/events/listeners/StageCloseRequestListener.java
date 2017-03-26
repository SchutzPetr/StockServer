package cz.schutzpetr.stock.server.events.listeners;

import cz.schutzpetr.stock.server.events.events.StageCloseRequest;

/**
 * Created by Petr Schutz on 13.03.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public interface StageCloseRequestListener extends Listener {

    /**
     * Invoked when a specific event of the type for which this handler is registered happens.
     *
     * @param event {@code StageCloseRequest}
     */
    void onStageCloseRequest(StageCloseRequest event);


}
