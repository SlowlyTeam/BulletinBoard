package pl.slowly.team.server.connection;

import org.apache.log4j.Logger;
import pl.slowly.team.common.packages.Package;

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

    final static Logger logger = Logger.getLogger(MultiThreadedServer.class);

    private ServerSocket serverSocket;

    private ExecutorService threadPool = Executors.newFixedThreadPool(20);

    private final int port;

    /**
     * Map where key is unique client id and element is reference to clientSocket.
     */
    private final Map<Integer, ClientInfo> clientMap;

    /**
     * Reference to the event's queue which is transfer to a controller.
     */
    private final BlockingQueue<Package> packagesQueue;


    public MultiThreadedServer(final int port, final BlockingQueue<Package> packagesQueue) {
        this.port = port;
        clientMap = new HashMap<>();
        this.packagesQueue = packagesQueue;
    }

    /**
     * Start listening for new clients in loop.
     */
    @Override
    public void listen() {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            logger.error("Could not listen on port: " + port, e);
        }
        new Thread(this).start();
    }

    /**
     * Listen for new clients and when they connect add them to the map with required info
     * to sending and receiving any packages.
     * Create new connection in separate thread which receive packages in a loop from the
     * connected client.
     */
    @Override
    public void run() {
        while (true) {
            try {
                Socket nextClientSocket = serverSocket.accept();

                final ObjectOutputStream oout = new ObjectOutputStream(nextClientSocket.getOutputStream());
                final ClientInfo clientsSocketInfo = new ClientInfo(oout, nextClientSocket);
                //TODO - ID na sztywno - generowanie unikalnego id lub wywalic id i zostawic w liscie tylko
                //info o gniezdzie albo zastapic id jakims loginem uzytkownika - ja proponuje generowac poprzez
                //inkrementowanie zmiennej statycznej id klientow nowo podlaczonych do serwera
                addClientToMap(1, clientsSocketInfo);

                this.threadPool.execute(new ClientConnection(nextClientSocket));
            } catch (IOException e) {
                logger.error("Error occurred during listen for new clients.", e);
            }
        }
    }

    @Override
    public void disconnect() throws IOException {
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
    public boolean removeClientFromMap(final Integer id) {
        boolean test = false;
        //Prevent from removing client from the map when sth is sending directly to him
        synchronized (clientMap) {
            if (clientMap.get(id) != null) {
                if (clientMap.remove(id) != null) {
                    test = true;
                }
            }
        }
        return test;
    }

    /**
     * Sending package to specified client.
     *
     * @param pack Package to send to client.
     * @param id   Identifier of the client.
     * @return True when correctly sent package.
     */
    @Override
    public boolean sendPackage(Package pack, Integer id) {
        final ClientInfo clientInfo = clientMap.get(id);
        if (clientInfo == null) {
            return false;
        }

        try {
            clientInfo.getOout().writeObject(pack);
        } catch (final IOException e) {
            e.printStackTrace();
            logger.error(e);
            return false;
        }
        return true;
    }

    /**
     * Send broadcast to all the clients connected to the server.
     *
     * @param pack Package to send to clients.
     * @return True when correctly sent packages to all the clients.
     */
    @Override
    public boolean sendBroadcastPackage(Package pack) {
        for (ClientInfo clientInfo : clientMap.values()) {

            if (clientInfo == null) {
                return false;
            }

            try {
                clientInfo.getOout().writeObject(pack);
            } catch (IOException e) {
                e.printStackTrace();
                logger.error(e);
                return false;
            }

        }
        return true;
    }

    //TODO - Add service for user verification by login and/or password and comparing it with database's data
    //Send some information packages between client and server about that.
    //In case of failed log into system user should be informed by sending appropriate statement

    /**
     * New connection for a client in separate thread.
     * <p/>
     * Created by Emil Kleszcz on 2014-11-29.
     */
    public class ClientConnection implements Runnable {
        private final Socket clientSocket;

        /**
         * Input Stream to reciving packages from typed client
         */
        private ObjectInputStream oin;

        public ClientConnection(Socket clientSocket) {
            this.clientSocket = clientSocket;
            try {
                oin = new ObjectInputStream(clientSocket.getInputStream());
            } catch (IOException e) {
                logger.error("An I/O error occured when creating the input stream.", e);
                e.printStackTrace();
            }
        }

        void closeClientsConnection() {
            //TODO remove client with specified id
            removeClientFromMap(1);
            try {
                clientSocket.close();
            } catch (final IOException e1) {
                e1.printStackTrace();
            }
        }

        /**
         * Reciving packages from specified client in a loop.
         */
        @Override
        public void run() {
            try {
                while (true) {
                    final Package event = (Package) oin.readObject();
                    //TODO set unique identifier of the sender in the event

                    packagesQueue.add(event);
                }
            } catch (IOException | ClassNotFoundException e) {
                // Broken connection
                //TODO - event connection broken for specified client - optional
                closeClientsConnection();
            }
        }
    }

}
