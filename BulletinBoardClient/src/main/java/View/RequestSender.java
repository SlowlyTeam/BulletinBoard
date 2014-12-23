package View;

import connection.Client;
import pl.slowly.team.common.data.Bulletin;
import pl.slowly.team.common.packets.helpers.Credentials;
import pl.slowly.team.common.packets.request.authorization.LogInRequest;
import pl.slowly.team.common.packets.request.connection.DisconnectFromServerRequest;
import pl.slowly.team.common.packets.request.data.*;

import java.io.IOException;
import java.util.List;

public class RequestSender {

    private Client client;

    public RequestSender() throws IOException {
//        this.client = new Client(null, 8081);
    }
//
//    public void sendTestRequests() throws IOException, InterruptedException {
//        if (connectToServer()) {
//            logIn("marek", "7875");
////            getCategories();
//            addBulletin();
////            deleteBulletin();
////            getBulletins(null);
////            getUserBulletins();
//        }
////        disconnectFromServer();
//    }


}
