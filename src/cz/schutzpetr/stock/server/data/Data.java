package cz.schutzpetr.stock.server.data;

import cz.schutzpetr.stock.core.connection.RequestResult;
import cz.schutzpetr.stock.core.expressions.WhereClause;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Petr Schutz on 19.06.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public abstract class Data<KeyType, ValueType, ReturnType> {

    protected final Map<KeyType, ValueType> data;

    Data() {
        this.data = new HashMap<>();
    }

    public abstract void updateData(Collection<ReturnType> data);

    public abstract RequestResult<ArrayList<ReturnType>> getFilteredData(WhereClause whereClause);

    public abstract ArrayList<ReturnType> getData();

}
