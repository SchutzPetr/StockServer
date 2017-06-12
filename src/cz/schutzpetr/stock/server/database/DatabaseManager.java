package cz.schutzpetr.stock.server.database;

import cz.schutzpetr.stock.server.utils.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Petr Schutz on 06.03.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class DatabaseManager {
    /**
     * Instance of {@code DatabaseManager}
     */
    private static DatabaseManager ourInstance = new DatabaseManager();
    /**
     * Instance of {@code Database}
     */
    private Database database;

    /**
     * Private constructor - singleton
     */
    private DatabaseManager() {
    }

    /**
     * @return instance of {@code DatabaseManager}
     */
    public static DatabaseManager getInstance() {
        return ourInstance;
    }

    /**
     * This method creating connection to Database
     *
     * @return instance of {@code Database}
     */
    private Database connect() {
        Logger.log("Connecting to database...");
        Database database = null;
        try {
            //ApplicationContext context = news ClassPathXmlApplicationContext("classpath*:conf/database.xml");
            ApplicationContext context = new ClassPathXmlApplicationContext("configuration/database.xml");
            database = (Database) context.getBean("databaseTemplate");
        } catch (BeansException e) {
            Logger.log("Connect failed!");
            e.printStackTrace();
        } finally {
            if (database == null || !database.isConnected()) {
                Logger.log("Connect failed!");
            } else {
                Logger.log("Database connected!");
            }
        }
        this.database = database;
        return database;
    }

    /**
     * @return instance of {@code Database}
     */
    public Database getDatabase() {
        if (database != null && database.isConnected()) {
            return database;
        }
        return connect();
    }
}
