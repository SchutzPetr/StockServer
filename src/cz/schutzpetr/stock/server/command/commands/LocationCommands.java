package cz.schutzpetr.stock.server.command.commands;

import cz.schutzpetr.stock.core.expressions.WhereClause;
import cz.schutzpetr.stock.core.location.Location;
import cz.schutzpetr.stock.server.client.Client;
import cz.schutzpetr.stock.server.command.annotation.BaseCommand;
import cz.schutzpetr.stock.server.command.annotation.Command;
import cz.schutzpetr.stock.server.command.interfaces.CommandClass;
import cz.schutzpetr.stock.server.command.interfaces.CommandSender;
import cz.schutzpetr.stock.server.command.utils.CommandType;
import cz.schutzpetr.stock.server.database.DatabaseManager;

/**
 * Created by Petr Schutz on 23.03.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
@BaseCommand(command = "location")
public class LocationCommands implements CommandClass {

    //private LocationCommands(){}

    @Command(command = "location", aliases = "get", type = CommandType.CLIENT, description = "/location get {whereClause}", min = 1, max = 1)
    public static void onGetAll(CommandSender sender, String[] args, Object[] objects) {
        if (sender instanceof Client) {
            Client client = (Client) sender;

            if (objects == null || objects.length == 0) {
                client.send(DatabaseManager.getInstance().getDatabase().getLocationTable().getLocations());
            } else if (objects[0] instanceof WhereClause) {
                client.send(DatabaseManager.getInstance().getDatabase().getLocationTable().getLocations((WhereClause) objects[0]));
            }
        }
    }

    @Command(command = "location", aliases = "create", type = CommandType.CLIENT, description = "", usage = "/location create %location%", min = 1, max = 1)
    public static void create(CommandSender sender, String[] args, Object[] objects) {
        if (sender instanceof Client) {//todo:
            Client client = (Client) sender;

            if (objects[0] instanceof Location) {
                Location location = (Location) objects[0];

                client.send(DatabaseManager.getInstance().getDatabase().getLocationTable().insertLocation(location));
            }
        }
    }
}
