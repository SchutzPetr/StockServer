package cz.schutzpetr.stock.server.database.extractor;

import cz.schutzpetr.stock.core.location.Pallet;
import cz.schutzpetr.stock.server.database.DatabaseManager;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Petr Schutz on 19.05.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class PalletExtractor implements ResultSetExtractor<Pallet> {

    /**
     * Implementations must implement this method to process the entire ResultSet.
     *
     * @param resultSet ResultSet to extract data from. Implementations should not close this: it will be closed by the calling JdbcTemplate.
     * @return an arbitrary result object, or null if none (the extractor will typically be stateful in the latter case).
     * @throws SQLException        if a SQLException is encountered getting column values or navigating (that is, there's no need to catch SQLException)
     * @throws DataAccessException in case of custom exceptions
     */
    @Override
    public Pallet extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        return new Pallet(resultSet.getString("pallet_number"),
                DatabaseManager.getInstance().getDatabase().getLocationTable().getLocation(
                        resultSet.getString("pallet_location_name")).getResult());
    }
}
