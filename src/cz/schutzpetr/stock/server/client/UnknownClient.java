package cz.schutzpetr.stock.server.client;

import cz.schutzpetr.stock.core.auth.AuthResult;
import cz.schutzpetr.stock.server.events.EventManager;
import cz.schutzpetr.stock.server.events.events.client.UnknownClientConnectEvent;
import cz.schutzpetr.stock.server.events.events.client.UnknownClientDisconnectEvent;
import cz.schutzpetr.stock.server.utils.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;

/**
 * Created by Petr Schutz on 16.03.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class UnknownClient {

    private final Socket clientSocket;
    private final ObjectInputStream objectInputStream;
    private final ObjectOutputStream objectOutputStream;

    protected UnknownClient(Socket clientSocket, ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) {
        this.clientSocket = clientSocket;
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;
    }

    public static UnknownClient connectClient(Socket clientSocket) throws IOException {
        UnknownClient unknownClient = new UnknownClient(clientSocket, new ObjectInputStream(clientSocket.getInputStream()), new ObjectOutputStream(clientSocket.getOutputStream()));
        EventManager.callEvent(new UnknownClientConnectEvent(unknownClient));
        return unknownClient;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public ObjectInputStream getObjectInputStream() {
        return objectInputStream;
    }

    public ObjectOutputStream getObjectOutputStream() {
        return objectOutputStream;
    }

    public void disconnect() {
        disconnect(new AuthResult(null, false, "Server is closing!"));
    }

    public void disconnect(AuthResult authResult) {
        EventManager.callEvent(new UnknownClientDisconnectEvent(this));
        try {
            this.objectOutputStream.writeObject(authResult);
            this.clientSocket.close();
        } catch (IOException e) {
            Logger.getLogger().log(Level.SEVERE, "Exception in disconnect client", e);
        }
    }

}
