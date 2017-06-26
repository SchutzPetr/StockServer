package cz.schutzpetr.stock.server.command.commands;

import cz.schutzpetr.stock.core.connection.RequestResult;
import cz.schutzpetr.stock.core.expressions.WhereClause;
import cz.schutzpetr.stock.core.location.Pallet;
import cz.schutzpetr.stock.server.client.Client;
import cz.schutzpetr.stock.server.command.annotation.BaseCommand;
import cz.schutzpetr.stock.server.command.annotation.Command;
import cz.schutzpetr.stock.server.command.interfaces.CommandClass;
import cz.schutzpetr.stock.server.command.interfaces.CommandSender;
import cz.schutzpetr.stock.server.command.utils.CommandType;
import cz.schutzpetr.stock.server.data.DataManager;

/**
 * Created by Petr Schutz on 28.03.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
@BaseCommand(command = "pallet")
public class PalletCommands implements CommandClass {

    @Command(command = "pallet", aliases = "get", type = CommandType.CLIENT, min = 1, max = 1)
    public static void onGetAll(CommandSender sender, String[] args, Object[] objects) {
        if (sender instanceof Client) {//todo:
            Client client = (Client) sender;

            if (objects == null || objects.length == 0)
                client.send(new RequestResult<>(DataManager.getPalletData().getData()));
            else if (objects[0] instanceof WhereClause)
                client.send(new RequestResult<>(DataManager.getPalletData().getFilteredData((WhereClause) objects[0])));

        }
    }

    @Command(command = "pallet", aliases = "create", type = CommandType.CLIENT, usage = "/pallet create %pallet%", min = 1, max = 1)
    public static void onCreateCommand(CommandSender sender, String[] args, Object[] objects) {
        if (sender instanceof Client) {
            Client client = (Client) sender;

            if (objects != null && objects[0] instanceof Pallet) {
                //noinspection unchecked
                client.send(new RequestResult<>(DataManager.getPalletData().insertData(((Pallet) objects[0]))));
            }
        }
    }

    @Command(command = "pallet", aliases = "remove", type = CommandType.CLIENT, usage = "/pallet remove %location%", min = 1, max = 1)
    public static void onRemoveCommand(CommandSender sender, String[] args, Object[] objects) {
        if (sender instanceof Client) {
            Client client = (Client) sender;

            if (objects != null && objects[0] instanceof Pallet) {
                //noinspection unchecked
                client.send(new RequestResult<>(DataManager.getPalletData().remove(((Pallet) objects[0]))));
            }
        }
    }
}
