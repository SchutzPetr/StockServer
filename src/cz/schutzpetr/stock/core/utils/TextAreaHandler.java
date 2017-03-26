package cz.schutzpetr.stock.core.utils;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by Petr Schutz on 16.03.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class TextAreaHandler extends Handler {
    private TextArea textArea;

    public TextAreaHandler(TextArea console) {
        this.textArea = console;
    }

    @Override
    public void publish(final LogRecord record) {
        Platform.runLater(() -> {
            StringWriter text = new StringWriter();
            PrintWriter out = new PrintWriter(text);
            out.println(this.getFormatter().format(record));
            textArea.appendText(text.toString());
        });

    }

    /**
     * Flush any buffered output.
     */
    @Override
    public void flush() {

    }

    /**
     * Close the <tt>Handler</tt> and free all associated resources.
     * <p>
     * The close method will perform a <tt>flush</tt> and then close the
     * <tt>Handler</tt>.   After close has been called this <tt>Handler</tt>
     * should no longer be used.  Method calls may either be silently
     * ignored or may throw runtime exceptions.
     *
     * @throws SecurityException if a security manager exists and if
     *                           the caller does not have <tt>LoggingPermission("control")</tt>.
     */
    @Override
    public void close() throws SecurityException {

    }
}
