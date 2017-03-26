package cz.schutzpetr.stock.server.database.table;

import cz.schutzpetr.stock.core.location.Location;
import cz.schutzpetr.stock.server.database.DatabaseResult;
import cz.schutzpetr.stock.server.database.mapper.LocationMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;

import java.util.ArrayList;

/**
 * Created by Petr Schutz on 17.12.2016
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class LocationTable {
    private JdbcTemplate jdbcTemplate;

    /**
     * @param jdbcTemplate jdbcTemplate
     */
    public LocationTable(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * @return list of all locations
     */
    public DatabaseResult<ArrayList<Location>> getLocations() {
        try {
            PreparedStatementCreator psc = connection -> connection.prepareStatement("select * from locations");
            ArrayList<Location> locations = new ArrayList<>(jdbcTemplate.query(psc, new LocationMapper()));
            return new DatabaseResult<>(true, locations);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return new DatabaseResult<>(false, null);
    }
}
