package cz.schutzpetr.stock.server.utils;

import cz.schutzpetr.stock.core.utils.DateUtils;
import cz.schutzpetr.stock.core.utils.StringUtils;

import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Created by Petr Schutz on 15.03.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class LoggerFormatter extends Formatter {

    private final boolean newLine;

    /**
     * Creates new instance of LoggerFormatter and insert a new line at the end of the record.
     */
    public LoggerFormatter() {
        this(true);
    }

    /**
     * Creates new instance of LoggerFormatter
     *
     * @param newLine Insert a new line at the end of the record?
     */
    public LoggerFormatter(boolean newLine) {
        this.newLine = newLine;
    }

    /**
     * Format the given log record and return the formatted string.
     * <p>
     * The resulting formatted String will normally include a
     * localized and formatted version of the LogRecord's message field.
     * It is recommended to use the {@link Formatter#formatMessage}
     * convenience method to localize and format the message field.
     *
     * @param record the log record to be formatted.
     * @return the formatted log record
     */
    @Override
    public String format(LogRecord record) {
        StringBuilder builder = new StringBuilder(1000);
        builder.append(DateUtils.DATE_FORMAT.format(new Date(record.getMillis()))).append(" ");
        builder.append("[").append(record.getLevel()).append("] ");
        builder.append(formatMessage(record));
        if (newLine) builder.append(StringUtils.NEW_LINE);
        return builder.toString();
    }
}
