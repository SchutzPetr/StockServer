package cz.schutzpetr.stock.server.data;

import cz.schutzpetr.stock.core.connection.RequestResult;
import cz.schutzpetr.stock.core.expressions.WhereClause;
import cz.schutzpetr.stock.core.items.Item;
import cz.schutzpetr.stock.core.location.BaseLocation;
import cz.schutzpetr.stock.core.location.Location;
import cz.schutzpetr.stock.core.location.Pallet;
import cz.schutzpetr.stock.server.database.DatabaseManager;
import cz.schutzpetr.stock.server.utils.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Petr Schutz on 19.06.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class ItemData extends Data<Integer, HashMap<String, Item>, Item> {

    private volatile boolean wait = false;

    @Override
    public void updateData(Collection<Item> data) {
        while (wait) Thread.yield();
        wait = true;
        super.data.clear();
        data.forEach(item -> super.data.computeIfAbsent(item.getSimpleStockCard().getCardNumber(), integer -> new HashMap<>()).put(item.getBaseLocation().getName(), item));
        wait = false;
    }

    private void insertToDatabase(List<Item> data) {
        DatabaseManager.getInstance().getDatabase().getData().insertItems(data);
    }

    private void updateItemsInDatabase(List<Item> data) {
        DatabaseManager.getInstance().getDatabase().getData().updateItems(data);
    }

    public RequestResult<Boolean> insertOrUpdate(Item i, List<Item> toUpdate, List<Item> toInsert) {
        BaseLocation destLoc = i.getBaseLocation() instanceof Location ? DataManager.getLocationData().data.get(i.getBaseLocation().getName()) :
                DataManager.getPalletData().data.get(i.getBaseLocation().getName());

        if (destLoc == null) {
            Logger.severe("Error! Položka nemohla být vytvořena, nebot lokace na niž se měla zaskladnit neexistuje");
            return new RequestResult<>(false);
        }

        if (destLoc.getItems().containsKey(i.getSimpleStockCard().getCardNumber())) {
            Item item1 = destLoc.getItems().get(i.getSimpleStockCard().getCardNumber());
            item1.incrementCount(i.getCount());
            toUpdate.add(item1);
        } else {
            destLoc.getItems().put(i.getSimpleStockCard().getCardNumber(), i);
            toInsert.add(i);
            super.data.computeIfAbsent(i.getSimpleStockCard().getCardNumber(), integer -> new HashMap<>());
            super.data.get(i.getSimpleStockCard().getCardNumber()).put(i.getBaseLocation().getName(), i);
        }

        return new RequestResult<>(true);
    }

    public RequestResult<Boolean> remove(Item item) {
        item.getBaseLocation().getItems().remove(item.getSimpleStockCard().getCardNumber());
        return DatabaseManager.getInstance().getDatabase().getData().removeItem(item);
    }

    public RequestResult<Boolean> relocate(Item i, BaseLocation baseLocation, int count) {
        while (wait) Thread.yield();
        wait = true;
        List<Item> toUpdate = new ArrayList<>();
        List<Item> toInsert = new ArrayList<>();
        Item item = super.data.get(i.getSimpleStockCard().getCardNumber()).get(i.getBaseLocation().getName());
        if (item == null) return new RequestResult<>(false);
        if (item.getCount() < count) return new RequestResult<>(false);

        BaseLocation destLoc = baseLocation instanceof Location ? DataManager.getLocationData().data.get(baseLocation.getName()) :
                DataManager.getPalletData().data.get(baseLocation.getName());

        if (destLoc == null) return new RequestResult<>(false);

        if (destLoc.getItems().containsKey(item.getSimpleStockCard().getCardNumber())) {
            Item item1 = destLoc.getItems().get(item.getSimpleStockCard().getCardNumber());
            toUpdate.add(item1);
            item1.incrementCount(count);
        } else {
            Location location;
            Pallet pallet;
            if (destLoc instanceof Location) {
                location = (Location) destLoc;
                pallet = null;
            } else {
                pallet = (Pallet) destLoc;
                location = pallet.getLocation();
            }
            Item newI = new Item(item.getSimpleStockCard(), location, pallet, count, 0);
            destLoc.getItems().put(item.getSimpleStockCard().getCardNumber(), newI);
            toInsert.add(newI);
            super.data.computeIfAbsent(newI.getSimpleStockCard().getCardNumber(), integer -> new HashMap<>());
            super.data.get(newI.getSimpleStockCard().getCardNumber()).put(newI.getBaseLocation().getName(), newI);
        }

        if (item.decrementCount(count) == null) {
            if (item.getPallet() != null) {
                item.getPallet().getItems().remove(item.getSimpleStockCard().getCardNumber());
            } else item.getLocation().getItems().remove(item.getSimpleStockCard().getCardNumber());

            DatabaseManager.getInstance().getDatabase().getData().removeItem(item);
        } else {
            toUpdate.add(item);
        }

        updateItemsInDatabase(toUpdate);
        insertToDatabase(toInsert);

        wait = false;
        return new RequestResult<>(true);
    }

    public ArrayList<Item> insertData(ArrayList<Item> data) {
        while (wait) Thread.yield();
        wait = true;
        List<Item> itemsToInsert = new ArrayList<>();
        List<Item> itemUpdate = new ArrayList<>();

        data.forEach(item -> insertOrUpdate(item, itemUpdate, itemsToInsert));

        updateItemsInDatabase(itemUpdate);
        insertToDatabase(itemsToInsert);

        ArrayList<Item> ret = new ArrayList<>();

        data.forEach(item ->
                ret.add(super.data.get(item.getSimpleStockCard().getCardNumber()).get(item.getBaseLocation().getName()))
        );
        wait = false;

        return ret;
    }

    @Override
    public RequestResult<ArrayList<Item>> getFilteredData(WhereClause whereClause) {
        return DatabaseManager.getInstance().getDatabase().getData().getItems(whereClause);
    }

    @Override
    public ArrayList<Item> getData() {

        ArrayList<Item> items = new ArrayList<>();

        super.data.forEach((integer, stringItemHashMap) -> items.addAll(stringItemHashMap.values()));

        return new ArrayList<>(items);
    }
}
