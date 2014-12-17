package pl.slowly.team.server.connection;

import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Contains socket and output stream for a client.
 * <p/>
 * Created by Emil Kleszcz on 2014-12-04.
 */
public class ClientInfo {

    private final ObjectOutputStream oout;

    private final Socket clientSocket;

    public ClientInfo(ObjectOutputStream oout, Socket clientSocket) {
        this.oout = oout;
        this.clientSocket = clientSocket;
    }

    public ObjectOutputStream getOout() {
        return oout;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }
}
