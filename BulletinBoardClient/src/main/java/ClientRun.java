import pl.slowly.team.common.packages.helpers.Credentials;
import pl.slowly.team.common.packages.request.Request;
import pl.slowly.team.common.packages.request.authorization.LogInRequest;

import java.io.*;
import java.net.Socket;

/**
 * Created by Marek Majde on 2014-12-21.
 */
public class ClientRun {

    public static void main(final String[] args)
    {
        String host = null;
        try {
            Socket clientSocket = new Socket(host, 8081);
//            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());

            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                Request logIn = new LogInRequest(new Credentials("marek", userInput));
                oos.writeObject(logIn);
                System.out.println("wysłano");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
