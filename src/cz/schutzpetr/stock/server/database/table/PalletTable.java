package cz.schutzpetr.stock.server.database.table;

import cz.schutzpetr.stock.core.connection.RequestResult;
import cz.schutzpetr.stock.core.location.Pallet;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;

import java.sql.PreparedStatement;

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


    public RequestResult<Boolean> insertPallet(Pallet pallet) {
        try {
            PreparedStatementCreator psc = connection -> {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO `pallets` (`pallet_number`, `pallet_location_name`) VALUES (?, ?);");
                ps.setString(1, pallet.getPalletNumber());
                ps.setString(2, pallet.getLocation().getName());
                return ps;
            };
            jdbcTemplate.update(psc);
            return new RequestResult<>(true, true);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new RequestResult<>(e);
        }
    }
}
