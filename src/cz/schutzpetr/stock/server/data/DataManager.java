package cz.schutzpetr.stock.server.data;

import cz.schutzpetr.stock.core.connection.RequestResult;
import cz.schutzpetr.stock.core.items.Item;
import cz.schutzpetr.stock.core.location.Location;
import cz.schutzpetr.stock.core.location.Pallet;
import cz.schutzpetr.stock.core.stockcard.SimpleStockCard;
import cz.schutzpetr.stock.server.database.DatabaseManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Petr Schutz on 19.06.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class DataManager {
    private static DataManager ourInstance = new DataManager();


    private final LocationData locationData;
    private final ItemData itemData;
    private final PalletData palletData;
    private final StockCardData stockCardData;

    private DataManager() {
        locationData = new LocationData();
        itemData = new ItemData();
        stockCardData = new StockCardData();
        palletData = new PalletData();
    }

    public static LocationData getLocationData() {
        return ourInstance.locationData;
    }

    public static ItemData getItemData() {
        return ourInstance.itemData;
    }

    public static PalletData getPalletData() {
        return ourInstance.palletData;
    }

    public static StockCardData getStockCardData() {
        return ourInstance.stockCardData;
    }

    public static void loadData() {

        RequestResult<ArrayList<Location>> result = DatabaseManager.getInstance().getDatabase().getData().getLocations();

        getLocationData().updateData(result.getResult());
        ArrayList<Pallet> pallets = new ArrayList<>();
        ArrayList<Item> items = new ArrayList<>();
        result.getResult().forEach(location -> {
            pallets.addAll(location.getPallets().values());
            location.getPallets().values().forEach(pallet -> items.addAll(pallet.getItems().values()));
            items.addAll(location.getItems().values());
        });
        getPalletData().updateData(pallets);
        getItemData().updateData(items);

        Map<Integer, SimpleStockCard> map = new HashMap<>();
        items.forEach(item -> map.putIfAbsent(item.getSimpleStockCard().getCardNumber(), item.getSimpleStockCard()));

        getStockCardData().updateData(map.values());
    }
}
