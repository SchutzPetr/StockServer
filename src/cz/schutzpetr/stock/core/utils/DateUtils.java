package cz.schutzpetr.stock.core.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Petr Schutz on 16.03.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class DateUtils {

    /**
     * Create a DateFormat to format the logger timestamp.
     * Date format: dd/MM/yyyy hh:mm:ss
     */
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

    private DateUtils() {
    }
}
