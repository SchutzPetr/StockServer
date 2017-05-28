package cz.schutzpetr.stock.server.database.table;

import cz.schutzpetr.stock.core.connection.RequestResult;
import cz.schutzpetr.stock.core.location.Location;
import cz.schutzpetr.stock.server.database.extractor.LocationExtractor;
import cz.schutzpetr.stock.server.database.mapper.LocationMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Petr Schutz on 17.12.2016
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class LocationTable extends DBTable {

    /**
     * @param jdbcTemplate jdbcTemplate
     */
    public LocationTable(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    /**
     * @return list of all locations
     */
    public RequestResult<ArrayList<Location>> getLocations() {
        return getLocations("select * from locations");
    }

    public RequestResult<Location> getLocation(String loc_Name) {
        try {
            PreparedStatementCreator psc = connection -> {
                PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT * FROM `locations` WHERE `locations`.location_name = ?");
                preparedStatement1.setString(1, loc_Name);
                return preparedStatement1;
            };

            return new RequestResult<>(true, jdbcTemplate.query(psc, new LocationExtractor()));
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new RequestResult<>(e);
        }
    }

    public RequestResult<Boolean> insertLocation(Location location) {
        try {
            PreparedStatementCreator psc = connection -> {
                PreparedStatement preparedStatement1 = connection.prepareStatement("INSERT INTO `locations`(`location_name`, `location_subStock`, `location_type`) VALUES (?, ?, ?)");
                preparedStatement1.setString(1, location.getName());
                preparedStatement1.setString(2, location.getSubStock());
                preparedStatement1.setString(3, location.getType().toString());
                return preparedStatement1;
            };
            jdbcTemplate.update(psc);
            return new RequestResult<>(true, true);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new RequestResult<>(e);
        }
    }

    public RequestResult<Boolean> insertLocations(List<Location> locations) {
        StringBuilder sql = new StringBuilder();
        for (int i = 0; i < locations.size() - 1; i++) {
            sql.append("(?, ?, ?),");
        }
        sql.append("(?, ?, ?)");
        try {
            PreparedStatementCreator psc = connection -> {
                PreparedStatement preparedStatement1 = connection.prepareStatement("INSERT INTO `locations`(`location_name`, `location_subStock`, `location_type`) VALUES " + sql.toString());
                for (int i = 0; i < locations.size(); i++) {
                    Location location = locations.get(i);
                    preparedStatement1.setString(1 + (i * 3), location.getName());
                    preparedStatement1.setString(2 + (i * 3), location.getSubStock());
                    preparedStatement1.setString(3 + (i * 3), location.getType().toString());
                }
                return preparedStatement1;
            };
            jdbcTemplate.update(psc);
            return new RequestResult<>(true, true);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new RequestResult<>(e);
        }
    }

    public RequestResult<ArrayList<Location>> getLocations(String sql) {
        try {
            PreparedStatementCreator psc = connection -> connection.prepareStatement(sql);
            return new RequestResult<>(true, new ArrayList<>(jdbcTemplate.query(psc, new LocationMapper())));
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return new RequestResult<>(false, null);
    }
}
