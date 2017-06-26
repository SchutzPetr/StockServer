package cz.schutzpetr.stock.server.data;

import cz.schutzpetr.stock.core.connection.RequestResult;
import cz.schutzpetr.stock.core.expressions.WhereClause;
import cz.schutzpetr.stock.core.location.Location;
import cz.schutzpetr.stock.server.database.DatabaseManager;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Petr Schutz on 19.06.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class LocationData extends Data<String, Location, Location> {

    private volatile boolean wait = false;

    @Override
    public void updateData(Collection<Location> data) {
        while (wait) Thread.yield();
        wait = true;
        super.data.clear();
        data.forEach(location -> super.data.put(location.getName(), location));
        wait = false;
    }

    public RequestResult<Boolean> remove(Location toRemove) {
        while (wait) Thread.yield();
        wait = true;
        if (super.data.containsKey(toRemove.getName())) {
            Location location = super.data.get(toRemove.getName());
            if (location.getPallets().isEmpty() && location.getItems().isEmpty()) {
                super.data.remove(location.getName());
                wait = false;
                return DatabaseManager.getInstance().getDatabase().getData().removeLocation(location);
            } else {
                wait = false;
                return new RequestResult<>(false);
            }
        }
        return new RequestResult<>(false);
    }

    public RequestResult<Location> insertData(Location data) {
        while (wait) Thread.yield();
        wait = true;
        if (super.data.containsKey(data.getName())) {
            wait = false;
            return null;
        } else {
            RequestResult<Boolean> result = DatabaseManager.getInstance().getDatabase().getData().insertLocation(data);
            if (result.isResult()) {
                wait = false;
                return new RequestResult<>(data);
            }
            wait = false;
            return new RequestResult<>(result.getException());
        }
    }

    @Override
    public RequestResult<ArrayList<Location>> getFilteredData(WhereClause whereClause) {
        return DatabaseManager.getInstance().getDatabase().getData().getLocations(whereClause);
    }

    @Override
    public ArrayList<Location> getData() {
        return new ArrayList<>(super.data.values());
    }
}
