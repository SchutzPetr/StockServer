package cz.schutzpetr.stock.server.database.extractor;

import cz.schutzpetr.stock.core.connection.RequestResult;
import cz.schutzpetr.stock.core.items.Item;
import cz.schutzpetr.stock.core.location.BaseLocation;
import cz.schutzpetr.stock.core.location.BaseLocationType;
import cz.schutzpetr.stock.core.location.Location;
import cz.schutzpetr.stock.core.location.Pallet;
import cz.schutzpetr.stock.core.storagecard.SimpleStorageCard;
import cz.schutzpetr.stock.server.database.DatabaseManager;
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
public class ItemExtractor implements ResultSetExtractor<Item> {

    private BaseLocation baseLocation;

    public ItemExtractor(BaseLocation baseLocation) {
        this.baseLocation = baseLocation;
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
    public Item extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        RequestResult<SimpleStorageCard> simpleStorageCard = DatabaseManager.getInstance().getDatabase().getStorageCardTable().getSimpleStorageCard(resultSet.getInt("card_number"));
        if (!simpleStorageCard.isResult()) return null;
        if (baseLocation == null) {
            switch (BaseLocationType.valueOf(resultSet.getString("base_location_type"))) {
                case LOCATION:
                    RequestResult<Location> location = DatabaseManager.getInstance().getDatabase().getLocationTable().getLocation(resultSet.getString("base_location"));
                    if (location.isResult()) {
                        baseLocation = location.getResult();
                    } else {
                        return null;
                    }
                    break;
                case PALLET:
                    RequestResult<Pallet> pallet = DatabaseManager.getInstance().getDatabase().getPalletTable().getPalletByNumber(resultSet.getString("base_location"));
                    if (pallet.isResult()) {
                        baseLocation = pallet.getResult();
                    } else {
                        return null;
                    }
                    break;
            }
        }
        return new Item(simpleStorageCard.getResult(), baseLocation, resultSet.getInt("item_count"), resultSet.getInt("item_reserved"));
    }
}
