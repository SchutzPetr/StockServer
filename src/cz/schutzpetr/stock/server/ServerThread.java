package cz.schutzpetr.stock.server;

import cz.schutzpetr.stock.core.utils.StoppableThread;
import cz.schutzpetr.stock.server.utils.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Petr Schutz on 23.02.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class ServerThread extends StoppableThread {
    private final ServerSocket serverSocket;

    ServerThread(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }


    @Override
    public void run() {
        while (super.isRunning()) {
            try {
                Socket socket = serverSocket.accept();
                SignInThread.signIn(socket);
                Logger.log("Client connected! " + socket.getInetAddress());
            } catch (IOException ignore) {
            }
        }
        Logger.log("The server thread has been terminated!");
    }

    public void terminate() {
        super.terminate();
        try {
            Logger.log("The ServerSocket is closing...");
            this.serverSocket.close();
            Logger.log("ServerSocket closed!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
