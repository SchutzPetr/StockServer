package cz.schutzpetr.stock.server.client;

import cz.schutzpetr.stock.core.auth.AuthResult;
import cz.schutzpetr.stock.core.connection.RequestResult;
import cz.schutzpetr.stock.core.permission.Permission;
import cz.schutzpetr.stock.core.user.User;
import cz.schutzpetr.stock.server.command.interfaces.CommandSender;
import cz.schutzpetr.stock.server.events.EventManager;
import cz.schutzpetr.stock.server.events.events.client.ClientDisconnectEvent;
import cz.schutzpetr.stock.server.events.events.client.ClientLoginEvent;
import cz.schutzpetr.stock.server.utils.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.logging.Level;

/**
 * Created by Petr Schutz on 16.03.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public final class Client extends UnknownClient implements CommandSender {
    private final User user;
    private final ClientThread clientThread;

    private Client(User user, Socket clientSocket, ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) throws IOException {
        super(clientSocket, objectInputStream, objectOutputStream);

        this.user = user;
        this.clientThread = new ClientThread(this);
        this.clientThread.start();
    }

    public static void signInClient(UnknownClient unknownClient, User user) throws IOException {
        Client client = new Client(user, unknownClient.getClientSocket(), unknownClient.getObjectInputStream(), unknownClient.getObjectOutputStream());
        EventManager.callEvent(new ClientLoginEvent(client, unknownClient));
    }

    public User getUser() {
        return user;
    }

    @Override
    public void disconnect(AuthResult authResult) {
        EventManager.callEvent(new ClientDisconnectEvent(this));
        try {
            getObjectOutputStream().writeObject(authResult);
            getClientSocket().close();
        } catch (IOException e) {
            Logger.getLogger().log(Level.SEVERE, "Exception in disconnect client", e);
        }
    }

    public void send(RequestResult<? extends Serializable> requestResult) {
        try {
            this.getObjectOutputStream().writeObject(requestResult);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Sends this sendera message
     *
     * @param message Message to be displayed
     */
    @Override
    public void sendMessage(String message) {

    }

    /**
     * Sends this sender multiple messages
     *
     * @param messages An array of messages to be displayed
     */
    @Override
    public void sendMessage(String[] messages) {

    }

    /**
     * Gets the value of the specified permission, if set.
     * If a permission override is not set on this object, the default value of the permission will be returned
     *
     * @param permission Permission to get
     * @return value of permission
     */
    @Override
    public boolean hasPermission(Permission permission) {
        return false;
    }
}
