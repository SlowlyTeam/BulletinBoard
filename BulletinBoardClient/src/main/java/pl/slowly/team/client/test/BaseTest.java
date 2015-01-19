package pl.slowly.team.client.test;

import pl.slowly.team.common.packets.helpers.Credentials;
import pl.slowly.team.common.packets.request.authorization.LogInRequest;
import pl.slowly.team.common.packets.response.LogInResponse;
import pl.slowly.team.common.packets.response.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class BaseTest {

    protected Socket socket;
    protected ObjectOutputStream outputStream;
    protected ObjectInputStream inputStream;

    public BaseTest(Socket socket) throws IOException {
       this.socket = socket;
    }

    public Response connectAndLogIn() throws Exception {
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());
        outputStream.writeObject(new LogInRequest(new Credentials("test", "test")));
        return (Response) inputStream.readObject();
    }

}
