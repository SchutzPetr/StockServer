package cz.schutzpetr.stock.server.database;

import cz.schutzpetr.stock.server.database.table.LocationTable;
import cz.schutzpetr.stock.server.database.table.UserTable;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Petr Schutz on 06.03.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class Database {

    /**
     * Instance of {@code DataSource}
     */
    private DataSource dataSource;
    /**
     * Instance of {@code JdbcTemplate}
     */
    private JdbcTemplate jdbcTemplateObject;

    /**
     * Instance of {@code UserTable}
     */
    private UserTable userTable;
    /**
     * Instance of {@code LocationTable}
     */
    private LocationTable locationTable;

    /**
     * @param dataSource instance of {@code DataSource}
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);

        this.userTable = new UserTable(jdbcTemplateObject, dataSource);
        this.locationTable = new LocationTable(jdbcTemplateObject);
    }

    /**
     * @return true is connected
     */
    public boolean isConnected() {
        final String CHECK_SQL_QUERY = "SELECT 1";
        boolean isConnected = false;
        try {
            final PreparedStatement statement = dataSource.getConnection().prepareStatement(CHECK_SQL_QUERY);
            isConnected = true;
        } catch (SQLException | NullPointerException ignored) {
        }
        return isConnected;
    }

    /**
     * @return instance of {@code UserTable}
     */
    public UserTable getUserTable() {
        return userTable;
    }

    /**
     * @return instance of {@code LocationTable}
     */
    public LocationTable getLocationTable() {
        return locationTable;
    }
}
