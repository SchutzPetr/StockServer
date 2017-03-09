package cz.schutzpetr.stock.server.database;

/**
 * Created by Petr Schutz on 06.03.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class DatabaseResult<T> {

    private T type;

    public DatabaseResult(T type){
        this.type = type;
    }
}
