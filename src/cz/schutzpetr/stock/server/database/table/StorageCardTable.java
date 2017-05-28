package cz.schutzpetr.stock.server.database.table;

import cz.schutzpetr.stock.core.connection.RequestResult;
import cz.schutzpetr.stock.core.storagecard.ConnectionStorageCard;
import cz.schutzpetr.stock.core.storagecard.SimpleStorageCard;
import cz.schutzpetr.stock.server.database.extractor.SimpleStorageCardExtractor;
import cz.schutzpetr.stock.server.database.extractor.StorageCardExtractor;
import cz.schutzpetr.stock.server.database.mapper.SimpleStorageCardMapper;
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
public class StorageCardTable extends DBTable {

    /**
     * @param jdbcTemplate jdbcTemplate
     */
    public StorageCardTable(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }


    public RequestResult<ConnectionStorageCard> getStorageCard(int cardNumber) {
        try {
            PreparedStatementCreator psc = connection -> {
                PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT * FROM `storage_cards` WHERE `card_number` = ?");
                preparedStatement1.setInt(1, cardNumber);
                return preparedStatement1;
            };
            return new RequestResult<>(true, jdbcTemplate.query(psc, new StorageCardExtractor()).getConnectionStorageCard());
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new RequestResult<>(e);
        }
    }

    public RequestResult<ArrayList<SimpleStorageCard>> getSimpleStorageCards() {
        return getSimpleStorageCards("SELECT `card_number`, `item_name`, `ean`, `item_number`, `price_per_unit`, `item_producer`, `item_weight`, `number_of_unit_in_package` FROM `storage_cards`");
    }


    public RequestResult<ArrayList<SimpleStorageCard>> getSimpleStorageCards(String sql) {
        try {
            PreparedStatementCreator psc = connection -> connection.prepareStatement(sql);
            return new RequestResult<>(true, new ArrayList<>(jdbcTemplate.query(psc, new SimpleStorageCardMapper())));
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return new RequestResult<>(false);
    }


    public RequestResult<SimpleStorageCard> getSimpleStorageCard(int cardNumber) {
        try {
            PreparedStatementCreator psc = connection -> {
                PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT `card_number`, `item_name`, `ean`, `item_number`, `price_per_unit`, `item_producer`, `item_weight`, `number_of_unit_in_package`, `item_image` FROM `storage_cards` WHERE `card_number` = ?");
                preparedStatement1.setInt(1, cardNumber);
                return preparedStatement1;
            };
            return new RequestResult<>(true, jdbcTemplate.query(psc, new SimpleStorageCardExtractor()));
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new RequestResult<>(e);
        }
    }

    public RequestResult<Boolean> insertStorageCard(StorageCard storageCard) {
        try {
            PreparedStatementCreator psc = connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `storage_cards`(`item_name`, `ean`, `item_number`, `price_per_unit`, `item_producer`, `item_weight`, `number_of_unit_in_package`, `item_image`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
                preparedStatement.setString(1, storageCard.getItemName());
                preparedStatement.setString(2, storageCard.getEuropeanArticleNumber().getNumber());
                preparedStatement.setString(3, storageCard.getItemNumber());
                preparedStatement.setDouble(4, storageCard.getPricePerUnit());
                preparedStatement.setString(5, storageCard.getProducer());
                preparedStatement.setDouble(6, storageCard.getWeight());
                preparedStatement.setInt(7, storageCard.getNumberOfUnitInPackage());
                preparedStatement.setBlob(8, storageCard.getImageInputStream());

                return preparedStatement;
            };

            jdbcTemplate.update(psc);
            return new RequestResult<>(true, true);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new RequestResult<>(e);
        }
    }

}
