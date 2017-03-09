package cz.schutzpetr.stock.server;

import cz.schutzpetr.stock.core.utils.iface.PrinterImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by Petr Schutz on 28.02.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class Logger {
    private Logger(){}

    private final static List<PrinterImpl> printers = new ArrayList<>();

    public static void addPrinter(PrinterImpl printer){
        printers.add(printer);
    }

    public static void log(){

    }

    public static void log(String message) {
        printers.forEach(x-> x.println(message));

        System.out.println(message);
    }

    public static void log(Level level, String message) {
        log(message);
    }
}
