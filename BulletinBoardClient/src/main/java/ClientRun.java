import pl.slowly.team.common.packages.helpers.Credentials;
import pl.slowly.team.common.packages.request.Request;
import pl.slowly.team.common.packages.request.authorization.LogInRequest;
import pl.slowly.team.common.packages.response.Response;

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
            ObjectInputStream oin = new ObjectInputStream(clientSocket.getInputStream());

            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                Request logIn = new LogInRequest(new Credentials("marek", userInput));
                oos.writeObject(logIn);
                System.out.println("wysłano");
                Response serverResponse = (Response) oin.readObject();
                System.out.println(serverResponse.getResponseStatus().toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}
