package cz.schutzpetr.stock.core.connection;

import java.io.Serializable;

/**
 * Created by Petr Schutz on 24.03.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class RequestResult<T> implements Serializable {
    private final T result;
    private final boolean isResult;


    public RequestResult(boolean isResult, T result) {
        this.isResult = isResult;
        this.result = result;
    }

    public T getResult() {
        return result;
    }

    public boolean isResult() {
        return isResult;
    }
}
