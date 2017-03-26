package cz.schutzpetr.stock.server.commands.utils;

import cz.schutzpetr.stock.server.commands.annotation.Command;

import java.lang.reflect.Method;

/**
 * Created by Petr Schutz on 24.03.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class CommandContainer {

    /**
     * Base command
     */
    private final Command command;

    /**
     * Method for {@code Command}
     */
    private final Method method;

    /**
     * Create a new instance of container that contains Command and his method.
     *
     * @param command base command
     * @param method  command method
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
     * @return a {@code Method} for command
     */
    public Method getMethod() {
        return method;
    }
}
