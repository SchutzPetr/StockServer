package cz.schutzpetr.stock.server.data;

import cz.schutzpetr.stock.core.connection.RequestResult;
import cz.schutzpetr.stock.core.expressions.WhereClause;
import cz.schutzpetr.stock.core.location.Pallet;
import cz.schutzpetr.stock.server.database.DatabaseManager;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Petr Schutz on 19.06.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class PalletData extends Data<String, Pallet, Pallet> {

    private volatile boolean wait = false;

    @Override
    public void updateData(Collection<Pallet> data) {
        while (wait) Thread.yield();
        wait = true;
        super.data.clear();
        data.forEach(pallet -> super.data.put(pallet.getName(), pallet));
        wait = false;
    }

    public RequestResult<Boolean> remove(Pallet toRemove) {
        while (wait) Thread.yield();
        wait = true;
        if (super.data.containsKey(toRemove.getPalletNumber())) {
            Pallet pallet = super.data.get(toRemove.getName());
            if (pallet.getItems().isEmpty()) {
                super.data.remove(pallet.getPalletNumber());
                pallet.getLocation().getPallets().remove(pallet.getPalletNumber());
                wait = false;
                return DatabaseManager.getInstance().getDatabase().getData().removePallet(pallet);
            } else {
                wait = false;
                return new RequestResult<>(false);
            }
        }
        return new RequestResult<>(false);
    }

    public RequestResult<Pallet> insertData(Pallet data) {
        while (wait) Thread.yield();
        wait = true;
        if (super.data.containsKey(data.getName())) {
            wait = false;
            return null;
        } else {
            RequestResult<Boolean> result = DatabaseManager.getInstance().getDatabase().getData().insertPallet(data);
            if (result.isResult()) {
                wait = false;
                return new RequestResult<>(data);
            }
            wait = false;
            return new RequestResult<>(result.getException());
        }
    }

    @Override
    public RequestResult<ArrayList<Pallet>> getFilteredData(WhereClause whereClause) {
        return DatabaseManager.getInstance().getDatabase().getData().getPallets(whereClause);
    }

    @Override
    public ArrayList<Pallet> getData() {
        return new ArrayList<>(super.data.values());
    }
}
