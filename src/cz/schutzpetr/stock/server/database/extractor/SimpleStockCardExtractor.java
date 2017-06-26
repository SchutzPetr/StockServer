package cz.schutzpetr.stock.server.database.extractor;

import cz.schutzpetr.stock.core.stockcard.SimpleStockCard;
import cz.schutzpetr.stock.core.utils.EuropeanArticleNumber;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Petr Schutz on 20.06.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class SimpleStockCardExtractor implements ResultSetExtractor<ArrayList<SimpleStockCard>> {
    public static SimpleStockCard getSimpleStorageCard(ResultSet resultSet) throws SQLException {
        return new SimpleStockCard(resultSet.getInt("card_id"),
                resultSet.getString("item_name"),
                new EuropeanArticleNumber(resultSet.getString("ean")),
                resultSet.getString("item_number"),
                resultSet.getDouble("price_per_unit"),
                resultSet.getString("item_producer"),
                resultSet.getDouble("item_weight"),
                resultSet.getInt("number_of_unit_in_package"));
    }

    /**
     * Implementations must implement this method to process the entire ResultSet.
     *
     * @param resultSet ResultSet to extract data from. Implementations should not close this: it will be closed by the calling JdbcTemplate.
     * @return an arbitrary result object, or null if none (the extractor will typically be stateful in the latter case).
     * @throws SQLException        if a SQLException is encountered getting column values or navigating (that is, there's no need to catch SQLException)
     * @throws DataAccessException in case of custom exceptions
     */
    @Override
    public ArrayList<SimpleStockCard> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        final ArrayList<SimpleStockCard> simpleStockCards = new ArrayList<>();
        while (resultSet.next()) simpleStockCards.add(getSimpleStorageCard(resultSet));
        return simpleStockCards;
    }
}
