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
    private boolean authorized;
    private String username;

    public ClientInfo(ObjectOutputStream oout, Socket clientSocket) {
        this.oout = oout;
        this.clientSocket = clientSocket;
        this.authorized = false;
    }

    public ObjectOutputStream getOout() {
        return oout;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public boolean isAuthorized() {
        return authorized;
    }

    public void setAuthorized(boolean authorized) {
        this.authorized = authorized;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
