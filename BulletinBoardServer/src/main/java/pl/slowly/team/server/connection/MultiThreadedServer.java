package pl.slowly.team.server.connection;

import org.apache.log4j.Logger;
import pl.slowly.team.common.packets.Packet;
import pl.slowly.team.common.packets.response.Response;
import pl.slowly.team.server.helpers.PacketWrapper;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Managing server and its connections with clients.
 * <p/>
 * Created by Emil Kleszcz on 2014-11-28.
 */
public class MultiThreadedServer implements IServer, Runnable {

    public final static Logger LOGGER = Logger.getLogger(MultiThreadedServer.class);

    private ExecutorService threadPool = Executors.newFixedThreadPool(20);
    private ServerSocket serverSocket;
    private final int port;
    /**
     * Defines current client Id to be assigned for new client. 0 is reserved for server.
     */
    private Integer currentClientId;
    /**
     * Map where key is unique client id and element is reference to clientSocket.
     */
    private final Map<Integer, ClientInfo> clientMap;
    /**
     * Reference to the event's queue which is transfer to a controller.
     */
    private final BlockingQueue<PacketWrapper> packetsQueue;


    public MultiThreadedServer(final int port, final BlockingQueue<PacketWrapper> packetsQueue) {
        this.port = port;
        this.clientMap = new HashMap<>();
        this.packetsQueue = packetsQueue;
        this.currentClientId = 1;
    }

