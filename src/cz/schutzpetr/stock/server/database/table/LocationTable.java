package cz.schutzpetr.stock.server.database.table;

import cz.schutzpetr.stock.core.location.Location;
import cz.schutzpetr.stock.server.database.DatabaseResult;
import cz.schutzpetr.stock.server.database.mapper.LocationMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;

import java.sql.PreparedStatement;
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

    public DatabaseResult<Location> getLocation(String loc_Name) {
        try {
            PreparedStatementCreator psc = connection -> {
                PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT * FROM `locations` WHERE `locations`.locations_name = ?");
                preparedStatement1.setString(1, loc_Name);
                return preparedStatement1;
            };
            ArrayList<Location> locations = new ArrayList<>(jdbcTemplate.query(psc, new LocationMapper()));
            if (!locations.isEmpty()) return new DatabaseResult<>(true, locations.get(0));
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return new DatabaseResult<>(false, null);
    }

    public void insertLocation(Location location) {
        PreparedStatementCreator psc = connection -> {
            PreparedStatement preparedStatement1 = connection.prepareStatement("INSERT INTO `locations`(`locations_name`, `locations_subStock`, `locations_type`) VALUES (?, ?, ?)");
            preparedStatement1.setString(1, location.getName());
            preparedStatement1.setString(2, location.getSubStock());
            preparedStatement1.setString(3, location.getType().toString());
            return preparedStatement1;
        };
        jdbcTemplate.update(psc);
    }
}
