package cz.schutzpetr.stock.server.database;

import cz.schutzpetr.stock.core.location.Location;
import cz.schutzpetr.stock.server.database.table.*;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
     * Instance of {@code UserTable}
     */
    private UserTable userTable;

    /**
     * Instance of {@code LocationTable}
     */
    private LocationTable locationTable;

    /**
     * Instance of {@code PalletTable}
     */
    private PalletTable palletTable;

    /**
     * Instance of {@code StorageCardTable}
     */
    private StorageCardTable storageCardTable;

    /**
     * Instance of {@code ItemTable}
     */
    private ItemTable itemTable;

    /**
     * @param dataSource instance of {@code DataSource}
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;

        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(dataSource);

        this.userTable = new UserTable(jdbcTemplateObject);
        this.locationTable = new LocationTable(jdbcTemplateObject);
        this.palletTable = new PalletTable(jdbcTemplateObject);
        this.storageCardTable = new StorageCardTable(jdbcTemplateObject);
        this.itemTable = new ItemTable(jdbcTemplateObject);

        List<Location> locations = new ArrayList<>();

        /*for (int i = 5; i < 10; i++) {
            for (int j = 0; j < 100; j++) {
                for (int k = 0; k < 30; k++) {
                    locations.add(new Rack(((i*100)+j) + "-" + k + "-1",  "101"));
                    locations.add(new Rack(((i*100)+j) + "-" + k + "-2",  "101"));
                    locations.add(new Rack(((i*100)+j) + "-" + k + "-3",  "101"));
                    locations.add(new Rack(((i*100)+j) + "-" + k + "-4",  "101"));
                }
            }
        }*/

        //locationTable.insertLocations(locations);
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

    public PalletTable getPalletTable() {
        return palletTable;
    }

    public StorageCardTable getStorageCardTable() {
        return storageCardTable;
    }

    public ItemTable getItemTable() {
        return itemTable;
    }
}
