package cz.schutzpetr.stock.server.client;

import cz.schutzpetr.stock.core.utils.StoppableThread;
import cz.schutzpetr.stock.server.commands.ServerCommandExecutor;
import cz.schutzpetr.stock.server.utils.Logger;

import java.io.IOException;
import java.util.logging.Level;

/**
 * Created by Petr Schutz on 16.03.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class ClientThread extends StoppableThread {
    /**
     * Instance of {@code Client}
     */
    private final Client client;

    /**
     * Creates new instance of {@code StoppableThread}
     *
     * @param client instance of {@code Client}
     */
    public ClientThread(Client client) {

        this.client = client;
    }

    /**
     * If this thread was constructed using a separate
     * <code>Runnable</code> run object, then that
     * <code>Runnable</code> object's <code>run</code> method is called;
     * otherwise, this method does nothing and returns.
     * <p>
     * Subclasses of <code>Thread</code> should override this method.
     *
     * @see #start()
     * @see #stop()
     */
    @Override
    public void run() {
        while (isRunning() && client.getClientSocket().isConnected() && !client.getClientSocket().isClosed()) {
            try {
                String command = (String) client.getObjectInputStream().readObject();
                System.out.println(command);
                ServerCommandExecutor.getInstance().onCommand(this.client, command);
            } catch (IOException e) {
                Logger.getLogger().log(Level.SEVERE, "Exeption in ClientThread", e);
                e.printStackTrace();
                Logger.getLogger().log(Level.SEVERE, Boolean.toString(client.getClientSocket().isClosed()) + " " + Boolean.toString(client.getClientSocket().isConnected()), e);
                this.terminate();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        client.disconnect();
    }
}
