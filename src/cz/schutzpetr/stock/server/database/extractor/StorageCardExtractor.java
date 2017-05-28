package cz.schutzpetr.stock.server.database.extractor;

import cz.schutzpetr.stock.core.utils.EuropeanArticleNumber;
import cz.schutzpetr.stock.server.utils.items.StorageCard;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Petr Schutz on 19.05.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class StorageCardExtractor implements ResultSetExtractor<StorageCard> {

    /**
     * Implementations must implement this method to process the entire ResultSet.
     *
     * @param resultSet ResultSet to extract data from. Implementations should not close this: it will be closed by the calling JdbcTemplate.
     * @return an arbitrary result object, or null if none (the extractor will typically be stateful in the latter case).
     * @throws SQLException        if a SQLException is encountered getting column values or navigating (that is, there's no need to catch SQLException)
     * @throws DataAccessException in case of custom exceptions
     */
    @Override
    public StorageCard extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        if (resultSet.next()) {
            return new StorageCard(resultSet.getInt("card_number"),
                    resultSet.getString("item_name"),
                    new EuropeanArticleNumber(resultSet.getString("ean")),
                    resultSet.getString("item_number"),
                    resultSet.getDouble("price_per_unit"),
                    resultSet.getString("item_producer"),
                    resultSet.getDouble("item_weight"),
                    resultSet.getInt("number_of_unit_in_package"),
                    resultSet.getBlob("item_image").getBinaryStream());
        }
        return null;
    }
}
