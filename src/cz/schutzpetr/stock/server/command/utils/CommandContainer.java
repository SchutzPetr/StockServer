package cz.schutzpetr.stock.server.command.utils;

import cz.schutzpetr.stock.server.command.annotation.Command;

import java.lang.reflect.Method;

/**
 * Created by Petr Schutz on 24.03.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class CommandContainer {

    /**
     * Base commands
     */
    private final Command command;

    /**
     * Method for {@code Command}
     */
    private final Method method;

    /**
     * Create a new instance of container that contains Command and his method.
     *
     * @param command base commands
     * @param method  commands method
     */
    public CommandContainer(Command command, Method method) {
        this.command = command;
        this.method = method;
    }

    /**
     * @return a {@code Command}
     */
    public Command getCommand() {
        return command;
    }

    /**
     * @return a {@code Method} for commands
     */
    public Method getMethod() {
        return method;
    }
}
