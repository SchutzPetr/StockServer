package cz.schutzpetr.stock.server.database.table;

import cz.schutzpetr.stock.core.connection.RequestResult;
import cz.schutzpetr.stock.core.location.Pallet;
import cz.schutzpetr.stock.server.database.extractor.PalletExtractor;
import cz.schutzpetr.stock.server.database.mapper.PalletMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;

import java.sql.PreparedStatement;
import java.util.ArrayList;

/**
 * Created by Petr Schutz on 17.12.2016
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class PalletTable extends DBTable {

    /**
     * @param jdbcTemplate jdbcTemplate
     */
    public PalletTable(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    /**
     * @return list of all locations
     */
    public RequestResult<ArrayList<Pallet>> getPallets() {
        return getPallets("select * from pallets");
    }

    public RequestResult<ArrayList<Pallet>> getPallets(String sql) {
        try {
            PreparedStatementCreator psc = connection -> connection.prepareStatement(sql);
            return new RequestResult<>(new ArrayList<>(jdbcTemplate.query(psc, new PalletMapper())));
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new RequestResult<>(e);
        }
    }

    public RequestResult<Pallet> getPalletByNumber(String palletNumber) {
        try {
            PreparedStatementCreator psc = connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement("select * from pallets where pallet_number = ?");
                preparedStatement.setString(1, palletNumber);
                return preparedStatement;
            };
            return new RequestResult<>(jdbcTemplate.query(psc, new PalletExtractor()));
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new RequestResult<>(e);
        }
    }

    public RequestResult<ArrayList<Pallet>> getPalletsByLocation(String locationName) {
        try {
            PreparedStatementCreator psc = connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement("select * from pallets where pallet_location_name = ?");
                preparedStatement.setString(1, locationName);
                return preparedStatement;
            };
            return new RequestResult<>(new ArrayList<>(jdbcTemplate.query(psc, new PalletMapper())));
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new RequestResult<>(e);
        }
    }
}
