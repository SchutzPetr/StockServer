package cz.schutzpetr.stock.server.database.table;

import cz.schutzpetr.stock.core.connection.RequestResult;
import cz.schutzpetr.stock.core.items.Item;
import cz.schutzpetr.stock.core.location.BaseLocation;
import cz.schutzpetr.stock.server.database.extractor.ItemExtractor;
import cz.schutzpetr.stock.server.database.mapper.ItemMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;

import java.sql.PreparedStatement;
import java.util.ArrayList;

/**
 * Created by Petr Schutz on 19.05.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class ItemTable extends DBTable {

    /**
     * @param jdbcTemplate jdbcTemplate
     */
    public ItemTable(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    public RequestResult<ArrayList<Item>> getItems() {
        try {
            PreparedStatementCreator psc = connection -> connection.prepareStatement("select * from locations");
            ArrayList<Item> locations = new ArrayList<>(jdbcTemplate.query(psc, new ItemMapper(null)));
            return new RequestResult<>(true, locations);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new RequestResult<>(e);
        }
    }

    public RequestResult<ArrayList<Item>> getItems(int cardNumber) {
        try {
            PreparedStatementCreator psc = connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `items` WHERE `items`.card_number = ?");
                preparedStatement.setInt(1, cardNumber);
                return preparedStatement;
            };
            return new RequestResult<>(new ArrayList<>(jdbcTemplate.query(psc, new ItemMapper(null))));
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new RequestResult<>(e);
        }
    }

    public RequestResult<ArrayList<Item>> getItems(BaseLocation baseLocation) {
        try {
            PreparedStatementCreator psc = connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `items` WHERE `items`.base_location = ?");
                preparedStatement.setString(1, baseLocation.getName());
                return preparedStatement;
            };
            return new RequestResult<>(new ArrayList<>(jdbcTemplate.query(psc, new ItemMapper(null))));
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new RequestResult<>(e);
        }
    }


    public RequestResult<Item> getItem(int cardNumber, BaseLocation locationName) {
        try {
            PreparedStatementCreator psc = connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `items` WHERE `items`.card_number = ? AND `items`.base_location = ?");
                preparedStatement.setInt(1, cardNumber);
                preparedStatement.setString(2, locationName.getName());
                return preparedStatement;
            };
            return new RequestResult<>(jdbcTemplate.query(psc, new ItemExtractor(locationName)));
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new RequestResult<>(e);
        }
    }

    public RequestResult<Boolean> insertItem(Item item) {
        try {
            PreparedStatementCreator psc = connection -> {
                PreparedStatement preparedStatement1 = connection.prepareStatement("INSERT INTO `items` (`storage_card`, `base_location`, `base_location_type`, `item_count`, `item_reserved`) VALUES (?, ?, ?, ?, ?)");
                preparedStatement1.setInt(1, item.getStorageCard().getCardNumber());
                preparedStatement1.setString(2, item.getBaseLocation().getName());
                preparedStatement1.setString(3, item.getBaseLocation().getBaseLocationType().toString());
                preparedStatement1.setInt(4, item.getCount());
                preparedStatement1.setInt(5, item.getReserved());
                return preparedStatement1;
            };
            jdbcTemplate.update(psc);
            return new RequestResult<>(true, true);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new RequestResult<>(e);
        }
    }
}
