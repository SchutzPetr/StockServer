package cz.schutzpetr.stock.server.commands.interfaces;

import java.util.Arrays;

/**
 * This interface contains method that executes the given command
 * <p>
 * <p>
 * Created by Petr Schutz on 25.03.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public interface CommandExecutor {
    /**
     * This method parse a command from string and call it
     *
     * @param sender         ource of the command
     * @param commandToParse command that will be parsed
     * @return true if a valid command, otherwise false
     */
    default boolean onCommand(CommandSender sender, String commandToParse) {
        String[] split = commandToParse.split(" ");

        return onCommand(sender, split[0], Arrays.copyOfRange(split, 1, split.length));
    }

    /**
     * Executes the given command, returning its success
     *
     * @param sender  ource of the command
     * @param command Command which was executed
     * @param args    Passed command arguments
     * @return true if a valid command, otherwise false
     */
    boolean onCommand(CommandSender sender, String command, String[] args);

}
