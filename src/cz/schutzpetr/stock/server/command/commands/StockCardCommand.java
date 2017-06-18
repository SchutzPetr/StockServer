package cz.schutzpetr.stock.server.command.commands;

import cz.schutzpetr.stock.server.client.Client;
import cz.schutzpetr.stock.server.command.annotation.BaseCommand;
import cz.schutzpetr.stock.server.command.annotation.Command;
import cz.schutzpetr.stock.server.command.interfaces.CommandClass;
import cz.schutzpetr.stock.server.command.interfaces.CommandSender;
import cz.schutzpetr.stock.server.command.utils.CommandType;
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

            if (objects == null || objects.length == 0) {
                client.send(DatabaseManager.getInstance().getDatabase().getLocationTable().getSimpleStockCards());
            } else if (objects[0] instanceof String) {
                //client.send(DatabaseManager.getInstance().getDatabase().getLocationTable().getLocations((String) objects[0]));
            }
        }
    }

/*
    @Command(command = "storagecard", aliases = "create", type = CommandType.CLIENT, description = "", usage = "/storagecard create %card%", min = 1, max = 1)
    public static void create(CommandSender sender, String[] args, Object[] objects) {
        if (sender instanceof Client) {//todo:
            Client client = (Client) sender;

            if (objects[0] instanceof ConnectionStorageCard) {
                StorageCard storageCard = new StorageCard((ConnectionStorageCard) objects[0]);

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
