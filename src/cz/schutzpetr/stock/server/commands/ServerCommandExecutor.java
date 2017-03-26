package cz.schutzpetr.stock.server.commands;

import cz.schutzpetr.stock.server.commands.interfaces.CommandExecutor;
import cz.schutzpetr.stock.server.commands.interfaces.CommandSender;
import cz.schutzpetr.stock.server.commands.utils.CommandContainer;

/**
 * This class contains method that executes the given command.
 * <p>
 * <p>
 * Created by Petr Schutz on 25.03.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class ServerCommandExecutor implements CommandExecutor {
    /**
     * instance of {@code ServerCommandExecutor}
     */
    private static ServerCommandExecutor ourInstance = new ServerCommandExecutor();

    /**
     * Create instance of this class
     */
    private ServerCommandExecutor() {
    }

    /**
     * @return instance of {@code ServerCommandExecutor}
     */
    public static ServerCommandExecutor getInstance() {
        return ourInstance;
    }

    /**
     * Executes the given command, returning its success
     *
     * @param sender  ource of the command
     * @param command Command which was executed
     * @param args    Passed command arguments
     * @return true if a valid command, otherwise false
     */
    @Override
    public boolean onCommand(CommandSender sender, String command, String[] args) {
        if (args.length == 0) {

            return true;
        }

        CommandContainer commandContainer = CommandManager.getCommandContainer(command, args);

        if (commandContainer == null) {
            sender.sendMessage("The specified command was not found!");
            return true;
        }

        if ((args.length < commandContainer.getCommand().min()) || (args.length > commandContainer.getCommand().max() && commandContainer.getCommand().max() != -1)) {
            sender.sendMessage("Usage: /" + command + " " + commandContainer.getCommand().aliases()[0] + " " + commandContainer.getCommand().usage());
            return true;
        }


        CommandManager.dispatchCommand(commandContainer, sender, args);
        return true;
    }
}
