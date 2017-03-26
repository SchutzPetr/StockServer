package cz.schutzpetr.stock.server.client;

import cz.schutzpetr.stock.server.events.events.client.ClientDisconnectEvent;
import cz.schutzpetr.stock.server.events.events.client.ClientLoginEvent;
import cz.schutzpetr.stock.server.events.events.client.UnknownClientConnectEvent;
import cz.schutzpetr.stock.server.events.events.client.UnknownClientDisconnectEvent;
import cz.schutzpetr.stock.server.events.events.server.ServerStoppingEvent;
import cz.schutzpetr.stock.server.events.listeners.client.ClientDisconnectListener;
import cz.schutzpetr.stock.server.events.listeners.client.ClientLoginListener;
import cz.schutzpetr.stock.server.events.listeners.client.UnknownClientConnectListener;
import cz.schutzpetr.stock.server.events.listeners.client.UnknownClientDisconnectListener;
import cz.schutzpetr.stock.server.events.listeners.server.ServerStoppingListener;
import cz.schutzpetr.stock.server.utils.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Petr Schutz on 25.02.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class Clients implements UnknownClientConnectListener, UnknownClientDisconnectListener, ClientDisconnectListener, ClientLoginListener, ServerStoppingListener {
    private final static List<Client> clients = new ArrayList<>();
    private final static List<UnknownClient> unknownClients = new ArrayList<>();
    private static Clients ourInstance = new Clients();

    private Clients() {
    }

    public static Clients getInstance() {
        return ourInstance;
    }

    private void disconnectClients() {
        Logger.log("Disconnecting clients...!");
        unknownClients.forEach(UnknownClient::disconnect);
        clients.forEach(Client::disconnect);
        Logger.log("All clients are disconnected!");
    }

    /**
     * Invoked when a specific event of the type for which this handler is registered happens.
     *
     * @param event {@code ClientLoginEvent}
     */
    @Override
    public void onClientLoginEvent(ClientLoginEvent event) {
        clients.add(event.getClient());
        unknownClients.remove(event.getUnknownClient());
        Logger.log("Client " + event.getClient().getUser().getFullName() + "IP:" + event.getClient().getClientSocket().getInetAddress() + " logged on!");
    }

    /**
     * Invoked when a specific event of the type for which this handler is registered happens.
     *
     * @param event {@code UnknownClientConnectEvent}
     */
    @Override
    public void onUnknownClientConnect(UnknownClientConnectEvent event) {
        unknownClients.add(event.getUnknownClient());
    }

    /**
     * Invoked when a specific event of the type for which this handler is registered happens.
     *
     * @param event {@code ClientDisconnectEvent}
     */
    @Override
    public void onClientDisconnect(ClientDisconnectEvent event) {
        clients.remove(event.getClient());
        Logger.log("Client " + event.getClient().getUser().getFullName() + "IP:" + event.getClient().getClientSocket().getInetAddress() + " disconnected!");
    }

    /**
     * Invoked when a specific event of the type for which this handler is registered happens.
     *
     * @param event {@code UnknownClientDisconnectEvent}
     */
    @Override
    public void onUnknownClientDisconnect(UnknownClientDisconnectEvent event) {
        unknownClients.remove(event.getUnknownClient());
        Logger.log("Client IP:" + event.getUnknownClient().getClientSocket().getInetAddress() + " disconnected!");
    }

    /**
     * Invoked when a specific event of the type for which this handler is registered happens.
     *
     * @param event {@code ServerStoppingEvent}
     */
    @Override
    public void onServerStopping(ServerStoppingEvent event) {
        disconnectClients();
    }
}
