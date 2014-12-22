package pl.slowly.team.server.connection;

import pl.slowly.team.common.packages.Packet;

import java.io.IOException;

/**
 * Created by Emil Kleszcz on 2014-11-29.
 */
public interface IServer {
    /**
     * Starting server and listen for new connections from clients.
     */
    public void listen();

    /**
     * Stop server.
     */
    public void disconnect() throws IOException;

    public void authorizeClient(final int id);

    /**
     * Sending package to specified client.
     *
     * @param pack Packet to send to client.
     * @param id   Identifier of the client.
     * @return True when correctly sended package.
     */
    public boolean sendPacket(final Packet pack, Integer id);

    /**
     * Send broadcast to all the clients connected to the server.
     *
     * @param pack Packet to send to clients.
     * @return True when correctly sended broadcast.
     */
    public boolean sendBroadcastPacket(Packet pack);
}