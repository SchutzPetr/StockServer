package cz.schutzpetr.stock.server.database;

import cz.schutzpetr.stock.core.items.Item;
import cz.schutzpetr.stock.core.location.Location;
import cz.schutzpetr.stock.core.location.Pallet;
import cz.schutzpetr.stock.core.location.Rack;
import cz.schutzpetr.stock.core.stockcard.SimpleStockCard;
import cz.schutzpetr.stock.server.database.table.Data;
import cz.schutzpetr.stock.server.database.table.UserTable;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
     * Instance of {@code Data}
     */
    private Data data;


    /**
     * @param dataSource instance of {@code DataSource}
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;

        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(dataSource);

        this.userTable = new UserTable(jdbcTemplateObject);
        this.data = new Data(jdbcTemplateObject);

        List<Location> locations = new ArrayList<>();
        List<Pallet> pallets = new ArrayList<>();

        for (int i = 1; i < 3; i++) {
            for (int j = 0; j < 20; j++) {
                for (int k = 0; k < 20; k++) {
                    locations.add(new Rack(((i*100)+j) + "-" + k + "-1",  "101", null));
                    locations.add(new Rack(((i*100)+j) + "-" + k + "-2",  "101", null));
                    locations.add(new Rack(((i*100)+j) + "-" + k + "-3",  "101",null));
                }
            }
        }

        Random random = new Random();

        List<Item> items = new ArrayList<>();

        for (int i = 0; i < 9999; i++) {
            pallets.add(new Pallet("003859081100" + i, locations.get(random.nextInt(locations.size()))));
        }

        List<SimpleStockCard> sc = data.getSimpleStockCards().getResult();

        locations.forEach(location -> {
            for (int i = 0; i < random.nextInt(); i++) {
                SimpleStockCard s = sc.get(random.nextInt(sc.size()));

                items.add(new Item(s, location, null, random.nextInt(500), 0));

            }
        });

        pallets.forEach(pallet -> {
            for (int i = 0; i < random.nextInt(); i++) {
                SimpleStockCard s = sc.get(random.nextInt(sc.size()));

                items.add(new Item(s, pallet.getLocation(), pallet, random.nextInt(500), 0));
            }
        });

        //data.insertItems(items);

        //data.insertLocations(locations);
        // data.insertPallets(pallets);
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
     * @return instance of {@code Data}
     */
    public Data getData() {
        return data;
    }

    public void disconnect() throws SQLException {
        this.dataSource.getConnection().close();
    }
}
