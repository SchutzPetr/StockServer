package cz.schutzpetr.stock.server.database.mapper;

import cz.schutzpetr.stock.core.connection.RequestResult;
import cz.schutzpetr.stock.core.items.Item;
import cz.schutzpetr.stock.core.location.BaseLocation;
import cz.schutzpetr.stock.core.location.BaseLocationType;
import cz.schutzpetr.stock.core.location.Location;
import cz.schutzpetr.stock.core.location.Pallet;
import cz.schutzpetr.stock.core.storagecard.SimpleStorageCard;
import cz.schutzpetr.stock.server.database.DatabaseManager;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Petr Schutz on 17.12.2016
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class ItemMapper implements RowMapper<Item> {
    private BaseLocation baseLocation;

    public ItemMapper(BaseLocation baseLocation) {
        this.baseLocation = baseLocation;
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
    public Item mapRow(ResultSet resultSet, int i) throws SQLException {
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
