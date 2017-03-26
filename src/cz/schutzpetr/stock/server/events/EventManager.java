package cz.schutzpetr.stock.server.events;

import cz.schutzpetr.stock.server.events.events.Event;
import cz.schutzpetr.stock.server.events.listeners.Listener;
import cz.schutzpetr.stock.server.events.listeners.StageCloseRequestListener;
import cz.schutzpetr.stock.server.events.listeners.client.ClientDisconnectListener;
import cz.schutzpetr.stock.server.events.listeners.client.ClientLoginListener;
import cz.schutzpetr.stock.server.events.listeners.client.UnknownClientConnectListener;
import cz.schutzpetr.stock.server.events.listeners.client.UnknownClientDisconnectListener;
import cz.schutzpetr.stock.server.events.listeners.server.ServerStartedListener;
import cz.schutzpetr.stock.server.events.listeners.server.ServerStartingListener;
import cz.schutzpetr.stock.server.events.listeners.server.ServerStoppedListener;
import cz.schutzpetr.stock.server.events.listeners.server.ServerStoppingListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Petr Schutz on 11.03.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class EventManager {

    private static final Map<Class<? extends Listener>, List<Listener>> listenerMap;

    static {
        listenerMap = new HashMap<>();

        listenerMap.put(ServerStartingListener.class, new ArrayList<>());
        listenerMap.put(ServerStoppingListener.class, new ArrayList<>());
        listenerMap.put(ServerStartedListener.class, new ArrayList<>());
        listenerMap.put(ServerStoppedListener.class, new ArrayList<>());

        listenerMap.put(StageCloseRequestListener.class, new ArrayList<>());

        listenerMap.put(ClientLoginListener.class, new ArrayList<>());
        listenerMap.put(UnknownClientDisconnectListener.class, new ArrayList<>());
        listenerMap.put(UnknownClientConnectListener.class, new ArrayList<>());
        listenerMap.put(ClientDisconnectListener.class, new ArrayList<>());
    }

    public static void registerListener(Listener listener) {
        for (Class<? extends Listener> clazz : listenerMap.keySet()) {
            if (clazz.isInstance(listener)) {
                listenerMap.get(clazz).add(listener);
            }
        }
    }

    public static void unregisterListener(Listener listener) {
        for (Class<? extends Listener> clazz : listenerMap.keySet()) {
            if (clazz.isInstance(listener)) {
                listenerMap.get(clazz).remove(listener);
            }
        }
    }

    public static void callEvent(Event event) {
        event.call(listenerMap.get(event.getListener()));
    }
}
