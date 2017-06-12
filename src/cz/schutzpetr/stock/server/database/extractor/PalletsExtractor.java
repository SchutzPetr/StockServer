package cz.schutzpetr.stock.server.database.extractor;

import cz.schutzpetr.stock.core.location.Location;
import cz.schutzpetr.stock.core.location.Pallet;
import org.springframework.dao.DataAccessException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Petr Schutz on 19.05.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class PalletsExtractor {

    private PalletsExtractor() {
    }


    public static ArrayList<Pallet> extractData(List<Location> locations) throws SQLException, DataAccessException {
        ArrayList<Pallet> pallets = new ArrayList<>();
        locations.forEach(location -> pallets.addAll(location.getPallets().values()));
        return pallets;
    }
}
