package cz.schutzpetr.stock.server.database.table;

import cz.schutzpetr.stock.core.location.Location;
import cz.schutzpetr.stock.server.database.mapper.LocationMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * Created by Petr Schutz on 17.12.2016
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class LocationTable {
    private JdbcTemplate jdbcTemplate;

    /**
     *
     * @param jdbcTemplate jdbcTemplate
     */
    public LocationTable(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     *
     * @return list of all locations
     */
    public List<Location> getLocations() {
        String SQL = "select * from Locations";
        return jdbcTemplate.query(SQL, new LocationMapper());
    }
}
