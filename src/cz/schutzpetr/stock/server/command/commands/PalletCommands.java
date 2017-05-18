package cz.schutzpetr.stock.server.command.commands;

import cz.schutzpetr.stock.core.Pallet;
import cz.schutzpetr.stock.core.connection.RequestResult;
import cz.schutzpetr.stock.server.client.Client;
import cz.schutzpetr.stock.server.command.annotation.BaseCommand;
import cz.schutzpetr.stock.server.command.annotation.Command;
import cz.schutzpetr.stock.server.command.interfaces.CommandClass;
import cz.schutzpetr.stock.server.command.interfaces.CommandSender;
import cz.schutzpetr.stock.server.command.utils.CommandType;
import cz.schutzpetr.stock.server.database.DatabaseManager;
import cz.schutzpetr.stock.server.database.DatabaseResult;

import java.util.ArrayList;

/**
 * Created by Petr Schutz on 28.03.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
@BaseCommand(command = "pallet")
public class PalletCommands implements CommandClass {

    @Command(command = "pallet", aliases = "getAll", type = CommandType.CLIENT, description = "", min = 1, max = 1)
    public static void onGetAll(CommandSender sender, String[] args, Object[] objects) {
        if (sender instanceof Client) {//todo:
            Client client = (Client) sender;

            DatabaseResult<ArrayList<Pallet>> pallets = DatabaseManager.getInstance().getDatabase().getPalletTable().getPallets();
            client.send(new RequestResult<>(pallets.isResult(), pallets.getResult()));
        }
    }
}
