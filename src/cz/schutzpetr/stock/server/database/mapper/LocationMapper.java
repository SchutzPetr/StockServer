package cz.schutzpetr.stock.server.database.mapper;

import cz.schutzpetr.stock.core.location.Location;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Petr Schutz on 19.05.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class LocationMapper implements RowMapper<Location> {

    /**
     * Implementations must implement this method to map each row of data in the ResultSet. This method should not call next() on the ResultSet;
     * it is only supposed to map values of the current row.
     *
     * @param resultSet - the ResultSet to map (pre-initialized for the current row)
     * @param i         - the number of the current row
     * @throws SQLException - if a SQLException is encountered getting column values (that is, there's no need to catch SQLException)
     */
    @Override
    public Location mapRow(ResultSet resultSet, int i) throws SQLException {
        return Location.getLocation(resultSet);
    }
}
