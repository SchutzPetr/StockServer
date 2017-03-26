package cz.schutzpetr.stock.server.utils;

import cz.schutzpetr.stock.server.gui.ServerApplication;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;

/**
 * Created by Petr Schutz on 28.02.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class Logger {
    /**
     * Instance of {@code java.util.logging.Logger}
     */
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(ServerApplication.class.getName());

    static {
        Handler handlerObj = new ConsoleHandler();
        handlerObj.setFormatter(new LoggerFormatter());
        handlerObj.setLevel(Level.ALL);
        LOGGER.addHandler(handlerObj);
        LOGGER.setLevel(Level.ALL);
        LOGGER.setUseParentHandlers(false);
    }

    /**
     * Private constructor
     */
    private Logger() {
    }

    /**
     * @return Instance of {@code java.util.logging.Logger}
     */
    public static java.util.logging.Logger getLogger() {
        return LOGGER;
    }

    /**
     * Log a message, with no arguments and Info level
     * <p>
     * If the logger is currently enabled for the given message
     * level then the given message is forwarded to all the
     * registered output Handler objects.
     * <p>
     *
     * @param msg The string message (or a key in the message catalog)
     */
    public static void log(String msg) {
        getLogger().info(msg);
    }
}
