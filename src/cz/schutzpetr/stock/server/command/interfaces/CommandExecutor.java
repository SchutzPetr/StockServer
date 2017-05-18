package cz.schutzpetr.stock.server.command.interfaces;

import cz.schutzpetr.stock.core.connection.ConnectionCommand;

import java.util.Arrays;

/**
 * This interface contains method that executes the given commands
 * <p>
 * <p>
 * Created by Petr Schutz on 25.03.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public interface CommandExecutor {
    /**
     * This method parse a commands from string and call it
     *
     * @param sender         ource of the commands
     * @param connectionCommand commands
     * @return true if a valid commands, otherwise false
     */
    default boolean onCommand(CommandSender sender, ConnectionCommand connectionCommand) {
        String[] split = connectionCommand.getCommand().split(" ");

        return onCommand(sender, split[0], Arrays.copyOfRange(split, 1, split.length), connectionCommand.getArgs());
    }

    /**
     * Executes the given commands, returning its success
     *
     * @param sender  ource of the commands
     * @param command Command which was executed
     * @param args    Passed commands arguments
     * @param objects Objects
     * @return true if a valid commands, otherwise false
     */
    boolean onCommand(CommandSender sender, String command, String[] args, Object... objects);

}
