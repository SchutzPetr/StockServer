package cz.schutzpetr.stock.server.data;

import cz.schutzpetr.stock.core.connection.RequestResult;
import cz.schutzpetr.stock.core.expressions.WhereClause;
import cz.schutzpetr.stock.core.stockcard.ConnectionStockCard;
import cz.schutzpetr.stock.core.stockcard.SimpleStockCard;
import cz.schutzpetr.stock.server.database.DatabaseManager;
import cz.schutzpetr.stock.server.utils.items.StockCard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Petr Schutz on 19.06.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class StockCardData extends Data<String, SimpleStockCard, SimpleStockCard> {

    private volatile boolean wait = false;

    @Override
    public void updateData(Collection<SimpleStockCard> data) {
        RequestResult<ArrayList<SimpleStockCard>> result = DatabaseManager.getInstance().getDatabase().getData().getSimpleStockCards();
        while (wait) Thread.yield();
        wait = true;
        if (result.isResult()) {
            super.data.clear();
            data.forEach(simpleStockCard -> super.data.putIfAbsent(simpleStockCard.getEuropeanArticleNumber().getNumber(), simpleStockCard));
            List<SimpleStockCard> data2 = result.getResult();
            data2.forEach(simpleStockCard -> super.data.putIfAbsent(simpleStockCard.getEuropeanArticleNumber().getNumber(), simpleStockCard));
        }
        wait = false;
    }

    public RequestResult<SimpleStockCard> insertData(ConnectionStockCard data) {
        while (wait) Thread.yield();
        wait = true;
        if (super.data.containsKey(data.getEuropeanArticleNumber().getNumber())) {
            wait = false;
            return null;
        } else {
            RequestResult<SimpleStockCard> result = DatabaseManager.getInstance().getDatabase().getData().insertStorageCard(new StockCard(data));
            if (result.isResult()) {
                super.data.put(result.getResult().getEuropeanArticleNumber().getNumber(), result.getResult());
            }
            wait = false;
            return result;
        }
    }

    public RequestResult<SimpleStockCard> edit(ConnectionStockCard data) {
        while (wait) Thread.yield();
        wait = true;
        if (super.data.containsKey(data.getEuropeanArticleNumber().getNumber())) {
            RequestResult<StockCard> result = DatabaseManager.getInstance().getDatabase().getData().updateStockCard(new StockCard(data));
            if (result.isResult()) {
                super.data.put(result.getResult().getEuropeanArticleNumber().getNumber(), StockCard.getSimple(result.getResult()));
                wait = false;
                return new RequestResult<>(result.getResult().getConnectionStockCard());
            }
            wait = false;
            return new RequestResult<>(result.getException());
        }
        return null;
    }

    @Override
    public RequestResult<ArrayList<SimpleStockCard>> getFilteredData(WhereClause whereClause) {
        return DatabaseManager.getInstance().getDatabase().getData().getSimpleStockCards(whereClause);
    }

    @Override
    public ArrayList<SimpleStockCard> getData() {
        return new ArrayList<>(super.data.values());
    }

    public RequestResult<Boolean> remove(SimpleStockCard simpleStockCard) {
        SimpleStockCard card = super.data.get(simpleStockCard.getEuropeanArticleNumber().getNumber());
        if (card == null || card.getItems() != null) return new RequestResult<>(false);
        RequestResult<Boolean> result = DatabaseManager.getInstance().getDatabase().getData().removeStockCard(card);
        if (result.getResult()) super.data.remove(card.getEuropeanArticleNumber().getNumber());
        return result;

    }
}
