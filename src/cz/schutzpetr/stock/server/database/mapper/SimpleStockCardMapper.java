package cz.schutzpetr.stock.server.database.mapper;

import cz.schutzpetr.stock.core.stockcard.SimpleStockCard;
import cz.schutzpetr.stock.core.utils.EuropeanArticleNumber;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Petr Schutz on 19.05.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class SimpleStockCardMapper implements RowMapper<SimpleStockCard> {

    public static SimpleStockCard getSimpleStorageCard(ResultSet resultSet) throws SQLException {
        return new SimpleStockCard(resultSet.getInt("card_number"),
                resultSet.getString("item_name"),
                new EuropeanArticleNumber(resultSet.getString("ean")),
                resultSet.getString("item_number"),
                resultSet.getDouble("price_per_unit"),
                resultSet.getString("item_producer"),
                resultSet.getDouble("item_weight"),
                resultSet.getInt("number_of_unit_in_package"));
    }

    /**
     * Implementations must implement this method to map each row of data in the ResultSet. This method should not call next() on the ResultSet;
     * it is only supposed to map values of the current row.
     *
     * @param resultSet - the ResultSet to map (pre-initialized for the current row)
     * @param i         - the number of the current row
     * @throws SQLException - if a SQLException is encountered getting column values (that is, there's no need to catch SQLException)
     */
    @Override
    public SimpleStockCard mapRow(ResultSet resultSet, int i) throws SQLException {
        return getSimpleStorageCard(resultSet);
    }
}