    /**
     * Start listening for new clients in loop.
     */
    @Override
    public void listen() throws RuntimeException{
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            LOGGER.error("Could not listen on port: " + port, e);
            throw new RuntimeException();
        }
        new Thread(this).start();
    }

    /**
     * Listen for new clients and when they connect add them to the map with required info
     * to sending and receiving any packets.
     * Create new connection in separate thread which receive packets in a loop from the
     * connected client.
     */
    @Override
    public void run() {
        while (true) {
            try {
                Socket nextClientSocket = serverSocket.accept();
                final ObjectOutputStream oout = new ObjectOutputStream(nextClientSocket.getOutputStream());
                final ClientInfo clientsSocketInfo = new ClientInfo(oout, nextClientSocket);
                addClientToMap(currentClientId, clientsSocketInfo);
                LOGGER.info("New user has connected to the server...");
                threadPool.execute(new ClientConnection(nextClientSocket, currentClientId));
                incrementClientDynamicId();

            } catch (IOException e) {
                LOGGER.error("Error occurred during listen for new clients.", e);
            }
        }
    }

    @Override
    public void disconnect() throws IOException {
        LOGGER.info("Closed server...");
        serverSocket.close();
    }

    /**
     * Adding client's socket info to the map.
     *
     * @param id               client connected to server
     * @param clientSocketInfo additional information about a client
     * @return True when add info about a client to the map.
     */
    public boolean addClientToMap(final Integer id, final ClientInfo clientSocketInfo) {
        boolean test = false;
        synchronized (clientMap) {
            if (clientMap.get(id) == null) {
                clientMap.put(id, clientSocketInfo);
                test = true;
            }
        }
        return test;
    }

    /**
     * Remove client's socket info from the map.
     *
     * @param id identifier of the client who is now on the list
     * @return True when removed client from the map
     */
    public boolean removeClientFromMap(final int id) {
        boolean test = false;
        synchronized (clientMap) {
            if (clientMap.get(id) != null) {
                if (clientMap.remove(id) != null) {
                    test = true;
                }
            }
        }
        return test;
    }

    public void authorizeClient(final int clientId, String username) {
        ClientInfo client;
        synchronized (clientMap) {
            if ((client = clientMap.get(clientId)) != null) {
                client.setAuthorized(true);
                client.setUsername(username);
                System.out.println("User authorized");
            }
        }
    }

    /**
     * Sending package to specified client.
     *
     * @param response Packet to send to client.
     * @param clientId Identifier of the client.
     * @return True when correctly sent package.
     */
    @Override
    public boolean sendResponseToClient(Response response, Integer clientId) throws IOException {
        final ClientInfo clientInfo = clientMap.get(clientId);
        if (clientInfo == null) {
            return false;
        }
        clientInfo.getOout().writeObject(response);
        return true;
    }

    /**
     * Send broadcast to all the clients connected to the server.
     *
     * @param packet Packet to send to clients.
     * @return True when correctly sent packets to all the clients.
     */
    @Override
    public boolean sendBroadcastPacket(Packet packet, int clientOwnerId) {
        ClientInfo clientOwnerInfo = clientMap.get(clientOwnerId);
        for (ClientInfo clientInfo : clientMap.values()) {
            if (clientInfo == null) {
                return false;
            }

            try {
                if (clientInfo != clientOwnerInfo && clientOwnerInfo.getCategoryID() == clientInfo.getCategoryID()) {
                    clientInfo.getOout().writeObject(packet);
                }
            } catch (IOException e) {
                e.printStackTrace();
                LOGGER.error(e);
                return false;
            }

        }
        return true;
    }

    @Override
    public String getUsername(int clientId) {
        synchronized (clientMap) {
            return clientMap.get(clientId).getUsername();
        }
    }

    @Override
    public boolean isAuthorized(int userId) {
        synchronized (clientMap) {
            return clientMap.get(userId).isAuthorized();
        }
    }

    @Override
    public void disconnectClient(int clientId) throws IOException {
        synchronized (clientMap) {
            Socket socket = clientMap.get(clientId).getClientSocket();
            if (!socket.isClosed()) {
                clientMap.get(clientId).getClientSocket().close();
                System.out.println("Connection with client closed");
                LOGGER.info("Connection closed with client: " + clientId);
            }
            System.out.println("Connection with client closed by one of sides.");
            clientMap.remove(clientId);
        }
    }

    @Override
    public void setUserCategory(int clientID, int categoryID) {
        clientMap.get(clientID).setCategoryID(categoryID);
    }

    @Override
    public int getUserCategory(int clientID) {
        return clientMap.get(clientID).getCategoryID();
    }

    /**
     * Increments client dynamic Id, so each client can be uniquely identified.
     */
    private void incrementClientDynamicId() {
        synchronized (currentClientId) {
            currentClientId++;
        }
    }

    /**
     * New connection for a client in separate thread.
     * <p/>
     * Created by Emil Kleszcz on 2014-11-29.
     */
    public class ClientConnection implements Runnable {
        private final Socket clientSocket;
        private int clientId;
        private ObjectInputStream oin;

        public ClientConnection(Socket clientSocket, int clientId) {
            this.clientSocket = clientSocket;
            this.clientId = clientId;
            try {
                oin = new ObjectInputStream(clientSocket.getInputStream());
            } catch (IOException e) {
                LOGGER.error("An I/O error occured when creating the input stream.", e);
                e.printStackTrace();
            }
        }

        public void closeClientConnection() {
            removeClientFromMap(clientId);
            try {
                if (!clientSocket.isClosed()) {
                    System.out.println("Connection closed with " + clientId);
                    LOGGER.info("Connection closed with " + clientId);
                    clientSocket.close();
                }
            } catch (final IOException e1) {
                e1.printStackTrace();
                LOGGER.error("Error during closing client connection for id: " + clientId, e1);
            }
        }

        /**
         * Receiving packets from specified client in a loop.
         */
        @Override
        public void run() {
            try {
                while (true) {
                    final Packet packet = (Packet) oin.readObject();
                    LOGGER.info("Packet recieved: " + packet.getClass().getSimpleName() + " " + packet.toString());
                    packetsQueue.add(new PacketWrapper(clientId, packet));
                }
            } catch (IOException | ClassNotFoundException e) {
                // Broken connection
                LOGGER.error("Broken connection: ", e);
                closeClientConnection();
            }
        }
    }

}
