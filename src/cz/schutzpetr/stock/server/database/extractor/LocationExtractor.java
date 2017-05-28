package cz.schutzpetr.stock.server.database.extractor;

import cz.schutzpetr.stock.core.location.*;
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
public class LocationExtractor implements ResultSetExtractor<Location> {

    /**
     * Implementations must implement this method to process the entire ResultSet.
     *
     * @param resultSet ResultSet to extract data from. Implementations should not close this: it will be closed by the calling JdbcTemplate.
     * @return an arbitrary result object, or null if none (the extractor will typically be stateful in the latter case).
     * @throws SQLException        if a SQLException is encountered getting column values or navigating (that is, there's no need to catch SQLException)
     * @throws DataAccessException in case of custom exceptions
     */
    @Override
    public Location extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Location location = null;
        switch (LocationType.valueOf(resultSet.getString("location_type"))) {
            case AISLE:
                location = new Aisle(resultSet.getString("location_name"), resultSet.getString("location_subStock"));
                break;
            case PILE:
                location = new Pile(resultSet.getString("location_name"), resultSet.getString("location_subStock"));
                break;
            case RACK:
                location = new Rack(resultSet.getString("location_name"), resultSet.getString("location_subStock"));
                break;
        }
        return location;
    }
}
