import View.RequestSender;

import java.io.IOException;

public class ClientRun {

    public static void main(final String[] args)
    {
        try {
            RequestSender requestSender = new RequestSender();
//            requestSender.sendTestRequests();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
