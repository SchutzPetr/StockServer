package cz.schutzpetr.stock.server.database.mapper;

import cz.schutzpetr.stock.core.location.Pallet;
import cz.schutzpetr.stock.server.database.DatabaseManager;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Petr Schutz on 17.12.2016
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class PalletMapper implements RowMapper<Pallet> {

    /**
     * Implementations must implement this method to map each row of data in the ResultSet. This method should not call next() on the ResultSet;
     * it is only supposed to map values of the current row.
     *
     * @param resultSet - the ResultSet to map (pre-initialized for the current row)
     * @param i         - the number of the current row
     * @throws SQLException - if a SQLException is encountered getting column values (that is, there's no need to catch SQLException)
     */
    @Override
    public Pallet mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Pallet(resultSet.getString("pallet_number"),
                DatabaseManager.getInstance().getDatabase().getLocationTable().getLocation(
                        resultSet.getString("pallet_location_name")).getResult());

    }
}
