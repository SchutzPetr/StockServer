package cz.schutzpetr.stock.server.command.commands;

import cz.schutzpetr.stock.core.connection.RequestResult;
import cz.schutzpetr.stock.core.expressions.WhereClause;
import cz.schutzpetr.stock.core.stockcard.ConnectionStockCard;
import cz.schutzpetr.stock.core.stockcard.SimpleStockCard;
import cz.schutzpetr.stock.server.client.Client;
import cz.schutzpetr.stock.server.command.annotation.BaseCommand;
import cz.schutzpetr.stock.server.command.annotation.Command;
import cz.schutzpetr.stock.server.command.interfaces.CommandClass;
import cz.schutzpetr.stock.server.command.interfaces.CommandSender;
import cz.schutzpetr.stock.server.command.utils.CommandType;
import cz.schutzpetr.stock.server.data.DataManager;
import cz.schutzpetr.stock.server.database.DatabaseManager;

/**
 * Created by Petr Schutz on 03.04.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
@BaseCommand(command = "storagecard")
public class StockCardCommand implements CommandClass {

    @Command(command = "storagecard", aliases = "get", type = CommandType.CLIENT, min = 1, max = 1)
    public static void onGetAll(CommandSender sender, String[] args, Object[] objects) {
        if (sender instanceof Client) {
            Client client = (Client) sender;

            if (objects == null || objects.length == 0)
                client.send(new RequestResult<>(DataManager.getStockCardData().getData()));
            else if (objects[0] instanceof WhereClause)
                client.send(new RequestResult<>(DataManager.getStockCardData().getFilteredData((WhereClause) objects[0])));
            else if (objects[0] instanceof SimpleStockCard) client.send(new RequestResult<>(
                    DatabaseManager.getInstance().
                            getDatabase().getData().getStockCard(((SimpleStockCard) objects[0]).getEuropeanArticleNumber()).getResult().getConnectionStockCard()));
        }
    }

    @Command(command = "storagecard", aliases = "create", type = CommandType.CLIENT, usage = "/storagecard create %storagecard%", min = 1, max = 1)
    public static void onCreateCommand(CommandSender sender, String[] args, Object[] objects) {
        if (sender instanceof Client) {
            Client client = (Client) sender;

            if (objects != null && objects[0] instanceof ConnectionStockCard) {
                //noinspection unchecked
                client.send(new RequestResult<>(DataManager.getStockCardData().insertData(((ConnectionStockCard) objects[0]))));
            }
        }
    }

    @Command(command = "storagecard", aliases = "edit", type = CommandType.CLIENT, usage = "/storagecard edit %storagecard%", min = 1, max = 1)
    public static void oEditCommand(CommandSender sender, String[] args, Object[] objects) {
        if (sender instanceof Client) {
            Client client = (Client) sender;

            if (objects != null && objects[0] instanceof ConnectionStockCard) {
                //noinspection unchecked
                client.send(new RequestResult<>(DataManager.getStockCardData().edit(((ConnectionStockCard) objects[0]))));
            }
        }
    }

    @Command(command = "storagecard", aliases = "remove", type = CommandType.CLIENT, usage = "/storagecard remove %location%", min = 1, max = 1)
    public static void onRemoveCommand(CommandSender sender, String[] args, Object[] objects) {
        if (sender instanceof Client) {
            Client client = (Client) sender;

            if (objects != null && objects[0] instanceof SimpleStockCard) {
                //noinspection unchecked
                client.send(DataManager.getStockCardData().remove(((SimpleStockCard) objects[0])));
            }
        }
    }


/*
    @Command(command = "storagecard", aliases = "create", type = CommandType.CLIENT, description = "", usage = "/storagecard create %card%", min = 1, max = 1)
    public static void create(CommandSender sender, String[] args, Object[] objects) {
        if (sender instanceof Client) {//todo:
            Client client = (Client) sender;

            if (objects[0] instanceof ConnectionStorageCard) {
                StockCard storageCard = new StockCard((ConnectionStorageCard) objects[0]);

                client.send(DatabaseManager.getInstance().getDatabase().getStorageCardTable().insertStorageCard(storageCard));
            }
        }
    }

    @Command(command = "storagecard", aliases = "getall", type = CommandType.CLIENT, description = "/storagecard getall", min = 1, max = 1)
    public static void getAll(CommandSender sender, String[] args, Object[] objects) {
        if (sender instanceof Client) {
            Client client = (Client) sender;

            client.send(DatabaseManager.getInstance().getDatabase().getStorageCardTable().getSimpleStorageCards());
        }
    }

    @Command(command = "storagecard", aliases = "getbysql", type = CommandType.CLIENT, description = "/storagecard getbysql %sql%", min = 1, max = 1)
    public static void onGetBySQL(CommandSender sender, String[] args, Object[] objects) {
        if (sender instanceof Client) {
            Client client = (Client) sender;

            if (objects[0] instanceof String) {
                client.send(DatabaseManager.getInstance().getDatabase().getStorageCardTable().getSimpleStorageCards((String) objects[0]));
            }
        }
    }

    @Command(command = "storagecard", aliases = "get", type = CommandType.CLIENT, description = "/storagecard get %card%", min = 1, max = 1)
    public static void get(CommandSender sender, String[] args, Object[] objects) {
        if (sender instanceof Client) {
            Client client = (Client) sender;

            if (objects[0] instanceof SimpleStorageCard) {
                client.send(DatabaseManager.getInstance().getDatabase().getStorageCardTable().getStorageCard(((SimpleStorageCard) objects[0]).getCardNumber()));
            } else if (objects[0] instanceof Integer) {
                client.send(DatabaseManager.getInstance().getDatabase().getStorageCardTable().getStorageCard((Integer) objects[0]));
            }
        }
    }*/
}
