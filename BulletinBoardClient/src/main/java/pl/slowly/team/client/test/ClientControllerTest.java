package pl.slowly.team.client.test;

import org.junit.Test;
import pl.slowly.team.common.packets.helpers.Credentials;
import pl.slowly.team.common.packets.helpers.ResponseStatus;
import pl.slowly.team.common.packets.request.authorization.LogInRequest;
import pl.slowly.team.common.packets.request.data.GetBulletinsRequest;
import pl.slowly.team.common.packets.request.data.GetCategoriesRequest;
import pl.slowly.team.common.packets.response.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientControllerTest extends BaseTest {

    public ClientControllerTest() throws IOException {
        super(new Socket("127.0.0.1", 8081));
    }

    @Test
    public void logInWithCorrectCredentials() throws Exception {
        Response logInResponse = connectAndLogIn();
        assert logInResponse.getResponseStatus().equals(ResponseStatus.AUTHORIZED);
    }

    @Test
    public void logInInvalidCredentials() throws Exception {
        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
        outputStream.writeObject(new LogInRequest(new Credentials("00000", "00000")));
        Response logInResponse = (LogInResponse) inputStream.readObject();
        assert logInResponse.getResponseStatus().equals(ResponseStatus.NOT_AUTHORIZED);
    }

    @Test
    public void executeRequestWithoutLoggingIn() throws Exception {
        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
        outputStream.writeObject(new GetCategoriesRequest());
        Response logInResponse = (NotAuthorizedResponse) inputStream.readObject();
        assert logInResponse.getResponseStatus().equals(ResponseStatus.NOT_AUTHORIZED);
    }

    @Test
    public void executeGetCategoriesRequest() throws Exception {
        connectAndLogIn();
        outputStream.writeObject(new GetCategoriesRequest());
        Response categoriesResponse = (GetCategoriesListResponse) inputStream.readObject();
        assert !categoriesResponse.getEntities().isEmpty();
    }

    @Test
    public void executeGetBulletinsRequest() throws Exception {
        connectAndLogIn();
        List<Integer> categoriesIds = new ArrayList<>();
        categoriesIds.add(1);
        outputStream.writeObject(new GetBulletinsRequest(categoriesIds, null));
        Response response = (GetBulletinsResponse) inputStream.readObject();
        assert !response.getEntities().isEmpty();
    }

}