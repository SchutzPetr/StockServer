package cz.schutzpetr.stock.server.events;

import cz.schutzpetr.stock.server.events.events.StageCloseRequest;
import cz.schutzpetr.stock.server.events.listeners.StageCloseRequestListener;

/**
 * Created by Petr Schutz on 11.03.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class Test implements StageCloseRequestListener {


    /**
     * Invoked when a specific event of the type for which this handler is registered happens.
     *
     * @param event {@code StageCloseRequest}
     */
    @Override
    public void onStageCloseRequest(StageCloseRequest event) {
        //System.out.println("Close" + event.getStage().getTitle());
    }
}
