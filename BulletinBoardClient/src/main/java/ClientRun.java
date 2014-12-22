import View.RequestSender;
import connection.Client;

import java.io.*;

public class ClientRun {

    public static void main(final String[] args)
    {
        try {
            RequestSender requestSender = new RequestSender();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
