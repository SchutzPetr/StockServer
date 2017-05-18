package cz.schutzpetr.stock.server.command.commands;

import cz.schutzpetr.stock.core.connection.ConnectionStorageCard;
import cz.schutzpetr.stock.core.connection.RequestResult;
import cz.schutzpetr.stock.server.client.Client;
import cz.schutzpetr.stock.server.command.annotation.BaseCommand;
import cz.schutzpetr.stock.server.command.annotation.Command;
import cz.schutzpetr.stock.server.command.interfaces.CommandClass;
import cz.schutzpetr.stock.server.command.interfaces.CommandSender;
import cz.schutzpetr.stock.server.command.utils.CommandType;
import cz.schutzpetr.stock.server.database.DatabaseManager;
import cz.schutzpetr.stock.server.database.DatabaseResult;
import cz.schutzpetr.stock.server.utils.items.StorageCard;

import java.util.ArrayList;

/**
 * Created by Petr Schutz on 03.04.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
@BaseCommand(command = "storagecard")
public class StorageCardCommand implements CommandClass {

    @Command(command = "storagecard", aliases = "create", type = CommandType.CLIENT, description = "", usage = "/storagecard create %card%", min = 1, max = 1)
    public static void onNewCard(CommandSender sender, String[] args, Object[] objects) {
        if (sender instanceof Client) {//todo:
            Client client = (Client) sender;

            if (objects[0] instanceof ConnectionStorageCard) {
                StorageCard storageCard = new StorageCard((ConnectionStorageCard) objects[0]);

                DatabaseResult<Boolean> databaseResult = DatabaseManager.getInstance().getDatabase().getStorageCardTable().insertStorageCard(storageCard);
                client.send(new RequestResult<>(databaseResult.getResult(), databaseResult.getResult()));
            }
        }
    }

    @Command(command = "storagecard", aliases = "getAll", type = CommandType.CLIENT, description = "", min = 1, max = 1)
    public static void getAllStorageCards(CommandSender sender, String[] args, Object[] objects) {
        if (sender instanceof Client) {
            Client client = (Client) sender;

            DatabaseResult<ArrayList<StorageCard>> storageCards = DatabaseManager.getInstance().getDatabase().getStorageCardTable().getStorageCards();
            ArrayList<ConnectionStorageCard> connectionStorageCards = new ArrayList<>();
            storageCards.getResult().forEach(storageCard -> connectionStorageCards.add(storageCard.getConnectionStorageCard()));
            client.send(new RequestResult<>(storageCards.isResult(), connectionStorageCards));
        }
    }
}
