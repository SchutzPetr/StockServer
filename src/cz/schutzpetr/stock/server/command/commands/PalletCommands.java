package cz.schutzpetr.stock.server.command.commands;

import cz.schutzpetr.stock.core.location.Location;
import cz.schutzpetr.stock.server.client.Client;
import cz.schutzpetr.stock.server.command.annotation.BaseCommand;
import cz.schutzpetr.stock.server.command.annotation.Command;
import cz.schutzpetr.stock.server.command.interfaces.CommandClass;
import cz.schutzpetr.stock.server.command.interfaces.CommandSender;
import cz.schutzpetr.stock.server.command.utils.CommandType;
import cz.schutzpetr.stock.server.database.DatabaseManager;

/**
 * Created by Petr Schutz on 28.03.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
@BaseCommand(command = "pallet")
public class PalletCommands implements CommandClass {

    @Command(command = "pallet", aliases = "getall", type = CommandType.CLIENT, description = "", min = 1, max = 1)
    public static void onGetAll(CommandSender sender, String[] args, Object[] objects) {
        if (sender instanceof Client) {//todo:
            Client client = (Client) sender;

            client.send(DatabaseManager.getInstance().getDatabase().getPalletTable().getPallets());
        }
    }

    @Command(command = "pallet", aliases = "getbysql", type = CommandType.CLIENT, description = "/pallet getbysql %sql%", min = 1, max = 1)
    public static void onGetBySQL(CommandSender sender, String[] args, Object[] objects) {
        if (sender instanceof Client) {
            Client client = (Client) sender;

            if (objects[0] instanceof String) {
                client.send(DatabaseManager.getInstance().getDatabase().getPalletTable().getPallets((String) objects[0]));
            }
        }
    }

    @Command(command = "pallet", aliases = "getbylocation", type = CommandType.CLIENT, description = "/pallet getbylocation %location%", min = 1, max = 1)
    public static void onGetByLocation(CommandSender sender, String[] args, Object[] objects) {
        if (sender instanceof Client) {
            Client client = (Client) sender;

            if (objects[0] instanceof Location) {
                client.send(DatabaseManager.getInstance().getDatabase().getPalletTable().getPalletsByLocation(((Location) objects[0]).getName()));
            }
        }
    }
}
