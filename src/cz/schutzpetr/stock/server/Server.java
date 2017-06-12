package cz.schutzpetr.stock.server;

import cz.schutzpetr.stock.server.database.DatabaseManager;
import cz.schutzpetr.stock.server.events.EventManager;
import cz.schutzpetr.stock.server.events.events.server.ServerStartedEvent;
import cz.schutzpetr.stock.server.events.events.server.ServerStartingEvent;
import cz.schutzpetr.stock.server.events.events.server.ServerStoppedEvent;
import cz.schutzpetr.stock.server.events.events.server.ServerStoppingEvent;
import cz.schutzpetr.stock.server.utils.Logger;

import javax.net.ServerSocketFactory;
import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;

/**
 * Created by Petr Schutz on 13.03.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class Server {

    private static Server ourInstance = new Server(8080);
    private final int port;
    private ServerThread serverThread;
    private boolean isRunning = false;

    private Server(int port) {
        this.port = port;
    }

    public static Server getInstance() {
        return ourInstance;
    }

    public void start() {
        EventManager.callEvent(new ServerStartingEvent());
        if (isRunning) return;
        Logger.log("Starting server on port: " + this.port);
        ServerSocket serverSocket = createServerSocket(this.port);

        if (serverSocket == null) {
            Logger.log("Server is shutting down......");
            return;
        }

        serverThread = new ServerThread(serverSocket);
        serverThread.start();

        isRunning = true;
        EventManager.callEvent(new ServerStartedEvent());
        DatabaseManager.getInstance().getDatabase();
        Logger.log("Server up & ready for connections......");
    }

    public void restart() {
        Logger.log("The server will now restart...");
        EventManager.callEvent(new ServerStoppingEvent());
        try {
            DatabaseManager.getInstance().getDatabase().disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        isRunning = false;
        serverThread.terminate();

        Thread thread = new Thread(() -> {
            while (serverThread.isAlive()) {
                Thread.yield();
            }

            EventManager.callEvent(new ServerStoppedEvent());
            start();
        });
        thread.setDaemon(true);
        thread.start();
    }

    public void stop() {
        EventManager.callEvent(new ServerStoppingEvent());
        isRunning = false;
        serverThread.terminate();

        Thread thread = new Thread(() -> {
            while (serverThread.isAlive()) {
                Thread.yield();
            }

            EventManager.callEvent(new ServerStoppedEvent());
            Logger.log("The server is shutting down...");
        });
        thread.setDaemon(true);
        thread.start();
    }

    private ServerSocket createServerSocket(int port) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = ServerSocketFactory.getDefault().createServerSocket(port);
        } catch (IOException e) {
            Logger.log("Creating socket failed!");
            e.printStackTrace();
        } finally {
            if (serverSocket == null) {
                Logger.log("Connect failed!");
                serverSocket = null;
            } else {
                Logger.log("Socket created!");
            }
        }
        return serverSocket;
    }

    public int getPort() {
        return port;
    }

    public ServerThread getServerThread() {
        return serverThread;
    }

    public boolean isRunning() {
        return isRunning;
    }
}
