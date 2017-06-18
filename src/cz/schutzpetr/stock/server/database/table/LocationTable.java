package cz.schutzpetr.stock.server.database.table;

import cz.schutzpetr.stock.core.connection.RequestResult;
import cz.schutzpetr.stock.core.expressions.WhereClause;
import cz.schutzpetr.stock.core.items.Item;
import cz.schutzpetr.stock.core.location.Location;
import cz.schutzpetr.stock.core.location.Pallet;
import cz.schutzpetr.stock.core.stockcard.SimpleStockCard;
import cz.schutzpetr.stock.server.database.extractor.ItemsExtractor;
import cz.schutzpetr.stock.server.database.extractor.LocationsExtractor;
import cz.schutzpetr.stock.server.database.extractor.PalletsExtractor;
import cz.schutzpetr.stock.server.database.mapper.SimpleStockCardMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Petr Schutz on 17.12.2016
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class LocationTable extends DBTable {

    /**
     * @param jdbcTemplate jdbcTemplate
     */
    public LocationTable(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    private RequestResult<ArrayList<Location>> get(String sql) {
        return get(connection -> connection.prepareStatement(sql));
    }

    private RequestResult<ArrayList<Location>> get(PreparedStatementCreator psc) {
        try {
            return new RequestResult<>(jdbcTemplate.query(psc, new LocationsExtractor()));
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new RequestResult<>(e);
        }
    }

    private RequestResult<ArrayList<Item>> getItems(PreparedStatementCreator psc) {
        try {
            return new RequestResult<>(jdbcTemplate.query(psc, new ItemsExtractor()));
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new RequestResult<>(e);
        }
    }

    private String replaceTableName(String toReplace, String tableName) {
        return toReplace.replace("%tableName%.", tableName);
    }

    /**
     * @return list of all locations
     */
    public RequestResult<ArrayList<Location>> getLocations() {
        return get("SELECT * FROM (SELECT `locPal`.`location_name`, `locPal`.`location_type`, `locPal`.`location_substock`, " +
                "`pallet_numbers`, `i`.`storage_card_id_g`, `i`.`item_name_g`, `i`.`ean_g`, `i`.`item_number_g`, `i`.`price_per_unit_g`, " +
                "`i`.`item_producer_g`, `i`.`item_weight_g`, `i`.`number_of_unit_in_package_g`, `i`.`item_count_g`, `i`.`item_reserved_g`, " +
                "`i`.`item_pallet_number_g` FROM (SELECT `l`.`location_name`, `l`.`location_type`, `l`.`location_substock`, " +
                "GROUP_CONCAT(`p`.`pallet_number` SEPARATOR ';') AS pallet_numbers FROM locations l LEFT JOIN pallets p ON " +
                "p.pallet_location_name = l.location_name GROUP BY `location_name` ) AS locPal LEFT JOIN (SELECT `i`.`location_name`, " +
                "GROUP_CONCAT(`i`.`storage_card_id` SEPARATOR ';') AS storage_card_id_g, GROUP_CONCAT(`i`.`pallet_number` SEPARATOR ';') " +
                "AS item_pallet_number_g, GROUP_CONCAT(`i`.`item_count` SEPARATOR ';') AS item_count_g, GROUP_CONCAT(`i`.`item_reserved` SEPARATOR ';') " +
                "AS item_reserved_g, GROUP_CONCAT(`sc`.`item_name` SEPARATOR ';') AS item_name_g, GROUP_CONCAT(`sc`.`ean` SEPARATOR ';') " +
                "AS ean_g, GROUP_CONCAT(`sc`.`item_number` SEPARATOR ';') AS item_number_g, GROUP_CONCAT(`sc`.`price_per_unit` SEPARATOR ';') " +
                "AS price_per_unit_g, GROUP_CONCAT(`sc`.`item_producer` SEPARATOR ';') AS item_producer_g, GROUP_CONCAT(`sc`.`item_weight` SEPARATOR ';') " +
                "AS item_weight_g, GROUP_CONCAT(`sc`.`number_of_unit_in_package` SEPARATOR ';') AS number_of_unit_in_package_g FROM items i " +
                "INNER JOIN storage_cards sc ON i.storage_card_id = sc.card_id GROUP BY `location_name` ) i ON locPal.location_name = i.location_name) " +
                "AS locPalItem ORDER BY `location_name`;");
    }

    public RequestResult<ArrayList<Location>> getLocations(WhereClause where) {
        return get(connection -> where.buildStatement(connection, "SELECT * FROM (SELECT `locPal`.`location_name`, " +
                "`locPal`.`location_type`, `locPal`.`location_substock`, `pallet_numbers`, `i`.`storage_card_id_g`, " +
                "`i`.`item_name_g`, `i`.`ean_g`, `i`.`item_number_g`, `i`.`price_per_unit_g`, `i`.`item_producer_g`, `i`.`item_weight_g`, " +
                "`i`.`number_of_unit_in_package_g`, `i`.`item_count_g`, `i`.`item_reserved_g`, `i`.`item_pallet_number_g` " +
                "FROM (SELECT `l`.`location_name`, `l`.`location_type`, `l`.`location_substock`, GROUP_CONCAT(`p`.`pallet_number` SEPARATOR ';') " +
                "AS pallet_numbers FROM locations l LEFT JOIN pallets p ON p.pallet_location_name = l.location_name WHERE {_WHERE_} GROUP BY `location_name` ) " +
                "AS locPal LEFT JOIN (SELECT `i`.`location_name`, GROUP_CONCAT(`i`.`storage_card_id` SEPARATOR ';') AS storage_card_id_g, " +
                "GROUP_CONCAT(`i`.`pallet_number` SEPARATOR ';') AS item_pallet_number_g, GROUP_CONCAT(`i`.`item_count` SEPARATOR ';') " +
                "AS item_count_g, GROUP_CONCAT(`i`.`item_reserved` SEPARATOR ';') AS item_reserved_g, GROUP_CONCAT(`sc`.`item_name` SEPARATOR ';') " +
                "AS item_name_g, GROUP_CONCAT(`sc`.`ean` SEPARATOR ';') AS ean_g, GROUP_CONCAT(`sc`.`item_number` SEPARATOR ';') AS item_number_g, " +
                "GROUP_CONCAT(`sc`.`price_per_unit` SEPARATOR ';') AS price_per_unit_g, GROUP_CONCAT(`sc`.`item_producer` SEPARATOR ';') AS item_producer_g, " +
                "GROUP_CONCAT(`sc`.`item_weight` SEPARATOR ';') AS item_weight_g, GROUP_CONCAT(`sc`.`number_of_unit_in_package` SEPARATOR ';') " +
                "AS number_of_unit_in_package_g FROM items i INNER JOIN storage_cards sc ON i.storage_card_id = sc.card_id GROUP BY `location_name` ) i " +
                "ON locPal.location_name = i.location_name) AS locPalItem ORDER BY `location_name`;"));
    }

    public RequestResult<ArrayList<Pallet>> getPallets() {
        RequestResult<ArrayList<Location>> requestResult = get("SELECT * FROM (SELECT `locPal`.`location_name`, `locPal`.`location_type`, " +
                "`locPal`.`location_substock`, `pallet_numbers`, `i`.`storage_card_id_g`, `i`.`item_name_g`, `i`.`ean_g`, `i`.`item_number_g`, " +
                "`i`.`price_per_unit_g`, `i`.`item_producer_g`, `i`.`item_weight_g`, `i`.`number_of_unit_in_package_g`, `i`.`item_count_g`, " +
                "`i`.`item_reserved_g`, `i`.`item_pallet_number_g` FROM (SELECT `l`.`location_name`, `l`.`location_type`, `l`.`location_substock`, " +
                "GROUP_CONCAT(`p`.`pallet_number` SEPARATOR ';') AS pallet_numbers FROM locations l INNER JOIN pallets p ON " +
                "p.pallet_location_name = l.location_name GROUP BY `location_name` ) AS locPal LEFT JOIN (SELECT `i`.`location_name`, " +
                "GROUP_CONCAT(`i`.`storage_card_id` SEPARATOR ';') AS storage_card_id_g, GROUP_CONCAT(`i`.`pallet_number` SEPARATOR ';') " +
                "AS item_pallet_number_g, GROUP_CONCAT(`i`.`item_count` SEPARATOR ';') AS item_count_g, GROUP_CONCAT(`i`.`item_reserved` SEPARATOR ';') " +
                "AS item_reserved_g, GROUP_CONCAT(`sc`.`item_name` SEPARATOR ';') AS item_name_g, GROUP_CONCAT(`sc`.`ean` SEPARATOR ';') " +
                "AS ean_g, GROUP_CONCAT(`sc`.`item_number` SEPARATOR ';') AS item_number_g, GROUP_CONCAT(`sc`.`price_per_unit` SEPARATOR ';') " +
                "AS price_per_unit_g, GROUP_CONCAT(`sc`.`item_producer` SEPARATOR ';') AS item_producer_g, GROUP_CONCAT(`sc`.`item_weight` SEPARATOR ';') " +
                "AS item_weight_g, GROUP_CONCAT(`sc`.`number_of_unit_in_package` SEPARATOR ';') AS number_of_unit_in_package_g FROM items i INNER JOIN " +
                "storage_cards sc ON i.storage_card_id = sc.card_id GROUP BY `location_name` ) i ON locPal.location_name = i.location_name) " +
                "AS locPalItem ORDER BY `location_name`;");
        try {
            return new RequestResult<>(PalletsExtractor.extractData(requestResult.getResult()));
        } catch (SQLException e) {
            return new RequestResult<>(e);
        }
    }

    public RequestResult<ArrayList<Pallet>> getPallets(WhereClause where) {
        RequestResult<ArrayList<Location>> requestResult = get(connection -> where.buildStatement(connection, "SELECT * FROM (SELECT `locPal`.`location_name`, `locPal`.`location_type`, " +
                "`locPal`.`location_substock`, `pallet_numbers`, `i`.`storage_card_id_g`, `i`.`item_name_g`, `i`.`ean_g`, `i`.`item_number_g`, " +
                "`i`.`price_per_unit_g`, `i`.`item_producer_g`, `i`.`item_weight_g`, `i`.`number_of_unit_in_package_g`, `i`.`item_count_g`, " +
                "`i`.`item_reserved_g`, `i`.`item_pallet_number_g` FROM (SELECT `l`.`location_name`, `l`.`location_type`, `l`.`location_substock`, " +
                "GROUP_CONCAT(IF(`p`." + (where.getColumns().containsKey("pallet_number") ? "{_WHERE_COLUMN:pallet_number_}" : "pallet_number LIKE '%'") + ", `p`.`pallet_number`, '') SEPARATOR ';') " +
                "AS pallet_numbers FROM locations l INNER JOIN pallets p ON " +
                "p.pallet_location_name = l.location_name " +
                "WHERE l." + (where.getColumns().containsKey("location_name") ? "{_WHERE_COLUMN:location_name_}" : "location_name LIKE '%'") + " " +
                "GROUP BY `location_name` ) AS locPal LEFT JOIN (SELECT `i`.`location_name`, " +
                "GROUP_CONCAT(`i`.`storage_card_id` SEPARATOR ';') AS storage_card_id_g, GROUP_CONCAT(`i`.`pallet_number` SEPARATOR ';') " +
                "AS item_pallet_number_g, GROUP_CONCAT(`i`.`item_count` SEPARATOR ';') AS item_count_g, GROUP_CONCAT(`i`.`item_reserved` SEPARATOR ';') " +
                "AS item_reserved_g, GROUP_CONCAT(`sc`.`item_name` SEPARATOR ';') AS item_name_g, GROUP_CONCAT(`sc`.`ean` SEPARATOR ';') " +
                "AS ean_g, GROUP_CONCAT(`sc`.`item_number` SEPARATOR ';') AS item_number_g, GROUP_CONCAT(`sc`.`price_per_unit` SEPARATOR ';') " +
                "AS price_per_unit_g, GROUP_CONCAT(`sc`.`item_producer` SEPARATOR ';') AS item_producer_g, GROUP_CONCAT(`sc`.`item_weight` SEPARATOR ';') " +
                "AS item_weight_g, GROUP_CONCAT(`sc`.`number_of_unit_in_package` SEPARATOR ';') AS number_of_unit_in_package_g FROM items i INNER JOIN " +
                "storage_cards sc ON i.storage_card_id = sc.card_id GROUP BY `location_name` ) i ON locPal.location_name = i.location_name) " +
                "AS locPalItem ORDER BY `location_name`;"));

        /*RequestResult<ArrayList<Location>> requestResult = get(connection -> where.buildStatement(connection, "SELECT * FROM (SELECT `locPal`.`location_name`, `locPal`.`location_type`, `locPal`.`location_substock`, " +
                "`pallet_numbers`, `i`.`storage_card_id`, `i`.`item_pallet_number`, `i`.`item_count`, `i`.`item_reserved`, " +
                "`i`.`item_name`, `i`.`ean`, `i`.`item_number`, `i`.`price_per_unit`, `i`.`item_producer`, `i`.`item_weight`, " +
                "`i`.`number_of_unit_in_package` FROM (SELECT `l`.`location_name`, `l`.`location_type`, `l`.`location_substock`," +
                " GROUP_CONCAT(IF(`p`." + (where.getColumns().containsKey("pallet_number") ? "{_WHERE_COLUMN:pallet_number_}" : "pallet_number LIKE '%'") + ", `p`.`pallet_number`, '') SEPARATOR ';') " +
                "AS pallet_numbers FROM locations l INNER JOIN pallets p ON p.pallet_location_name = l.location_name " +
                "WHERE l." + (where.getColumns().containsKey("location_name") ? "{_WHERE_COLUMN:location_name_}" : "location_name LIKE '%'") + " GROUP BY `location_name` ORDER BY `location_name` ) " +
                "AS locPal LEFT JOIN (SELECT `i`.`location_name`, GROUP_CONCAT(`i`.`storage_card_id` SEPARATOR \";\") " +
                "AS storage_card_id, GROUP_CONCAT(`i`.`pallet_number` SEPARATOR \";\") AS item_pallet_number, " +
                "GROUP_CONCAT(`i`.`item_count` SEPARATOR \";\") AS item_count, GROUP_CONCAT(`i`.`item_reserved` SEPARATOR \";\") " +
                "AS item_reserved, GROUP_CONCAT(`sc`.`item_name` SEPARATOR \";\") AS item_name, GROUP_CONCAT(`sc`.`ean` SEPARATOR \";\") " +
                "AS ean, GROUP_CONCAT(`sc`.`item_number` SEPARATOR \";\") AS item_number, GROUP_CONCAT(`sc`.`price_per_unit` SEPARATOR \";\") " +
                "AS price_per_unit, GROUP_CONCAT(`sc`.`item_producer` SEPARATOR \";\") AS item_producer, GROUP_CONCAT(`sc`.`item_weight` SEPARATOR \";\") " +
                "AS item_weight, GROUP_CONCAT(`sc`.`number_of_unit_in_package` SEPARATOR \";\") AS number_of_unit_in_package " +
                "FROM items i INNER JOIN storage_cards sc ON i.storage_card_id = sc.card_id GROUP BY `location_name` ORDER BY `location_name` ) i " +
                "ON locPal.location_name = i.location_name GROUP BY `location_name` ORDER BY `location_name`) AS locPalItem GROUP BY `location_name` " +
                "ORDER BY `location_name`;"));


                this.locationObservableList = ApplicationManager.getInstance().getConnectionManager().getLocations();

        this.locationObservableList.forEach(location -> {
            locationMap.put(location.getName(), location);
            location.getPallets().values().forEach(pallet -> {
                palletObservableList.add(pallet);
                palletMap.put(pallet.getPalletNumber(), pallet);
                itemObservableList.addAll(pallet.getItems().values());
            });
            itemObservableList.addAll(location.getItems().values());
        });

                */
        try {
            return new RequestResult<>(PalletsExtractor.extractData(requestResult.getResult()));
        } catch (SQLException e) {
            return new RequestResult<>(e);
        }
    }

    public RequestResult<ArrayList<Item>> getItems() {
        RequestResult<ArrayList<Location>> requestResult = get("SELECT * FROM (SELECT `locPal`.`location_name`, `locPal`.`location_type`, `locPal`.`location_substock`, " +
                "`pallet_numbers`, `i`.`storage_card_id_g`, `i`.`item_name_g`, `i`.`ean_g`, `i`.`item_number_g`, `i`.`price_per_unit_g`, " +
                "`i`.`item_producer_g`, `i`.`item_weight_g`, `i`.`number_of_unit_in_package_g`, `i`.`item_count_g`, `i`.`item_reserved_g`, " +
                "`i`.`item_pallet_number_g` FROM (SELECT `l`.`location_name`, `l`.`location_type`, `l`.`location_substock`, " +
                "GROUP_CONCAT(`p`.`pallet_number` SEPARATOR ';') AS pallet_numbers FROM locations l LEFT JOIN pallets p ON " +
                "p.pallet_location_name = l.location_name GROUP BY `location_name` ) AS locPal LEFT JOIN (SELECT `i`.`location_name`, " +
                "GROUP_CONCAT(`i`.`storage_card_id` SEPARATOR ';') AS storage_card_id_g, GROUP_CONCAT(`i`.`pallet_number` SEPARATOR ';') " +
                "AS item_pallet_number_g, GROUP_CONCAT(`i`.`item_count` SEPARATOR ';') AS item_count_g, GROUP_CONCAT(`i`.`item_reserved` SEPARATOR ';') " +
                "AS item_reserved_g, GROUP_CONCAT(`sc`.`item_name` SEPARATOR ';') AS item_name_g, GROUP_CONCAT(`sc`.`ean` SEPARATOR ';') " +
                "AS ean_g, GROUP_CONCAT(`sc`.`item_number` SEPARATOR ';') AS item_number_g, GROUP_CONCAT(`sc`.`price_per_unit` SEPARATOR ';') " +
                "AS price_per_unit_g, GROUP_CONCAT(`sc`.`item_producer` SEPARATOR ';') AS item_producer_g, GROUP_CONCAT(`sc`.`item_weight` SEPARATOR ';') " +
                "AS item_weight_g, GROUP_CONCAT(`sc`.`number_of_unit_in_package` SEPARATOR ';') AS number_of_unit_in_package_g FROM items i " +
                "INNER JOIN storage_cards sc ON i.storage_card_id = sc.card_id GROUP BY `location_name` ) i ON locPal.location_name = i.location_name) " +
                "AS locPalItem ORDER BY `location_name`;");

        ArrayList<Item> items = new ArrayList<>();

        requestResult.getResult().forEach(location -> {
            items.addAll(location.getItems().values());
            location.getPallets().values().forEach(pallet -> items.addAll(pallet.getItems().values()));
        });

        return new RequestResult<>(items);
    }

    public RequestResult<ArrayList<Item>> getItems(WhereClause whereClause) {
        return getItems(connection -> whereClause.buildStatement(connection,
                "SELECT `item`.`card_id`, `item`.`item_name`, `item`.`ean`, `item`.`item_number`, `item`.`price_per_unit`, " +
                        "`item`.`item_producer`, `item`.`item_weight`, `item`.`number_of_unit_in_package`, `item`.`item_count`, " +
                        "`item`.`item_reserved`, `locPalItem`.`location_name`, `item`.`pallet_number`, `locPalItem`.`location_type`, " +
                        "`locPalItem`.`location_substock`, `locPalItem`.`pallet_numbers`, `locPalItem`.`storage_card_id_g`, " +
                        "`locPalItem`.`item_name_g`, `locPalItem`.`ean_g`, `locPalItem`.`item_number_g`, `locPalItem`.`price_per_unit_g`, " +
                        "`locPalItem`.`item_producer_g`, `locPalItem`.`item_weight_g`, `locPalItem`.`number_of_unit_in_package_g`, " +
                        "`locPalItem`.`item_count_g`, `locPalItem`.`item_reserved_g`, `locPalItem`.`item_pallet_number_g` " +
                        "FROM (SELECT `sc`.`card_id`, `sc`.`item_name`, `sc`.`ean`, `sc`.`item_number`, `sc`.`price_per_unit`, " +
                        "`sc`.`item_producer`, `sc`.`item_weight`, `sc`.`number_of_unit_in_package`, `it`.`item_count`, `it`.`item_reserved`, " +
                        "`it`.`location_name`, `it`.`pallet_number` FROM items it LEFT JOIN storage_cards sc ON sc.card_id = it.storage_card_id) " +
                        "AS item LEFT JOIN (SELECT `locPal`.`location_name`, `locPal`.`location_type`, `locPal`.`location_substock`, `locPal`.`pallet_numbers`," +
                        " `i`.`storage_card_id_g`, `i`.`item_name_g`, `i`.`ean_g`, `i`.`item_number_g`, `i`.`price_per_unit_g`, `i`.`item_producer_g`, " +
                        "`i`.`item_weight_g`, `i`.`number_of_unit_in_package_g`, `i`.`item_count_g`, `i`.`item_reserved_g`, `i`.`item_pallet_number_g` " +
                        "FROM (SELECT `l`.`location_name`, `l`.`location_type`, `l`.`location_substock`, GROUP_CONCAT(`p`.`pallet_number` SEPARATOR ';') " +
                        "AS pallet_numbers FROM locations l LEFT JOIN pallets p ON p.pallet_location_name = l.location_name GROUP BY `location_name` ) " +
                        "AS locPal LEFT JOIN (SELECT `i`.`location_name`, GROUP_CONCAT(`i`.`storage_card_id` SEPARATOR ';') AS storage_card_id_g, " +
                        "GROUP_CONCAT(`i`.`pallet_number` SEPARATOR ';') AS item_pallet_number_g, GROUP_CONCAT(`i`.`item_count` SEPARATOR ';') " +
                        "AS item_count_g, GROUP_CONCAT(`i`.`item_reserved` SEPARATOR ';') AS item_reserved_g, GROUP_CONCAT(`sc`.`item_name` SEPARATOR ';') " +
                        "AS item_name_g, GROUP_CONCAT(`sc`.`ean` SEPARATOR ';') AS ean_g, GROUP_CONCAT(`sc`.`item_number` SEPARATOR ';') " +
                        "AS item_number_g, GROUP_CONCAT(`sc`.`price_per_unit` SEPARATOR ';') AS price_per_unit_g, GROUP_CONCAT(`sc`.`item_producer` SEPARATOR ';') " +
                        "AS item_producer_g, GROUP_CONCAT(`sc`.`item_weight` SEPARATOR ';') AS item_weight_g, GROUP_CONCAT(`sc`.`number_of_unit_in_package` SEPARATOR ';') " +
                        "AS number_of_unit_in_package_g FROM items i INNER JOIN storage_cards sc ON i.storage_card_id = sc.card_id GROUP BY `location_name` )" +
                        " i ON locPal.location_name = i.location_name GROUP BY `location_name` ) AS locPalItem ON item.location_name = locPalItem.location_name " +
                        "WHERE {_WHERE_} ORDER BY card_id;"));
    }

    public RequestResult<Boolean> insertLocation(Location location) {
        try {
            PreparedStatementCreator psc = connection -> {
                PreparedStatement preparedStatement1 = connection.prepareStatement("INSERT INTO `locations`(`location_name`, `location_subStock`, `location_type`) VALUES (?, ?, ?)");
                preparedStatement1.setString(1, location.getName());
                preparedStatement1.setString(2, location.getSubStock());
                preparedStatement1.setString(3, location.getType().toString());
                return preparedStatement1;
            };
            jdbcTemplate.update(psc);
            return new RequestResult<>(true, true);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new RequestResult<>(e);
        }
    }

    public RequestResult<Boolean> insertLocations(List<Location> locations) {
        StringBuilder sql = new StringBuilder();
        for (int i = 0; i < locations.size() - 1; i++) {
            sql.append("(?, ?, ?),");
        }
        sql.append("(?, ?, ?)");
        try {
            PreparedStatementCreator psc = connection -> {
                PreparedStatement preparedStatement1 = connection.prepareStatement("INSERT INTO `locations`(`location_name`, `location_subStock`, `location_type`) VALUES " + sql.toString());
                for (int i = 0; i < locations.size(); i++) {
                    Location location = locations.get(i);
                    preparedStatement1.setString(1 + (i * 3), location.getName());
                    preparedStatement1.setString(2 + (i * 3), location.getSubStock());
                    preparedStatement1.setString(3 + (i * 3), location.getType().toString());
                }
                return preparedStatement1;
            };
            jdbcTemplate.update(psc);
            return new RequestResult<>(true, true);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new RequestResult<>(e);
        }
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

    public RequestResult<ArrayList<SimpleStockCard>> getSimpleStockCards() {
        try {
            return new RequestResult<>(new ArrayList<>(jdbcTemplate.query("SELECT `card_id`, `item_name`, `ean`, `item_number`, `price_per_unit`, `item_producer`, `item_weight`, `number_of_unit_in_package` FROM `storage_cards`;", new SimpleStockCardMapper())));
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new RequestResult<>(e);
        }
    }

    public RequestResult<ArrayList<SimpleStockCard>> getSimpleStockCards(String whereClause) {
        try {
            return new RequestResult<>(new ArrayList<>(jdbcTemplate.query("SELECT `card_id`, `item•_name`, `ean`, `item_number`, `price_per_unit`, `item_producer`, `item_weight`, `number_of_unit_in_package` FROM storage_cards;", new SimpleStockCardMapper())));
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new RequestResult<>(e);
        }
    }
}
