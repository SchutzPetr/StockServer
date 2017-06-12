package cz.schutzpetr.stock.server.database.extractor;

import cz.schutzpetr.stock.core.location.Location;
import cz.schutzpetr.stock.core.stockcard.SimpleStockCard;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Petr Schutz on 19.05.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class LocationsExtractor implements ResultSetExtractor<ArrayList<Location>> {

    /**
     * Implementations must implement this method to process the entire ResultSet.
     *
     * @param resultSet ResultSet to extract data from. Implementations should not close this: it will be closed by the calling JdbcTemplate.
     * @return an arbitrary result object, or null if none (the extractor will typically be stateful in the latter case).
     * @throws SQLException        if a SQLException is encountered getting column values or navigating (that is, there's no need to catch SQLException)
     * @throws DataAccessException in case of custom exceptions
     */
    @Override
    public ArrayList<Location> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        final ArrayList<Location> locations = new ArrayList<>();
        final Map<String, SimpleStockCard> simpleStorageCardMap = new HashMap<>();
        while (resultSet.next()) locations.add(Location.getLocation(resultSet, simpleStorageCardMap));
        return locations;
    }
}
