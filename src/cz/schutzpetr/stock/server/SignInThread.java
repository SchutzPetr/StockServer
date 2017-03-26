package cz.schutzpetr.stock.server;

import configuration.Config;
import cz.schutzpetr.stock.core.auth.AuthData;
import cz.schutzpetr.stock.core.auth.AuthResult;
import cz.schutzpetr.stock.server.client.Client;
import cz.schutzpetr.stock.server.client.UnknownClient;
import cz.schutzpetr.stock.server.database.DatabaseManager;
import cz.schutzpetr.stock.server.utils.Logger;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;

/**
 * Created by Petr Schutz on 16.03.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class SignInThread extends Thread {


    private final UnknownClient unknownClient;

    private SignInThread(UnknownClient unknownClient) throws SocketException {
        this.unknownClient = unknownClient;
    }

    public static void signIn(Socket clientSocket) {
        try {
            SignInThread signInThread = new SignInThread(UnknownClient.connectClient(clientSocket));
            signInThread.setDaemon(true);
            signInThread.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            run(unknownClient);
        } catch (IOException | ClassNotFoundException e) {
            Logger.getLogger().log(Level.SEVERE, "Exeption in SignInThread", e);
        }
        System.out.println("exit");
    }

    public void run(UnknownClient unknownClient) throws IOException, ClassNotFoundException {
        unknownClient.getClientSocket().setSoTimeout(Config.LOGIN_TIME_OUT);

        AuthData a = (AuthData) unknownClient.getObjectInputStream().readObject();
        AuthResult authResult = DatabaseManager.getInstance().getDatabase().getUserTable().check(a);

        if (authResult == null) {
            unknownClient.disconnect(new AuthResult(null, false, "User authentication failed!"));
        } else if (!authResult.isResult() || authResult.getUser() == null) {
            unknownClient.disconnect(authResult);
        } else {
            unknownClient.getClientSocket().setSoTimeout(0);
            unknownClient.getObjectOutputStream().writeObject(authResult);

            Client.signInClient(unknownClient, authResult.getUser());
        }
    }

}
