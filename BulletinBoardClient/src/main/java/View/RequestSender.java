package View;

import connection.Client;
import pl.slowly.team.common.packages.data.Bulletin;
import pl.slowly.team.common.packages.helpers.Credentials;
import pl.slowly.team.common.packages.request.authorization.LogInRequest;
import pl.slowly.team.common.packages.request.data.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class RequestSender {

    private Client client;

    public RequestSender() throws IOException {
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        Client client = new Client(null, 8081);
    }

    public void sendTestRequests() throws IOException {
        if (client.connectToServer()) {
            logIn("marek", "7875");
            getCategories();
        }
    }

    public void logIn(String userName, String password) throws IOException {
        client.sendRequest(new LogInRequest(new Credentials(userName, password)));
    }

    public void addBulletin() throws IOException {
        client.sendRequest(new AddBulletinRequest(new Bulletin()));
    }

    public void deleteBulletin() throws IOException {
        client.sendRequest(new DeleteBulletinRequest(1));
    }

    public void getCategories() throws IOException {
        client.sendRequest(new GetCategoriesRequest());
    }

    public void getBulletins(List<Integer> bulletinsIds) throws IOException {
        client.sendRequest(new GetBulletinsRequest(bulletinsIds, null));
    }

    public void getUserBulletins() throws IOException {
        client.sendRequest(new GetMyBulletinsRequest());
    }


}
