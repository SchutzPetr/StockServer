package cz.schutzpetr.stock.server.database;

/**
 * Created by Petr Schutz on 06.03.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class DatabaseResult<T> {

    private final T result;
    private final boolean isResult;

    public DatabaseResult(boolean isResult, T result) {
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
