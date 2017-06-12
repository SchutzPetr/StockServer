package cz.schutzpetr.stock.server.command.commands;

import cz.schutzpetr.stock.core.location.Pallet;
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

    @Command(command = "pallet", aliases = "get", type = CommandType.CLIENT, description = "", min = 1, max = 1)
    public static void onGetAll(CommandSender sender, String[] args, Object[] objects) {
        if (sender instanceof Client) {//todo:
            Client client = (Client) sender;

            if (objects == null || objects.length == 0) {
                client.send(DatabaseManager.getInstance().getDatabase().getLocationTable().getPallets());
            } else if (objects[0] instanceof String && objects[1] instanceof String) {
                client.send(DatabaseManager.getInstance().getDatabase().getLocationTable().getPallets((String) objects[0], (String) objects[1]));
            }
        }
    }

    @Command(command = "pallet", aliases = "create", type = CommandType.CLIENT, description = "", usage = "/pallet create %pallet%", min = 1, max = 1)
    public static void create(CommandSender sender, String[] args, Object[] objects) {
        if (sender instanceof Client) {//todo:
            Client client = (Client) sender;

            if (objects[0] instanceof Pallet) {
                Pallet pallet = (Pallet) objects[0];

                client.send(DatabaseManager.getInstance().getDatabase().getPalletTable().insertPallet(pallet));
            }
        }
    }
}
