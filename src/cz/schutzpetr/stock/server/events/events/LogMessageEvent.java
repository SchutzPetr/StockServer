package cz.schutzpetr.stock.server.events.events;

import cz.schutzpetr.stock.server.events.listeners.Listener;
import cz.schutzpetr.stock.server.events.listeners.LogMessageListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by Petr Schutz on 15.03.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class LogMessageEvent extends Event {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private final String message;


    public LogMessageEvent(String message) {
        this.message = message;
    }

    /**
     * This method calls this event
     *
     * @param listeners list of listeners
     */
    @Override
    public void call(List<? extends Listener> listeners) {
        listeners.forEach(x -> ((LogMessageListener) x).onLogMessage(this));
    }

    /**
     * @return listener iface for this event
     */
    @Override
    public Class<LogMessageListener> getListener() {
        return LogMessageListener.class;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return DATE_TIME_FORMATTER.format(LocalDateTime.now());
    }
}
