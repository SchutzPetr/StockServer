package cz.schutzpetr.stock.server.database.table;

import cz.schutzpetr.stock.server.database.DatabaseResult;
import cz.schutzpetr.stock.server.database.mapper.StorageCardMapper;
import cz.schutzpetr.stock.server.utils.items.StorageCard;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;

import java.sql.PreparedStatement;
import java.util.ArrayList;

/**
 * Created by Petr Schutz on 03.04.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class StorageCardTable {

    private JdbcTemplate jdbcTemplate;

    /**
     * @param jdbcTemplate jdbcTemplate
     */
    public StorageCardTable(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public DatabaseResult<ArrayList<StorageCard>> getStorageCards() {
        try {
            PreparedStatementCreator psc = connection -> connection.prepareStatement("select * from storage_cards");
            ArrayList<StorageCard> locations = new ArrayList<>(jdbcTemplate.query(psc, new StorageCardMapper()));
            return new DatabaseResult<>(true, locations);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return new DatabaseResult<>(false, null);
    }

    public DatabaseResult<StorageCard> getStorageCards(int cardNumber) {
        try {
            PreparedStatementCreator psc = connection -> {
                PreparedStatement preparedStatement1 = connection.prepareStatement("select * from storage_cards where card_number = ?");
                preparedStatement1.setInt(1, cardNumber);
                return preparedStatement1;
            };
            ArrayList<StorageCard> storageCards = new ArrayList<>(jdbcTemplate.query(psc, new StorageCardMapper()));
            if (!storageCards.isEmpty()) return new DatabaseResult<>(true, storageCards.get(0));
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return new DatabaseResult<>(false, null);
    }

    public DatabaseResult<Boolean> insertStorageCard(StorageCard storageCard) {
        try {
            PreparedStatementCreator psc = connection -> {
                PreparedStatement preparedStatement1 = connection.prepareStatement("INSERT INTO `storage_cards`(`card_number`, `item_name`, `ean`, `item_number`, `price_per_unit`, `item_producer`, `item_weight`, `number_of_unit_in_package`, `item_image`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
                preparedStatement1.setInt(1, storageCard.getCardNumber());
                preparedStatement1.setString(2, storageCard.getItemName());
                preparedStatement1.setString(3, storageCard.getEan().getEAN());
                preparedStatement1.setString(4, storageCard.getItemNumber());
                preparedStatement1.setDouble(5, storageCard.getPricePerUnit());
                preparedStatement1.setString(6, storageCard.getProducer());
                preparedStatement1.setDouble(7, storageCard.getWeight());
                preparedStatement1.setInt(8, storageCard.getNumberOfUnitInPackage());
                preparedStatement1.setBlob(9, storageCard.getImageInputStream());

                return preparedStatement1;
            };
            jdbcTemplate.update(psc);
        } catch (DataAccessException e) {
            return new DatabaseResult<>(false, null);
        }
        return new DatabaseResult<>(true, true);
    }

}
